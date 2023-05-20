package com.netflix_clone.boardservice.configure.fileUpload;

import com.netflix_clone.boardservice.constants.Constants;
import org.newkayak.FileUpload.FileUpload;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.DependsOn;

@Configurable(value = "fileUpload")
@DependsOn(value = "constants")
public class Config {
    private FileUpload fileUpload = new FileUpload(Constants.FILE_PATH, false, 1024L * 10L);
}
