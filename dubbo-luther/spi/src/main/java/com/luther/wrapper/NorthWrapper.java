package com.luther.wrapper;

import com.luther.api.PersonInterface;
import org.apache.dubbo.common.URL;
//Wrapper类定义必须要有一个public构造函数并且有一个参数为spi标注的接口类型
public class NorthWrapper implements PersonInterface {
    private PersonInterface personInterface;



    public NorthWrapper(PersonInterface personInterface) {
        this.personInterface = personInterface;

    }

    @Override
    public void getCountry(URL url) {
        System.out.println("north before");
        personInterface.getCountry(url);
        System.out.println("north after");
    }
}
