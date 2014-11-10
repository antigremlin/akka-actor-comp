package spike.act;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ChainActor extends UntypedActor {
    OutputLink output = new OutputLink(getSelf());
    Collection<Handler<Object, Object>> handlers;

    public ChainActor(Collection<Handler<Object, Object>> handlers) {
        this.handlers = new ArrayList<>(handlers);
    }

    @Override public void onReceive(Object msg) {
        if (output.onReceive(msg)) return;
        Optional<?> result = handlers.stream()
                                     .filter(h -> h.match(msg)).limit(1)
                                     .findFirst().map(h -> h.handle(msg));
        result.ifPresent(output::send);
        if (!result.isPresent()) unhandled(msg);
    }

}
