package com.xgl.test;
/**
 * SynchronousQueue  线程一对一通讯
 */

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {
    private static final  SynchronousQueue queue = new SynchronousQueue();
    public static void main(String[] args) {
        new Thread(()->{
              for(int i =0 ;i<5 ; i++){
                  System.out.println("put start : "+i);
                  try {
                      queue.put(i);
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
                  System.out.println("put end : "+i);
              }
          }
       ).start();
        new Thread(()->{
            for(int i =0 ;i<5 ; i++){
                System.out.println("take start : "+i);
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("take end : "+i);
            }
        }
        ).start();
    }
}
