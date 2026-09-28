package org.testcharm.pf;

import org.testcharm.dal.DAL;
import org.testcharm.interpreter.SyntaxException;
import org.testcharm.util.Pair;
import org.testcharm.util.Sneaky;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class PageFactory {
    private final List<Pair<Predicate<Indicator>, BiFunction<Element<?, ?, ?>, Indicator, Page<?>>>> factories = new ArrayList<>();

    public <E extends Element<E, ?, ?>> PageFactory register(Predicate<Indicator> predicate, BiFunction<E, Indicator, Page<?>> constructor) {
        factories.add(new Pair<>(predicate, Sneaky.cast(constructor)));
        return this;
    }

    public <E extends Element<E, ?, ?>> PageFactory register(String daLExpression, BiFunction<E, Indicator, Page<?>> constructor) {
        return register(indicator -> {
            try {
                DAL.dal().evaluate(indicator, daLExpression);
                return true;
            } catch (SyntaxException e) {
                throw new RuntimeException("\n" + e.show(daLExpression, 0) + "\n\n" + e.getMessage());
            } catch (Exception e) {
                return false;
            }
        }, constructor);
    }

    public <E extends Element<E, ?, ?>> Optional<Page<?>> create(E element, Indicator indicator) {
        for (int i = factories.size() - 1; i >= 0; i--) {
            Pair<Predicate<Indicator>, BiFunction<Element<?, ?, ?>, Indicator, Page<?>>> pair = factories.get(i);
            if (pair.getFirst().test(indicator)) {
                return Optional.of(pair.getSecond().apply(element, indicator));
            }
        }
        return Optional.empty();
    }
}
