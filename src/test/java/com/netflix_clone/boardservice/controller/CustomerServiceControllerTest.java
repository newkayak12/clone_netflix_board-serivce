package com.netflix_clone.boardservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.boardservice.component.enums.CsType;
import com.netflix_clone.boardservice.repository.dto.reference.CustomerServiceDto;
import com.netflix_clone.boardservice.repository.dto.request.SaveCsRequest;
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
class CustomerServiceControllerTest extends AbstractControllerTest {
    private final String prefix = "/api/v1/cs";

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName(value = "CS 가져오기")
    @Nested
    class FetchCs{
        @Test
        @DisplayName(value = "빈 리스트")
        void emptyList() throws Exception {
            mockMvc.perform(
                get(String.format("%s/", prefix))
                .param("page","1")
                .param("limit", "10")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isEmpty());
        }

        @Test
        @DisplayName(value = "리스트")
        void list() {

        }
        @Test
        @DisplayName(value = "단일")
        void customerService() {

        }
    }

    @DisplayName(value = "저장/답변")
    @Nested
    class SaveCs{

        @Test
        @DisplayName(value = "답변")
        void replyCs() throws Exception {

            mockMvc.perform(
                patch(String.format("%s/reply", prefix))
                    .param("csNo", "11")
                    .param("answer", "A")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));

        }
        @Transactional
        @Rollback
        @ParameterizedTest
        @DisplayName(value = "문의")
        @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,11})
        void saveCs(int number) throws Exception {
            SaveCsRequest request = new SaveCsRequest();
            request.setEmail("newkayak12@gmail.com");
            request.setQuestion("Q"+number);
            CsType type = null;
            if(number % 4 == 1){
                type = CsType.QUESTION;
            } else if( number % 4 == 2){
                type = CsType.TOO_MANY_ERRORS;
            } else if( number % 4 == 3){
                type = CsType.UNCOMFORTABLE;
            } else {
                type = CsType.UNSATISFIED;
            }
            request.setType(type);

            mockMvc.perform(
              post(String.format("%s/", prefix))
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
        }
    }
}