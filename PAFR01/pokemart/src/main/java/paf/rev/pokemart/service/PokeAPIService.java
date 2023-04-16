package paf.rev.pokemart.service;
//interfaces with https://pokeapi.co/ to obtain item data

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PokeAPIService {

    @Value("${rev.poke.api.url}")
    private String pokeAPI;

    //Should not be static as the method only exists with a Service instance
    public Optional<String> getItemData(int itemID){
        //Build APIaccessURL. Note path segments auto-adds /
        String itemURL = UriComponentsBuilder.fromUriString(pokeAPI).pathSegment("item").pathSegment(Integer.toString(itemID)).toUriString();
        System.out.println(">>> Retrieve Data from:" + itemURL);
        
        //Build GET Request Entity from Rest Template
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(itemURL).build();

        //Request to API
        try{
            ResponseEntity<String> resp  = restTemplate.exchange(req,String.class);
            // System.out.println(resp.getBody());
            return Optional.of(resp.getBody());
        }
        catch(HttpClientErrorException e){ //TODO: Need to handle 404 and 500
            return Optional.empty();
        }
    }
    
}


