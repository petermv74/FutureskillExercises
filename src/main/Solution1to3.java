//package solution;
//
//import api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Solution1to3 implements SolutionInterface {
    private APICaller api;
    private List<Integer> pegDistances;
    private List<Integer> gearRadii;
    private int gearRatio;
    private List<Integer> gearOrder;
    private Pair startNEndGear;

    public Solution1to3(APICaller api) {
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

        List<Integer> gearOrder = findGearOrder();

        return gearOrder;
    }

    private Pair findStartNEndGear() {
        System.out.println("findStartNEndGear");
        for(int first = 0; first < gearRadii.size(); first++) {
            for(int last = 0; last < gearRadii.size(); last++) {
                if(gearRatio == gearRadii.get(first) / gearRadii.get(last)) {
                    System.out.println("got - " + first + " and " + last + ": " + gearRadii.get(first) + " / " + gearRadii.get(last));
                    return new Pair(first, last);
                }
            }
        }
        throw new NoSuchElementException("No Start/End combination found for gearRatio");
    }

    private List<Integer> findGearOrder() {
        System.out.println("findGearOrder");

        gearOrder = new ArrayList<>();
        int startRadius = gearRadii.get(startNEndGear.getFirstGear());
        int endRadius = gearRadii.get(startNEndGear.getLastGear());
        gearRadii.remove(startNEndGear.getLastGear());
        gearRadii.remove(startNEndGear.getFirstGear());

        gearOrder.add(startRadius);
        addNextGear(startRadius, 0);
        gearOrder.add(endRadius);

        return gearOrder;
    }

    private void addNextGear(int firstGearRadius, int pegDistanceCounter) {
        if(pegDistanceCounter < pegDistances.size() && gearOrder.size() < pegDistances.size()) {
            System.out.println("addNextGear - firstGearRadius = " + firstGearRadius + ", pegDistance = " + pegDistances.get(pegDistanceCounter));
            int pegDistance = pegDistances.get(pegDistanceCounter);
            int nextRadius = pegDistance - firstGearRadius;
            boolean foundNextGear = false;
            for(int n = 0; n < gearRadii.size() && !foundNextGear; n++) {
                int gearRadi = gearRadii.get(n);
                System.out.println(firstGearRadius + " + " + gearRadi + " = " + pegDistance);
                if(gearRadi == nextRadius) {
                    foundNextGear = true;
                    gearOrder.add(gearRadii.get(n));
                    gearRadii.remove(n);
                    addNextGear(gearRadi, pegDistanceCounter + 1);
                    return;
                }
            }
            if(!foundNextGear) {
                gearOrder.add(nextRadius);
                addNextGear(nextRadius, pegDistanceCounter + 1);
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
}