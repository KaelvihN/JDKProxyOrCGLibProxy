package com.example.springproxy;


import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author : KaelvihN
 * @date : 2023/8/24 1:30
 */
public class SpringProxyDemo {
    interface I1 {
        void foo();

        void bar();
    }

    static class Target1 implements I1 {
        @Override
        public void foo() {
            System.out.println("Target1 foo");
        }

        @Override
        public void bar() {
            System.out.println("Target1 bar");
        }
    }

    static class Target2 {
        public void foo() {
            System.out.println("Target2 foo");
        }

        public void bar() {
            System.out.println("Target2 bar");
        }
    }

    public static DefaultPointcutAdvisor getAdvisor() {
        //创建切点(任何返回值，无参数，foo()方法)
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo())");
        //创建通知
        MethodInterceptor advice = invocation -> {
            System.out.println("Before...");
            Object result = invocation.proceed();
            System.out.println("After...");
            return result;
        };
        //创建切面
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
        return advisor;
    }

    public static void test0() {
        DefaultPointcutAdvisor advisor = getAdvisor();
        //创建代理
        Target1 target1 = new Target1();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target1);
        factory.addAdvisor(advisor);
        //设置Interfaces
        factory.setInterfaces(target1.getClass().getInterfaces());

        I1 proxy = (I1) factory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();
    }

    public static void test1() {
        DefaultPointcutAdvisor advisor = getAdvisor();
        //创建代理
        Target1 target1 = new Target1();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target1);
        factory.addAdvisor(advisor);
        //设置Interfaces
        factory.setInterfaces(target1.getClass().getInterfaces());
        //设置ProxyTargetClass
        factory.setProxyTargetClass(true);

        I1 proxy = (I1) factory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();
    }

    public static void test2() {
        DefaultPointcutAdvisor advisor = getAdvisor();
        //创建代理
        Target2 target2 = new Target2();
        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(target2);
        factory.addAdvisor(advisor);
        //设置Interfaces
        factory.setInterfaces(target2.getClass().getInterfaces());

        Target2 proxy = (Target2) factory.getProxy();
        System.out.println(proxy.getClass());
        proxy.foo();
        proxy.bar();
    }

    public static void main(String[] args) {
//        test0();
//        test1();
        test2();
    }
}
