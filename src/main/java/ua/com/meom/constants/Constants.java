package ua.com.meom.constants;

import java.awt.*;

/**
 * This class contains all constants used in the application
 */
public class Constants {

    public static class Common {
        public static final String APP_NAME = "Meom";
        public static final String APP_PROMO = "Programming tutor";
        public static final String LOCALE_PREFIX = "Locale";
        public static final String RANKING_FILE_NAME = "Data.mem";
        public static final int USERNAME_MIN = 2;
        public static final int USERNAME_MAX = 20;
        public static final int LANDING_WINDOW_WIDTH = 700;
        public static final int LANDING_WINDOW_HEIGHT = 600;
        public static final int MAIN_WINDOW_WIDTH = 1080;
        public static final int MAIN_WINDOW_HEIGHT = (int) (MAIN_WINDOW_WIDTH / ((Math.sqrt(5) + 1) / 2));
        public static final int WINDOW_TITLE_BAR_HEIGHT = 28;
        public static final int BUTTON_WIDTH = 400;
        public static final int BUTTON_HEIGHT = 100;
        public static final int ELEMENTS_CLEARANCE = 20;
        public static final Font FONT_LOGO = new Font("Tahoma", Font.PLAIN, 100);
        public static final Font FONT_KEY = new Font("Tahoma", Font.BOLD, 40);
        public static final Font FONT_MAIN = new Font("Tahoma", Font.PLAIN, 26);
        public static final Font FONT_HINT = new Font("Tahoma", Font.PLAIN, 14);
        public static final Color LOGO_COLOR = new Color(213, 211, 209);
    }

    public static class Game {
        public static final int SQUARES_HORIZONTAL_NUMBER_BOARD = 5;
        public static final int SQUARES_VERTICAL_NUMBER_BOARD = 9;
        public static final int SQUARE_SIDE_BOARD = 75;
        public static final Color SQUARES_COLOR_BRIGHT = Color.LIGHT_GRAY;
        public static final Color SQUARES_COLOR_DARK = Color.GRAY;
        public static final int SQUARES_HORIZONTAL_NUMBER_LOGGING = 2;
        public static final int SQUARES_VERTICAL_NUMBER_LOGGING = 16;
        public static final int SQUARE_SIDE_LOGGING = 50;
    }

    public static class Screen {
        public static final String REGISTER = "Register";
        public static final String SETTINGS = "Settings";
        public static final String BRIEFING = "Briefing";
        public static final String GAME = "Game";
        public static final String RANKING = "Ranking";
    }
}
