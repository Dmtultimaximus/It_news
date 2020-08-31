package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.ImgNewsEntity;
import ITNews.project.ITNews.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cloud")
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/{newsId}")
    public boolean upload(@RequestBody MultipartFile[] multipartFile,
                                     @PathVariable Long newsId) throws IOException {
        return cloudinaryService.upload(multipartFile, newsId);
    }

    @PostMapping("/other/{newsId}")
    public boolean uploadOther(@RequestBody MultipartFile[] multipartFile,
                                          @PathVariable Long newsId) throws IOException {
        return cloudinaryService.uploadOtherImg(multipartFile, newsId);
    }

    @GetMapping("/{newsId}")
    public List<ImgNewsEntity> getAllImgNews(@PathVariable Long newsId) {
        return cloudinaryService.getAllImg(newsId);
    }

    @DeleteMapping("/{idImage}")
    public boolean delete(@PathVariable String idImage) throws IOException {
        return cloudinaryService.delete(idImage);
    }
}
