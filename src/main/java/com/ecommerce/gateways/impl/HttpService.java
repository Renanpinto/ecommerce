package com.ecommerce.gateways.impl;


import com.ecommerce.exceptions.HttpErrorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpService {

    public Stream<String> doGet(String url) throws HttpErrorException {
        StringBuilder builder = new StringBuilder();

        try (
                InputStream inputStream = new URL(url).openStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            builder.append(reader.lines().collect(Collectors.joining()));
        } catch (IOException e) {
            throw new HttpErrorException();
        }

        return Stream.of(builder.toString().split("\n"));
    }
}
