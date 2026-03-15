package com.atm.util;

import javafx.scene.text.Font;

public class FontsLoader {
    public static void loadFonts() {
    	Font.loadFont(FontsLoader.class.getResourceAsStream("/fonts/RobotoRegular.ttf"), 18);
        Font.loadFont(FontsLoader.class.getResourceAsStream("/fonts/RobotoBold.ttf"), 18);
        Font.loadFont(FontsLoader.class.getResourceAsStream("/fonts/InterRegular.ttf"), 18);
        Font.loadFont(FontsLoader.class.getResourceAsStream("/fonts/InterBold.ttf"), 18);
    }
}
