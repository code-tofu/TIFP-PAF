package ibf2022.batch2.paf.server.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.paf.server.models.Comment;
import ibf2022.batch2.paf.server.models.Restaurant;
import ibf2022.batch2.paf.server.repositories.RestaurantRepository;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepo;

    // Task 2
    public List<String> getCuisines() {
        List<String> cuisineList = restaurantRepo.getCuisines();
        List<String> replacedList = new ArrayList<>();
        for (String cuisine : cuisineList) {
            replacedList.add(cuisine.replace("/", "_"));
        }
        Collections.sort(replacedList);
        return replacedList;
    }

    // Task 3
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepo.getRestaurantsByCuisine(cuisine);
    }

    // Task 4
    public Optional<Restaurant> getRestaurantById(String id) {
        return restaurantRepo.getRestaurantById(id);
    }

    // Task 5
    public void postRestaurantComment(Comment comment) {
        restaurantRepo.insertRestaurantComment(comment);
    }
}
