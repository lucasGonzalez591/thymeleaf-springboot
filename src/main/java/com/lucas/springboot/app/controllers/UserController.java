package com.lucas.springboot.app.controllers;

import com.lucas.springboot.app.models.User;
import com.lucas.springboot.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.attribute.standard.PresentationDirection;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@SessionAttributes("{user}")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping({"/view","/another"})
    public String view(Model model){
        model.addAttribute("title","Hola mundo spring boot!!!");
        model.addAttribute("message","Esta es una aplicacion de ejemplo usando spring boot");
        model.addAttribute("user",new User("Lucas","Gonzalez"));
        return "view";
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("title","Listado de usuarios");
        model.addAttribute("users",service.findAll());
        return "list";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("title", "Crear usuario");
        return "form";
    }

    @GetMapping("/form/{id}")
    public String form(@PathVariable Long id ,Model model, RedirectAttributes redirect){
        Optional<User> optionalUser = service.findByID(id);
        if (optionalUser.isPresent()){
            model.addAttribute("user",optionalUser.get());
            model.addAttribute("tittle", "editar usuario");
            return "form";
        }else {
            redirect.addFlashAttribute("error","El usuario con id "
                    + id
                    + " no existe en la base de datos");
            return "redirect:/users";
        }
    }

    @PostMapping
    public String form(@Valid User user, BindingResult result, Model model, RedirectAttributes redict, SessionStatus status){

        if (result.hasErrors()){
            return "form";
        }

        String message = "";
        if ( user.getId() != null && user.getId() > 0){
            message = "El usuario: "
                    + user.getName() +
                    " Se ha actulizado con éxito";
        }else{
            message = "El usuario " +
                    user.getName() +
                    " se ha creado con éxito";
        }
        service.save(user);
        status.setComplete();
        redict.addFlashAttribute("success",message);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        Optional<User> userOptional = service.findByID(id);
        if (userOptional.isPresent()){
            redirect.addFlashAttribute("success","El usuario "+
                    userOptional.get().getName() + " se ha eliminado co éxito");
            service.delete(id);
            return "redirect:/users";
        }else {
            redirect.addFlashAttribute("error","El usuario con el id  "+
                    id +
                    " No se encuentra en el sistema");
            return  "redirect:/users";
        }

    }




}
