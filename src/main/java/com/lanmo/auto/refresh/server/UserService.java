package com.lanmo.auto.refresh.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class UserService {


    public boolean hidePhone(){

       return ThreadLocalRandom.current().nextBoolean();
    }


}
