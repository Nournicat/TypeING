package ru.ssau.operatingsystem.project.typeingapp;

public class RandomTextProvider implements TypingTextProvider {
    private int count_words;
    private RandomString stringGenerator;

    public RandomTextProvider(int count_words, RandomString generator) {
        this.count_words = count_words;
        this.stringGenerator = generator;
    }

    @Override
    public String generate() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < count_words; i++)
            str.append(stringGenerator.nextString()).append(" ");
        str.deleteCharAt(str.length()-1);

        return str.toString();
    }

}
