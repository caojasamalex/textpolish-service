package com.example.textpolish_service.utils;

import org.springframework.stereotype.Component;

@Component
public class TagRemover {

    public static String removeTags(String text){
        return text.replaceAll("<[^>]+?>", "");
    }
}
