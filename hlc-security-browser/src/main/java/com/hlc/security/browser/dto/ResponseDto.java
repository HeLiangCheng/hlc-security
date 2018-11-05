package com.hlc.security.browser.dto;

/**
 * Created by Liang on 2018/10/19.
 */
public class ResponseDto {

    private String content;

    public ResponseDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
