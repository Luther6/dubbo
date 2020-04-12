/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.configcenter.support.zookeeper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.config.configcenter.ConfigurationListener;
import org.apache.dubbo.common.config.configcenter.DynamicConfiguration;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.dubbo.remoting.zookeeper.ZookeeperTransporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptySortedSet;
import static java.util.Collections.unmodifiableSortedSet;
import static org.apache.dubbo.common.config.configcenter.Constants.CONFIG_NAMESPACE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PATH_SEPARATOR;
import static org.apache.dubbo.common.utils.CollectionUtils.isEmpty;
import static org.apache.dubbo.common.utils.StringUtils.EMPTY_STRING;

/**
 *
 */
public class ZookeeperDynamicConfiguration implements DynamicConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperDynamicConfiguration.class);

    private Executor executor;
    // The final root path would be: /configRootPath/"config"
    private String rootPath;
    private final ZookeeperClient zkClient;
    private CountDownLatch initializedLatch;

    private CacheListener cacheListener;
    private URL url;


    ZookeeperDynamicConfiguration(URL url, ZookeeperTransporter zookeeperTransporter) {
        this.url = url;
        //定义rootPath:/dubbo/config
        rootPath = PATH_SEPARATOR + url.getParameter(CONFIG_NAMESPACE_KEY, DEFAULT_GROUP) + "/config";
        //设置阀门
        initializedLatch = new CountDownLatch(1);
        //初始化监听器
        this.cacheListener = new CacheListener(rootPath, initializedLatch);
        //获取线程服务
        this.executor = Executors.newFixedThreadPool(1, new NamedThreadFactory(this.getClass().getSimpleName(), true));
        //连接并缓存该链接
        zkClient = zookeeperTransporter.connect(url);
        //注册监听器
        zkClient.addDataListener(rootPath, cacheListener, executor);
        try {
            // Wait for connection 得到初始化超时时间如果没有配置的话默认为5000
            long timeout = url.getParameter("init.timeout", 5000);
            boolean isCountDown = this.initializedLatch.await(timeout, TimeUnit.MILLISECONDS);//当zk服务端连接建立完毕,后调用到监听器执行countDown()
            if (!isCountDown) {
                throw new IllegalStateException("Failed to receive INITIALIZED event from zookeeper, pls. check if url "
                        + url + " is correct");
            }
        } catch (InterruptedException e) {
            logger.warn("Failed to build local cache for config center (zookeeper)." + url);
        }
    }

    /**
     * @param key e.g., {service}.configurators, {service}.tagrouters, {group}.dubbo.properties
     * @return
     */
    @Override
    public Object getInternalProperty(String key) {
        return zkClient.getContent(key);
    }

    /**
     * For service governance, multi group is not supported by this implementation. So group is not used at present.
     */
    @Override
    public void addListener(String key, String group, ConfigurationListener listener) {
        cacheListener.addListener(getPathKey(group, key), listener);
    }

    @Override
    public void removeListener(String key, String group, ConfigurationListener listener) {
        cacheListener.removeListener(getPathKey(group, key), listener);
    }

    @Override
    public String getConfig(String key, String group, long timeout) throws IllegalStateException {
        return (String) getInternalProperty(getPathKey(group, key));
    }

    @Override
    public boolean publishConfig(String key, String group, String content) {
        String path = getPathKey(group, key);
        zkClient.create(path, content, false);
        return true;
    }

    @Override
    public SortedSet<String> getConfigKeys(String group) {
        String path = getPathKey(group, EMPTY_STRING);
        List<String> nodes = zkClient.getChildren(path);
        return isEmpty(nodes) ? emptySortedSet() : unmodifiableSortedSet(new TreeSet<>(nodes));
    }

    private String buildPath(String group) {
        String actualGroup = StringUtils.isEmpty(group) ? DEFAULT_GROUP : group;
        return rootPath + PATH_SEPARATOR + actualGroup;
    }

    private String getPathKey(String group, String key) {
        if (StringUtils.isEmpty(key)) {
            return buildPath(group);
        }
        return buildPath(group) + PATH_SEPARATOR + key;
    }

}
