package ua.com.meom.panels;

import ua.com.meom.constants.Constants;
import ua.com.meom.enums.KeyCommand;
import ua.com.meom.helpers.GameContext;
import ua.com.meom.panels.subpanels.InfoBarSubPanel;
import ua.com.meom.panels.subpanels.LoggingSubPanel;
import ua.com.meom.panels.subpanels.TableSubPanel;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class GamePanel extends JPanel {

    private JPanel contentPane;
    private InfoBarSubPanel infoBarSubPanel;
    private TableSubPanel tableSubPanel;
    private LoggingSubPanel loggingSubPanel;
    private JButton keyUpButton, keyDownButton, keyLeftButton, keyRightButton, keyCleanUpButton, launchButton, stopButton;
    private Clip keyUpSound, keyDownSound, keyLeftSound, keyRightSound, keyLaunchSound, keyCleanUpSound;

    private RankingPanel rankingPanel;

    public GamePanel(JPanel contentPane, RankingPanel rankingPanel) {
        this.contentPane = contentPane;
        this.rankingPanel = rankingPanel;
        this.setLayout(null);
        registerKeyboardAction(
                e -> keyUpButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        registerKeyboardAction(
                e -> keyDownButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        registerKeyboardAction(
                e -> keyLeftButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        registerKeyboardAction(
                e -> keyRightButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        registerKeyboardAction(
                e -> launchButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        registerKeyboardAction(
                e -> keyCleanUpButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        try {
            AudioInputStream audioInputStreamKeyUp = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyUp.wav")));
            keyUpSound = AudioSystem.getClip();
            keyUpSound.open(audioInputStreamKeyUp);

            AudioInputStream audioInputStreamKeyDown = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyDown.wav")));
            keyDownSound = AudioSystem.getClip();
            keyDownSound.open(audioInputStreamKeyDown);

            AudioInputStream audioInputStreamKeyLeft = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyLeft.wav")));
            keyLeftSound = AudioSystem.getClip();
            keyLeftSound.open(audioInputStreamKeyLeft);

            AudioInputStream audioInputStreamKeyRight = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyRight.wav")));
            keyRightSound = AudioSystem.getClip();
            keyRightSound.open(audioInputStreamKeyRight);

            AudioInputStream audioInputStreamKeyEnter = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyEnter.wav")));
            keyLaunchSound = AudioSystem.getClip();
            keyLaunchSound.open(audioInputStreamKeyEnter);

            AudioInputStream audioInputStreamKeyBackSlash = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyBackSlash.wav")));
            keyCleanUpSound = AudioSystem.getClip();
            keyCleanUpSound.open(audioInputStreamKeyBackSlash);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }

        GUI();
    }

    /**
     * Refreshes GUI to pull the latest data and starts the game
     */
    public void startGame() {
        infoBarSubPanel.refreshGUI();
//        gameSubPanel.startGame();
    }

    /**
     * Starts the next game, increasing the level (if it's available)
     */
    public void nextLevel() {
        int currentLevel = GameContext.getSettings().getLevel();
        int lastLevel = GameContext.getMaxLevel();
        if (lastLevel > currentLevel) {
            //The next level is available - switch to the next level
            GameContext.getSettings().setLevel(currentLevel + 1);
        }
        startGame();
    }

    /**
     * Stops the game, saves the results and switches to the next frame
     */
    public void stopGame() {
//        tableSubPanel.stopGame();
        GameContext.getRecord().setScore(GameContext.getSettings().getScore());
        GameContext.getRecord().setLevel(GameContext.getSettings().getLevel());
        GameContext.getRecord().setMistakes(GameContext.getSettings().getMistakes());
        GameContext.getRecord().setDate(Calendar.getInstance());
        GameContext.saveRecordToDisk();
        rankingPanel.refreshGUI();
        CardLayout cardLayout = (CardLayout) contentPane.getLayout();
        cardLayout.show(contentPane, Constants.Screen.RANKING);
    }

    /**
     * Initiates and configures the UI elements
     */
    private void GUI() {
        Locale locale = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle(Constants.Common.LOCALE_PREFIX, locale);
        final int LOGGING_KEY_MAX = Constants.Game.SQUARES_HORIZONTAL_NUMBER_LOGGING * Constants.Game.SQUARES_VERTICAL_NUMBER_LOGGING;

        infoBarSubPanel = new InfoBarSubPanel();
        infoBarSubPanel.setBounds(0, 0, Constants.Common.BUTTON_WIDTH * 2, Constants.Common.BUTTON_HEIGHT);
        this.add(infoBarSubPanel);

        tableSubPanel = new TableSubPanel(Constants.Game.SQUARES_HORIZONTAL_NUMBER_BOARD, Constants.Game.SQUARES_VERTICAL_NUMBER_BOARD, Constants.Game.SQUARE_SIDE_BOARD, Constants.Game.SQUARES_COLOR_BRIGHT, Constants.Game.SQUARES_COLOR_DARK);
        tableSubPanel.setBounds(Constants.Common.ELEMENTS_CLEARANCE, infoBarSubPanel.getY() + infoBarSubPanel.getHeight(), Constants.Game.SQUARE_SIDE_BOARD * Constants.Game.SQUARES_VERTICAL_NUMBER_BOARD, Constants.Game.SQUARE_SIDE_BOARD * Constants.Game.SQUARES_HORIZONTAL_NUMBER_BOARD);
        this.add(tableSubPanel);

        keyRightButton = new JButton(KeyCommand.MOVE_RIGHT.getText());
        keyRightButton.setFont(Constants.Common.FONT_MAIN);
        keyRightButton.setBounds(Constants.Common.MAIN_WINDOW_WIDTH - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_HEIGHT + Constants.Common.ELEMENTS_CLEARANCE * 4, Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyRightButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            if (GameContext.getKeyCommandList().size() < LOGGING_KEY_MAX) {
                playButtonSound(KeyCommand.MOVE_RIGHT);
                GameContext.addKey(KeyCommand.MOVE_RIGHT);
                loggingSubPanel.refreshUI(GameContext.getKeyCommandList());
            }
        }));
        this.add(keyRightButton);

        keyCleanUpButton = new JButton(KeyCommand.CLEANUP.getText());
        keyCleanUpButton.setFont(Constants.Common.FONT_MAIN);
        keyCleanUpButton.setBounds(keyRightButton.getX() - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE / 2, keyRightButton.getY(), Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyCleanUpButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            playButtonSound(KeyCommand.CLEANUP);
            GameContext.removeLastKey();
            loggingSubPanel.refreshUI(GameContext.getKeyCommandList());
        }));
        this.add(keyCleanUpButton);

        keyLeftButton = new JButton(KeyCommand.MOVE_LEFT.getText());
        keyLeftButton.setFont(Constants.Common.FONT_MAIN);
        keyLeftButton.setBounds(keyCleanUpButton.getX() - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE / 2, keyCleanUpButton.getY(), Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyLeftButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            if (GameContext.getKeyCommandList().size() < LOGGING_KEY_MAX) {
                playButtonSound(KeyCommand.MOVE_LEFT);
                GameContext.addKey(KeyCommand.MOVE_LEFT);
                loggingSubPanel.refreshUI(GameContext.getKeyCommandList());
            }
        }));
        this.add(keyLeftButton);

        keyUpButton = new JButton(KeyCommand.MOVE_UP.getText());
        keyUpButton.setFont(Constants.Common.FONT_MAIN);
        keyUpButton.setBounds(keyCleanUpButton.getX(), keyCleanUpButton.getY() - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE / 2, Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyUpButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            if (GameContext.getKeyCommandList().size() < LOGGING_KEY_MAX) {
                playButtonSound(KeyCommand.MOVE_UP);
                GameContext.addKey(KeyCommand.MOVE_UP);
                loggingSubPanel.refreshUI(GameContext.getKeyCommandList());
            }
        }));
        this.add(keyUpButton);

        keyDownButton = new JButton(KeyCommand.MOVE_DOWN.getText());
        keyDownButton.setFont(Constants.Common.FONT_MAIN);
        keyDownButton.setBounds(keyCleanUpButton.getX(), keyCleanUpButton.getY() + Constants.Common.BUTTON_HEIGHT + Constants.Common.ELEMENTS_CLEARANCE / 2, Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyDownButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            if (GameContext.getKeyCommandList().size() < LOGGING_KEY_MAX) {
                playButtonSound(KeyCommand.MOVE_DOWN);
                GameContext.addKey(KeyCommand.MOVE_DOWN);
                loggingSubPanel.refreshUI(GameContext.getKeyCommandList());
            }
        }));
        this.add(keyDownButton);

        launchButton = new JButton(rb.getString("launch_button"));
        launchButton.setFont(Constants.Common.FONT_MAIN);
        launchButton.setBounds(keyLeftButton.getX(), keyDownButton.getY() + keyDownButton.getHeight() + Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_HEIGHT * 3 + Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_HEIGHT);
        launchButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            playButtonSound(KeyCommand.LAUNCH);
            startGame();
        }));
        this.add(launchButton);

        stopButton = new JButton(rb.getString("stop_button"));
        stopButton.setFont(Constants.Common.FONT_MAIN);
        stopButton.setBounds(Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.MAIN_WINDOW_HEIGHT - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_HEIGHT * 2, Constants.Common.BUTTON_HEIGHT);
        stopButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            stopGame();
        }));
        this.add(stopButton);

        loggingSubPanel = new LoggingSubPanel(Constants.Game.SQUARES_HORIZONTAL_NUMBER_LOGGING, Constants.Game.SQUARES_VERTICAL_NUMBER_LOGGING, Constants.Game.SQUARE_SIDE_LOGGING, Constants.Game.SQUARES_COLOR_BRIGHT, Constants.Game.SQUARES_COLOR_DARK);
        loggingSubPanel.setBounds(Constants.Common.MAIN_WINDOW_WIDTH - (Constants.Game.SQUARE_SIDE_LOGGING * Constants.Game.SQUARES_VERTICAL_NUMBER_LOGGING) - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.MAIN_WINDOW_HEIGHT - (Constants.Game.SQUARE_SIDE_LOGGING * Constants.Game.SQUARES_HORIZONTAL_NUMBER_LOGGING) - Constants.Common.ELEMENTS_CLEARANCE, Constants.Game.SQUARE_SIDE_LOGGING * Constants.Game.SQUARES_VERTICAL_NUMBER_LOGGING, Constants.Game.SQUARE_SIDE_LOGGING * Constants.Game.SQUARES_HORIZONTAL_NUMBER_LOGGING);
        this.add(loggingSubPanel);
    }

    /**
     * Plays the Round Lose sound if the sound preferences is set to on
     */
    public void playButtonSound(KeyCommand key) {
        if (GameContext.getSettings().isSoundOn()) {
            switch (key) {
                case MOVE_RIGHT -> {
                    keyRightSound.setMicrosecondPosition(0);
                    keyRightSound.start();
                }
                case MOVE_LEFT -> {
                    keyLeftSound.setMicrosecondPosition(0);
                    keyLeftSound.start();
                }
                case MOVE_DOWN -> {
                    keyDownSound.setMicrosecondPosition(0);
                    keyDownSound.start();
                }
                case MOVE_UP -> {
                    keyUpSound.setMicrosecondPosition(0);
                    keyUpSound.start();
                }
                case CLEANUP -> {
                    keyCleanUpSound.setMicrosecondPosition(0);
                    keyCleanUpSound.start();
                }
                case LAUNCH -> {
                    keyLaunchSound.setMicrosecondPosition(0);
                    keyLaunchSound.start();
                }
                default -> throw new IllegalArgumentException("Can't recognize sound for the key - " + key + "!");
            }
        }
    }
}
