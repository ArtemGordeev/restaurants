package com.web.restaurant;

import com.View;
import com.model.Dish;
import com.model.Role;
import com.model.User;
import com.repository.DishRepository;
import com.repository.UserRepository;
import com.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    static final String REST_URL = "/rest/restaurants";

    private final static Logger log = LoggerFactory.getLogger(DishRestController.class);

    private DishRepository dishRepository;

    private UserRepository userRepository;

    public DishRestController(DishRepository dishRepository, UserRepository userRepository) {
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{restaurantId}/menus/{menuId}/dishes/{dishId}")
    public Dish get(@PathVariable int dishId) {
        Dish dish = dishRepository.get(dishId);
        log.info("get {}", dish);
        return dish;
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int dishId) {
        log.info("delete {}", dishId);
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            dishRepository.delete(dishId);
        }
    }

    @GetMapping("/{restaurantId}/menus/{menuId}/dishes")
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll");
        List<Dish> all = dishRepository.getAll(menuId);
        return all;
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Dish dish,
                       @PathVariable int menuId) {
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("update");
            dishRepository.save(dish, menuId);
        }
    }

    @PostMapping(value = "/{restaurantId}/menus/{menuId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated @RequestBody Dish dish,
                                                   //@PathVariable int restaurantId,
                                                   @PathVariable int menuId) {
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("create");
            Dish created = dishRepository.save(dish, menuId);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return null;
    }
}