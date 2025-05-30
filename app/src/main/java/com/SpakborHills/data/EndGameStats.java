package com.SpakborHills.data;

import java.util.List;


public class EndGameStats<T extends List<?>, U extends List<?>, V extends List<?>, W, X extends List<?>, Y> {
    private T moneyFlows;
    private U seasonalMoneyFlows;
    private V npcs;
    private W totalCropHarvested;
    private X fishCaught;
    private Y totalDaysPlayed;


    public EndGameStats(T moneyFlows, U seasonalMoneyFlows, V npcs, W totalCropHarvested, X fishCaught, Y totalDaysPlayed) {
        this.moneyFlows = moneyFlows;
        this.seasonalMoneyFlows = seasonalMoneyFlows;
        this.npcs = npcs;
        this.totalCropHarvested = totalCropHarvested;
        this.fishCaught = fishCaught;
        this.totalDaysPlayed = totalDaysPlayed;
    }

    public T getMoneyFlows() {
        return moneyFlows;
    }

    public U getSeasonalMoneyFlows() {
        return seasonalMoneyFlows;
    }

    public V getNpcs() {
        return npcs;
    }

    public W getTotalCropHarvested() {
        return totalCropHarvested;
    }

    public X getFishCaught() {
        return fishCaught;
    }

    public Y getTotalDaysPlayed() {
        return totalDaysPlayed;
    }


    @Override
    public String toString() {
        return String.format(
            """
            === END GAME STATS ===
            Total Income: %dg
            Total Expenditure: %dg
            Average Seasonal Income: %.2fg
            Average Seasonal Expenditure: %.2fg
            Total Crops Harvested: %s
            Fish Species Caught: %s
            NPCs Known: %d
            Total Days Played: %s
            """,
            moneyFlows.get(0),
            moneyFlows.get(1),
            seasonalMoneyFlows.get(0),
            seasonalMoneyFlows.get(1),
            totalCropHarvested,
            fishCaught,
            npcs.size(),
            totalDaysPlayed
        );
    }
}