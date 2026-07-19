package com.rahulagarwal.promptforge.rag.retrieval.util;

public final class VectorFormatter {

    private VectorFormatter() {
    }

    public static String format(float[] vector) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < vector.length; i++) {
            if (i > 0) {
                builder.append(",");
            }
            builder.append(vector[i]);
        }
        builder.append("]");
        return builder.toString();
    }
}