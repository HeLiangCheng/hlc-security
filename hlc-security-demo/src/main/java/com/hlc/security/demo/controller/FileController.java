package com.hlc.security.demo.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang on 2018/8/28.
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @PostMapping
    @ResponseBody
    public Map<String,String> uploadfile(MultipartFile file) {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        try {
            //保存文件
            String folder = "E:\\Laboratory\\hlc-security";
            File localfile = new File(folder, new Date().getTime() + ".txt");
            file.transferTo(localfile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Map<String, String> relust = new HashMap<String, String>();
        relust.put("filename", file.getName());
        relust.put("size", file.getSize() + "");
        return relust;
    }


    @GetMapping("/down/{id}")
    public void downFile(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        String folder = "E:\\Laboratory\\hlc-security";
        try (InputStream input = new FileInputStream(new File(folder, id + ".txt")); OutputStream stream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment:filename-test.txt");
            IOUtils.copy(input, stream);
            stream.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
