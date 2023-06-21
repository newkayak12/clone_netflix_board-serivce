package com.netflix_clone.boardservice.controller;

import com.netflix_clone.boardservice.repository.dto.reference.CommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.ProfileDto;
import com.netflix_clone.boardservice.repository.dto.request.SaveReport;
import com.netflix_clone.boardservice.util.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
@AutoConfigureRestDocs
class ReportControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/report";

    @Test
    @DisplayName(value = "신고")
    void report() {
        SaveReport report = new SaveReport();

        ProfileDto boardProfile = new ProfileDto();
        boardProfile.setProfileNo(18L);
        boardProfile.setProfileName("PROFILE_NUMBER_1");

        CommentDto commentDto = new CommentDto();
        ProfileDto target = new ProfileDto();
        boardProfile.setProfileNo(19L);
        boardProfile.setProfileName("profile_2");
        commentDto.setProfile(target);
        commentDto.setComments("TEST");
        commentDto.setStar(4.5);
        commentDto.setContentsNo(1L);

        report.setProfile(boardProfile);
        report.setComment(commentDto);







    }
}