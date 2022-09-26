package com.omega.cowalk.controller;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omega.cowalk.domain.dto.NicknamePatchRequestDto;
import com.omega.cowalk.domain.dto.RegisterSendCodeRequestDto;
import com.omega.cowalk.domain.dto.SoundPictureUpdateRequestDto;
import com.omega.cowalk.domain.entity.user.Role;
import com.omega.cowalk.domain.entity.user.User;
import com.omega.cowalk.security.auth.PrincipalUserDetails;
import com.omega.cowalk.security.token.JwtAuthenticationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.Principal;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    UserController userController;

    /*
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //This
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }
     */


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Principal getPrincipal(){
        User user = User.builder().id(Long.valueOf(2)).role(Role.USER).build();
        PrincipalUserDetails principalUserDetails = new PrincipalUserDetails(new User());
        Principal principal = new JwtAuthenticationToken(principalUserDetails, null, principalUserDetails.getAuthorities());
        return principal;
    }

    @Test
    void sendEmailCode() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/user/register/email/send")
                        .content(asJsonString(new RegisterSendCodeRequestDto("harryjung0330@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void checkCode() {

    }

    @Test
    void registerUser() {

    }

    @Test
    void nicknamePatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/profile/nickname")
                .principal(getPrincipal())
                .content(asJsonString(new NicknamePatchRequestDto("updateFromTest")))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    private MultipartFile getFileFromResource(String fileName) throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            

            File file = new File(resource.toURI());
            MultipartFile result = new MockMultipartFile("test",
                    "test", "image/jpeg", IOUtils.toByteArray(new FileInputStream(file)));
            return result;
        }

    }

    @Test
    void profileUpdate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/profile/user-picture")
                .principal(getPrincipal())
                .content(getFileFromResource("water.jpg").getBytes())
                .accept(MediaType.MULTIPART_FORM_DATA)
        ).andExpect(status().isOk());
    }

    @Test
    void soundPictureUpdate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/profile/sound-picture")
                .principal(getPrincipal())
                .content(asJsonString(new SoundPictureUpdateRequestDto(1)))
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void getUserProfile() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/profile")
                .principal(getPrincipal())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void sendIdToEmail() throws  Exception {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/pw-inquiry/email/send")
                        .content(asJsonString(new RegisterSendCodeRequestDto("harryjung0330@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void sendCodeForPassword() {
    }

    @Test
    void checkCodeForPassword() {
    }

    @Test
    void updatePassword() {
    }
}