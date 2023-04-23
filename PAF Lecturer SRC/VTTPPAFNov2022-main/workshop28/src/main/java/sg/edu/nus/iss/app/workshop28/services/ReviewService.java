package sg.edu.nus.iss.app.workshop28.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceTransformerSupport;

import sg.edu.nus.iss.app.workshop28.models.Comment;
import sg.edu.nus.iss.app.workshop28.models.EditedComment;
import sg.edu.nus.iss.app.workshop28.models.Game;
import sg.edu.nus.iss.app.workshop28.models.Review;
import sg.edu.nus.iss.app.workshop28.repositories.ReviewRepository;

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

    public Optional<Game> aggregateGame(String gid) {
        return reviewRepo.aggregrateGameReviews(gid);
    }

    public List<Comment> aggregateGamesComment(Integer limit, String username, Integer rating) {
        return reviewRepo.aggregateGamesComment(limit, username, rating);
    }
}
