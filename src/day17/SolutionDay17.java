package day17;

import java.util.*;

import static utility.AOCUtility.readFile;

public class SolutionDay17 {
    public static void main(String[] args) {
        var fileName = "resources/input/day17.txt";
        List<String> input = readFile(fileName);

        Map<CoordinateXD, Node> nodeMap = initializeMap(input, 3);
        applyNewStatesToNodes(nodeMap);

        evolveNumberOfRounds(nodeMap, 6);
        System.out.format("SolutionA = %d\n", nodeMap.values().stream().filter(t -> t.enabled).count());

        nodeMap = initializeMap(input, 4);

        applyNewStatesToNodes(nodeMap);
        evolveNumberOfRounds(nodeMap,6);
        System.out.format("SolutionB = %d\n", nodeMap.values().stream().filter(t -> t.enabled).count());
    }

    private static void applyNewStatesToNodes(Map<CoordinateXD, Node> nodeMap) {
        HashSet<Node> tempNodes = new HashSet<>(nodeMap.values());
        for (Node tempNode : tempNodes) {
            tempNode.apply(nodeMap);
        }
    }

    private static void evolveNumberOfRounds(Map<CoordinateXD, Node> nodeMap, int numberOfRounds) {
        for (int i = 0; i < numberOfRounds; i++) {
            for (Node tempNode : nodeMap.values()) {
                int activeNeighbors = tempNode.getActiveNeighbours(nodeMap);
                if (tempNode.enabled && (activeNeighbors < 2 || activeNeighbors > 3)) {
                    tempNode.setEnabled(false);
                }
                if (!tempNode.enabled && activeNeighbors == 3) {
                    tempNode.setEnabled(true);
                }
            }

            applyNewStatesToNodes(nodeMap);
        }
    }

    private static Map<CoordinateXD, Node> initializeMap(List<String> input, int dimensions) {
        Map<CoordinateXD, Node> nodeMap = new HashMap<>();

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                CoordinateXD coordinate = new CoordinateXD(dimensions, x, y);
                Node tempNode = nodeMap.getOrDefault(coordinate, new Node(coordinate));
                nodeMap.put(coordinate, tempNode);
                if (input.get(y).charAt(x) == '#') {
                    tempNode.setEnabled(true);
                }
            }
        }
        return nodeMap;
    }

    static class Node {
        CoordinateXD coordinates;
        boolean enabled;
        boolean enabledToBeSet;

        Node(CoordinateXD coordinates) {
            this.coordinates = coordinates;
            enabled = false;
            enabledToBeSet = false;
        }

        void apply(Map<CoordinateXD, Node> nodeMap) {
            enabled = enabledToBeSet;
            if (enabled) {
                for (int x = coordinates.x - 1; x <= coordinates.x + 1; x++) {
                    for (int y = coordinates.y - 1; y <= coordinates.y + 1; y++) {
                        for (int z = coordinates.z - 1; z <= coordinates.z + 1; z++) {
                            for (int w = coordinates.w - 1; w <= coordinates.w + 1; w++) {
                                if (coordinates.dimensions < 3 && z != 0) continue;
                                if (coordinates.dimensions < 4 && w != 0) continue;

                                CoordinateXD coordinate = new CoordinateXD(coordinates.dimensions, x, y, z, w);
                                if (coordinates.equals(coordinate)) continue;
                                if (!nodeMap.containsKey(coordinate)) {
                                    nodeMap.put(coordinate, new Node(coordinate));
                                }
                            }
                        }
                    }
                }
            }
        }

        int getActiveNeighbours(Map<CoordinateXD, Node> nodeMap) {
            int sum = 0;
            for (int x = coordinates.x - 1; x <= coordinates.x + 1; x++) {
                for (int y = coordinates.y - 1; y <= coordinates.y + 1; y++) {
                    for (int z = coordinates.z - 1; z <= coordinates.z + 1; z++) {
                        for (int w = coordinates.w - 1; w <= coordinates.w + 1; w++) {
                            if (coordinates.dimensions < 3 && z != 0) continue;
                            if (coordinates.dimensions < 4 && w != 0) continue;
                            CoordinateXD coordinate = new CoordinateXD(coordinates.dimensions, x, y, z, w);
                            if (coordinates.equals(coordinate)) continue;
                            Node tempNode = nodeMap.getOrDefault(coordinate, null);
                            if (tempNode == null || !tempNode.enabled) continue;
                            sum++;
                        }
                    }
                }
            }
            return sum;
        }

        void setEnabled(boolean enabled) {
            this.enabledToBeSet = enabled;
        }
    }

    static class CoordinateXD {
        int dimensions;
        int x;
        int y;
        int z;
        int w;

        CoordinateXD(int dimensions, int x, int y) {
            this.dimensions = dimensions;
            this.x = x;
            this.y = y;
            this.z = 0;
            this.w = 0;
        }

        CoordinateXD(int dimensions, int x, int y, int z, int w) {
            this.dimensions = dimensions;
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CoordinateXD that = (CoordinateXD) o;
            return x == that.x &&
                    y == that.y &&
                    z == that.z &&
                    w == that.w &&
                    dimensions == that.dimensions;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, w, dimensions);
        }
    }
}