package com.web.restaurant;

import com.model.Dish;
import com.repository.DishRepository;
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
public class DishController {

    private final static Logger log = LoggerFactory.getLogger(DishController.class);

    private DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping("/{restaurantId}/menus/{menuId}/dishes")
    public String get(@PathVariable("menuId") int menuId,
                      Model model) {
        model.addAttribute("dishes", dishRepository.getAll(menuId));
        return "dishes";
    }

    @GetMapping("/{restaurantId}/menus/{menuId}/dishes/delete/{dishId}")
    public String delete(@PathVariable("restaurantId") int restaurantId,
                         @PathVariable("menuId") int menuId,
                         @PathVariable("dishId") int dishId) {
        dishRepository.delete(dishId);
        return "redirect:/restaurants/" + restaurantId + "/menus/" + menuId + "/dishes";
    }

    @GetMapping("/{restaurantId}/menus/{menuId}/dishes/update/{dishId}")
    public String update(@PathVariable("dishId") int dishId,
                         Model model) {
        final Dish dish = dishRepository.get(dishId);
        model.addAttribute("dish", dish);
        return "/dishForm";
    }

    @PostMapping("/{restaurantId}/menus/{menuId}/dishes/update/{dishId}")
    public String update(@PathVariable("restaurantId") int restaurantId,
                         @PathVariable("menuId") int menuId,
                         HttpServletRequest request) throws IOException {
        Dish dish = getDish(request);
        int id = getId(request);
        assureIdConsistent(dish, id);

        log.info("update {}", dish);
        dishRepository.save(dish, menuId);
        return "redirect:/restaurants/" + restaurantId + "/menus/" + menuId + "/dishes";
    }

    @GetMapping("/{restaurantId}/menus/{menuId}/dishes/create")
    public String create(Model model) {
        final Dish dish = new Dish();
        model.addAttribute("dish", dish);
        return "/dishForm";
    }

    @PostMapping("/{restaurantId}/menus/{menuId}/dishes/create")
    public String create(@PathVariable("restaurantId") int restaurantId,
                         @PathVariable("menuId") int menuId,
                         HttpServletRequest request) throws IOException {
        Dish dish = getDish(request);
        checkNew(dish);
        log.info("create {}", dish);
        dishRepository.save(dish, menuId);
        return "redirect:/restaurants/" + restaurantId + "/menus/" + menuId + "/dishes";
    }

    private Dish getDish(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        return new Dish(request.getParameter("description"),
                Integer.parseInt(request.getParameter("price")));
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
