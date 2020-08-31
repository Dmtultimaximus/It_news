package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.dto.RatingRequest;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.service.RatingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingNewsController {

    private final RatingService ratingService;

    @PostMapping("/add")
    public ControllerResponse addRating(@AuthenticationPrincipal UserEntity userData,
                                        @RequestBody RatingRequest ratingRequest){
        return ratingService.addRating(userData, ratingRequest);
    }
    @GetMapping
    public ResponseEntity<Double> getRating(@RequestParam Long newsId){
        return new ResponseEntity<>(ratingService.getRating(newsId), HttpStatus.OK);
    }

    @GetMapping("/check")
    public ControllerResponse checkRating(@AuthenticationPrincipal UserEntity userData,
                                          @RequestParam Long newsId){
        return ratingService.checkRating(userData, newsId);
    }
}
