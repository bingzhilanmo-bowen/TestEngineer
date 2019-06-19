package com.lanmo.auto.refresh.spring.spi;

import com.lanmo.auto.refresh.spring.Ordered;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

public interface ConfigPropertySourcesProcessorHelper extends Ordered {

  void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;
}
