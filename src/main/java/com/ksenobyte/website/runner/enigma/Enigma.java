package com.ksenobyte.website.runner.enigma;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

//@SpringBootApplication
public class Enigma implements CommandLineRunner {
    private static final Logger LOGGER = LogManager.getLogger(Enigma.class);
    private static final int dummyCountOfRepetition = 10;
    private static final double distortionRate = 0.2;


    @Override
    public void run(String... args) throws Exception {
        String message = "Hello there! Dummy message to test here.";
        String encrypted = dummyEncrypt(message);
        String distorted = distort(encrypted);
        String decrypted = decrypt(distorted);

        System.out.println("Result:");
        System.out.println(decrypted);
    }

    private String dummyEncrypt(String message) {
        LOGGER.info("Encrypting message:\n" + message);

        StringBuilder sb = new StringBuilder();
        for (char c : message.toCharArray()) {
            for (int i = 0; i < dummyCountOfRepetition; i++) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String distort(String message) {
        LOGGER.info("Distorting message:\n" + message);

        Random random = new Random();

        char[] chars = message.toCharArray();
        char[] distortedChars = new char[chars.length];

        for (int i = 0; i < chars.length; i++) {
            if (Math.random() < distortionRate) {
                distortedChars[i] = chars[random.nextInt(chars.length)];
            } else {
                distortedChars[i] = chars[i];
            }
        }

        return new String(distortedChars);
    }

    private String decrypt(String message) {
        LOGGER.info("Decrypting message:\n" + message);

        StringBuilder sb = new StringBuilder();
        char[] charArray = message.toCharArray();
        for (int i = 0; i < charArray.length; i += dummyCountOfRepetition) {
            HashMap<Character, Integer> map = new HashMap<>();
            for (int j = i; j < i + dummyCountOfRepetition; j++) {
                map.merge(charArray[j], 1, (a, b) -> a + b);
            }

            Optional<Integer> maxCountOpt = map.values().stream().max(Integer::compareTo);
            maxCountOpt.ifPresentOrElse(maxCount -> {
                Optional<Map.Entry<Character, Integer>> first = map.entrySet().stream()
                        .filter(entry -> maxCount.equals(entry.getValue()))
                        .findFirst();
                first.ifPresent(entry -> sb.append(entry.getKey()));
            }, () -> {
                throw new RuntimeException("Can't decode it, sorry");
            });
        }
        return sb.toString();
    }

}
