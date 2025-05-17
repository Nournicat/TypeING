package ru.ssau.operatingsystem.project.typeingapp.utility;

public class ElementStack {
    private final int errorIndex;
    private final char correctSymbol;

    public ElementStack(int index, char correctSymbol){
        this.errorIndex = index;
        this.correctSymbol = correctSymbol;
    }

    public int getErrorIndex(){
        return errorIndex;
    }

    public char getCorrectSymbol(){
        return correctSymbol;
    }
}
