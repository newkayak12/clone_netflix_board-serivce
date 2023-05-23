package com.netflix_clone.boardservice.repository.dto.request;

import com.netflix_clone.boardservice.repository.dto.reference.BannerDto;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class SaveBannerRequest extends BannerDto {
    private MultipartFile rawFile;
}
