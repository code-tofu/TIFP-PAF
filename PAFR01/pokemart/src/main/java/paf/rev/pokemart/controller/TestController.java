package paf.rev.pokemart.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import paf.rev.pokemart.service.TestService;

@Controller
public class TestController {
    
    @Autowired
    private TestService testDB;

    @GetMapping("/testInventory") 
    @ResponseBody public String displayTestInventory(){
        testDB.createTestInventory();
        return Arrays
        .toString(testDB.getTestInventory()
        .toArray());

    }   
}
