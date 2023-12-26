package ua.com.meom.panels.subpanels;

import ua.com.meom.constants.Constants;
import ua.com.meom.enums.KeyCommand;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.List;

public class LoggingSubPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private int sideSize;
    private int lineNumber;
    private int columnNumber;
    private Color colorBright;
    private Color colorDark;
    private List<KeyCommand> keyCommandList;

    public LoggingSubPanel(int lineNumber, int columnNumber, int sideSize, Color colorBright, Color colorDark) {
        this.sideSize = sideSize;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.colorBright = colorBright;
        this.colorDark = colorDark;
        this.setLayout(null);
    }


    /**
     * Refreshes GUI to pull the latest data
     */
    public void refreshUI(List<KeyCommand> keyCommandList) {
        this.keyCommandList = keyCommandList;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw rectangles
        for (int row = 0; row < lineNumber; row++) {
            for (int col = 0; col < columnNumber; col++) {
                int x = col * sideSize;
                int y = row * sideSize;

                Color cellColor = (row + col) % 2 == 0 ? colorBright : colorDark;

                g.setColor(cellColor);
                g.fillRect(x, y, sideSize, sideSize);
            }
        }

        // Draw text in the middle of rectangles
        if (keyCommandList != null) {
            g.setFont(Constants.Common.FONT_KEY);

            int index = 0;
            int yPositionCoefficient = 20;
            for (int row = 0; row < lineNumber; row++) {
                for (int col = 0; col < columnNumber; col++) {
                    int x = col * sideSize;
                    int y = row * sideSize;

                    // Check if there are more items in the list
                    if (index < keyCommandList.size()) {
                        String text = keyCommandList.get(index).getText();
                        int textWidth = g.getFontMetrics().stringWidth(text);
                        int textHeight = g.getFontMetrics().getHeight();

                        // Calculate the position to center the text
                        int textX = x + (sideSize - textWidth) / 2;
                        int textY = y + (sideSize + (textHeight - yPositionCoefficient)) / 2;

                        // Draw the text
                        g.setColor(new Color(190, 117, 25));
                        g.drawString(text, textX, textY);

                        index++;
                    }
                }
            }
        }
    }
}
