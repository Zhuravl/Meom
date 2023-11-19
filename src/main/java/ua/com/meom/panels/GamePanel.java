package ua.com.meom.panels;

import ua.com.meom.constants.Constants;
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
    private JButton stopButton, keyUpButton, keyDownButton, keyLeftButton, keyRightButton, keyEnterButton, keyBackSpaceButton;
    private Clip keyUpSound, keyDownSound, keyLeftSound, keyRightSound, keyEnterSound, keyBackSlashSound, roundWinSound, roundLoseSound;

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
                e -> keyEnterButton.doClick(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        registerKeyboardAction(
                e -> keyBackSpaceButton.doClick(),
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
            keyEnterSound = AudioSystem.getClip();
            keyEnterSound.open(audioInputStreamKeyEnter);

            AudioInputStream audioInputStreamKeyBackSlash = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/keyBackSlash.wav")));
            keyBackSlashSound = AudioSystem.getClip();
            keyBackSlashSound.open(audioInputStreamKeyBackSlash);

            AudioInputStream audioInputStreamWin = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/roundWin.wav")));
            roundWinSound = AudioSystem.getClip();
            roundWinSound.open(audioInputStreamWin);

            AudioInputStream audioInputStreamLose = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/roundLose.wav")));
            roundLoseSound = AudioSystem.getClip();
            roundLoseSound.open(audioInputStreamLose);
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
//        setGameSubPanel(getNextGameSkin());
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
//        gameSubPanel.stopGame();
        GameContext.getRecord().setScore(GameContext.getSettings().getScore());
        GameContext.getRecord().setLevel(GameContext.getSettings().getLevel());
        GameContext.getRecord().setMistakes(GameContext.getSettings().getMistakes());
        GameContext.getRecord().setDate(Calendar.getInstance());
//        GameContext.saveRecordToDisk();
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

        infoBarSubPanel = new InfoBarSubPanel();
        infoBarSubPanel.setBounds(0, 0, Constants.Common.BUTTON_WIDTH * 6, Constants.Common.BUTTON_HEIGHT);
        this.add(infoBarSubPanel);

        tableSubPanel = new TableSubPanel();
        tableSubPanel.setBounds(Constants.Common.ELEMENTS_CLEARANCE, infoBarSubPanel.getY() + infoBarSubPanel.getHeight(), Constants.Game.BOARD_SQUARE_SIDE * Constants.Game.BOARD_SQUARES_HORIZONTAL_NUMBER, Constants.Game.BOARD_SQUARE_SIDE * Constants.Game.BOARD_SQUARES_VERTICAL_NUMBER);
        this.add(tableSubPanel);

        loggingSubPanel = new LoggingSubPanel();
        loggingSubPanel.setBounds(Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.MAIN_WINDOW_HEIGHT - (Constants.Game.LOGGING_SQUARE_SIDE * Constants.Game.LOGGING_SQUARES_VERTICAL_NUMBER) - Constants.Common.ELEMENTS_CLEARANCE, Constants.Game.LOGGING_SQUARE_SIDE * Constants.Game.LOGGING_SQUARES_HORIZONTAL_NUMBER, Constants.Game.LOGGING_SQUARE_SIDE * Constants.Game.LOGGING_SQUARES_VERTICAL_NUMBER);
        this.add(loggingSubPanel);

        keyRightButton = new JButton("▶");
        keyRightButton.setFont(Constants.Common.FONT_MAIN);
        keyRightButton.setBounds(Constants.Common.MAIN_WINDOW_WIDTH - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE, (Constants.Common.MAIN_WINDOW_HEIGHT / 2) - Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyRightButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            //add action here
        }));
        this.add(keyRightButton);

        keyBackSpaceButton = new JButton("C");
        keyBackSpaceButton.setFont(Constants.Common.FONT_MAIN);
        keyBackSpaceButton.setBounds(keyRightButton.getX() - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE / 2, keyRightButton.getY(), Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyBackSpaceButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            //add action here
        }));
        this.add(keyBackSpaceButton);

        keyLeftButton = new JButton("◀");
        keyLeftButton.setFont(Constants.Common.FONT_MAIN);
        keyLeftButton.setBounds(keyBackSpaceButton.getX() - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE / 2, keyBackSpaceButton.getY(), Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyLeftButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            //add action here
        }));
        this.add(keyLeftButton);

        keyUpButton = new JButton("▲");
        keyUpButton.setFont(Constants.Common.FONT_MAIN);
        keyUpButton.setBounds(keyBackSpaceButton.getX(), keyBackSpaceButton.getY() - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE / 2, Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyUpButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            //add action here
        }));
        this.add(keyUpButton);

        keyDownButton = new JButton("▼");
        keyDownButton.setFont(Constants.Common.FONT_MAIN);
        keyDownButton.setBounds(keyBackSpaceButton.getX(), keyBackSpaceButton.getY() + Constants.Common.BUTTON_HEIGHT + Constants.Common.ELEMENTS_CLEARANCE / 2, Constants.Common.BUTTON_HEIGHT, Constants.Common.BUTTON_HEIGHT);
        keyDownButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            //add action here
        }));
        this.add(keyDownButton);

        keyEnterButton = new JButton(rb.getString("launch_button"));
        keyEnterButton.setFont(Constants.Common.FONT_MAIN);
        keyEnterButton.setBounds(Constants.Common.MAIN_WINDOW_WIDTH - Constants.Common.BUTTON_WIDTH - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.MAIN_WINDOW_HEIGHT - Constants.Common.BUTTON_HEIGHT - Constants.Common.ELEMENTS_CLEARANCE, Constants.Common.BUTTON_WIDTH, Constants.Common.BUTTON_HEIGHT);
        keyEnterButton.addActionListener(e -> EventQueue.invokeLater(() -> {
            //start game
        }));
        this.add(keyEnterButton);
    }

    /**
     * Plays the Round Lose sound if the sound preferences is set to on
     */
    public void playRoundLoseSound() {
        if (GameContext.getSettings().isSoundOn()) {
            roundLoseSound.setMicrosecondPosition(0);
            roundLoseSound.start();
        }
    }

    /**
     * Checks if the correct key was pressed and adds score or mistake
     *
     * @param key the key pressed by the gamer
     */
//    private void checkKeyPressed(String key) {
//        if (letters.length > currentLetterIndex) {
//            //There are keys to press existing
//            if (letters[currentLetterIndex].equals(key)) {
//                //The correct key was pressed - add scores
//                if (GameContext.getSettings().isSoundOn()) {
//                    keyUpSound.setMicrosecondPosition(0);
//                    keyUpSound.start();
//                }
//                gameSubPanel.hideBlock(currentLetterIndex);
//                int score = GameContext.getSettings().getScore();
//                int level = GameContext.getSettings().getLevel();
//                score = score + level; //Level-based score multiplication to make the higher level more valuable compared with the same effort spent
//                GameContext.getSettings().setScore(score);
//                infoBarPanel.setScoreField(score);
//                currentLetterIndex++;
//                if (letters.length > currentLetterIndex) {
//                    //Highlight the next key to continue the round
////                    keyboardPanel.highlightButton(letters[currentLetterIndex]);
//                } else {
//                    //All keys were successfully hit - user wins the round
////                    keyboardPanel.resetButtonHighlighting();
//                    if (GameContext.getSettings().isSoundOn()) {
//                        roundWinSound.setMicrosecondPosition(0);
//                        roundWinSound.start();
//                    }
//                }
//            } else {
//                //The incorrect key was pressed - add mistakes
//                if (GameContext.getSettings().isSoundOn()) {
//                    keyDownSound.setMicrosecondPosition(0);
//                    keyDownSound.start();
//                }
//                int mistakes = GameContext.getSettings().getMistakes();
//                mistakes++;
//                GameContext.getSettings().setMistakes(mistakes);
//                infoBarPanel.setMistakesBar(mistakes);
////                if (mistakes >= GameContext.getMaxMistakes()) {
////                    //The player has made too many mistakes - stopping the game
////                    playRoundLoseSound();
////                    stopGame();
////                }
//            }
//        }
//    }

    /**
     * Returns the instance of the next game skin, based on the current one.
     * The main idea is to move through the seasons (summer -> autumn -> winter -> spring -> summer)
     */
//    private AbstractGamePanel getNextGameSkin() {
//        if (gameSubPanel instanceof MovingFloorGamePanel) {
//            return new HuntingWhirlwindGamePanel(this, letters);
//        } else if (gameSubPanel instanceof HuntingWhirlwindGamePanel) {
//            return new FallingCeilingGamePanel(this, letters);
//        } else if (gameSubPanel instanceof FallingCeilingGamePanel) {
//            return new ScaryCloudGamePanel(this, letters);
//        } else {
//            return new MovingFloorGamePanel(this, letters);
//        }
//    }
}
