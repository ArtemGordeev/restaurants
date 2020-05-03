package com.web.restaurant;

import com.model.Menu;
import com.repository.MenuRepository;
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
import java.time.LocalDate;
import java.util.Objects;

import static com.util.ValidationUtil.assureIdConsistent;
import static com.util.ValidationUtil.checkNew;

@Controller
@RequestMapping("/restaurants")
public class MenuController {

    private final static Logger log = LoggerFactory.getLogger(MenuController.class);

    private MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping("/{restaurantId}/menus")
    public String getAll(@PathVariable("restaurantId") int restaurantId, Model model) {
        model.addAttribute("menus", menuRepository.getAll(restaurantId));
        log.info("get all menus from restaurant {}", restaurantId);
        return "menus";
    }

    @GetMapping("/{restaurantId}/menus/delete/{id}")
    public String delete(@PathVariable("restaurantId") int restaurantId,
                         @PathVariable("id") int id) {
        menuRepository.delete(id);
        return "redirect:/restaurants/" + restaurantId + "/menus";
    }

    @GetMapping("/{restaurantId}/menus/update/{menuId}")
    public String update(//@PathVariable("restaurantId") int restaurantId,
                         @PathVariable("menuId") int menuId,
                         Model model) {
        final Menu menu = menuRepository.get(menuId);
        model.addAttribute("menu", menu);
        return "/menuForm";
    }

    @PostMapping("/{restaurantId}/menus/update/{menuId}")
    public String update(@PathVariable("restaurantId") int restaurantId,
                         //@PathVariable("menuId") int menuId,
                         HttpServletRequest request) throws IOException {
        Menu menu = getMenu(request);
        int id = getId(request);
        //int userId = SecurityUtil.authUserId();
        assureIdConsistent(menu, id);

        log.info("update {}", menu);
        menuRepository.save(menu, restaurantId);
        return "redirect:/restaurants/" + restaurantId + "/menus";
    }

    @GetMapping("/{restaurantId}/menus/create")
    public String create(Model model) {
        final Menu menu = new Menu();
        model.addAttribute("menu", menu);
        return "/menuForm";
    }

    @PostMapping("/{restaurantId}/menus/create")
    public String create(@PathVariable("restaurantId") int restaurantId,
                         HttpServletRequest request) throws IOException {
        Menu menu = getMenu(request);
        //int userId = SecurityUtil.authUserId();
        checkNew(menu);
        log.info("create {}", menu);
        menuRepository.save(menu, restaurantId);
        return "redirect:/restaurants/" + restaurantId + "/menus";
    }

    private Menu getMenu(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        return new Menu(request.getParameter("title"), LocalDate.parse(request.getParameter("date")));
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
