package com.example.vincentwang.rainbowtranslate.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by vincentwang on 2017/8/18.
 */

public class JsoupUtil {

    private static JsoupUtil INSTANCE;

    public static JsoupUtil getInstance(){
        if(INSTANCE == null){
            INSTANCE = new JsoupUtil();
        }
        return INSTANCE;
    }

    public void getHttp(String url, final ResponseCallback responseCallback) throws Exception{
            Document doc =Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.3")
                    .get();
            responseCallback.onResponse(doc);
    }


    public interface ResponseCallback{
        void onResponse(Document document) throws Exception;
    }
}
