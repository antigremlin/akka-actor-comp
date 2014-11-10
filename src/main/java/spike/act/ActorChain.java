package spike.act;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.List;

public class ActorChain {

    private final ActorRef ref;

    public ActorChain(ActorRef ref) {
        this.ref = ref;
    }

    public void outputTo(ActorRef output) {
        ref.tell(new OutputLink.Subscribe(output), ActorRef.noSender());
    }

    public static ActorChain create(ActorSystem system, List<Handler<Object, Object>> handlers) {
        ActorRef actorRef = system.actorOf(Props.create(ChainActor.class, handlers));
        return new ActorChain(actorRef);
    }
}
