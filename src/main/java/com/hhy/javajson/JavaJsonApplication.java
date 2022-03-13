package com.hhy.javajson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhy.javajson.model.User;
import com.hhy.javajson.services.BusinessService;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootApplication
public class JavaJsonApplication implements CommandLineRunner {
    @Autowired
    BusinessService businessService;

    public static void main(String[] args) {
        SpringApplication.run(JavaJsonApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        businessService.process();
    }
}
