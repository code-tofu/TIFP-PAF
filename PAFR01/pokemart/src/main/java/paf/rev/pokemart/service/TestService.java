package paf.rev.pokemart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import jakarta.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.util.JsonUtil;

@Service
public class TestService {

    @Autowired
    private PokeAPIService papis;

    private int size = 5;
    private List<Item> testInventory = new ArrayList<>();
    
    
    public TestService() {
    }

    public void createTestInventory(){
        Random rand = new Random();
        for (int i = 0; i<this.size;){
            try{
                int randInt = rand.nextInt(999)+1;
                Optional<String> itemJsonStr = papis.getItemData(randInt);
                if(itemJsonStr.isEmpty()) continue;

                JsonObject itemJson = JsonUtil.JsonStr2Obj(itemJsonStr.get());
                this.testInventory.add(Item.ItemFromJson(itemJson));
                System.out.println(">>> Downloading...");
                Thread.sleep(1000); //sleep to prevent api limit
                i += 1;
            } catch (InterruptedException interErr){
                System.out.println(">>> Downloading Interrupted. Restarting Download");
            }
        }
        System.out.println(">>> Created Test Inventory of Size " + this.size);
    }

    

    public int getSize() {
        return size;
    }

    public List<Item> getTestInventory() {
        return testInventory;
    }




    
}
