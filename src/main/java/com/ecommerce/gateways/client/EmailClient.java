package com.ecommerce.gateways.client;

import com.ecommerce.domains.Email;
import com.ecommerce.gateways.EmailGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@RequiredArgsConstructor
public class EmailClient implements EmailGateway {
    private static final String EMAIL_URL = "https://email-node-api.herokuapp.com/email";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void send(Email email) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EMAIL_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(parseEmail(email)))
                .build();

        HttpResponse<String> responseOfString = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + responseOfString.statusCode());
        System.out.println("Body: " + responseOfString.body());

    }

    private String parseEmail(Email email) {
        try {
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            return objectMapper.writeValueAsString(email);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
