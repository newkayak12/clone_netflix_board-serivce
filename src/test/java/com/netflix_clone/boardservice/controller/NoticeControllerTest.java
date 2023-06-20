package com.netflix_clone.boardservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties
@ActiveProfiles(value = {"local"})
@AutoConfigureRestDocs
class NoticeControllerTest extends AbstractControllerTest {

    private final String prefix = "/api/v1/notice";
    @Autowired
    private ObjectMapper mapper;

    @DisplayName(value = "공지 가져오기")
    @Nested
    class FetchNotice {
        @Test
        @DisplayName(value = "리스트 비어 있을 경우")
        void emptyList() throws Exception {
            mockMvc.perform(
                get(String.format("%s/", prefix))
                .param("page", "1")
                .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isNotEmpty());
        }

        @Test
        @DisplayName(value = "리스트 있는 경우")
        void list() throws Exception {
            mockMvc.perform(
                            get(String.format("%s/", prefix))
                                    .param("page", "1")
                                    .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.size()").value(10));
        }

        @Test
        @DisplayName(value = "단일")
        void notice() throws Exception {
            mockMvc.perform(
                get(String.format("%s/%d", prefix, 105))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.noticeNo").value(105))
            .andExpect(jsonPath("$.title").value("NOTICE_30"))
            .andExpect(jsonPath("$.content").value("NOTICE_30"))
            .andExpect(jsonPath("$.images").isNotEmpty());
        }


        @Test
        @DisplayName(value = "단일 테스트2")
        void noticeTest_v2() throws Exception {
            mockMvc.perform(
                            get(String.format("%s/%d", prefix, 1))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.noticeNo").value(1))
                    .andExpect(jsonPath("$.title").value("NOTICE_1_A"))
                    .andExpect(jsonPath("$.content").value("NOTICE_1_B"))
                    .andExpect(jsonPath("$.images").isNotEmpty());
        }
    }

    @DisplayName(value = "공지 저장")
    @Nested
    class SaveNotice {
        @ParameterizedTest
        @DisplayName(value = "저장 성공")
        @Transactional
        @Rollback
        @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15})
        void insertSuccess(int number) throws Exception {
            mockMvc.perform(
                multipart(String.format("%s/", prefix))
                .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFiles"))
                .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFiles"))
                .param("title", String.format("NOTICE_%d", number + 15))
                .param("content", String.format("NOTICE_%d", number + 15))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isBoolean())
            .andExpect(jsonPath("$").value(true));
        }

        @Test
//        @Transactional
//        @Rollback
        @DisplayName(value = "수정 성공")
        void modifySuccess () throws Exception {
            mockMvc.perform(
                            multipart(String.format("%s/", prefix))
                                    .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFiles"))
                                    .file(TestUtil.getMockMultiPartFile("/Users/sanghyeonkim/Downloads/R1280x0.png", "image","rawFiles"))
                                    .param("noticeNo", "1")
                                    .param("title", String.format("NOTICE_%d_A", 1))
                                    .param("content", String.format("NOTICE_%d_B", 1))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isBoolean())
                    .andExpect(jsonPath("$").value(true));
        }
    }

    @DisplayName(value = "공지 삭제")
    @Nested
    class RemoveNotice {
        @Test
        @DisplayName(value = "삭제 성공")
        void successRemove() throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d", prefix, 105))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
        }
    }



}