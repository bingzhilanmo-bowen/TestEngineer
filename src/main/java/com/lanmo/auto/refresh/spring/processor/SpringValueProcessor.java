package com.lanmo.auto.refresh.spring.processor;


import com.lanmo.auto.refresh.spring.property.PlaceholderHelper;
import com.lanmo.auto.refresh.spring.property.SpringValue;
import com.lanmo.auto.refresh.spring.reg.SpringValueRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
public class SpringValueProcessor extends BaseProcessor implements BeanFactoryAware {


    private final PlaceholderHelper placeholderHelper;

    private final SpringValueRegistry springValueRegistry;

    private BeanFactory beanFactory;

    public SpringValueProcessor(){
        this.placeholderHelper = PlaceholderHelper.getInstance();
        this.springValueRegistry = SpringValueRegistry.getInstance();
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
            System.out.println("postProcessBeforeInitialization......");
            super.postProcessBeforeInitialization(bean, beanName);
             // processBeanPropertyValues(bean, beanName);
            return bean;
    }


    @Override
    protected void processField(Object bean, String beanName, Field field) {
        // register @Value on field
        Value value = field.getAnnotation(Value.class);
        if (value == null) {
            return;
        }
        Set<String> keys = placeholderHelper.extractPlaceholderKeys(value.value());

        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            SpringValue springValue = new SpringValue(key, value.value(), bean, beanName, field, false);
            springValueRegistry.register(beanFactory, key, springValue);
            log.debug("Monitoring {}", springValue);
        }
    }

    @Override
    protected void processMethod(Object bean, String beanName, Method method) {
        //register @Value on method
        Value value = method.getAnnotation(Value.class);
        if (value == null) {
            return;
        }
        //skip Configuration bean methods
        if (method.getAnnotation(Bean.class) != null) {
            return;
        }
        if (method.getParameterTypes().length != 1) {
            log.error("Ignore @Value setter {}.{}, expecting 1 parameter, actual {} parameters",
                    bean.getClass().getName(), method.getName(), method.getParameterTypes().length);
            return;
        }

        Set<String> keys = placeholderHelper.extractPlaceholderKeys(value.value());

        if (keys.isEmpty()) {
            return;
        }

        for (String key : keys) {
            SpringValue springValue = new SpringValue(key, value.value(), bean, beanName, method, false);
            springValueRegistry.register(beanFactory, key, springValue);
            log.info("Monitoring {}", springValue);
        }
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
