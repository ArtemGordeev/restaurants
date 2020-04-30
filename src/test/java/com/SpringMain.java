package com;

import com.model.Dish;
import com.model.User;
import com.repository.DishRepositoryImpl;
import com.repository.RestaurantRepositoryImpl;
import com.repository.UserRepositoryImpl;
import com.to.RestaurantTo;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringMain {



    public static void main(String[] args) {
        User user = new User();
        user.setName("Max");
        Dish meal1 = new Dish();
        meal1.setDescription("Сыр");


        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring-db.xml");
        //Arrays.asList(appCtx.getBeanDefinitionNames()).forEach(System.out::println);
        UserRepositoryImpl userRepository = appCtx.getBean(UserRepositoryImpl.class);
        DishRepositoryImpl dishRepository = appCtx.getBean(DishRepositoryImpl.class);
        RestaurantRepositoryImpl restaurantRepository = appCtx.getBean(RestaurantRepositoryImpl.class);
        userRepository.save(user);
        List<RestaurantTo> all = restaurantRepository.getAll();
        all.forEach(System.out::println);
        appCtx.close();
    }

}
