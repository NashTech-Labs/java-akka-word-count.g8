package com.knoldus;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class WordCountActor extends AbstractActor {
    
    LoggingAdapter logger = Logging.getLogger(getContext().system(), this);
    private static int counter = 0;
    private BufferedReader bufferedReader;
    
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, message -> wordCount())
                .matchAny(o -> logger.info("received unknown message"))
                .build();
    }
    
    private void wordCount() throws IOException {
        
        bufferedReader = readFile();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.split(" ");
            counter = words.length + counter;
        }
        logger.info("Number of words in file are: " + counter);
    }
    
    private BufferedReader readFile() throws UnsupportedEncodingException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("file");
        Reader reader = new InputStreamReader(in, "UTF-8");
        
        return new BufferedReader(reader);
    }
    
}

