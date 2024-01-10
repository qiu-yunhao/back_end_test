package com.example.demo.Bean;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    @Column
    private String path;


    @Column(length = 50)
    private String name;


    public Image(MultipartFile file, String filePath) {
        this.name = UUID.randomUUID().toString();
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf('.'));
        this.path = filePath + '\\' + name + suffix;
    }

    public Image() {
        this.name = UUID.randomUUID().toString();
    }

    public String getSuffix() {
        if (!path.isEmpty()) {
            return path.substring(path.lastIndexOf('.'));
        }
        return "jepg";
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
