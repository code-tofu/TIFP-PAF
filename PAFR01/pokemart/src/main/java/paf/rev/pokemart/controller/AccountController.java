package paf.rev.pokemart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import paf.rev.pokemart.model.Login;
import paf.rev.pokemart.service.AccountService;

@Controller
public class AccountController {

    @Autowired
    AccountService accountSvc;

    @GetMapping("/") 
    public String welcomePage(Model model){
        model.addAttribute("login", new Login());
        return "welcome";
    }

    @PostMapping("/login")
    public String loginPage(Model model, @ModelAttribute @Valid Login login, BindingResult binding, HttpSession session){
        if(binding.hasErrors()){
            model.addAttribute("login", login);
            return "welcome";
        }
        int authResult = accountSvc.authenticateAccount(login.getEmail(), login.getPassword());
        switch(authResult){
            case -1:
            binding.addError(
                new FieldError("login", "password", "Email does not exist! Please sign up for a new account.")
            );
            model.addAttribute("login", login);
            return "welcome";
            case 0:
                binding.addError(
                    new FieldError("login", "password", "Incorrect email and password")
                );
                model.addAttribute("login", login);
                return "welcome";
            case 1:
                boolean isAuthenticatedFlag = true;
                session.setAttribute("authFlag",isAuthenticatedFlag);
                return "redirect:/mart/main";
            default:
                binding.addError(
                    new FieldError("login", "password", "Validation Error")
                );
                model.addAttribute("login", login);
                return "welcome";
        }
    }



    @GetMapping(path={"/logout"})
    public String logoutPage(HttpSession session){
        session.invalidate();
        System.out.println(">>LOGGING OUT");
        return "redirect:/";
    }

}
