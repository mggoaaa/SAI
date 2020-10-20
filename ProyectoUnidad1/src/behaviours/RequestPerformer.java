package behaviours;

import comprador.Comprador;
import interfaces.GuiComprador;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import javax.swing.*;

public class RequestPerformer extends Behaviour {
    private AID mejorVendedor;
    private int mejorPrecio;
    private int repliesCount = 0;
    private MessageTemplate mt;
    private int step = 0;
    private Comprador comprador;
    private String titulo;

    private GuiComprador guiComprador;

    public RequestPerformer(Comprador com) {
        comprador = com;
        titulo = com.getTitulo();
    }

    public void action() {
        switch (step) {
            case 0:
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (int i = 0; i < comprador.getAgentesVendedores().length; i++) {
                    cfp.addReceiver(comprador.getAgentesVendedores()[i]);
                }

                cfp.setContent(titulo);
                cfp.setConversationId("book-trade");
                cfp.setReplyWith("cfp" + System.currentTimeMillis());
                myAgent.send(cfp);

                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                        MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                step = 1;
                break;

            case 1:
                ACLMessage reply = comprador.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.PROPOSE) {
                        int price = Integer.parseInt(reply.getContent());
                        if (mejorVendedor == null || price < mejorPrecio) {
                            mejorPrecio = price;
                            mejorVendedor = reply.getSender();
                        }
                    }
                    repliesCount++;
                    if (repliesCount >= comprador.getAgentesVendedores().length) {
                        step = 2;
                    }
                } else {
                    block();
                }
                break;

            case 2:
                ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                order.addReceiver(mejorVendedor);
                order.setContent(titulo);
                order.setConversationId("book-trade");
                order.setReplyWith("order" + System.currentTimeMillis());
                comprador.send(order);

                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("book-trade"),
                        MessageTemplate.MatchInReplyTo(order.getReplyWith()));

                step = 3;

                break;

            case 3:
                reply = myAgent.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.INFORM) {
                        System.out.println(titulo + " successfully purchased from agent " + reply.getSender().getName());
                        JOptionPane.showMessageDialog(guiComprador, "Libro adquirido: " + titulo + "\n Con precio de: " + mejorPrecio,"Successful", JOptionPane.PLAIN_MESSAGE);
                        myAgent.doDelete();
                    } else {
                        System.out.println("Attempt failed: requested book already sold.");
                    }

                    step = 4;
                } else {
                    block();
                }
                break;
        }
    }

    public boolean done() {
        if (step == 2 && mejorVendedor == null) {
            System.out.println("Attempt failed: " + titulo + " not available for sale");
        }
        return ((step == 2 && mejorVendedor == null) || step == 4);
    }
}
