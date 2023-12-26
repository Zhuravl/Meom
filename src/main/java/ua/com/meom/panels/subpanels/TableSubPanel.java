package ua.com.meom.panels.subpanels;

import ua.com.meom.helpers.GameContext;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.util.Objects;

public class TableSubPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private int sideSize;
    private int lineNumber;
    private int columnNumber;
    private Color colorBright;
    private Color colorDark;
    private Clip roundWinSound, roundLoseSound;

    public TableSubPanel(int lineNumber, int columnNumber, int sideSize, Color colorBright, Color colorDark) {
        this.sideSize = sideSize;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.colorBright = colorBright;
        this.colorDark = colorDark;
        this.setLayout(null);

        try {
            AudioInputStream audioInputStreamWin = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/roundWin.wav")));
            roundWinSound = AudioSystem.getClip();
            roundWinSound.open(audioInputStreamWin);

            AudioInputStream audioInputStreamLose = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("/sounds/roundLose.wav")));
            roundLoseSound = AudioSystem.getClip();
            roundLoseSound.open(audioInputStreamLose);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < lineNumber; row++) {
            for (int col = 0; col < columnNumber; col++) {
                int x = col * sideSize;
                int y = row * sideSize;

                Color cellColor = (row + col) % 2 == 0 ? colorBright : colorDark;

                g.setColor(cellColor);
                g.fillRect(x, y, sideSize, sideSize);
            }
        }
        g.dispose();
    }

    /**
     * Plays the Round Win sound if the sound preferences is set to on
     */
    public void playRoundWinSound() {
        if (GameContext.getSettings().isSoundOn()) {
            roundWinSound.setMicrosecondPosition(0);
            roundWinSound.start();
        }
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
}
