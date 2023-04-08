package paf.rev.pokemart.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.repository.ItemRepoSQL;
import paf.rev.pokemart.service.TestService;

@Controller
public class TestController {
    
    @Autowired
    private TestService testSvc;
    @Autowired
    private ItemRepoSQL itemRepo;

    @GetMapping("/testInventory") 
    @ResponseBody public String displayTestDatabase(){
        testSvc.createTestItemDatabase();
        int addCount = 0;
        for(Item item: testSvc.getTestDatabase()){
            //REFACTOR: change from direct repo access to service access
            addCount += itemRepo.createItem(item);
            
        }
        System.out.println(">>>" + addCount + " items added to SQLDB");
        return Arrays
        .toString(testSvc.getTestDatabase()
        .toArray());

    }   

    @GetMapping("/api/item/{id}") 
    @ResponseBody public String getItembyID(@PathVariable String id){
        try{
            int itemID = Integer.parseInt(id);
            Optional<Item> itemdata = itemRepo.getItembyId(itemID);
            if(itemdata.isEmpty()){
                return "{\"error\": \"Item does not exist\"}";
            }
            return itemdata.get().toString();
        } catch(NumberFormatException numErr){
            return "{\"error\": \"Item ID should be an integer\"}";
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
                return "{\"error\": \"Database is Empty\"}";
            }
            return item_id_list.toString();
        } catch(NumberFormatException numErr){
            return "{\"error\": \"Item ID should be an integer\"}";
        }
    }
}
