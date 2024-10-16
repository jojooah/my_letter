package com.jojo.my_letter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    private String uploadDir;
    private String webDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getWebDir() {
        return webDir;
    }

    public void setWebDir(String webDir) {
        this.webDir = webDir;
    }
}
