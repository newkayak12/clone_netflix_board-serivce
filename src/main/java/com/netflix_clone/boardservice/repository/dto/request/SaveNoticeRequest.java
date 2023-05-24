package com.netflix_clone.boardservice.repository.dto.request;

import com.netflix_clone.boardservice.repository.dto.reference.NoticeDto;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ToString
public class SaveNoticeRequest extends NoticeDto {
    private List<MultipartFile> rawFiles;
}
