package spike.act;

import java.util.function.Function;
import java.util.function.Predicate;

interface Handler <T, U> {
    boolean match(T msg);
    U handle(T msg);

    static <T, U> Handler<T,U> match(Predicate<T> p, Function<T, U> h) {
        return new Handler<T,U>() {
            @Override public boolean match(T msg) { return p.test(msg); }
            @Override public U handle(T msg) { return h.apply(msg); }
        };
    }

    static <T, U> Handler<T,U> match(Class<? extends T> c, Function<T, U> h) {
        return new Handler<T,U>() {
            @Override public boolean match(T msg) { return c.isInstance(msg); }
            @Override public U handle(T msg) { return h.apply(msg); }
        };
    }
}
