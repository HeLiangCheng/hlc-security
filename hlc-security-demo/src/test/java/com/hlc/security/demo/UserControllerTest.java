package com.hlc.security.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Liang on 2018/8/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public  void setup(){
        mockMvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void querySuccess() throws  Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                .param("name", "testuser")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()")
                .value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void queryByPageSuccess() throws  Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                .param("name", "testuser")
                .param("agefrom", "10")
                .param("ageto", "20")
                .param("page","12")
                .param("size","100")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()")
                .value(3)).andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void queryUserModel() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                        .value("test"));
    }

    @Test
    public void saveUserModel() throws  Exception{
        String body="{\"name\":\"tom\",\"password\":\"123455\",\"email\":\"tom@126.com\",\"birthdate\":\""+new Date().getTime()+"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }


    @Test
    public void saveUserModelValidate() throws  Exception{
        String body="{\"name\":\"tom\",\"password\":null,\"email\":\"tom@126.com\",\"birthdate\":\""+new Date().getTime()+"\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void updateUserModel() throws Exception {
        Date birth = new Date(LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        String body = "{\"id\":\"10\",\"name\":\"tom\",\"password\":\"123456\",\"email\":\"tom@126.com\",\"birthdate\":\"" + birth.getTime() + "\"}";
        String content = mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("10"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }

    @Test
    public void deleteUserTest() throws  Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);
    }


}
