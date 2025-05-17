package ru.ssau.operatingsystem.project.typeingapp.textProviders;

import java.util.Random;

public class RandomStringTextProvider implements TypingTextProvider{
    private final int count_words;
    private final String[] words;

    public RandomStringTextProvider(int count_words, String[] words) {
        if(count_words <= 0)
            throw new IllegalArgumentException("Count words must be a natural number");

        if(words.length == 0)
            throw new IllegalArgumentException("Number of words must be at least 1");

        this.count_words = count_words;
        this.words = words;
    }

    private String nextString(Random random){
        return words[random.nextInt(words.length)];
    }

    @Override
    public String generate() {
        Random random = new Random();

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < count_words; i++)
            str.append(nextString(random)).append(" ");
        str.deleteCharAt(str.length()-1);

        return str.toString();
    }
}
