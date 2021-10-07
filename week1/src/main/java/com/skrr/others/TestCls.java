package com.skrr.others;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class TestCls {

    public static void main(String[] args) {
        String appPath = "file:/d:/app/";
        // 获取加载当前类的类加载器
        URLClassLoader urlClassLoader = (URLClassLoader) TestCls.class.getClassLoader();
        try {
            Method addURL = URLClassLoader.class.getDeclaredMethod("addURL");
            addURL.setAccessible(true);
            URL url = new URL(appPath);
            addURL.invoke(urlClassLoader, url);
            Class.forName("jvm.Hello"); // 效果跟Class.forName("jvm.Hello").newInstance()一样
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}