package ua.com.meom.panels;

import ua.com.meom.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class BriefingPanel extends JPanel {

    private JPanel contentPane;
    private JLabel briefingLabel1, briefingLabel2, briefingLabel3, briefingLabel4, briefingLabel5, briefingLabel6, briefingLabel7, briefingLabel8;
    private JButton startButton, backButton;
    private GamePanel gamePanel;

    public BriefingPanel(JPanel contentPane, GamePanel gamePanel) {
        this.contentPane = contentPane;
        this.gamePanel = gamePanel;
        this.setLayout(null);

        GUI();
    }

    /**
     * Initiates and configures the UI elements
     */
    private void GUI() {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.Common.LOCALE_PREFIX, locale);

        briefingLabel1 = new JLabel(rb.getString("briefing_label_1"));
        briefingLabel1.setFont(Constants.Common.FONT_MAIN);
        briefingLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        briefingLabel1.setBounds(0, Constants.Common.ELEMENTS_CLEARANCE * 5, Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel1);

        briefingLabel2 = new JLabel(rb.getString("briefing_label_2"));
        briefingLabel2.setFont(Constants.Common.FONT_HINT);
        briefingLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        briefingLabel2.setBounds(Constants.Common.ELEMENTS_CLEARANCE * 3, briefingLabel1.getY() + briefingLabel1.getHeight() + (Constants.Common.ELEMENTS_CLEARANCE * 2), Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel2);

        briefingLabel3 = new JLabel(rb.getString("briefing_label_3"));
        briefingLabel3.setFont(Constants.Common.FONT_HINT);
        briefingLabel3.setHorizontalAlignment(SwingConstants.LEFT);
        briefingLabel3.setBounds(Constants.Common.ELEMENTS_CLEARANCE * 3, briefingLabel2.getY() + briefingLabel2.getHeight(), Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel3);

        briefingLabel4 = new JLabel(rb.getString("briefing_label_4"));
        briefingLabel4.setFont(Constants.Common.FONT_HINT);
        briefingLabel4.setHorizontalAlignment(SwingConstants.LEFT);
        briefingLabel4.setBounds(Constants.Common.ELEMENTS_CLEARANCE * 3, briefingLabel3.getY() + briefingLabel3.getHeight(), Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel4);

        briefingLabel5 = new JLabel(rb.getString("briefing_label_5"));
        briefingLabel5.setFont(Constants.Common.FONT_HINT);
        briefingLabel5.setHorizontalAlignment(SwingConstants.LEFT);
        briefingLabel5.setBounds(Constants.Common.ELEMENTS_CLEARANCE * 3, briefingLabel4.getY() + briefingLabel4.getHeight(), Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel5);

        briefingLabel6 = new JLabel(rb.getString("briefing_label_6"));
        briefingLabel6.setFont(Constants.Common.FONT_HINT);
        briefingLabel6.setHorizontalAlignment(SwingConstants.LEFT);
        briefingLabel6.setBounds(Constants.Common.ELEMENTS_CLEARANCE * 3, briefingLabel5.getY() + briefingLabel5.getHeight(), Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel6);

        briefingLabel7 = new JLabel(rb.getString("briefing_label_7"));
        briefingLabel7.setFont(Constants.Common.FONT_HINT);
        briefingLabel7.setHorizontalAlignment(SwingConstants.LEFT);
        briefingLabel7.setBounds(Constants.Common.ELEMENTS_CLEARANCE * 3, briefingLabel6.getY() + briefingLabel6.getHeight(), Constants.Common.MAIN_WINDOW_WIDTH, 40);
        this.add(briefingLabel7);

        backButton = new JButton(rb.getString("back_button"));
        backButton.setFont(Constants.Common.FONT_MAIN);
        backButton.setBounds(Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.MAIN_WINDOW_HEIGHT - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_WIDTH / 2, Constants.Common.BUTTON_HEIGHT);
        backButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            CardLayout cardLayout = (CardLayout) contentPane.getLayout();
            cardLayout.show(contentPane, Constants.Screen.SETTINGS);
        }));
        this.add(backButton);

        startButton = new JButton(rb.getString("start_button"));
        startButton.setFont(Constants.Common.FONT_MAIN);
        startButton.setBounds(Constants.Common.MAIN_WINDOW_WIDTH - Constants.Common.BUTTON_WIDTH - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.MAIN_WINDOW_HEIGHT - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_WIDTH, Constants.Common.BUTTON_HEIGHT);
        startButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            CardLayout cardLayout = (CardLayout) contentPane.getLayout();
            cardLayout.show(contentPane, Constants.Screen.GAME);
            gamePanel.startGame();
        }));
        this.add(startButton);
    }
}
