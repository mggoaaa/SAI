package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import vendedor.Vendedor;

public class OfferRequestServer extends CyclicBehaviour {
    Vendedor vendedor;

    public OfferRequestServer(Vendedor a) {
        vendedor = a;
    }

    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage msg = vendedor.receive(mt);

        if(msg != null) {
            String title = msg.getContent();
            ACLMessage reply = msg.createReply();

            Integer price = (Integer) vendedor.getCatalogo().get(title);

            if(price != null) {
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(String.valueOf(price.intValue()));
            } else {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("not-available");
            }

            vendedor.send(reply);
        } else {
            block();
        }
    }
}
