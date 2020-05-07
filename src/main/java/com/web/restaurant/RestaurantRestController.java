package com.web.restaurant;

import com.model.Restaurant;
import com.model.Role;
import com.model.User;
import com.repository.RestaurantRepository;
import com.repository.UserRepository;
import com.to.RestaurantTo;
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
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";

    private final static Logger log = LoggerFactory.getLogger(RestaurantRestController.class);

    private RestaurantRepository restaurantRepository;

    private UserRepository userRepository;

    public RestaurantRestController(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantRepository.get(restaurantId);
        log.info("get {}", restaurantId);
        return restaurant;
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("delete {}", restaurantId);
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            restaurantRepository.delete(restaurantId);
        }
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return restaurantRepository.getAll();
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("update");
            restaurantRepository.save(restaurant);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated @RequestBody Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("create");
            Restaurant created = restaurantRepository.save(restaurant);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return null;
    }

    @GetMapping("/winner")
    public RestaurantTo getWinner() {
        log.info("getWinner");
        return restaurantRepository.winner();
    }
}