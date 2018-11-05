package com.hlc.security.demo.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Created by Liang on 2018/8/30.
 */
public class WireMockMain {

    public static void main(String[] args){
        System.out.println("wiremock...");
        WireMock.configureFor(9999);
        WireMock.removeAllMappings();

        //伪造一个测试桩
        /*
        stubFor(get(urlPathEqualTo("/order/1")).willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "text/json")
                .withBody("<response>hello  wiremock</response>")));
        */

        try {
            ClassPathResource resource =new ClassPathResource("mock/response/01.txt");
            String content= FileUtils.readFileToString(resource.getFile(), "UTF-8");
            stubFor(get(urlPathEqualTo("/order/1")).willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "text/json")
                    .withBody(content)));
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

}
