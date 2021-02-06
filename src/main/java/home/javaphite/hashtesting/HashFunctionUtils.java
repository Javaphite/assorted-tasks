package home.javaphite.hashtesting;

import java.util.Objects;

public class HashFunctionUtils {

    public static int getGroupHash(Object... hashComponents) {
        int result = 0;
        int prime = 31;
        int linearOffset = 117;

        for (Object component : hashComponents) {
            result ^= Objects.isNull(component) ? 0 : component.hashCode() * prime + linearOffset;
        }
        return result;
    }
}
