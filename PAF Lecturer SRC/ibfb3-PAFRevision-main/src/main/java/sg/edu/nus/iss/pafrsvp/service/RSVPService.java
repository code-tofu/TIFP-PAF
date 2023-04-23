package sg.edu.nus.iss.pafrsvp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.pafrsvp.model.AggregationRSVP;
import sg.edu.nus.iss.pafrsvp.model.RSVP;
import sg.edu.nus.iss.pafrsvp.repository.RSVPRepository;

@Service
public class RSVPService {
    @Autowired
    private RSVPRepository repo;

    public RSVP insertRSVP(final RSVP rsvp) {
        return this.repo.insertRSVP(rsvp);
    }

    public List<AggregationRSVP> aggregateByFoodType(){
        return this.repo.aggregateByFoodType();
    }
}
