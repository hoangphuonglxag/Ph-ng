package com.rtic.webhub.tag.util;

import java.text.Normalizer;
public class TextNormalizer {

    public static String normalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        normalized = normalized.toLowerCase().replaceAll("[^a-z0-9 ]", "").replaceAll("\\s+", " ").trim();
        return normalized;
    }
}
