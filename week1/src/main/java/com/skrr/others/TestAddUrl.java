package com.skrr.others;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class TestAddUrl {

    public static void main(String[] args) throws Exception {

        // 获取加载当前类的类加载器
        URLClassLoader classLoader = (URLClassLoader) TestAddUrl.class.getClassLoader();
        String dir = "/Users/wuliang/Documents/geek/code/javaCourse223/week1/src/main/resources";
        // URLClassLoader的addURL是protected，所以只能反射
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        // 调用addURL，把目标class加入url列表
        method.invoke(classLoader, new File(dir).toURL());

        // 使用当前这个classLoader加载目标类Hello
        Class klass = Class.forName("com.skrr.others.Hello", true, classLoader);
        Object obj = klass.newInstance();
        Method hello = klass.getDeclaredMethod("show");
        hello.invoke(obj);
    }

}
