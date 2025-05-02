package ru.ssau.operatingsystem.project.typeingapp.controller.strategy;

import javafx.scene.input.KeyEvent;
import ru.ssau.operatingsystem.project.typeingapp.controller.TypingController;
import ru.ssau.operatingsystem.project.typeingapp.utility.ElementStack;
import ru.ssau.operatingsystem.project.typeingapp.utility.Utility;

import java.time.LocalTime;

public class HandleDefaultStrategy implements HandleStrategy{

    @Override
    public void handleKeyPressed(KeyEvent event, TypingController context) {
        if (!context.isTypingInitialized()) return;
        if (context.getOverlayText().getText().isEmpty()) return;
        if (event.getCharacter().isEmpty()) return;

        if (("\r".equals(event.getCharacter()) || "\n".equals(event.getCharacter()))) return;

        char enteredKey = event.getCharacter().charAt(0);
        context.setTextEnteredButton(event, enteredKey);
        char currentKey = context.getOverlayText().getText().charAt(0);
        if (enteredKey == currentKey){
            context.getEnteredText().setText(context.getEnteredText().getText() + enteredKey);
            context.getOverlayText().setText(context.getOverlayText().getText().substring(1));
            context.setCurrIndex(context.getCurrIndex()-1);
//            System.out.println(currIndex);
        }
        else {
            if (context.getStack().isEmpty() || context.getStack().peek().getErrorIndex()!= context.getCurrIndex()) {
                context.getStack().push(new ElementStack(context.getCurrIndex(), currentKey));
                int errorCount = context.getCalculator().getCurrStats().getErrorCount();
                context.getCalculator().getCurrStats().setErrorCount(++errorCount);
//                System.out.println(currIndex);
            }

        }
        context.getCalculator().calculateStats(context.getEnteredText().getText());
        context.getCalculator().updateStats(context.getSymbolsCountLabel(), context.getErrorCountLabel(), context.getSpeedLabel());

        if (context.getOverlayText().getText().isEmpty()){
            context.getCalculator().getTimeline().stopTimer();
//            getResultPanel().setVisible(true);

            Utility.getUserTimeService().updateBestTime(
                    Utility.getCurrentMode(),
                    Utility.getCurrentLanguage(),
                    Utility.getCurrentLanguageType(),
                    LocalTime.ofSecondOfDay(context.getCalculator().getTimeline().getElapsedSeconds())
            );
        }
    }
}
