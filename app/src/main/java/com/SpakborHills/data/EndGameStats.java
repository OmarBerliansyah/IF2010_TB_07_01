package com.SpakborHills.data;

import java.util.List;

import com.SpakborHills.entity.Entity.FishableProperties;
import com.SpakborHills.entity.NPC;


public class EndGameStats<T extends List<?>, U extends List<?>, V extends List<NPC>, W, X extends List<FishableProperties>, Y> {
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
        int commonCnt = 0, regularCnt = 0, legendaryCnt = 0;
        StringBuilder fishCaughtStr = new StringBuilder();

        for (FishableProperties fish : fishCaught) {
            String category = fish.getFishCategory();
            if (category.equalsIgnoreCase("Common")) {
                commonCnt++;
            } else if (category.equalsIgnoreCase("Regular")) {
                regularCnt++;
            } else if (category.equalsIgnoreCase("Legendary")) {
                legendaryCnt++;
            }
        }

        fishCaughtStr.append("Common fish catch: ").append(commonCnt).append("\n");
        fishCaughtStr.append("Regular fish catch: ").append(regularCnt).append("\n");
        fishCaughtStr.append("Legendary fish catch: ").append(legendaryCnt).append("\n");

        String[] npcNames = new String[6];
        String[] npcStatuses = new String[6];
        int[] chatFreq = new int[6];
        int[] giftFreq = new int[6];
        int[] visitFreq = new int[6];
        // Initialize all arrays first
        for (int i = 0; i < 6; i++) {
            npcNames[i] = "-";
            npcStatuses[i] = "-";
            chatFreq[i] = 0;
            giftFreq[i] = 0;
            visitFreq[i] = 0;
        }
        for (int i = 0; i < 6; i++) {
            if (i < npcs.size() && npcs.get(i) != null) {
                npcNames[i] = npcs.get(i).name;
                npcStatuses[i] = String.valueOf(npcs.get(i).getRelationshipStatus());
                chatFreq[i] = npcs.get(i).getChattingFrequency();
                giftFreq[i] = npcs.get(i).getGiftingFrequency();
                visitFreq[i] = npcs.get(i).getVisitingFrequency();
            }
        }

        return String.format(
            """
            === END GAME STATS ===
            Total Income: %dg
            Total Expenditure: %dg
            Average Seasonal Income: %.2fg
            Average Seasonal Expenditure: %.2fg
            Total Days Played: %s
            NPCs status:
                Relationships with NPC 1:
                    Relationships with %s: %s
                    Chatting Frequency: %d
                    Gifting Frequency: %d
                    Visiting Frequency: %d
                Relationships with NPC 2:
                    Relationships with %s: %s
                    Chatting Frequency: %d
                    Gifting Frequency: %d
                    Visiting Frequency: %d
                Relationships with NPC 3:
                    Relationships with %s: %s
                    Chatting Frequency: %d
                    Gifting Frequency: %d
                    Visiting Frequency: %d
                Relationships with NPC 4:
                    Relationships with %s: %s
                    Chatting Frequency: %d
                    Gifting Frequency: %d
                    Visiting Frequency: %d
                Relationships with NPC 5:
                    Relationships with %s: %s
                    Chatting Frequency: %d
                    Gifting Frequency: %d
                    Visiting Frequency: %d
                Relationships with NPC 6:
                    Relationships with %s: %s 
                    Chatting Frequency: %d
                    Gifting Frequency: %d
                    Visiting Frequency: %d
            Total Crops Harvested: %s
            Total fish caught: %d
            Fish caught details: 
                %s
            """,
            moneyFlows.get(0),
            moneyFlows.get(1),
            seasonalMoneyFlows.get(0),
            seasonalMoneyFlows.get(1),
            totalDaysPlayed,
            npcNames[0], npcStatuses[0], chatFreq[0], giftFreq[0], visitFreq[0],
            npcNames[1], npcStatuses[1], chatFreq[1], giftFreq[1], visitFreq[1],
            npcNames[2], npcStatuses[2], chatFreq[2], giftFreq[2], visitFreq[2],
            npcNames[3], npcStatuses[3], chatFreq[3], giftFreq[3], visitFreq[3],
            npcNames[4], npcStatuses[4], chatFreq[4], giftFreq[4], visitFreq[4],
            npcNames[5], npcStatuses[5], chatFreq[5], giftFreq[5], visitFreq[5],
            totalCropHarvested,
            fishCaught.size(),
            fishCaughtStr.toString()
        );

    }
}