package web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.models.User;
import web.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserDao userDao, UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.index());
        return "user/index";
    }
    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "user/show";
    }
    @GetMapping("/new")
    public String newUser(@ModelAttribute("userList") User user) {
        return "user/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("userList") @Valid User user,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "user/new";

        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("users", userService.show(id));
        return "user/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("users") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if(bindingResult.hasErrors())
            return "user/edit";

        userService.update(id, user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/user";
    }
}
