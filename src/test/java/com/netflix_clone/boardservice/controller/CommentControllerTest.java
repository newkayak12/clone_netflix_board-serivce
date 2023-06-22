package com.netflix_clone.boardservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.boardservice.repository.dto.reference.CommentDto;
import com.netflix_clone.boardservice.repository.dto.reference.ProfileDto;
import com.netflix_clone.boardservice.util.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.awt.*;

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
class CommentControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/comment";

    @Autowired
    private ObjectMapper mapper;

   @DisplayName(value = "가져오기")
   @Nested
   class FetchComment {
        @Test
        @DisplayName(value = "리스트")
        void comments() throws Exception {
            mockMvc.perform(
                get(String.format("%s/", prefix))
                .param("tableNo", "1")
                .param("page", "1")
                .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.sized()").value(10));
        }

        @Test
        @DisplayName(value = "단일")
        void comment() throws Exception {
            mockMvc.perform(
                    get(String.format("%s/%d", prefix, 1))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentNo").value(1))
                .andExpect(jsonPath("$.profile.profileNo").value(18))
                .andExpect(jsonPath("$.profile.profileName").value("PROFILE_NUMBER_1"))
                .andExpect(jsonPath("$.contentsNo").value(1))
                .andExpect(jsonPath("$.comments").value("COMMENT_1"))
                .andExpect(jsonPath("$.star").value(1.0));
        }
    }

   @Nested
   @DisplayName(value = "댓글 작성/수정")
   class SaveComment {
       @ParameterizedTest
       @DisplayName(value = "작성")
       @CsvFileSource(resources = "/csv/saveComment.csv", numLinesToSkip = 1)
       void insert(Long contentsNo, Long profileNo, Double star,String comments) throws Exception {
           CommentDto dto = new CommentDto(
                   null,
                   new ProfileDto(profileNo, null),
                   contentsNo,
                   comments,
                   star,
                   null,
                   null
           );

           mockMvc.perform(
               post(String.format("%s/", prefix))
               .contentType(MediaType.APPLICATION_JSON)
               .content(mapper.writeValueAsString(dto))
           )
           .andExpect(status().isOk())
           .andExpect(jsonPath("$").isBoolean())
           .andExpect(jsonPath("$").value(true));
       }

       @ParameterizedTest
       @DisplayName(value = "수정")
       @CsvFileSource(resources = "/csv/modifyComment.csv", numLinesToSkip = 1)
       void modify(Long commentNo, Long contentsNo, Long profileNo, Double star,String comments) throws Exception {
           CommentDto dto = new CommentDto(
                   commentNo,
                   new ProfileDto(profileNo, null),
                   contentsNo,
                   comments,
                   star,
                   null,
                   null
           );

           mockMvc.perform(
                       post(String.format("%s/", prefix))
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(mapper.writeValueAsString(dto))
                   )
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$").isBoolean())
                   .andExpect(jsonPath("$").value(true));
       }
   }

    @Nested
    @DisplayName(value = "댓글 삭제")
    class RemoveComment {
        @Test
        @DisplayName(value = "삭제")
        void remove() throws Exception {
            mockMvc.perform(
                delete(String.format("%s/%d", prefix, 1))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isBoolean())
            .andExpect(jsonPath("$").value(true));
        }
    }
}