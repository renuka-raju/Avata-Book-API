package com.avata.assestment.Scheduler;

import com.avata.assestment.CacheLayer.InMemoryStorage;
import com.avata.assestment.Resources.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class BookDownloadScheduler {

    @Scheduled(fixedRate = 4000)
    public void scheduleTaskWithFixedRate() {
        RestTemplate restTemplate = new RestTemplate();
        String responseEntity = restTemplate.postForObject("https://di37ol03g7.execute-api.us-west-2.amazonaws.com/dev/", null, String.class);
        //System.out.println(responseEntity);
        String strjson=responseEntity.replace("{\"results\":","");
        ObjectMapper objectMapper = new ObjectMapper();
        String json=strjson.substring(0,strjson.length()-1);
        //System.out.println(json);
        //JSON String to List conversion
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        TypeReference<List<Book>> mapType = new TypeReference<List<Book>>() {};
        List<Book> jsonToBookList = null;
        try {
            jsonToBookList = objectMapper.readValue(json, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(jsonToBookList.size());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        InMemoryStorage.getInstance().setCapacity(50);
        InMemoryStorage.getInstance().saveBooksMap(jsonToBookList,timestamp.getTime());
    }
}
