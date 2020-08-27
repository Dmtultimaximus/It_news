package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.dto.RatingRequest;
import ITNews.project.ITNews.exeption.RatingExeption;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.model.UserRaitingNewsEntity;
import ITNews.project.ITNews.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;

    public ControllerResponse addRating(UserEntity userData , RatingRequest ratingRequest) {
        if(ratingRepository.findByUserIdAndNewsId(userData.getUserId(), ratingRequest.getNewsId()).isPresent()){
            return new ControllerResponse("Add Rating", "you already added rating", false);
        } else {
            UserRaitingNewsEntity userRating = new UserRaitingNewsEntity();
            userRating.setRating(ratingRequest.getRating());
            userRating.setNewsId(ratingRequest.getNewsId());
            userRating.setUserId(userData.getUserId());
            ratingRepository.save(userRating);
        }
        return new ControllerResponse("Add Rating", "success", true);
    }

    public double getRating(Long newsId) {
        List<UserRaitingNewsEntity> rating = ratingRepository.findAllByNewsId(newsId);
        double rait = 0;
        for (UserRaitingNewsEntity userRaitingNewsEntity : rating) {
            rait += userRaitingNewsEntity.getRating();
        }
        rait = rait / rating.size();
        return rait;
    }

    public ControllerResponse checkRating(UserEntity userData, Long newsId) {
        if (ratingRepository.findByUserIdAndNewsId(userData.getUserId(), newsId).isPresent()){
            return new ControllerResponse("Add Rating", "you already added rating", false);
        } else {
            return new ControllerResponse("Add Rating", "you already added rating", true);
        }
    }
}
