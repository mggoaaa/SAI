package behaviours;

import interfaces.GuiVendedor;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import vendedor.Vendedor;

import javax.swing.*;

public class PurchaseOrdersServer extends CyclicBehaviour {
    Vendedor vendedor;
    GuiVendedor guiVendedor;

    public PurchaseOrdersServer(Vendedor a) {
        vendedor = a;
    }

    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
        ACLMessage msg = vendedor.receive(mt);

        if (msg != null) {
            String title = msg.getContent();
            ACLMessage reply = msg.createReply();

            Integer price = (Integer) vendedor.getCatalogo().remove(title);
            if (price != null) {
                reply.setPerformative(ACLMessage.INFORM);
                System.out.println(title + " vendido a " + msg.getSender().getName());
                JOptionPane.showMessageDialog(guiVendedor, "Libro vendido: " + title + 
                        "\nAl agente: " + msg.getSender().getName(),"Successful sale", JOptionPane.PLAIN_MESSAGE);
            } else {
                reply.setPerformative(ACLMessage.FAILURE);
                reply.setContent("not-available");
            }
            vendedor.send(reply);
        } else {
            block();
        }
    }
}
