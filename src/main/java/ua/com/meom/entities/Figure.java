package ua.com.meom.entities;

import java.awt.*;

public class Figure {

    private Image view;
    private int height;
    private int width;
    private int x;
    private int y;

    public Figure(Image view, int height, int width, int x, int y) {
        this.view = view;
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public Image getView() {
        return view;
    }

    public void setView(Image view) {
        this.view = view;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
