package paf.rev.pokemart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.repository.ItemRepo;

@Controller
public class APIController {
    
    @Autowired
    private ItemRepo itemRepo;

    @GetMapping("/api/item/{id}") 
    @ResponseBody public String getItembyID(@PathVariable String id){
        try{
            int itemID = Integer.parseInt(id);
            Optional<Item> itemdata = itemRepo.getItembyId(itemID);
            if(itemdata.isEmpty()){
                return "{\"404 error\": \"Item does not exist\"}";
            }
            return itemdata.get().toString();
        } catch(NumberFormatException numErr){
            return "{\"400 error\": \"Item ID should be an integer\"}";
        }
    }
    
    @GetMapping("/api/item/all") 
    @ResponseBody public String getAllItemIDs(@RequestParam(defaultValue = "999") String LT,@RequestParam(defaultValue = "0") String OT){
        try{
            int limit = Integer.parseInt(LT);
            int offset = Integer.parseInt(OT);
            System.out.println(">>> Getting item_ids limit " + limit + " offset " + offset);
            List<String> item_id_list= itemRepo.getAllItemIds(limit, offset);
            if(item_id_list.isEmpty()){
                return "{\"404 error\": \"Database is Empty\"}";
            }
            return item_id_list.toString();
        } catch(NumberFormatException numErr){
            return "{\"400 error\": \"Item ID should be an integer\"}";
        }
    }
}
