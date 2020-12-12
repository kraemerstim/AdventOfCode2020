package day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static utility.AOCUtility.readFile;

public class SolutionDay12 {
    enum Direction {NORTH, SOUTH, EAST, WEST, LEFT, RIGHT, FORWARD}

    public static void main(String[] args) {
        String PATTERN = "^(\\S)(\\d*)";

        var fileName = "resources/input/day12.txt";
        List<String> input = readFile(fileName);

        var pattern = Pattern.compile(PATTERN);
        List<Movement> movements = new ArrayList<>();

        for (String line: input) {
            var matcher = pattern.matcher(line);
            if (matcher.matches()) {
                movements.add(new Movement(matcher.group(1).charAt(0), Integer.parseInt(matcher.group(2))));
            }
        }

        Position pos = new Position();
        for (Movement movement: movements) {
            movement.applySolutionAMovement(pos);
        }
        System.out.format("Solution A = %d\n", pos.getManhattanDistance());

        pos = new Position();
        for (Movement movement: movements) {
            movement.applySolutionBMovement(pos);
        }
        System.out.format("Solution B = %d\n", pos.getManhattanDistance());
    }

    static class Movement {
        ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));

        Direction direction;
        int value;

        Movement(char direction, int value) {
            switch(direction) {
                case 'E':
                    this.direction = Direction.EAST;
                    break;
                case 'W':
                    this.direction = Direction.WEST;
                    break;
                case 'S':
                    this.direction = Direction.SOUTH;
                    break;
                case 'N':
                    this.direction = Direction.NORTH;
                    break;
                case 'F':
                    this.direction = Direction.FORWARD;
                    break;
                case 'L':
                    this.direction = Direction.LEFT;
                    break;
                case 'R':
                    this.direction = Direction.RIGHT;
                    break;
            }
            this.value = value;


        }

        void applySolutionAMovement(Position position) {
            Direction movingDirection = direction;
            if (movingDirection == Direction.FORWARD) {
                movingDirection = position.direction;
            }
            switch (movingDirection) {
                case NORTH:
                    position.vertical -= value;
                    break;
                case SOUTH:
                    position.vertical += value;
                    break;
                case EAST:
                    position.horizontal += value;
                    break;
                case WEST:
                    position.horizontal -= value;
                    break;
                case LEFT:
                    int directionIndex = directions.indexOf(position.direction);
                    position.direction = directions.get((directionIndex + 4 - value/90) % 4);
                    break;
                case RIGHT:
                    directionIndex = directions.indexOf(position.direction);
                    position.direction = directions.get((directionIndex + value/90) % 4);
                    break;
            }

        }

        void applySolutionBMovement(Position position) {
            int directionChange = 0;
            switch (direction) {
                case NORTH:
                    position.waypoint_vertical -= value;
                    break;
                case SOUTH:
                    position.waypoint_vertical += value;
                    break;
                case EAST:
                    position.waypoint_horizontal += value;
                    break;
                case WEST:
                    position.waypoint_horizontal -= value;
                    break;
                case RIGHT:
                    directionChange = value/90;
                    break;
                case LEFT:
                    directionChange = 4 - value/90;
                    break;
                case FORWARD:
                    position.vertical += value*position.waypoint_vertical;
                    position.horizontal += value*position.waypoint_horizontal;
                    break;
            }
            if (directionChange > 0) {
                switch ((directionChange+4) % 4) {
                    case 2:
                        position.waypoint_horizontal *= -1;
                        position.waypoint_vertical *= -1;
                        break;
                    case 1:
                        int newVert = position.waypoint_horizontal;
                        int newHor = position.waypoint_vertical*-1;
                        position.waypoint_vertical = newVert;
                        position.waypoint_horizontal = newHor;
                        break;
                    case 3:
                        newVert = position.waypoint_horizontal*-1;
                        newHor = position.waypoint_vertical;
                        position.waypoint_vertical = newVert;
                        position.waypoint_horizontal = newHor;
                        break;
                }
            }
        }
    }

    public static class Position {
        int horizontal;
        int vertical;
        Direction direction;

        int waypoint_horizontal;
        int waypoint_vertical;

        Position() {
            direction = Direction.EAST;
            waypoint_horizontal = 10;
            waypoint_vertical = -1;
        }

        int getManhattanDistance() {
            return Math.abs(horizontal) + Math.abs(vertical);
        }
    }
}
