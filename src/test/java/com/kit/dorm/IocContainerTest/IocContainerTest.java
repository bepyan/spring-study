package com.kit.dorm.IocContainerTest;

import com.kit.dorm.AppConfig;
import com.kit.dorm.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.assertj.core.api.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    @Test
    void ReflectionSingletonTest() throws Exception{
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        System.out.println("instance1 = " + instance1);
        System.out.println("instance2 = " + instance2);

        Class<Singleton> singletonClass = Singleton.class;
        Constructor<Singleton> declaredConstructor = singletonClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);

        Singleton newSingleton = declaredConstructor.newInstance();
        System.out.println("newSingleton = " + newSingleton);

        assertThat(newSingleton).isNotSameAs(instance1);
        assertThat(newSingleton).isNotSameAs(instance2);
        // 결론: 새로 생성한 싱글톤이 기존 인스턴스와 다르다
    }

    @Test
    void SpringSingletonTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        assertThat(memberService1).isSameAs(memberService2);
    }

    @Test
    void ThreadNotSafetyTest(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonConfig.class);

        DeliveryService sharedService1 = ac.getBean("sharedService", DeliveryService.class);
        sharedService1.deliveryStart("디지털관");

        DeliveryService sharedService2 = ac.getBean("sharedService", DeliveryService.class);
        sharedService2.deliveryStart("글로벌관");

        System.out.println("sharedService1 = " + sharedService1.getAddress());

    }
}
