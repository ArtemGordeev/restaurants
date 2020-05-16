package com.web.restaurant;

import com.View;
import com.model.Menu;
import com.service.MenuService;
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
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    static final String REST_URL = "/rest";

    private final static Logger log = LoggerFactory.getLogger(MenuRestController.class);

    private MenuService menuService;

    public MenuRestController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/restaurants/{restaurantId}/menus/{menuId}")
    public Menu get(@PathVariable int menuId) {
        Menu menu = menuService.get(menuId);
        log.info("get {}", menu);
        return menu;
    }

    @DeleteMapping("/admin/restaurants/{restaurantId}/menus/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId) {
        log.info("delete {}", menuId);
        menuService.delete(menuId);
    }

    @GetMapping("/restaurants/{restaurantId}/menus")
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        return menuService.getAll(restaurantId);
    }

    @PutMapping(value = "/admin/restaurants/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Menu menu,
                       @PathVariable int restaurantId) {
        log.info("update");
        menuService.save(menu, restaurantId);
    }

    @PostMapping(value = "/admin/restaurants/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Validated(View.Web.class) @RequestBody Menu menu,
                                                   @PathVariable int restaurantId) {
        log.info("create");
        Menu created = menuService.save(menu, restaurantId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/restaurants/{restaurantId}/menus/today")
    public Menu getTodayWithDishes(@PathVariable int restaurantId) {
        log.info("get today's menu");
        return menuService.getTodayWithDishes(restaurantId);
    }
}