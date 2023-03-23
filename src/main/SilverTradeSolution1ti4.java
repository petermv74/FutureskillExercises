//package solution;
//
//import api.*;

public class SilverTradeSolution1ti4 implements SolutionInterface {
    private APICaller api;
    private int bestBuyDay;

    public SilverTradeSolution1ti4(APICaller api) {
        this.api = api;
        System.out.println("Press run code to see this in the console!");
        // You can initiate and calculate things here
    }

    /**
     * Return the day which you buy silver. The first day has number zero.
     * This method is called first, and only once.
     */
    public int getBuyDay() {
        // Write your code here
        bestBuyDay = 0;
        int bestProfit = calculateBestPossibleProfitforDay(bestBuyDay);
        for (int day = 0; day < api.getNumDays(); day++) {
            int profit = calculateBestPossibleProfitforDay(day);
            if (profit > bestProfit) {
                bestProfit = profit;
                bestBuyDay = day;
            }
        }
        return bestBuyDay;
    }

    private int calculateBestPossibleProfitforDay(int bestBuyDay) {
        int numberOfSDays = api.getNumDays();
        int bestSellPrice = 0;
        int buyPrice = api.getPriceOnDay(bestBuyDay);
        for (int n= bestBuyDay; n < numberOfSDays; n++) {
            int price = api.getPriceOnDay(n);
            if (price > bestSellPrice) {
                bestSellPrice = price;
            }
        }
        return bestSellPrice - buyPrice;
    }


    /**
     * Return the day to sell silver on. This day has to be after (greater
     * than) the buy day. The first day has number zero (although this is not
     * a valid sell day). This method is called second, and only once.
     */
    public int getSellDay() {
        // Write your code here
        int numberOfSDays = api.getNumDays();
        int bestPrice = api.getPriceOnDay(0);
        int bestSellDay = 0;
        for (int n = bestBuyDay; n < numberOfSDays; n++) {
            int price = api.getPriceOnDay(n);
            if (price > bestPrice) {
                bestPrice = price;
                bestSellDay = n;
            }
        }
        System.out.println("bestSellDay!" + bestSellDay + " - " + bestPrice);
        return bestSellDay;
    }
}