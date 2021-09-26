package com.skrr;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class OKHttpClientTest {

    public static OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws Exception {

        String url = "http://localhost:8801";
        String text = OKHttpClientTest.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);
        OKHttpClientTest.client = null;
    }

    public static String getAsString(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            ResponseBody body = response.body();
            return body.string();
        }
    }
}