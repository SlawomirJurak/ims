package pl.sgnit.ims.util;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Month {

    private int id;
    private String name;

    private static List<Month> months = Arrays.asList(
            new Month(1, "Styczeń"),
            new Month(2, "Luty"),
            new Month(3, "Marzec"),
            new Month(4, "Kwiecień"),
            new Month(5, "Maj"),
            new Month(6, "Czerwiec"),
            new Month(7, "Lipiec"),
            new Month(8, "Sierpień"),
            new Month(9, "Wrzesień"),
            new Month(10, "Październik"),
            new Month(11, "Listopad"),
            new Month(12, "Grudzień"));

    public Month(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static List<Month> monthList() {
        return months;
    }

    public static String getMonthName(int monthNo) {
        return months.get(monthNo).name;
    }
}
