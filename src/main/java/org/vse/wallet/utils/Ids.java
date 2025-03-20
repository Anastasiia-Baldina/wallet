package org.vse.wallet.utils;

import java.time.Instant;

public final class Ids {
    private Ids(){
        //no instances
    }

    public static long generateId(){
        return Instant.now().toEpochMilli();
    }
}
