/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.koichatapp.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author datcy
 */
public class BaseAPIServices {

    private static final String API_URL = "https://api.pawan.krd/v1/chat/completions";
    private static final String API_KEY = "pk-vSkDPrKJUAtPMOTqLiwUeAjhBmxtITcLNXgSmOAxCmigaaOs";
    private static final int MAX_TOKEN = 2000;

    public static BaseAPIServices getInstance() {
        return new BaseAPIServices();
    }

    public String generateResponse(String message) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            String requestBody = "{\n"
                    + "    \"model\": \"gpt-3.5-turbo\",\n"
                    + "    \"max_tokens\": " + MAX_TOKEN + ",\n"
                    + "    \"messages\": [\n"
                    + "        {\n"
                    + "            \"role\": \"system\",\n"
                    + "            \"content\": \"You are an helpful assistant.\"\n"
                    + "        },\n"
                    + "        {\n"
                    + "            \"role\": \"user\",\n"
                    + "            \"content\": \"" + StringEscapeUtils.escapeJson(message) + "\"\n"
                    + "        }\n"
                    + "    ]\n"
                    + "}";
//            String requestBody = "{\n"
//                    + "    \"model\": \"text-davinci-003\",\n"
//                    + "    \"prompt\": \"" + StringEscapeUtils.escapeJson(message) + "\",\n"
//                    + "    \"temperature\": 1,\n"
//                    + "    \"max_tokens\": " + MAX_TOKEN + ",\n"
//                    + "    \"stop\": [\n"
//                    + "        \"Human:\",\n"
//                    + "        \"AI:\"\n"
//                    + "    ]\n"
//                    + "}";
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();
                return response.toString();
            } else {
                connection.disconnect();
                throw new Exception("Lỗi kết nối, vui lòng kiểm tra đường truyền!");
            }

        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
            return e.getMessage();
        }
    }
}
