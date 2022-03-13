package com.hhy.javajson.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.hhy.javajson.model.User;
import com.hhy.javajson.utils.ResponseHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HttpClient httpClient;

    private int idCreated;

    private static final String TOKEN = "Bearer 779cfecc7dc5a79aae8964f21999a48ec342a64d38e7200a60ea114c3329ddda";
    private static final String BASE_URI_USERS = "https://gorest.co.in/public/v2/users";

    public void process() {
        handleAndIterateListItems(false);
        System.out.println();
        handleAndCreateSingleItem();
        System.out.println();
        handleAndDeleteItem();
    }

    private void handleAndIterateListItems(boolean hasFilter) {
        String json = sendAndReceiveWithHttpGet();
        List<User> users = new ArrayList<>();

        try {
            users = objectMapper.readValue(json, new TypeReference<List<User>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (hasFilter) {
            for (User user : users) {
                if (user.getName().equals("hhan")) {
                    this.idCreated = user.getId();
                    System.out.println(String.format("User with id %s is created.", idCreated));
                }
            }

        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }

    }

    private void handleAndCreateSingleItem() {
        User userCreated = new User();
        userCreated.setId(222);
        userCreated.setEmail("hhan@gmail.com");
        userCreated.setName("hhan");
        userCreated.setGender("male");
        userCreated.setStatus("active");

        String userJson = null;
        try {
            userJson = objectMapper.writeValueAsString(userCreated);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (!Strings.isNullOrEmpty(userJson)) {
            sendWithHttpPost(userJson);
        }

        handleAndIterateListItems(true);
    }

    private void handleAndDeleteItem() {
        sendWithHttpDelete(this.idCreated);
        handleAndIterateListItems(false);
    }

    private String sendAndReceiveWithHttpGet(){
        HttpGet httpGet = new HttpGet(BASE_URI_USERS);
        httpGet.addHeader("Authorization", TOKEN);
        HttpResponse response = null;

        try {
            response = this.httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response == null) {
            throw new RuntimeException("The Response is null!");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode > 300) {
            throw new RuntimeException("The process is NOT executed as expected: HTTP status code : "
                    + response.getStatusLine().getStatusCode());
        }

        return ResponseHelper.convertContentAsString(response);
    }

    private void sendWithHttpPost(String json) {
        HttpPost httpPost = new HttpPost(BASE_URI_USERS);
        httpPost.addHeader("Authorization", TOKEN);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        try {
            httpPost.setEntity(new StringEntity(json));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = this.httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response == null) {
            throw new RuntimeException("The Response is null!");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode > 300) {
            throw new RuntimeException("The process is NOT executed as expected: HTTP status code : "
                    + response.getStatusLine().getStatusCode());
        }
    }

    private void httpPatchProcess() {

    }

    private void sendWithHttpDelete(int id) {
        HttpDelete httpDelete = new HttpDelete(BASE_URI_USERS + "/" + id);
        httpDelete.addHeader("Authorization", TOKEN);

        HttpResponse response = null;
        try {
            response = this.httpClient.execute(httpDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response == null) {
            throw new RuntimeException("The Response is null!");
        }

        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < 200 || statusCode > 300) {
            throw new RuntimeException("The process is NOT executed as expected: HTTP status code : "
                    + response.getStatusLine().getStatusCode());
        }

        System.out.println("Deleted!");
    }
}
