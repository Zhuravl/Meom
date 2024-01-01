package ua.com.meom.panels.subpanels;

import ua.com.meom.entities.Figure;
import ua.com.meom.enums.KeyCommand;
import ua.com.meom.panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.List;

public class TableSubPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private final int TIMER_DELAY = 40;

    private GamePanel gamePanel;
    private int sideSize, lineNumber, columnNumber, stepIndex, commandIndex, reachesCount;
    private Color colorBright, colorDark;
    private List<KeyCommand> keyCommands;
    private Figure hero;
    private List<Figure> aims;
    private List<Figure> barriers;
    private Timer timer;

    public TableSubPanel(GamePanel gamePanel, int lineNumber, int columnNumber, int sideSize, Color colorBright, Color colorDark) {
        this.gamePanel = gamePanel;
        this.sideSize = sideSize;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.colorBright = colorBright;
        this.colorDark = colorDark;
        this.stepIndex = 0;
        this.commandIndex = 0;
        this.reachesCount = 0;
        this.setLayout(null);

        timer = new Timer(TIMER_DELAY, e -> {
            moveHero();
            repaint();
        });
    }

    /**
     * Refreshes GUI to pull the latest data
     *
     * @param hero     the hero representation
     * @param aims     the aims representation
     * @param barriers the barriers representation
     */
    public void refreshGUI(Figure hero, List<Figure> aims, List<Figure> barriers) {
        this.hero = hero;
        this.aims = aims;
        this.barriers = barriers;
        this.stepIndex = 0;
        this.commandIndex = 0;
        this.reachesCount = 0;
        repaint();
    }

    /**
     * Launches the code by executing the list of commands
     *
     * @param keyCommands list of commands to execute
     */
    public void launchCode(List<KeyCommand> keyCommands) {
        this.keyCommands = keyCommands;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        for (int row = 0; row < lineNumber; row++) {
            for (int col = 0; col < columnNumber; col++) {
                int x = col * sideSize;
                int y = row * sideSize;

                Color cellColor = (row + col) % 2 == 0 ? colorBright : colorDark;

                g.setColor(cellColor);
                g.fillRect(x, y, sideSize, sideSize);
            }
        }

        // Draw barriers for the hero
        if (barriers != null) {
            for (Figure barrier : barriers) {
                g.drawImage(barrier.getView(), barrier.getCoordinate().getX(), barrier.getCoordinate().getY(), barrier.getWidth(), barrier.getHeight(), this);
            }
        }

        // Draw hero's aims
        if (aims != null) {
            for (Figure aim : aims) {
                g.drawImage(aim.getView(), aim.getCoordinate().getX(), aim.getCoordinate().getY(), aim.getWidth(), aim.getHeight(), this);
            }
        }

        // Draw hero
        if (hero != null) {
            g.drawImage(hero.getView(), hero.getCoordinate().getX(), hero.getCoordinate().getY(), hero.getWidth(), hero.getHeight(), this);
        }
        g.dispose();
    }


    /**
     * Move the Hero using the list of commands
     */
    private void moveHero() {
        if (reachesCount == aims.size()) {
            // The Hero has reached all aims - pass the level
            timer.stop();
            stepIndex = 0;
            commandIndex = 0;
            reachesCount = 0;
            gamePanel.passLevel();
        } else {
            // The Hero has still not reached all aims - continue moving
            if (isHeroHitBarrier()) {
                // The Hero has hit a barrier - lose the level
                timer.stop();
                stepIndex = 0;
                commandIndex = 0;
                reachesCount = 0;
                gamePanel.loseLevel();
            } else {
                // The Hero has not hit a barrier - continue moving
                if (commandIndex >= keyCommands.size()) {
                    // The Hero has used all his steps (but has not reach aims) - lose the level
                    timer.stop();
                    stepIndex = 0;
                    commandIndex = 0;
                    reachesCount = 0;
                    gamePanel.loseLevel();
                } else {
                    // The Hero has not hit a barrier and needs to continue moving to aims
                    hero.getCoordinate().setX(hero.getCoordinate().getX() + keyCommands.get(commandIndex).getKeyX());
                    hero.getCoordinate().setY(hero.getCoordinate().getY() + keyCommands.get(commandIndex).getKeyY());
                    stepIndex += 1;

                    // Check if the Hero reaches an aim
                    if (isHeroReachAim()) {
                        reachesCount += 1;
                    }

                    // Check that the Hero is still in the current cell
                    if (stepIndex >= sideSize) {
                        // Move to the next cell by using the next command
                        stepIndex = 0;
                        commandIndex += 1;
                    }
                }
            }
        }
    }

    /**
     * Checks if the Hero reaches an aim (by crossing the center of them)
     */
    private boolean isHeroReachAim() {
        for (Figure aim : aims) {
            if ((hero.getCoordinate().getX() + (hero.getWidth() / 2)) == (aim.getCoordinate().getX() + (aim.getWidth() / 2)) &&
                    (hero.getCoordinate().getY() + (hero.getHeight() / 2)) == (aim.getCoordinate().getY() + (aim.getHeight() / 2))) {
                // Collision detected
                return true;
            }
        }
        // No collision detected
        return false;
    }

    /**
     * Checks of the Hero hits any of the existing barrier (by crossing a boarder of them)
     */
    private boolean isHeroHitBarrier() {
        if (barriers != null && barriers.size() > 0) {
            for (Figure barrier : barriers) {
                if (hero.getCoordinate().getX() < barrier.getCoordinate().getX() + barrier.getWidth() &&
                        hero.getCoordinate().getX() + hero.getWidth() > barrier.getCoordinate().getX() &&
                        hero.getCoordinate().getY() < barrier.getCoordinate().getY() + barrier.getHeight() &&
                        hero.getCoordinate().getY() + hero.getHeight() > barrier.getCoordinate().getY()) {
                    // Collision detected
                    return true;
                }
            }
        }
        // No collision detected
        return false;
    }
}
