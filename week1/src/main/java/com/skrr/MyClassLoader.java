package com.skrr;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        MyClassLoader loader = new MyClassLoader();
        Class<?> clazz = loader.loadClass("Hello");
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        Method helloMethod = clazz.getMethod("hello");
        Object obj = clazz.getDeclaredConstructor().newInstance();
        helloMethod.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(name + ".xlass");
        try {
            int available = inputStream.available();
            byte[] bytes = new byte[available];
            inputStream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                byte tempByte = bytes[i];
                byte newByte = (byte) (255 - tempByte);
                bytes[i] = newByte;
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
