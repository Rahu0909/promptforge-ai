package com.rahulagarwal.promptforge.vector.util;

public final class VectorUtils {

    private VectorUtils() {
    }

    public static String toPgVector(float[] embedding) {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < embedding.length; i++) {
            builder.append(embedding[i]);
            if (i < embedding.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}