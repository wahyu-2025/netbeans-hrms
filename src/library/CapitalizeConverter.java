/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author ramdhan
 */
public class CapitalizeConverter {

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String capitalizeWords(String str) {
        if (str == null || str.isBlank()) {
            return str;
        }

        return Arrays.stream(str.trim().split("\\s+"))
                .filter(word -> !word.isEmpty())
                .map(word -> Character.toUpperCase(word.charAt(0))
                + (word.length() > 1 ? word.substring(1).toLowerCase() : ""))
                .collect(Collectors.joining(" "));
    }
}
