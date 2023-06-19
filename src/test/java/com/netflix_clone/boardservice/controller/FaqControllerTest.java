package com.netflix_clone.boardservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.boardservice.repository.dto.request.SaveFaqRequest;
import com.netflix_clone.boardservice.util.AbstractControllerTest;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
class FaqControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/faq";

    @Autowired private ObjectMapper mapper;

    @Nested
    @DisplayName(value = "FAQ ")
    public class FetchFaq {
        @Test
        @DisplayName(value = "FAQ 리스트")
        public void faqs() throws Exception {
            mockMvc.perform (
                            get(String.format("%s/", prefix))
                                    .param("page", "1")
                                    .param("limit", "10")
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content.size()").value(10));
        }

        @Test
        @DisplayName(value = "FAQ 단일")
        public void faq() throws Exception {
            mockMvc.perform (
                            get(String.format("%s/%d", prefix, 1))
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.question").value("QUEST_A"))
                    .andExpect(jsonPath("$.answer").value("ANSWER_A"));
        }
    }


    @Nested
    @DisplayName(value = "FAQ 저장")
    public class SaveFaq{
        @ParameterizedTest
        @DisplayName(value = "저장 성공")
        @Transactional
        @Rollback
        @ValueSource(chars = {'A','B','C','D','E','F','G','H','I','J','K','L'})
        void insertSuccess(char letter) throws Exception {
            SaveFaqRequest request = new SaveFaqRequest();
            request.setQuestion(String.format("QUEST_%c", letter));
            request.setAnswer(String.format("ANSWER_%c", letter));

            mockMvc.perform(
                post(String.format("%s/", prefix))
                .content(mapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isBoolean())
            .andExpect(jsonPath("$").value(true));

        }

        @ParameterizedTest
        @DisplayName(value = "수정 성공")
        @Transactional
        @Rollback
        @ValueSource(
                chars = {'A','B','C','D','E','F','G','H','I','J','K','L'}
        )
        public void modifySuccess(char letter) throws Exception {
            SaveFaqRequest request = new SaveFaqRequest();
            request.setFaqNo((int)letter - 64L);
            request.setQuestion(String.format("QUESTION_%c", letter));
            request.setAnswer(String.format("ANSWER_%c", letter));
//            request.setRegDate(LocalDateTime.now());
            mockMvc.perform(
                            post(String.format("%s/", prefix))
                                    .content(mapper.writeValueAsString(request))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isBoolean())
                    .andExpect(jsonPath("$").value(true));

        }
    }

    @Nested
    @DisplayName(value = "FAQ 삭제")
    @Transactional
    @Rollback
    public class RemoveFaq {
        @Test
        @DisplayName(value = "삭제 성공")
        void removeSuccess() throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d", prefix, 12))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
        }
    }
}