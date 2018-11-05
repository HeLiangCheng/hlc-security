package com.hlc.security.demo;

import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Liang on 2018/8/28.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void uploadfileTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                    .file(new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes())))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void downloadFileTest() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/file/down/1")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
