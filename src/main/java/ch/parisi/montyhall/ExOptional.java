package ch.parisi.montyhall;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * java does not have Try,Optional
 */
public class ExOptional {
    public static <T> Optional<T> of(ExSupplier<T> tSupplier) {
        try {
            return Optional.of(tSupplier.get());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> T orElse(ExSupplier<T> tSupplier, Supplier<T> orElse) {
        return of(tSupplier).orElseGet(orElse);
    }

    public static <T> T untilSuccess(ExSupplier<T> tSupplier) {
        Optional<T> empty = Optional.empty();
        while (empty.isEmpty()) {
            empty = of(tSupplier);
        }
        return empty.get();
    }

    @FunctionalInterface
    public interface ExSupplier<T> {
        T get() throws Exception;
    }
}

