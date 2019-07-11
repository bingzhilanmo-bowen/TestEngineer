package com.lanmo.auto.refresh.controller;

import com.lanmo.auto.refresh.annotation.LoginUser;
import com.lanmo.auto.refresh.domain.User;
import com.lanmo.auto.refresh.spring.property.PlaceholderHelper;
import com.lanmo.auto.refresh.spring.property.SpringValue;
import com.lanmo.auto.refresh.spring.reg.SpringValueRegistry;
import com.lanmo.my.starter.MyStartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.concurrent.ForkJoinPool;

@RestController
public class TestController {

    @Value("${test.pro}")
    private String pro;

    @Autowired
    private MyStartService startService;

    @GetMapping("/")
    public String hello(){
        return pro;
    }


    @GetMapping("/change")
    public void change(){
        Collection<SpringValue> targetValues = SpringValueRegistry.getInstance().get(null, "test.pro");
        for (SpringValue val : targetValues) {
            updateSpringValue(val);
        }
    }

    private void updateSpringValue(SpringValue springValue) {
        try {
            Object value = "new dev 1616";
            springValue.update(value);
        } catch (Throwable ex) {
        }
    }


    @GetMapping("/user")
    public User getUser(){
       User user = User.builder().name("hydxin")
                .age(18)
                .phone("15918561583").build();

        return user;
    }

    @GetMapping("/login")
    public User login(@LoginUser User user){

        return user;
    }

    @RequestMapping("/test/long/poll")
    public DeferredResult<String> LongPollingTest(){
        DeferredResult<String> output = new DeferredResult<String>(6000l);

        ForkJoinPool.commonPool().submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            output.setResult("Are You Ok!!");
        });

        return output;
    }

    @GetMapping("/auto/cfg/test")
    public String autoCfgTest(String cn){
        System.out.println(startService.getMyInfo());
        return startService.getChildInfo(cn);
    }

}
