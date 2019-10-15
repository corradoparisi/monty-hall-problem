package ch.parisi.montyhall;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The {@link ExOptional} class provides helper methods around the default {@link Optional}
 * to simplify the usage with exceptions.
 */
public class ExOptional {
    public static <T> Optional<T> of(ExSupplier<T> tSupplier) {
        try {
            return Optional.of(tSupplier.get());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static <T> T orElseGet(ExSupplier<T> tSupplier, Supplier<T> orElse) {
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

