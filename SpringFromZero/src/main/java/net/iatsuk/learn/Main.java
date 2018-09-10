package net.iatsuk.learn;

import org.springframework.beans.factory.BeanFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException, URISyntaxException,
            InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try (BeanFactory beanFactory = new BeanFactory()) {
            beanFactory.addPostProcessor(new TracePostProcessor());
            beanFactory.addPostProcessor(new PostConstructPostProcessor());
            beanFactory.instantiate("net.iatsuk.learn");

            ProductService productService = (ProductService) beanFactory.getBean("productService");
            System.out.println(productService);

            beanFactory.populateProperties();
            PromotionsService promotionsService = productService.getPromotionsService();
            System.out.println(promotionsService);

            beanFactory.injectBeanNames();
            System.out.println(promotionsService.getBeanName());
            beanFactory.injectBeanFactories();
            System.out.println(beanFactory == productService.getBeanFactory());

            beanFactory.initializeBeans();
        }
    }
}
