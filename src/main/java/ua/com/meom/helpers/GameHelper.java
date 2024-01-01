package ua.com.meom.helpers;

import ua.com.meom.entities.Coordinate;
import ua.com.meom.entities.Figure;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class GameHelper {

    /**
     * Returns a list of existing coordinates taken from hero and aims
     *
     * @param hero the hero to take coordinates
     * @param aims the aims to take coordinates
     */
    public static List<Coordinate> getExistingCoordinatesList(Figure hero, List<Figure> aims) {
        List<Coordinate> resultList = new ArrayList<>();
        resultList.add(hero.getCoordinate());
        for (Figure aim : aims) {
            resultList.add(aim.getCoordinate());
        }
        return resultList;

    }

    /**
     * Returns a hero with randomly generated coordinates
     *
     * @param columnNumber number of board columns
     * @param lineNumber   number of board lines
     * @param sideSize     size of the board cell
     */
    public static Figure getHero(int columnNumber, int lineNumber, int sideSize) {
        Figure hero = new Figure();
        hero.setView(new ImageIcon(Objects.requireNonNull(GameHelper.class.getResource("/images/hero.gif"))).getImage());
        hero.setHeight(sideSize);
        hero.setWidth(sideSize);
        hero.setCoordinate(getRandomCoordinates(sideSize, columnNumber, lineNumber));
        return hero;
    }

    /**
     * Returns a list of aims with randomly generated coordinates
     *
     * @param columnNumber    number of board columns
     * @param lineNumber      number of board lines
     * @param sideSize        size of the board cell
     * @param level           the target level to calculate aims for
     * @param heroCoordinates the hero coordinates
     */
    public static List<Figure> getAims(int columnNumber, int lineNumber, int sideSize, int level, Coordinate heroCoordinates) {
        List<Figure> result = new ArrayList<>();
        List<Coordinate> occupiedCoordinates = new ArrayList<>();
        occupiedCoordinates.add(heroCoordinates);
        for (int i = 0; i < getAimsNumber(level); i++) {
            Figure aim = new Figure();
            aim.setView(new ImageIcon(Objects.requireNonNull(GameHelper.class.getResource("/images/aim.gif"))).getImage());
            aim.setHeight(sideSize);
            aim.setWidth(sideSize);
            aim.setCoordinate(getRandomCoordinates(sideSize, columnNumber, lineNumber, occupiedCoordinates));
            result.add(aim);
            occupiedCoordinates.add(aim.getCoordinate());
        }
        return result;
    }

    /**
     * Returns a list of aims with randomly generated coordinates
     *
     * @param columnNumber        number of board columns
     * @param lineNumber          number of board lines
     * @param sideSize            size of the board cell
     * @param level               the target level to calculate aims for
     * @param occupiedCoordinates the list of occupied coordinates
     */
    public static List<Figure> getBarriers(int columnNumber, int lineNumber, int sideSize, int level, List<Coordinate> occupiedCoordinates) {
        List<Figure> result = new ArrayList<>();
        for (int i = 0; i < getBarriersNumber(level); i++) {
            Figure barrier = new Figure();
            barrier.setView(new ImageIcon(Objects.requireNonNull(GameHelper.class.getResource("/images/barrier.gif"))).getImage());
            barrier.setHeight(sideSize);
            barrier.setWidth(sideSize);
            barrier.setCoordinate(getRandomCoordinates(sideSize, columnNumber, lineNumber, occupiedCoordinates));
            result.add(barrier);
            occupiedCoordinates.add(barrier.getCoordinate());
        }
        return result;
    }

    /**
     * Returns a random coordinates that are not occupied
     *
     * @param sideSize            size of the board cell
     * @param maxX                the max X coordinate value
     * @param maxY                the max Y coordinate value
     * @param occupiedCoordinates the list of occupied coordinates
     */
    private static Coordinate getRandomCoordinates(int sideSize, int maxX, int maxY, List<Coordinate> occupiedCoordinates) {
        Coordinate result;
        do {
            result = getRandomCoordinates(sideSize, maxX, maxY);
        } while (!isCoordinateFree(result, occupiedCoordinates));
        return result;
    }

    /**
     * Returns TRUE if the candidate coordinate is not occupied, otherwise FALSE
     *
     * @param candidate the candidate coordinate to validate
     * @param existing  the list of existing coordinates
     */
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

    /**
     * Returns a random coordinates based on the size of the board
     *
     * @param sideSize size of the board cell
     * @param maxX     the max X coordinate value
     * @param maxY     the max Y coordinate value
     */
    private static Coordinate getRandomCoordinates(int sideSize, int maxX, int maxY) {
        Coordinate result = new Coordinate();
        result.setX(sideSize * getRandomCoordinate(maxX));
        result.setY(sideSize * getRandomCoordinate(maxY));
        return result;
    }

    /**
     * Returns a random number in the range [0 - max]
     *
     * @param max the max value (excluded)
     */
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

    /**
     * Returns the number of barriers for the defined level.
     * The main logic is to increase the number of barriers by 1 for each three levels.
     *
     * @param level the level to calculate barriers for
     */
    private static int getBarriersNumber(int level) {
        return ((level - 1) / 3);
    }
}
