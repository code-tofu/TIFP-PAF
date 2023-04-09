package paf.rev.pokemart.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.repository.ItemRepo;
import paf.rev.pokemart.service.AdminService;
import paf.rev.pokemart.service.PokeAPIService;

@Controller
public class AdminController {

    @Autowired private PokeAPIService papis;
    @Autowired private AdminService adminSvc;
    @Autowired private ItemRepo itemRepo;

    //change to take in int var for number of items to create
    //remove "test"
    @GetMapping("/admin/item/insertDB/{size}") 
    @ResponseBody public String insertDatabase(@PathVariable int size){
        adminSvc.createNewDatabase(size);
        int addCount = 0;
        for(Item item: adminSvc.getNewDatabase()){
            //REFACTOR: change from direct repo access to service access
            addCount += itemRepo.createItem(item);
            
        }
        System.out.println(">>>" + addCount + " items added to SQLDB");
        return Arrays
        .toString(adminSvc.getNewDatabase()
        .toArray());
    }   
    @GetMapping("/admin/item/api/{id}") 
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

    // @GetMapping("admin/inventory/insertDB")
    // @ResponseBody  

    
    
}
