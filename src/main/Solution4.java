//package solution;
//import api.*;

import java.util.ArrayList;
import java.util.List;

public class Solution4 implements SolutionInterface {
    private APICaller api;
    private List<Integer> pegDistances;
    private List<Integer> gearRadii;
    private int gearRatio;
    private List<Integer> gearOrder;
    private Pair startNEndGear;

    public Solution4(APICaller api) {
        this.api = api;
        System.out.println("Press run code to see this in the console!");
        // You can initiate and calculate things here
    }

    /**
     * Given all available gear radii on the ground, the peg distances and the gear ratio to achieve, this method returns the placement of the gears.
     */
    public List<Integer> gearOrder(List<Integer> pegDistances, List<Integer> gearRadii, int gearRatio) {
        System.out.println("gearOrder - main-method");
        System.out.println("gearRadii.size() = " + gearRadii.size());
        gearRadii.stream().forEach(gearRadi -> System.out.print(" " + gearRadi));
        System.out.println();
        System.out.println("pegDistances.size() = " + pegDistances.size());
        pegDistances.stream().forEach(pegDistance -> System.out.print(" " + pegDistance));
        System.out.println();
        System.out.println("gearRatio = " + gearRatio);

        this.pegDistances = pegDistances;
        this.gearRadii = gearRadii;
        this.gearRatio = gearRatio;

        startNEndGear = findStartNEndGear();

        return findGearOrder();
    }

    private Pair createStartEndGear() {
        System.out.println("createStartEndGear");

        int highestPegdistance = pegDistances.stream().reduce((first, second) -> first >= second ? first : second).get();
        int lowestPegdistance = pegDistances.stream().reduce((first, second) -> first <= second ? first : second).get();

        int last = highestPegdistance - lowestPegdistance - 2;
        System.out.println("createStartEndGear: highestPegdistance = " + highestPegdistance + ", lowestPegdistance = " + lowestPegdistance + ", lastRadius = " + last);
        int first = 1;
        first = gearRatio * last;
        System.out.println("createStartEndGear - gearRatio = " + gearRatio + ", first = "  + first + ", last = " + last);
        gearRadii.add(first);
        gearRadii.add(last);
        return findStartNEndGear();
    }

    private Pair findStartNEndGear() {
        System.out.println("findStartNEndGear");
        for(int first = 0; first < gearRadii.size(); first++) {
            for(int last = 0; last < gearRadii.size(); last++) {
                System.out.println("findStartNEndGear - first = " + gearRadii.get(first) + ", last = " + gearRadii.get(last));
                if(gearRatio == gearRadii.get(first) / gearRadii.get(last)) {
                    System.out.println("got - " + first + " and " + last + ": " + gearRadii.get(first) + " / " + gearRadii.get(last));
                    return new Pair(first, last);
                }
            }
        }
        return createStartEndGear();
    }

    private List<Integer> findGearOrder() {
        System.out.println("findGearOrder");

        gearOrder = new ArrayList<>();
        int startRadius = gearRadii.get(startNEndGear.getFirstGear());
        int endRadius = gearRadii.get(startNEndGear.getLastGear());
        gearRadii.remove(startNEndGear.getLastGear()); // Must remove lastGearsFirst not to fuck up index
        gearRadii.remove(startNEndGear.getFirstGear());

        gearOrder.add(startRadius);
        addNextGear(startRadius, 0);
        gearOrder.add(endRadius);

        return gearOrder;
    }

    private void addNextGear(int firstGearRadius, int pegDistanceCounter) {
        if(pegDistanceCounter < pegDistances.size() && gearOrder.size() < pegDistances.size()) {
            int pegDistance = pegDistances.get(pegDistanceCounter);
            int nextRadius = pegDistance - firstGearRadius;
            System.out.println("addNextGear - firstGearRadius = " +
                               firstGearRadius +
                               ", pegDistance = " +
                               pegDistances.get(pegDistanceCounter) +
                               ". nextRadius = " +
                               nextRadius);
            boolean foundNextGear = false;
            int nextPegDistanceCounterValue = pegDistanceCounter + 1;
            for(int n = 0; n < gearRadii.size() && !foundNextGear; n++) {
                int gearRadi = gearRadii.get(n);
                System.out.println(firstGearRadius + " + " + gearRadi + " = " + pegDistance);
                if(gearRadi == nextRadius) {
                    foundNextGear = true;
                    gearOrder.add(gearRadii.get(n));
                    gearRadii.remove(n);
                    addNextGear(gearRadi, nextPegDistanceCounterValue);
                    return;
                }
            }
            if(!foundNextGear) {
                System.out.println("Create gear");
                gearOrder.add(nextRadius);
                addNextGear(nextRadius, nextPegDistanceCounterValue);
            }
        }
    }

    private class Pair {
        private final int firstGear;
        private final int lastGear;

        public Pair(int a, int b) {
            this.firstGear = a;
            this.lastGear = b;
        }

        public int getFirstGear() {
            return firstGear;
        }

        public int getLastGear() {
            return lastGear;
        }
    }

    private class NoStartNEndGearFoundException extends Exception {
        public NoStartNEndGearFoundException(String s) {
            super(s);
        }
    }
}