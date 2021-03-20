package com.kit.dorm.IocContainerTest;

import com.kit.dorm.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.assertj.core.api.Assert;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IocContainerTest {
    @Test
    void findApplicationBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames){
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            System.out.println(beanDefinitionName+"___"+beanDefinition);
        }
    }

    @Test
    void SingletonTest(){
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);
        assertThat(instance1).isSameAs(instance2);
    }

}
