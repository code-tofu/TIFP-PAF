package paf.rev.pokemart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import jakarta.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import paf.rev.pokemart.model.Item;
import paf.rev.pokemart.util.JsonUtil;

@Service
public class AdminService {

    @Autowired
    private PokeAPIService papis;

    private List<Item> newDatabase = new ArrayList<>();
    
    
    public AdminService() {
    }

    public void createNewDatabase(int size){
        Random rand = new Random();
        for (int i = 0; i<size;){
            try{
                int randInt = rand.nextInt(999)+1;
                Optional<String> itemJsonStr = papis.getItemData(randInt);
                if(itemJsonStr.isEmpty()) continue;

                JsonObject itemJson = JsonUtil.JsonStr2Obj(itemJsonStr.get());
                this.newDatabase.add(Item.ItemFromJson(itemJson));
                System.out.println(">>> Downloading...");
                Thread.sleep(1000); //sleep to prevent api limit
                i += 1;
            } catch (InterruptedException interErr){
                System.out.println(">>> Downloading Interrupted. Restarting Download");
            }
        }
        System.out.println(">>> Created New Database of Size " + size);
    }


    public List<Item> getNewDatabase() {
        return newDatabase;
    }
}
