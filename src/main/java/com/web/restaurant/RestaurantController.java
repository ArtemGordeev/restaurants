package com.web.restaurant;

import com.model.Restaurant;
import com.repository.RestaurantRepository;
import com.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import static com.util.ValidationUtil.assureIdConsistent;
import static com.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    private final static Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("restaurants", restaurantRepository.getAll());
        log.info("get all restaurants");
        return "restaurants";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        restaurantRepository.delete(id);
        log.info("delete restaurant {}", id);
        return "redirect:/restaurants";
    }

    @GetMapping("/update/{restaurantId}")
    public String update(@PathVariable("restaurantId") int restaurantId, Model model) {
        final Restaurant restaurant = restaurantRepository.get(restaurantId);
        model.addAttribute("restaurant", restaurant);
        return "/restaurantForm";
    }

    @PostMapping("/update/{restaurantId}")
    public String update(HttpServletRequest request) throws IOException {
        Restaurant restaurant = getRestaurant(request);
        int id = getId(request);
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(restaurant, id);
        log.info("update {}", restaurant);
        restaurantRepository.save(restaurant);
        return "redirect:/restaurants";
    }

    @GetMapping("/create")
    public String create(Model model) {
        final Restaurant restaurant = new Restaurant();
        model.addAttribute("restaurant", restaurant);
        return "/restaurantForm";
    }

    @PostMapping("/create")
    public String create(HttpServletRequest request) throws IOException {
        Restaurant restaurant = getRestaurant(request);
        int userId = SecurityUtil.authUserId();
        checkNew(restaurant);
        log.info("create {}", restaurant);
        restaurantRepository.save(restaurant);
        return "redirect:/restaurants";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Restaurant getRestaurant(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        return new Restaurant(
                request.getParameter("title")
                //Integer.parseInt(request.getParameter("votes"))
        );
    }
}
