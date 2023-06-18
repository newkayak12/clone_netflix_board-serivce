package com.netflix_clone.boardservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.boardservice.component.exception.BecauseOf;
import com.netflix_clone.boardservice.util.AbstractControllerTest;
import com.netflix_clone.boardservice.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
@AutoConfigureRestDocs
class BannerControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/banner";

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName(value = "배너 리스트")
    @Test
    void banners() throws Exception {
        mockMvc.perform(
            get(String.format("%s/", prefix))
            .param("page", "1")
            .param("limit", "10")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content.size()").value(5))
        .andExpect(jsonPath("$.pageable").isNotEmpty());

    }

    @DisplayName(value = "배너 단일")
    @Test
    void banner() throws Exception {
        mockMvc.perform(
            get(String.format("%s/%d", prefix, 11))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.bannerNo").value(11))
        .andExpect(jsonPath("$.title").value("BANNER_11"))
        .andExpect(jsonPath("$.url").value("https://www.netflix.com"))
        .andExpect(jsonPath("$.image").isNotEmpty());
    }

    @Nested
    @DisplayName(value = "배너 저장")
    class SaveBanner {

        @DisplayName(value = "저장 성공")
        @ParameterizedTest
        @Transactional
        @Rollback
        @ValueSource(ints = {1,2,3,4,5})
        public void successInsert(int numbering) throws Exception {
            mockMvc.perform(
                multipart(String.format("%s/", prefix))
                .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFile"))
                .param("title", String.format("BANNER_%d", numbering))
                .param("url", "https://www.netflix.com")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true))
            .andExpect(jsonPath("$").isBoolean());
        }

        @DisplayName(value = "저장 실패")
        @Test
        public void failureInsert() throws Exception {
            mockMvc.perform(
                        multipart(String.format("%s/", prefix))
                            .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFile"))
                            .param("title", String.format("BANNER_%d", 6))
                            .param("url", "https://www.netflix.com")
                    )
                    .andExpect(status().is5xxServerError())
                    .andExpect(jsonPath("$").value(BecauseOf.EXCEED_MAXIMUM_BANNER_COUNT.getMsg()));
        }

        @DisplayName(value = "수정 성공")
        @Test
        public void successModify() throws Exception {
            mockMvc.perform(
                            multipart(String.format("%s/", prefix))
                                    .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFile"))
                                    .param("bannerNo", "11")
                                    .param("title", String.format("BANNER_%d", 11))
                                    .param("url", "https://www.netflix.com")
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(true))
                    .andExpect(jsonPath("$").isBoolean());
        }
    }

    @Nested
    @DisplayName(value = "배너 삭제")
    class RemoveBanner {

        @DisplayName(value = "삭제 성공")
        @ParameterizedTest
        @Transactional
        @Rollback
        @ValueSource(ints = {16})
        public void success(int bannerNo) throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d", prefix, bannerNo))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
        }

        @DisplayName(value = "삭제 실패")
        @Transactional
        @Rollback
        @Test
        public void failure() throws Exception {
            mockMvc.perform(
                            delete(String.format("%s/%d", prefix, 1))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value(false));
        }
    }
}