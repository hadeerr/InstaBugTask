package com.example.instabugtask.presenter;


public class RemoveHtmlTags {

    static  public  String removeTags (String html ){

        String result = html.replaceAll("<(.*?)\\>"," ");
        result = result.replaceAll("<(.*?)\\\n"," ");
        result = result.replaceFirst("(.*?)\\>", " ");
        result = result.replaceAll("&nbsp;"," ");
        result = result.replaceAll("&amp;"," ");
        return result;

    }
}

