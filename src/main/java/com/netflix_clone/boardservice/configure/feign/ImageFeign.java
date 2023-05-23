package com.netflix_clone.boardservice.configure.feign;

import com.netflix_clone.boardservice.enums.FileType;
import com.netflix_clone.boardservice.repository.dto.reference.FileDto;
import com.netflix_clone.boardservice.repository.dto.reference.FileRequest;
import com.netflix_clone.userservice.enums.FileType;
import com.netflix_clone.userservice.repository.dto.reference.FileDto;
import com.netflix_clone.userservice.repository.dto.reference.FileRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2023-05-12
 * Project user-service
 */
@Qualifier(value = "image")
@FeignClient(name = "netflix-clone-image-service")
public interface ImageFeign {

    @PostMapping(value = "/save", produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<List<FileDto>> save(@RequestPart(value = "requestList") List<FileRequest> requestList);
    @GetMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<List<FileDto>> files(@PathVariable Long tableNo, @PathVariable FileType fileType);
    @GetMapping(value = "/{fileType}")
    public ResponseEntity<List<FileDto>> files(@RequestParam List<Long> tableNos, @PathVariable FileType fileType);
    @DeleteMapping(value = "/{tableNo}/{fileType}")
    public ResponseEntity<Boolean> remove(@PathVariable Long tableNo, @PathVariable  FileType fileType);
    @DeleteMapping(value = "/{fileType}")
    public ResponseEntity<Boolean> remove(@RequestParam List<Long> tableNos, @PathVariable  FileType fileType);


}
