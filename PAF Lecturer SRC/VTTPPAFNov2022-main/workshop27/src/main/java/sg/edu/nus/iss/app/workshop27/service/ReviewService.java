package sg.edu.nus.iss.app.workshop27.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop27.models.EditedComment;
import sg.edu.nus.iss.app.workshop27.models.Review;
import sg.edu.nus.iss.app.workshop27.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepo;

    public Review insertReview(Review r) {
        return reviewRepo.insertReview(r);
    }

    public Review updateEdits(String _id, EditedComment c) {
        return reviewRepo.updateEdits(_id, c);
    }

    public Review getReview(String _id) {
        Review r = reviewRepo.getReview(_id);
        if (r.getEdited() != null) {
            List<EditedComment> ll = (List<EditedComment>) r.getEdited();
            System.out.println(ll.size());
            if (ll.size() > 0)
                r.setIsEdited(Boolean.valueOf(true));
            else
                r.setIsEdited(Boolean.valueOf(false));
        }

        r.setTimestamp(LocalDateTime.now());
        return r;
    }
}
