package ITNews.project.ITNews.controller;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/cloud")
@RequiredArgsConstructor
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload/{newsId}")
    public ControllerResponse upload(@RequestBody MultipartFile[] multipartFile,
                                     @PathVariable Long newsId) throws IOException {
        return cloudinaryService.upload(multipartFile, newsId);
    }

    @PostMapping("/uploadOther/{newsId}")
    public ControllerResponse uploadOther(@RequestBody MultipartFile[] multipartFile,
                                          @PathVariable Long newsId) throws IOException {
        return cloudinaryService.uploadOtherImg(multipartFile, newsId);
    }

    @DeleteMapping("/delete/{idImage}")
    public ControllerResponse delete(@PathVariable String idImage) throws IOException {
        return cloudinaryService.delete(idImage);
    }
}
