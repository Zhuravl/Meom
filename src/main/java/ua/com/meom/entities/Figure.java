package ua.com.meom.entities;

import java.awt.*;

public class Figure {

    private Image view;
    private int height;
    private int width;
    private Coordinate coordinate;

    public Figure() {
    }

    public Figure(Image view, int height, int width, Coordinate coordinate) {
        this.view = view;
        this.height = height;
        this.width = width;
        this.coordinate = coordinate;
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}
