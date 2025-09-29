package xela.chris.barbearia.negocio;

import java.util.concurrent.atomic.AtomicInteger;

public class GeradoraDeIds {
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static int nextId() {
        return counter.incrementAndGet();
    }
}
