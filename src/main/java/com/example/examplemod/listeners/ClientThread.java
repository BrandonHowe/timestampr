package com.example.examplemod.listeners;

import com.example.examplemod.TimeStampr;

import java.util.Calendar;

public final class ClientThread extends Thread {
    private final static Calendar cal = Calendar.getInstance();

    public static String getTime(TimeStampr main) {
        cal.setTimeInMillis(System.currentTimeMillis());
        final int hour = cal.get(main.is24Hour() ? Calendar.HOUR_OF_DAY : Calendar.HOUR);
        final int minute = cal.get(Calendar.MINUTE);
        final int second = cal.get(Calendar.SECOND);
        final int milliseconds = cal.get(Calendar.MILLISECOND);
        final StringBuilder b = new StringBuilder(main.getSettings().get("prefix"));
        if (!main.is24Hour() && hour >= 10) {
            b.append(hour / 10);
        }
        b.append(hour % 10);
        b.append(main.getSettings().get("separator"));
        b.append(minute / 10);
        b.append(minute % 10);
        if (main.isSeconds()) {
            b.append(main.getSettings().get("separator"));
            b.append(second / 10);
            b.append(second % 10);
        }
        if (main.isMilliseconds()) {
            b.append(".");
            b.append(milliseconds / 100);
            b.append((milliseconds / 10) % 10);
            b.append(milliseconds % 10);
        }
        if (!main.is24Hour()) {
            b.append(cal.get(Calendar.HOUR_OF_DAY) < 12 ? " AM" : " PM");
        }
        b.append(main.getSettings().get("suffix"));
        return b.toString();
    }
}