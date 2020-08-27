package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.ControllerResponse;
import ITNews.project.ITNews.model.ImgNewsEntity;
import ITNews.project.ITNews.repository.ImgNewsRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
@Transactional
public class CloudinaryService {
    private static final String noImg= "https://res.cloudinary.com/itnewscloud/image/upload/v1597912961/No-image-available_dljkwb.png";
    private final ImgNewsRepository imgNewsRepository;
    private final Cloudinary cloudinary;


    public  CloudinaryService(ImgNewsRepository imgNewsRepository){
        this.imgNewsRepository = imgNewsRepository;
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("cloud_name", "itnewscloud");
        valueMap.put("api_key", "442436395883333");
        valueMap.put("api_secret", "6pcPHShLjPr_3HTd6r9SKH_Cg-M");
        cloudinary = new Cloudinary(valueMap);
    }
    public ControllerResponse upload(MultipartFile[] multipartFile, Long newsId) throws IOException {
        boolean main;
        for ( int i=0; i<multipartFile.length; i++){
            File file = convert(multipartFile[i]);
            setBodyImg(file, newsId, main = i == 0);
            imgNewsRepository.save(setBodyImg(file, newsId, main));
        }
        return new  ControllerResponse("upload Img", "success", true);
    }
    public ControllerResponse uploadOtherImg(MultipartFile[] multipartFiles, Long newsId) throws IOException{
        for (MultipartFile i:multipartFiles){
            File file = convert(i);
            imgNewsRepository.save(setBodyImg(file, newsId, false));
        }
        return new ControllerResponse("upload Img", "success", true);
    }
    private ImgNewsEntity setBodyImg(File file, Long newsId, Boolean main) throws IOException {
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        ImgNewsEntity imgNewsEntity = new ImgNewsEntity();
        imgNewsEntity.setNameImg((String) result.get("original_filename"));
        imgNewsEntity.setUrlImg((String) result.get("url"));
        imgNewsEntity.setCloudIdImg((String) result.get("public_id"));
        imgNewsEntity.setIdNews(newsId);
        imgNewsEntity.setMainImg(main);
        return imgNewsEntity;
    }

    public ControllerResponse delete(String id) throws IOException {
        cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        this.imgNewsRepository.deleteByCloudIdImg(id);
        return new ControllerResponse("delete img", "success", true);
    }
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

    public String getImgNews(Long newsId) {
        List<ImgNewsEntity> urlImg = imgNewsRepository.findByIdNewsAndMainImg(newsId, true);
        if (urlImg.isEmpty()) {
            return noImg;
        } else{
            return urlImg.get(0).getUrlImg();
        }
    }
}
