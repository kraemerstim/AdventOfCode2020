package day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utility.AOCUtility.readFile;

public class SolutionDay11 {
    private static int width;
    private static int height;

    public static void main(String[] args) {
        var fileName = "resources/input/day11.txt";
        List<String> input = readFile(fileName);

        width = input.get(0).length();
        height = input.size();

        SeatPosition[][] seatsA = new SeatPosition[height][width];
        List<SeatPosition> seatListA = new ArrayList<>();

        SeatPosition[][] seatsB = new SeatPosition[height][width];
        List<SeatPosition> seatListB = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char seatState = input.get(i).charAt(j);
                if (seatState == 'L') {
                    seatsA[i][j] = new SeatPosition(i, j);
                    seatsB[i][j] = new SeatPosition(i, j);
                    seatListA.add(seatsA[i][j]);
                    seatListB.add(seatsB[i][j]);
                }
            }
        }

        for (SeatPosition seat : seatListA) {
            addAdjacentSeatsA(seat, seatsA);
        }
        System.out.format("SolutionA = %d\n", getCountAfterChaosEnds(seatListA, 4));

        for (SeatPosition seat : seatListB) {
            addAdjacentSeatsB(seat, seatsB);
        }
        System.out.format("SolutionB = %d\n",getCountAfterChaosEnds(seatListB, 5));
    }

    private static long getCountAfterChaosEnds(List<SeatPosition> seatList, int neighborThreshold) {
        boolean hasChanged;
        hasChanged = true;
        while (hasChanged) {
            applyRules(seatList, neighborThreshold);
            hasChanged = applyChanges(seatList);
        }
        return seatList.stream().filter(i -> i.isSeated).count();
    }

    private static void addAdjacentSeatsA(SeatPosition seatPosition, SeatPosition[][] seats) {
        if (seatPosition == null) return;
        for (int i = seatPosition.row - 1; i <= seatPosition.row + 1; i++) {
            if (i < 0 || i >= height) continue;
            for (int j = seatPosition.column - 1; j <= seatPosition.column + 1; j++) {
                if (i == seatPosition.row && seatPosition.column == j) continue;
                if (j < 0 || j >= width) continue;
                if (seats[i][j] != null)
                    seatPosition.addAdjacentSeatPosition(seats[i][j]);
            }
        }
    }

    private static void addAdjacentSeatsB(SeatPosition seatPosition, SeatPosition[][] seats) {
        if (seatPosition == null) return;

        for (int right = -1; right <= 1; right++) {
            for (int down = -1; down <= 1; down ++) {
                if (right == 0 && down == 0) continue;
                for (int i = seatPosition.row + down, j = seatPosition.column + right; i >= 0 && j >=0 && i < height && j < width; i+=down, j+=right) {
                    if (seats[i][j] != null) {
                        seatPosition.addAdjacentSeatPosition(seats[i][j]);
                        break;
                    }
                }
            }
        }
    }

    private static void applyRules(List<SeatPosition> seatList, int neighborThreshold) {
        for (SeatPosition seat : seatList) {
            boolean isSeated = seat.isSeated;
            long count = seat.getAdjacentSeated();

            if (isSeated && count >= neighborThreshold)
                seat.isSeatedNextTurn = false;
            else if (!isSeated && count == 0)
                seat.isSeatedNextTurn = true;
        }
    }

    private static boolean applyChanges(List<SeatPosition> seatList) {
        boolean changed = false;
        for (SeatPosition seat : seatList) {
            if (seat.apply()) changed = true;
        }
        return changed;
    }

    private static class SeatPosition {
        boolean isSeated;
        boolean isSeatedNextTurn;
        int row;
        int column;
        List<SeatPosition> adjacentSeatPositions;

        private SeatPosition(int row, int column) {
            this.isSeated = false;
            this.adjacentSeatPositions = new ArrayList<>();
            this.row = row;
            this.column = column;
        }

        void addAdjacentSeatPosition(SeatPosition position) {
            adjacentSeatPositions.add(position);
        }

        long getAdjacentSeated() {
            return adjacentSeatPositions.stream().filter(i -> i.isSeated).count();
        }

        boolean apply() {
            if (isSeated != isSeatedNextTurn) {
                isSeated = isSeatedNextTurn;
                return true;
            }
            return false;
        }
    }
}
