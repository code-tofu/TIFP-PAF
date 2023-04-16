package paf.rev.pokemart.controller;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.repository.InventoryRepo;
import paf.rev.pokemart.repository.ItemRepo;
import paf.rev.pokemart.service.AdminService;
import paf.rev.pokemart.service.PokeAPIService;

@Controller
public class AdminController {

    @Autowired private PokeAPIService papis;
    @Autowired private AdminService adminSvc;

    @Autowired @Qualifier("Item")
    private ItemRepo itemRepo;

    @Autowired 
    private InventoryRepo invtRepo;


    @GetMapping("/admin/item/createItemsFromAPI") 
    @ResponseBody public String createItemsFromApi(@RequestParam int size){
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


    @GetMapping("/admin/stock/createStockFromDB")
    @ResponseBody public String createStock(){
        int newstock = invtRepo.createStockfromDB();
        return "{\"201 Created\": \" %d new stock created in Inventory  \"}".formatted(newstock); //return 201
    }


    @GetMapping("/admin/stock/upsertStock")
    @ResponseBody public String upsertStock(@RequestParam String id, @RequestParam String qty){
        try{ 
            int itemID = Integer.parseInt(id);
            Optional<String> itemdata = papis.getItemData(itemID);
            if (itemdata.isEmpty()) return "{\"error\": \"Item does not exist\"}";

            int upsert = invtRepo.upsertStockinDB(itemID, Integer.parseInt(qty));
            if(upsert == 1) return "{\"201 Created\": \" New item %s created in Inventory with Quantity %s \" }".formatted(id,qty);
            if(upsert == 0) return "{\"200 Success\": \" Item %s stock in Inventory updated to %s \" }".formatted(id,qty);
        } catch(NumberFormatException numErr){
            return "{\"400 error\": \"Item ID or Quantity should be an integer\"}";
        } return "{\"500 error\": \" Internal Server Error\"}";
    }


    @GetMapping("/admin/item/getItemFromAPI/{id}") 
    @ResponseBody public String getItemDataFromApi(@PathVariable String id){
        try{
            int itemID = Integer.parseInt(id);
            Optional<String> itemdata = papis.getItemData(itemID);
            if (itemdata.isEmpty()) return "{\"404 error\": \"Item does not exist\"}";
            return itemdata.get(); //TODO: Code chuk's explcit JSON convertors
        } catch(NumberFormatException numErr){
            return "{\"400 error\": \"Item ID should be an integer\"}";
        }
    }

}

