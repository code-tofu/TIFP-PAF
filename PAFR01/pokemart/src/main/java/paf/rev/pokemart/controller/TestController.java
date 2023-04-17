package paf.rev.pokemart.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import paf.rev.pokemart.model.CartListDTO;
import paf.rev.pokemart.model.QuantityDTO;
import paf.rev.pokemart.service.AccountService;

@Controller
public class TestController {
    @Autowired
    AccountService accountSvc;
   

    //UNDERCONSTRUCTION
    @GetMapping("/account/create")
    public String createNewAccountPage(){
        return "construction";
    }
    @RequestMapping("/temp")
    public String tempPage(){
        return "construction";
    }
    @RequestMapping("/404error")
    public String notFoundPage(){
        return "notfound";
    }





    // @RequestMapping(path={"/mart/**","/test"})
    // public String ProtectionPage(HttpServletRequest httpRequest, HttpSession session){
    //     if((boolean)session.getAttribute("authFlag")){
    //         return "redirect:" + httpRequest.getRequestURI();
    //     } else {
    //         return "redirect:/";
    //     }
    // }

}
