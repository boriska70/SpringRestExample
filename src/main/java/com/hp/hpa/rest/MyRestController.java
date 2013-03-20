package com.hp.hpa.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: belozovs
 * Date: 3/13/13
 * Description
 */


@Controller
@RequestMapping(value = "/MyData")
public class MyRestController {

    @Autowired
    MyQueue myQueue;

    @RequestMapping(value = "/getName/{name}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getName(@PathVariable String name) {
        String myName = new MyData(name).getName();
        return myName;
    }

    @RequestMapping(value = "/getNameDelay/{name}/{seconds}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getNameDelay(@PathVariable String name, @PathVariable("seconds") int delay) {
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            System.out.println("Bad error handling fir sleep...");
        }
        String myName = new MyData(name).getName();
        return myName + " (back after " + delay + " seconds delay)";
    }

    @RequestMapping(value = "/getNameAsync/{name}/{seconds}", method = RequestMethod.GET)
    public
    @ResponseBody
    Callable<String> getNameAsync(@PathVariable final String name, final @PathVariable("seconds") int delay) {
        System.out.println(Thread.currentThread().getId() + ": Entered to async method !!!!!!!!!!!!!!!");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(delay * 1000);
                String myName = new MyData(name).getName();
                System.out.println(Thread.currentThread().getId() + ": We waited enough, finishing...");
                return myName + " (back after " + delay + " seconds delay)";
            }
        };
        System.out.println(Thread.currentThread().getId() + ": Controller code has finished, waiting for DeferredResult");
        return result;
    }

    @RequestMapping(value = "/getNameDeferred/{name}/{seconds}", method = RequestMethod.GET)
    public
    @ResponseBody
    DeferredResult<String> getNamedDeferred(@PathVariable String name, @PathVariable("seconds") int delay) {
        System.out.println(Thread.currentThread().getId() + ": Entered to deferred method !!!!!!!!!!!!!!!");
        DeferredResult<String> result = new DeferredResult<String>();
        myQueue.setMyName(name);
        myQueue.setDelay(delay * 1000);
        System.out.println(Thread.currentThread().getId() + ": Going to put data to queue");
        myQueue.put(result);
        System.out.println(System.currentTimeMillis() + "   " + Thread.currentThread().getId() + ": Waiting for result");
        return result;
    }

    @RequestMapping(value = "/getNameDelay", method = RequestMethod.GET, headers = {"content-type=application/json","content-type=application/xml"})
    public @ResponseBody String getNameDelayQueryParam(@RequestParam("name") String name, @RequestParam("seconds") long delay) {
        System.out.println("Name in query param: "+name);
        System.out.println("Delay in query param (seconds): "+delay);
        try {
            Thread.sleep(delay*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return name + " (back after " + delay + " seconds delay)";
    }

}
