package com.lhf.test.lhfandroidutils;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**   
 * @Title: SingleThreadPool.java 
 * @author: LHF
 * @date: 2015年6月10日 上午10:10:13 
 * @description:
 *
 */
public class SingleThreadPool {
	private static SingleThreadPool singleThreadPool = new SingleThreadPool();
	private ThreadPoolExecutor executor;
	private SingleThreadPool() {
	}
	public static SingleThreadPool getIntance() {
		return singleThreadPool;
	}
	
	public void creatThreadPool() {
//		executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<Runnable>(5));
		
		executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
//		Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池
//		Executors.newFixedThreadPool(int);    //创建固定容量大小的缓冲池
		
//		 for(int i=0;i<15;i++){
//             MyTask myTask = new MyTask(i);
//             executor.execute(myTask);
//             System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
//             executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
//         }
	}
	
	public void executeThread(Runnable myTask) {
		executor.execute(myTask);
	}
	
	public int getThreadSize(){
		return executor.getQueue().size();
	}
	
	
	public void shutDownThreadPool() {
         executor.shutdown();
	}
	
}
