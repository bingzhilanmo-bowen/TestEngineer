package com.lanmo.auto.refresh.spring.config;

import com.lanmo.auto.refresh.spring.spi.ConfigPropertySourcesProcessorHelper;
import com.lanmo.auto.refresh.spring.spi.DefaultConfigPropertySourcesProcessorHelper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ConfigPropertySourcesProcessor extends PropertySourcesProcessor implements BeanDefinitionRegistryPostProcessor {

    private ConfigPropertySourcesProcessorHelper helper = new DefaultConfigPropertySourcesProcessorHelper();
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        helper.postProcessBeanDefinitionRegistry(registry);
    }

}