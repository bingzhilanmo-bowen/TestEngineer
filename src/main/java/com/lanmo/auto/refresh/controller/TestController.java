package com.lanmo.auto.refresh.controller;

import com.lanmo.auto.refresh.annotation.LoginUser;
import com.lanmo.auto.refresh.domain.User;
import com.lanmo.auto.refresh.spring.property.PlaceholderHelper;
import com.lanmo.auto.refresh.spring.property.SpringValue;
import com.lanmo.auto.refresh.spring.reg.SpringValueRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class TestController {

    @Value("${test.pro}")
    private String pro;

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

}
