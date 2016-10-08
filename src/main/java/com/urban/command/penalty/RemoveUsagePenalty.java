package com.urban.command.penalty;

import com.urban.usage.UsageCounter;

public class RemoveUsagePenalty {
    public String penaltyMessage(String username) {
        return "Hey " + username + "! Only lols allowed! Penalty given!";
    }

    public String noPenaltyMessage(String username) {
        return "Hey " + username + "! Only lols allowed!";
    }

    public void addPenalty(UsageCounter counter, String username) {
        counter.removeUsage(username);
    }
}