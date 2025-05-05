package ru.ssau.operatingsystem.project.typeingapp.controller.strategy;

import javafx.scene.input.KeyEvent;
import ru.ssau.operatingsystem.project.typeingapp.controller.TypingController;
import ru.ssau.operatingsystem.project.typeingapp.utility.ElementStack;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.time.LocalTime;

public class HandleWithErasingStrategy implements HandleStrategy{

    @Override
    public void handleKeyPressed(KeyEvent event, TypingController context) {
        if (!context.isTypingInitialized()) return;
        if (!context.isTypingStarted()) return;
        if (("\r".equals(event.getCharacter()) || "\n".equals(event.getCharacter()))) return;
        if (context.getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        char enteredKey = event.getCharacter().charAt(0);
        context.setTextEnteredButton(event, enteredKey);
        char currentKey = context.getOverlayText().getText().charAt(0);
//        System.out.println("enteredKey = " + enteredKey);
//        System.out.println("currentKey = " + currentKey);
        if (enteredKey == currentKey){
//            System.out.println("Правильный символ");
            context.getEnteredText().setText(context.getEnteredText().getText() + enteredKey);
            context.getOverlayText().setText(context.getOverlayText().getText().substring(1));
            context.setCurrIndex(context.getCurrIndex()+1);
//            System.out.println(currIndex);
        }
        else {
            if ("\b".equals(event.getCharacter())) {
                if (!context.getEnteredText().getText().isEmpty()) {
                    context.setCurrIndex(context.getCurrIndex()-1);
//                    System.out.println(stack.isEmpty());
//                    if (!context.getStack().isEmpty()) System.out.println(stack.peek().getErrorIndex() + " " + stack.peek().getCorrectSymbol());

                    if (context.getStack().isEmpty() || context.getStack().peek().getErrorIndex() != context.getCurrIndex()) {
//                        System.out.println("Стирание без стека");
                        char deletedChar = context.getEnteredText().getText().charAt(context.getEnteredText().getText().length() - 1);
                        context.getEnteredText().setText(context.getEnteredText().getText().substring(0, context.getEnteredText().getText().length() - 1));
                        context.getOverlayText().setText(deletedChar + context.getOverlayText().getText());
                    }
                    else {
//                        System.out.println("Стирание со стеком");
                        char correctSymbol = context.getStack().pop().getCorrectSymbol();
                        context.getEnteredText().setText(context.getEnteredText().getText().substring(0, context.getEnteredText().getText().length() - 1));
                        context.getOverlayText().setText(correctSymbol + context.getOverlayText().getText());

                        int errorCount = context.getCalculator().getCurrStats().getErrorCount();
                        context.getCalculator().getCurrStats().setErrorCount(--errorCount);
                    }
//                    System.out.println(currIndex);
                }
            }
            else {
//                System.out.println("Неправильный символ");
                context.getEnteredText().setText(context.getEnteredText().getText() + enteredKey);
                context.getOverlayText().setText(context.getOverlayText().getText().substring(1));
                context.getStack().push(new ElementStack(context.getCurrIndex(), currentKey));
                context.setCurrIndex(context.getCurrIndex()+1);
//                System.out.println(currIndex);

                int errorCount = context.getCalculator().getCurrStats().getErrorCount();
                context.getCalculator().getCurrStats().setErrorCount(++errorCount);
            }
        }
        context.getCalculator().calculateStats(context.getEnteredText().getText());
        context.getCalculator().updateStats(context.getSymbolsCountLabel(), context.getErrorCountLabel(), context.getSpeedLabel());

        resultStatistic(context);
    }

    private void resultStatistic(TypingController context){
        if (context.getOverlayText().getText().isEmpty()){
            context.getCalculator().getTimeline().stopTimer();
            context.getResultPanel().setVisible(true);
            context.getCalculator().setFinalTime(context.getTimerLabel().getText());
            context.getCalculator().setDataResultPanel(
                    context.getTextLength(),
                    context.getResultAccuracyLabel(),
                    context.getResultTimeLabel(),
                    context.getResultSpeedLabel()
            );

            boolean isBest = Utility.getUserTimeService().updateBestTime(
                    Utility.getCurrentMode(),
                    Utility.getCurrentLanguage(),
                    Utility.getCurrentLanguageType(),
                    LocalTime.ofSecondOfDay(context.getCalculator().getTimeline().getElapsedSeconds())
            );
            if (isBest) context.getNewRecordPanel().setVisible(true);
        }
    }
}
