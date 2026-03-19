package com.sharom.wrm.common.util;

public class BoxNumberGenerator {

    public static String generateGroupCode(int groupNumber) {
        if (groupNumber <= 0) {
            throw new IllegalArgumentException("Group number must be >= 1");
        }

        StringBuilder sb = new StringBuilder();
        int n = groupNumber;

        while (n > 0) {
            n--; // корректировка для 0-based
            int remainder = n % 26;
            sb.append((char) ('A' + remainder));
            n = n / 26;
        }

        return sb.reverse().toString();
    }


    public static String generateBoxNumber(String groupCode, int boxIndex) {
        return String.format("%s-%02d", groupCode, boxIndex);
    }
}
