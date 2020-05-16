package com.web.restaurant;

import com.model.Restaurant;
import com.service.RestaurantService;
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
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest";

    private final static Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    private RestaurantService restaurantService;

    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        log.info("get {}", restaurantId);
        return restaurant;
    }

    @DeleteMapping("/admin/restaurants/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("delete {}", restaurantId);
        restaurantService.delete(restaurantId);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    @PutMapping(value = "/admin/restaurants/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Restaurant restaurant) {
        log.info("update");
        restaurantService.save(restaurant);
    }

    @PostMapping(value = "/admin/restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated @RequestBody Restaurant restaurant) {
        log.info("create");
        Restaurant created = restaurantService.save(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/restaurants/today")
    public List<Restaurant> getAllWithTodayMenu() {
        return restaurantService.getAllWithTodayMenu();
    }
}