package ua.com.meom.helpers;

import ua.com.meom.entities.Coordinate;
import ua.com.meom.entities.Figure;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class GameHelper {

    public static List<Coordinate> getExistingCoordinatesList(Figure hero, List<Figure> aims) {
        List<Coordinate> resultList = new ArrayList<>();
        resultList.add(hero.getCoordinate());
        for (Figure aim : aims) {
            resultList.add(aim.getCoordinate());
        }
        return resultList;

    }

    public static Figure getHero(int columnNumber, int lineNumber, int sideSize) {
        Figure hero = new Figure();
        hero.setView(new ImageIcon(Objects.requireNonNull(GameHelper.class.getResource("/images/hero.gif"))).getImage());
        hero.setHeight(sideSize);
        hero.setWidth(sideSize);
        hero.setCoordinate(getRandomCoordinates(sideSize, columnNumber, lineNumber));
        return hero;
    }

    public static List<Figure> getAims(int columnNumber, int lineNumber, int sideSize, int level, Coordinate heroCoordinates) {
        List<Figure> result = new ArrayList<>();
        List<Coordinate> existingCoordinates = new ArrayList<>();
        existingCoordinates.add(heroCoordinates);
        for (int i = 0; i < getAimsNumber(level); i++) {
            Figure aim = new Figure();
            aim.setView(new ImageIcon(Objects.requireNonNull(GameHelper.class.getResource("/images/aim.gif"))).getImage());
            aim.setHeight(sideSize);
            aim.setWidth(sideSize);
            aim.setCoordinate(getRandomCoordinates(sideSize, columnNumber, lineNumber, existingCoordinates));
            result.add(aim);
            existingCoordinates.add(aim.getCoordinate());
        }
        return result;
    }

    public static List<Figure> getBarriers(int columnNumber, int lineNumber, int sideSize, int level, List<Coordinate> existingCoordinates) {
        List<Figure> result = new ArrayList<>();
        for (int i = 0; i < getBarriersNumber(level); i++) {
            Figure barrier = new Figure();
            barrier.setView(new ImageIcon(Objects.requireNonNull(GameHelper.class.getResource("/images/barrier.gif"))).getImage());
            barrier.setHeight(sideSize);
            barrier.setWidth(sideSize);
            barrier.setCoordinate(getRandomCoordinates(sideSize, columnNumber, lineNumber, existingCoordinates));
            result.add(barrier);
            existingCoordinates.add(barrier.getCoordinate());
        }
        return result;
    }

    private static Coordinate getRandomCoordinates(int sideSize, int maxX, int maxY, List<Coordinate> coordinates) {
        Coordinate result;
        do {
            result = getRandomCoordinates(sideSize, maxX, maxY);
        } while (!isCoordinateFree(result, coordinates));
        return result;
    }

    private static boolean isCoordinateFree(Coordinate candidate, List<Coordinate> existing) {
        if (existing != null) {
            for (Coordinate coordinate : existing) {
                if (coordinate.equals(candidate)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static Coordinate getRandomCoordinates(int sideSize, int maxX, int maxY) {
        Coordinate result = new Coordinate();
        result.setX(sideSize * getRandomCoordinate(maxX));
        result.setY(sideSize * getRandomCoordinate(maxY));
        return result;
    }

    private static int getRandomCoordinate(int max) {
        return ThreadLocalRandom.current().nextInt(0, max);
    }

    /**
     * Returns the number of aims for the defined level.
     * The main logic is to increase the number of aims by 1 for each ten levels.
     *
     * @param level the level to calculate aims for
     */
    private static int getAimsNumber(int level) {
        return (1 + (level - 1) / 10);
    }

    private static int getBarriersNumber(int level) {
        return ((level - 1) / 3);
    }
}
