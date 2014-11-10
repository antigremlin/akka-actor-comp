package spike.act;

import akka.actor.ActorRef;

public class OutputLink {
    private final ActorRef sender;
    private ActorRef next = ActorRef.noSender();

    public OutputLink(ActorRef sender) {this.sender = sender;}

    static class Subscribe {
        public final ActorRef actor;
        Subscribe(ActorRef actor) {this.actor = actor;}
    }

    public boolean onReceive(Object msg) {
        if (msg instanceof Subscribe) {
            next = ((Subscribe) msg).actor;
            return true;
        }
        return false;
    }

    public void send(Object result) {
        next.tell(result, sender);
    }
}
