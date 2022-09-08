package com.redis.pusub.thread;

public class PubSubThread extends Thread {

    private String message;

    public PubSubThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
