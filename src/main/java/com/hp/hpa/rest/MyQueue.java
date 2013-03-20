package com.hp.hpa.rest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: belozovs
 * Date: 3/18/13
 * Description
 */
@Component
public class MyQueue {

    private String myName="empty";

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private int delay=0;
    private Queue<DeferredResult<String>> queue;

    @PostConstruct
    private void init(){
        queue=new ConcurrentLinkedQueue<DeferredResult<String>>();
    }

    public String getMyName() {
        return myName;
    }
    public void setMyName(String myName) {
        this.myName = myName;
    }
    public Queue<DeferredResult<String>> getQueue() {
        return queue;
    }
    public void setQueue(Queue<DeferredResult<String>> queue) {
        this.queue = queue;
    }

    public void put(DeferredResult<String> result){
        queue.add(result);
    }

    @Scheduled(fixedRate = 1000)
    public void setResult(){
        if(!queue.isEmpty()){
            System.out.println(Thread.currentThread().getId()+": The queue is NOT empty, setting result to "+myName);
            DeferredResult<String> result=queue.poll();
            System.out.println(Thread.currentThread().getId()+": Waiting for "+ delay+" msec");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println(System.currentTimeMillis()+"   "+Thread.currentThread().getId()+": Returning result");
            result.setResult(myName);
            System.out.println(System.currentTimeMillis()+"   "+Thread.currentThread().getId()+": Returned result");
        } else {
            System.out.println(Thread.currentThread().getId()+": The queue is empty");
        }
    }

}
