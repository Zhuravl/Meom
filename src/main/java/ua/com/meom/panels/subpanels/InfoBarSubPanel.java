package ua.com.meom.panels.subpanels;

import ua.com.meom.constants.Constants;

import javax.swing.*;
import java.io.Serial;
import java.util.Locale;
import java.util.ResourceBundle;

public class InfoBarSubPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private JLabel levelLabel, scoresLabel, mistakesLabel;
    private JTextField levelField, scoreField, mistakesField;

    public InfoBarSubPanel() {
        this.setLayout(null);

        GUI();
    }

    /**
     * Refreshes GUI to pull the latest data
     *
     * @param level    the current level
     * @param score    the current score
     * @param mistakes the current mistakes
     */
    public void refreshGUI(int level, int score, int mistakes) {
        levelField.setText(String.valueOf(level));
        scoreField.setText(String.valueOf(score));
        mistakesField.setText(String.valueOf(mistakes));
    }

    /**
     * Initiates and configures the UI elements
     */
    private void GUI() {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.Common.LOCALE_PREFIX, locale);

        levelLabel = new JLabel(rb.getString("level_label") + ":");
        levelLabel.setFont(Constants.Common.FONT_HINT);
        levelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        levelLabel.setBounds(Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_WIDTH / 5, Constants.Common.BUTTON_HEIGHT / 2);
        this.add(levelLabel);

        levelField = new JTextField();
        levelField.setBounds(levelLabel.getX() + levelLabel.getWidth() + Constants.Common.ELEMENTS_CLEARANCE, levelLabel.getY(), Constants.Common.BUTTON_WIDTH / 5, Constants.Common.BUTTON_HEIGHT / 2);
        levelField.setEnabled(false);
        this.add(levelField);

        scoresLabel = new JLabel(rb.getString("score_label") + ":");
        scoresLabel.setFont(Constants.Common.FONT_HINT);
        scoresLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoresLabel.setBounds(levelField.getX() + levelField.getWidth() + Constants.Common.ELEMENTS_CLEARANCE, levelField.getY(), Constants.Common.BUTTON_WIDTH / 5, Constants.Common.BUTTON_HEIGHT / 2);
        this.add(scoresLabel);

        scoreField = new JTextField();
        scoreField.setBounds(scoresLabel.getX() + scoresLabel.getWidth() + Constants.Common.ELEMENTS_CLEARANCE, scoresLabel.getY(), Constants.Common.BUTTON_WIDTH / 5, Constants.Common.BUTTON_HEIGHT / 2);
        scoreField.setEnabled(false);
        this.add(scoreField);

        mistakesLabel = new JLabel(rb.getString("mistakes_label") + ":");
        mistakesLabel.setFont(Constants.Common.FONT_HINT);
        mistakesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        mistakesLabel.setBounds(scoreField.getX() + scoreField.getWidth() + Constants.Common.ELEMENTS_CLEARANCE, scoreField.getY(), Constants.Common.BUTTON_WIDTH / 5, Constants.Common.BUTTON_HEIGHT / 2);
        this.add(mistakesLabel);

        mistakesField = new JTextField();
        mistakesField.setBounds(mistakesLabel.getX() + mistakesLabel.getWidth() + Constants.Common.ELEMENTS_CLEARANCE, mistakesLabel.getY(), Constants.Common.BUTTON_WIDTH / 5, Constants.Common.BUTTON_HEIGHT / 2);
        mistakesField.setEnabled(false);
        this.add(mistakesField);
    }
}
