package com.spring.customapp.thread;

import com.spring.customapp.constants.AppConstants;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	private static ThreadPool instance =null;
	private static ThreadPoolExecutor threadPoolExecutor =null;

	private static LinkedBlockingQueue<Runnable> threadPoolExecutorBlockingQueue = new LinkedBlockingQueue<>();

	private ThreadPool(){}

	public static ThreadPool getInstance() {
		if(instance == null){
			synchronized (ThreadPool.class) {
					createThreadPool();
					instance = new ThreadPool();
			}
		}
		return instance;
	}

	private static void createThreadPool(){
		try{
			threadPoolExecutor =new ThreadPoolExecutor(AppConstants.THREAD_POOL_CORE_SIZE, AppConstants.THREAD_POOL_MAX_SIZE,0L, TimeUnit.MILLISECONDS,threadPoolExecutorBlockingQueue);
		}catch (Exception e) {
			System.out.println("ERROR CREATING THREAD POOL:: "+e.getMessage());
		}
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public static void shutdown(){
		try{
			if(threadPoolExecutor !=null) {
				threadPoolExecutor.shutdown();
				if(!threadPoolExecutor.awaitTermination(AppConstants.THREAD_POOL_AWAIT_TERMINATION_TIME, TimeUnit.SECONDS))
					threadPoolExecutor.shutdownNow();
				System.out.println("THREAD POOL IS SHUTDOWN");
			}
		}catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}catch (Exception e) {
			System.out.println("EXCEPTION WHILE SHUTTING DOWN THREAD POOL:: "+e.getMessage());
		}
	}
}
