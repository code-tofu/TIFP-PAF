package paf.rev.pokemart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import paf.rev.pokemart.service.PokeAPIService;

@Controller
public class PokeAPIController {

    @Autowired PokeAPIService papis;

    @GetMapping("/item/{id}") 
    @ResponseBody public String displayFullItemData(@PathVariable String id){
        int itemID;
        try{
            itemID = Integer.parseInt(id);
            Optional<String> itemdata = papis.getItemData(itemID);
            if (itemdata.isEmpty()) return "{\"error\": \"Item does not exist\"}";
            return itemdata.get(); //TODO: code chuk's explcit JSON convertors
        } catch(NumberFormatException numErr){
            return "{\"error\": \"Item ID should be an integer\"}";
        }
    }   
}
