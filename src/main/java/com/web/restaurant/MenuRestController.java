package com.web.restaurant;

import com.View;
import com.model.Menu;
import com.model.Role;
import com.model.User;
import com.repository.MenuRepository;
import com.repository.UserRepository;
import com.service.MenuService;
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
@RequestMapping(value = MenuRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuRestController {
    static final String REST_URL = "/rest/restaurants";

    private final static Logger log = LoggerFactory.getLogger(MenuRestController.class);

    private MenuService menuService;

    private UserRepository userRepository;

    public MenuRestController(MenuService menuService, UserRepository userRepository) {
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{restaurantId}/menus/{menuId}")
    public Menu get(@PathVariable int menuId) {
        Menu menu = menuService.get(menuId);
        log.info("get {}", menu);
        return menu;
    }

    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId) {
        log.info("delete {}", menuId);
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            menuService.delete(menuId);
        }
    }

    @GetMapping("/{restaurantId}/menus")
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll");
        List<Menu> all = menuService.getAll(restaurantId);
        return all;
    }

    @PutMapping(value = "/{restaurantId}/menus/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Menu menu,
                       @PathVariable int restaurantId) {
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("update");
            menuService.save(menu, restaurantId);
        }
    }

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Validated(View.Web.class) @RequestBody Menu menu,
                                                   @PathVariable int restaurantId) {
        int userId = SecurityUtil.authUserId();
        User user = userRepository.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("create");
            Menu created = menuService.save(menu, restaurantId);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return null;
    }

    @GetMapping("/{restaurantId}/menus/today")
    public Menu getToday(@PathVariable int restaurantId) {
        log.info("get today's menu");
        return menuService.getTodayMenu(restaurantId);
    }
}