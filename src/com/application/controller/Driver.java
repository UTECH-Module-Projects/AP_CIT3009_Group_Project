package com.application.controller;

import com.application.models.Utilities;

import java.util.Arrays;

public class Driver {
    public static void main(String[] args) {
        final int max = 20;
        final int maxLen = 3;
        String[] strIds = new String[max];
        int[] intIds = new int[max];

        Arrays.fill(strIds, "");
        Arrays.fill(intIds, 0);

        for (int i = 0; i < max; i++) {
            strIds[i] = Utilities.generateUniqueIDString(strIds, maxLen);
            intIds[i] = Utilities.generateUniqueIDInt(intIds, maxLen);
        }

        System.out.println(Arrays.toString(strIds));
        System.out.println(Arrays.toString(intIds));
    }
}
