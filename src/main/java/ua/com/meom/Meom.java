package ua.com.meom;

import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;
import ua.com.meom.frames.LandingFrame;

import java.awt.*;
import java.util.Locale;

public class Meom {
    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        FlatHighContrastIJTheme.setup();
        EventQueue.invokeLater(LandingFrame::new);
    }
}