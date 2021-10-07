package com.skrr.others;

import java.lang.reflect.Method;
import java.util.Base64;

/**
 * 把 com.skrr.others.Hello 的.class文件base64成一段文本，在自定义类加载器中，通过解析这段base64文本来加载 com.skrr.others.Hello
 */
public class Base64ClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> aClass = new Base64ClassLoader().findClass("com.skrr.others.Hello");
        Object o = aClass.newInstance();
        Method show = aClass.getMethod("show");
        show.invoke(o);
    }

    @Override
    protected Class<?> findClass(String name) {
        String base64Str = "yv66vgAAADQAHwoABwAQCQARABIIABMKABQAFQgAFgcAFwcAGAEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAARzaG93AQAIPGNsaW5pdD4BAApTb3VyY2VGaWxlAQAKSGVsbG8uamF2YQwACAAJBwAZDAAaABsBAA5zaG93KCkgaW52b2tlZAcAHAwAHQAeAQAZaGVsbG8gY2xhc3MgaGFzIGluaXRpYWxlZAEAFWNvbS9za3JyL290aGVycy9IZWxsbwEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xhbmcvU3lzdGVtAQADb3V0AQAVTGphdmEvaW8vUHJpbnRTdHJlYW07AQATamF2YS9pby9QcmludFN0cmVhbQEAB3ByaW50bG4BABUoTGphdmEvbGFuZy9TdHJpbmc7KVYAIQAGAAcAAAAAAAMAAQAIAAkAAQAKAAAAHQABAAEAAAAFKrcAAbEAAAABAAsAAAAGAAEAAAADAAEADAAJAAEACgAAACUAAgABAAAACbIAAhIDtgAEsQAAAAEACwAAAAoAAgAAAAoACAALAAgADQAJAAEACgAAACUAAgAAAAAACbIAAhIFtgAEsQAAAAEACwAAAAoAAgAAAAYACAAHAAEADgAAAAIADw==";
        byte[] bytes = decode(base64Str);
        return defineClass(name,bytes,0,bytes.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
