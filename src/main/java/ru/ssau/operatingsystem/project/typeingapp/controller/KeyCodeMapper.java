package ru.ssau.operatingsystem.project.typeingapp.controller;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class KeyCodeMapper {

    private static final Map<KeyCode, String> keyCodeNames = new EnumMap<>(KeyCode.class);
    private static final List<KeyCode> allowedKeys = new ArrayList<>();

    static {
        // Добавим буквы A-Z и цифры основного блока (без клавиш NumPad)
        for (KeyCode code : KeyCode.values()) {
            String name = code.getName();
            if (code.isLetterKey() || (code.isDigitKey() && !code.isKeypadKey())) {
                keyCodeNames.put(code, name);
                allowedKeys.add(code);
            }
        }

        // Добавим функциональные клавиши F1–F12
        for (int i = 1; i <= 12; i++) {
            KeyCode fKey = KeyCode.valueOf("F" + i);
            keyCodeNames.put(fKey, fKey.getName());
            allowedKeys.add(fKey);
        }

        // Добавим специальные клавиши (без numpad)
        KeyCode[] specialKeys = {
            KeyCode.ENTER,
            KeyCode.BACK_SPACE,
            KeyCode.TAB,
            KeyCode.SHIFT,
            KeyCode.CONTROL,
            KeyCode.ALT,
            KeyCode.ESCAPE,
            KeyCode.SPACE,
            KeyCode.DELETE,
            KeyCode.CAPS,
            KeyCode.UP,
            KeyCode.DOWN,
            KeyCode.LEFT,
            KeyCode.RIGHT,
            KeyCode.PAGE_UP,
            KeyCode.PAGE_DOWN,
            KeyCode.HOME,
            KeyCode.END,
            KeyCode.INSERT
        };

        for (KeyCode code : specialKeys) {
            keyCodeNames.put(code, code.getName());
            allowedKeys.add(code);
        }
    }

    /** Получить строковое представление клавиши */
    public static String getName(KeyCode code) {
        return keyCodeNames.get(code);
    }

    /** Получить случайный KeyCode из основной раскладки */
    public static KeyCode getRandomKeyCode() {
        int idx = ThreadLocalRandom.current().nextInt(allowedKeys.size());
        return allowedKeys.get(idx);
    }

    /** Получить случайную пару (KeyCode, его имя) */
    public static Map.Entry<KeyCode, String> getRandomEntry() {
        KeyCode code = getRandomKeyCode();
        return Map.entry(code, getName(code));
    }
}
