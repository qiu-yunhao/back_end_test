package com.example.demo.Mapper;

import com.example.demo.Bean.Image;
import com.example.demo.Dao.ImageDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImageMapper {

    private static ImageMapper INSTANCE;
    private ImageDao dao;

    ImageMapper(ImageDao dao) {
        this.dao = dao;
    }

    public static ImageMapper getINSTANCE(ImageDao dao) {
        if (INSTANCE == null) {
            INSTANCE = new ImageMapper(dao);
        }
        return INSTANCE;
    }

    public Image getImageByPath(String path) {
        List<Image> images = dao.getImageByPath(path);
        if (images.isEmpty()) {
            return null;
        } else {
            return images.get(0);
        }
    }

    public Image getImageByID(int pid) {
        List<Image> images = dao.getImageByID(pid);
        if (images.isEmpty()) {
            return null;
        } else {
            return images.get(0);
        }
    }
}
