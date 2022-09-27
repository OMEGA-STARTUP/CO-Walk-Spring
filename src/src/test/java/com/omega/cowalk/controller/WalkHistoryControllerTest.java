package com.omega.cowalk.controller;

import com.omega.cowalk.TestSecurityConfig;
import com.omega.cowalk.service.WalkHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(
        classes = {
                ServletWebServerFactoryAutoConfiguration.class,
                WalkHistoryController.class
        },
        webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import(TestSecurityConfig.class)
class WalkHistoryControllerTest {
    @Autowired
    private TestSecurityConfig testSecurityConfig;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalkHistoryService walkHistoryService;
    // MVC 환경의 테스트를 위한 WebApplicationContext 세팅 및 시큐리티 세팅
    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(new WalkHistoryController(walkHistoryService))
                .setCustomArgumentResolvers(testSecurityConfig)
                .build();
    }

    @Test
    void getWalkHistories() throws Exception {
        // given
        String requestUri = "/walk-history";
        MultiValueMap<String, String> requestParamMap = new LinkedMultiValueMap<>();
        requestParamMap.add("month", "9");

        // when, then
        mockMvc
            .perform(get(requestUri)
            .queryParams(requestParamMap))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print());
    }

    @Test
    void addWalkHistory() throws Exception {
        // given
        String requestUri = "/walk-history";

        // when, then
        mockMvc
            .perform(post(requestUri))
            .andExpect(status().isOk())
            .andDo(print());
    }
}