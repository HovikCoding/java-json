package com.hhy.javajson.utils;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ResponseHelper {

    public static String convertContentAsString(HttpResponse response) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
