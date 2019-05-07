package com.osc.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Kerisnarendra on 6/05/2019.
 */

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
