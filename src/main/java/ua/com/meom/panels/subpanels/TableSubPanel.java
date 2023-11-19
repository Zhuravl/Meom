package ua.com.meom.panels.subpanels;

import ua.com.meom.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class TableSubPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    public TableSubPanel() {
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.fillRect(0, 0, Constants.Game.BOARD_SQUARE_SIDE, Constants.Game.BOARD_SQUARE_SIDE);
        g2d.setColor(Constants.Game.BOARD_SQUARES_COLOR_DARK);
        for (int i = 0; i < Constants.Game.BOARD_SQUARES_HORIZONTAL_NUMBER; i++) {
            for (int j = 0; j < Constants.Game.BOARD_SQUARES_VERTICAL_NUMBER; j++) {
                g2d.fillRect(Constants.Game.BOARD_SQUARE_SIDE * i, Constants.Game.BOARD_SQUARE_SIDE * j, Constants.Game.BOARD_SQUARE_SIDE, Constants.Game.BOARD_SQUARE_SIDE);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    g2d.setColor(Constants.Game.BOARD_SQUARES_COLOR_BRIGHT);
                } else {
                    g2d.setColor(Constants.Game.BOARD_SQUARES_COLOR_DARK);
                }
            }
        }
        g2d.dispose();
    }
}
