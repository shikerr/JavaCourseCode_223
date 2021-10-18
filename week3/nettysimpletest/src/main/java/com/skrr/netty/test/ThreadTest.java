package com.skrr.netty.test;

public class ThreadTest {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(1000);
                System.out.println("thread_name = " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.setName("my thread");
        thread.setDaemon(false);
        thread.start();
    }
}
