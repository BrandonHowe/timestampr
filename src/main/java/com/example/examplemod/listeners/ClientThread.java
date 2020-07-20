package com.example.examplemod.listeners;

import com.example.examplemod.TimeStampr;

import java.util.Calendar;

public final class ClientThread extends Thread {
    private final static Calendar cal = Calendar.getInstance();

    public static String getTime(TimeStampr main) {
        cal.setTimeInMillis(System.currentTimeMillis());
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);
        final StringBuilder b = new StringBuilder(main.getSettings().get("prefix"));
        b.append(hour / 10);
        b.append(hour % 10);
        b.append(main.getSettings().get("separator"));
        b.append(minute / 10);
        b.append(minute % 10);
        b.append(main.getSettings().get("separator"));
        b.append(second / 10);
        b.append(second % 10);
        b.append(main.getSettings().get("suffix"));
        return b.toString();
    }
}