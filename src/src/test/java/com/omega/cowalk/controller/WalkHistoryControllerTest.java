package com.omega.cowalk.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class WalkHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private WalkHistoryController walkHistoryController;

    @Test
    void getWalkHistories() throws Exception {
//        this.mockMvc.perform(
//                MockMvcRequestBuilders
//                        .get("/walk-history")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .queryParam("month", "8"))
//                .andExpect()
    }

    @Test
    void addWalkHistory() {
    }
}