package com.web.restaurant;

import com.View;
import com.model.Dish;
import com.service.DishService;
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
    static final String REST_URL = "/rest";

    private final static Logger log = LoggerFactory.getLogger(DishRestController.class);

    private DishService dishService;

    public DishRestController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/restaurants/{restaurantId}/menus/{menuId}/dishes/{dishId}")
    public Dish get(@PathVariable int dishId) {
        Dish dish = dishService.get(dishId);
        log.info("get {}", dish);
        return dish;
    }

    @DeleteMapping("/admin/restaurants/{restaurantId}/menus/{menuId}/dishes/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int dishId) {
        log.info("delete {}", dishId);
        dishService.delete(dishId);
    }

    @GetMapping("/restaurants/{restaurantId}/menus/{menuId}/dishes")
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll");
        return dishService.getAll(menuId);
    }

    @PutMapping(value = "/admin/restaurants/{restaurantId}/menus/{menuId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Dish dish,
                       @PathVariable int menuId) {
        log.info("update");
        dishService.save(dish, menuId);
    }

    @PostMapping(value = "/admin/restaurants/{restaurantId}/menus/{menuId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated @RequestBody Dish dish,
                                                   //@PathVariable int restaurantId,
                                                   @PathVariable int menuId) {
        log.info("create");
        Dish created = dishService.save(dish, menuId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}