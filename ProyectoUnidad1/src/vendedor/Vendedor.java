package vendedor;

import behaviours.OfferRequestServer;
import behaviours.PurchaseOrdersServer;
import interfaces.GuiVendedor;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.Hashtable;

public class Vendedor extends Agent {
    private Hashtable catalogo;
    private GuiVendedor guiVendedor;

    protected void setup() {
        catalogo = new Hashtable();

        guiVendedor = new GuiVendedor(this);
        guiVendedor.mostrar();

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType("book-selling");
        sd.setName("book-trading");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new OfferRequestServer(this));

        addBehaviour(new PurchaseOrdersServer(this));
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        guiVendedor.dispose();

        System.out.println("Agente Vendedor " + getAID().getName() + "terminando");
    }

    public void updateCatalogue(String titulo, int precio) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                catalogo.put(titulo, precio);
                System.out.println(titulo + " cuesta" + precio);
            }
        });
    }

    public Hashtable getCatalogo() {
        return catalogo;
    }
}
