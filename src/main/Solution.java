//package solution;
//
//import api.*;

import java.util.Hashtable;

public class Solution implements SolutionInterface {
    private final APICaller api;
    private final PriceOnDayCache priceOnDayCache;
    private final int numberOfDays;
    private int bestBuyDay = 0;
    private int bestSellDay = 1;

    public Solution(APICaller api) {
        this.api = api;
        numberOfDays = this.api.getNumDays();
        priceOnDayCache = new PriceOnDayCache(numberOfDays);
        calculateBestDayToBuyAndSell();
    }

    private void calculateBestDayToBuyAndSell() {
        DayAndProfit bestSellDayAndProfit = calculatePotentialBestSellDayAndProfit(0);
        for(int buyDay = 0; buyDay < numberOfDays; buyDay++) {
            DayAndProfit dayAndProfit = calculatePotentialBestSellDayAndProfit(buyDay);
            if(bestSellDayAndProfit.profit < dayAndProfit.profit) {
                bestSellDayAndProfit = dayAndProfit;
                bestBuyDay = buyDay;
                bestSellDay = bestSellDayAndProfit.sellDay;
            }
        }
    }

    private DayAndProfit calculatePotentialBestSellDayAndProfit(int buyDay) {
        int bestSellPrice = 0;
        int bestSellDay = 0;
        for(int sellDay = buyDay + 1; sellDay < numberOfDays; sellDay++) {
            int price = priceOnDayCache.getPriceOnDayFromCacheOrApi(sellDay);
            if(price > bestSellPrice) {
                bestSellPrice = price;
                bestSellDay = sellDay;
            }
        }
        int priceOnBuy = priceOnDayCache.getPriceOnDayFromCacheOrApi(buyDay);
        int bestProfit = bestSellPrice - priceOnBuy;
        return new DayAndProfit(bestProfit, bestSellDay);
    }

    /**
     * Return the day which you buy silver. The first day has number zero. This method is called first, and only once.
     */
    public int getBuyDay() {
        return bestBuyDay;
    }

    /**
     * Return the day to sell silver on. This day has to be after (greater than) the buy day. The first day has number zero (although this is not a valid sell day).
     * This method is called second, and only once.
     */
    public int getSellDay() {
        return bestSellDay;
    }

    private class DayAndProfit {
        private final int profit;
        private final int sellDay;

        public DayAndProfit(int profit, int sellDay) {
            this.profit = profit;
            this.sellDay = sellDay;
        }
    }

    private class PriceOnDayCache {
        private final Hashtable<Integer, Integer> priceOnDayCache;

        private PriceOnDayCache(Integer numberOfDays) {
            priceOnDayCache = new Hashtable<>(numberOfDays);
        }

        public int getPriceOnDayFromCacheOrApi(int dayNumber) {
            if(!priceOnDayCache.containsKey(dayNumber)) {
                priceOnDayCache.put(dayNumber, api.getPriceOnDay(dayNumber));
            }

            return priceOnDayCache.get(dayNumber);
        }
    }
}