package com.example.spotifyfestival.ConcertsAndFestivals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateTimeFormatDetector {
    public static String detectDateTimeFormat(String dateTimeStr) {
        String[] dateFormats = {
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd"
        };

        for (String dateFormat : dateFormats) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            try {
                Date date = formatter.parse(dateTimeStr);
                if (date != null) {
                    return "Detected Format: " + dateFormat;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        return "Unknown format";
    }

    public static void main(String[] args) {
        String dateTimeStr = "2023-10-20";
        String dateTimeStr2 = "2023-10-21T21:00:00+0300";
        List<String> array = new ArrayList<>();
        array.add(dateTimeStr2);
        array.add(dateTimeStr);

        for(String str : array){
            String formatType = detectDateTimeFormat(str);
            System.out.println(formatType);
        }

    }
}

