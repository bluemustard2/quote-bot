package com.github.bluemustard2;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String quoteChoice;
    private static Random rng;
    private static int index;

    public static void main(String[] args) {
        // Enable debug logging
        FallbackLoggerConfiguration.setDebug(true);

        // Enable trace logging
        FallbackLoggerConfiguration.setTrace(true);

        DiscordApi api = new DiscordApiBuilder()
                .setToken(System.getProperty("QUOTE_BOT_TOKEN"))
                .login()
                .join();

        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!quote")) {
                event.getChannel().sendMessage(quoteSelector());
            }
        });
    }

    public static String quoteSelector(){
        File file = new File("quotes.txt");
        try {
            Scanner fileReader = new Scanner(file);
            rng = new Random();
            ArrayList<String> quoteList = new ArrayList<>();

            while (fileReader.hasNext()){
                String line = fileReader.nextLine();
                quoteList.add(line);
            }

            index = rng.nextInt(quoteList.size()-1);
            quoteChoice = quoteList.get(index);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return quoteChoice;
    }
}
