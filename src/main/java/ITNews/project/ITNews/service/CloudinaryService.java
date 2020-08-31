package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.ImgNewsEntity;
import ITNews.project.ITNews.repository.ImgNewsRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class CloudinaryService {
    @Value("${urlCloud}")
    private String noImg;

    @Value("${cloudName}")
    private String cloudName;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${apiSecret}")
    private String apiSecret;

    private final ImgNewsRepository imgNewsRepository;
    private Cloudinary cloudinary;

    public void Cloudinary() {
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("cloud_name", cloudName);
        valueMap.put("api_key", apiKey);
        valueMap.put("api_secret", apiSecret);
        cloudinary = new Cloudinary(valueMap);
    }

    public boolean upload(MultipartFile[] multipartFile, Long newsId) throws IOException {
        boolean main;
        for (int i = 0; i < multipartFile.length; i++) {
            File file = convert(multipartFile[i]);
            setBodyImg(file, newsId, main = i == 0);
            imgNewsRepository.save(setBodyImg(file, newsId, main));
        }
        return true;
    }

    public boolean uploadOtherImg(MultipartFile[] multipartFiles, Long newsId) throws IOException {
        for (MultipartFile i : multipartFiles) {
            File file = convert(i);
            imgNewsRepository.save(setBodyImg(file, newsId, false));
        }
        return true;
    }

    private ImgNewsEntity setBodyImg(File file, Long newsId, Boolean main) throws IOException {
        Cloudinary();
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        ImgNewsEntity imgNewsEntity = new ImgNewsEntity();
        imgNewsEntity.setNameImg((String) result.get("original_filename"));
        imgNewsEntity.setUrlImg((String) result.get("url"));
        imgNewsEntity.setCloudIdImg((String) result.get("public_id"));
        imgNewsEntity.setIdNews(newsId);
        imgNewsEntity.setMainImg(main);
        return imgNewsEntity;
    }

    public boolean delete(String id) throws IOException {
        Cloudinary();
        cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        this.imgNewsRepository.deleteByCloudIdImg(id);
        return true;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fo = new FileOutputStream(file)) {
            fo.write(multipartFile.getBytes());
        }
        return file;
    }
    public String getImgNews(Long newsId) {
        List<ImgNewsEntity> urlImg = imgNewsRepository.findByIdNewsAndMainImg(newsId, true);
        if (urlImg.isEmpty()) {
            return noImg;
        } else {
            return urlImg.get(0).getUrlImg();
        }
    }

    public List<ImgNewsEntity> getAllImg(Long newsId) {
        return imgNewsRepository.findByIdNews(newsId);
    }
}
