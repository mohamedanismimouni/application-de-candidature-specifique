package com.talan.polaris.common;

import java.util.StringTokenizer;

public class FloatWordConverter {

    public static final String[] units = {
            "", "Un", "Deux", "Trois", "Quatre", "Cinq", "Six", "Sept",
            "Huit", "Neuf", "Dix", "Onze", "Douze", "Treize", "Quatorze",
            "Quinze", "Seize", "Dix-sept", "Dix-huit", "Dix-neuf"
    };

    public static final String[] tens = {
            "", // 0
            "", // 1
            "Vingt", // 2
            "Trente", // 3
            "Quarante", // 4
            "Cinquante", // 5
            "Soixante", // 6
            "Soixante-dix", // 7
            "Quatre-vingts", // 8
            "Quatre-vingt-dix" // 9
    };

    public static String doubleConvert(final double n) {
        String pass = n + "";
        StringTokenizer token = new StringTokenizer(pass, ".");
        String first = token.nextToken();
        String last = token.nextToken();
        try {
            pass = convert(Integer.parseInt(first))+" ";
            pass=pass+"Et"+" ";
            pass=pass+convert(Integer.parseInt(last));

        } catch (NumberFormatException nf) {
        }
        return pass;
    }

    public static String convert(final int n) {
        if (n < 0) {
            return "minus " + convert(-n);
        }

        if (n < 20) {
            return units[n];
        }

        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }

        if (n < 1000) {
            return units[n / 100] + " Cent" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
        }

        if (n < 1000000) {
            return convert(n / 1000) + " Mille" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
        }

        if (n < 1000000000) {
            return convert(n / 1000000) + " Million " + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
        }

        return convert(n / 1000000000) + " Billion " + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
    }
}