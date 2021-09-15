package crudApp.controllers;

import crudApp.models.User;
import crudApp.service.RoleService;
import crudApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("user", userService.getAllUsers());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user,
                            Model model){
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("user") @Valid User user, BindingResult bindingResultUser,
                               @RequestParam(value = "selectedRoles", required = false) Long [] roles){
        if (bindingResultUser.hasErrors()) {
            return "admin/new";
        }
        userService.add(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                               @RequestParam(value = "selectedRoles", required = false) Long [] roles) {
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.update(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
