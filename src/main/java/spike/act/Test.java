package spike.act;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Test {

    static class Message {}

    static List<Handler<Object, Object>> handlers = Arrays.asList(
            Handler.match(Message.class::isInstance, Function.identity()),
            Handler.match(Message.class, Function.identity())
    );

    public static void main(String... args) {
        ActorSystem actorSystem = ActorSystem.create();
        ActorChain ch = ActorChain.create(actorSystem, handlers);
        ActorRef ref = null;
        ch.outputTo(ref);
    }
}
