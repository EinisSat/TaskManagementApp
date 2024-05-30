package com.example.myapplication;
import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Converters {
    @TypeConverter
    public static LocalDateTime toDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            return LocalDateTime.parse(dateString);
        }
    }

    @TypeConverter
    public static String toDateString(LocalDateTime date) {
        if (date == null) {
            return null;
        } else {
            return date.toString();
        }
    }
}
