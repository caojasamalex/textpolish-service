package com.example.textpolish_service.utils;

import org.springframework.stereotype.Component;

@Component
public class TagRemover {

    public String removeTags(String text){
        return text.replaceAll("<[^>]+?>", "");
    }
}
