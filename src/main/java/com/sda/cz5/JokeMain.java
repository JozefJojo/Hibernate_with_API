package com.sda.cz5;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Scanner;

public class JokeMain {
    private  static ObjectMapper objectMapper=new ObjectMapper();
    public static void main(String[] args) throws IOException {
        //objectMapper.readValue(URI.create(url).toURL(), clazz);
        Scanner scanner = new Scanner(System.in);
        do{
            String pocetVtipu = scanner.nextLine();
            if("exit".equals(pocetVtipu)){
                break;
            }
            var joke = objectMapper.readValue(URI.create("https://v2.jokeapi.dev/joke/Any?lang=cs&amount=" + pocetVtipu).toURL(), Joke.class);
            System.out.println(joke);
        }while (true);
    }
}
