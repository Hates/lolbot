package com.urban.command.penalty;

import com.urban.usage.UsageCounter;

public class RemoveUsageAndTimeoutPenalty extends RemoveUsagePenalty {

    public String penaltyMessage(String username) {
        return "Hey " + username + "! Only lols allowed! Penalty and 5 minute timeout given!";
    }

    public String noPenaltyMessage(String username) {
        return "Hey " + username + "! Only lols allowed!";
    }

    public void addPenalty(UsageCounter counter, String username) {
        counter.removeUsage(username);
        counter.storeTimeout(username);
    }
}