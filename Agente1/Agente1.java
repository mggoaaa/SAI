/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agente1;
import jade.core.Agent;
/**
 *
 * @author mggo9
 */
public class Agente1 extends Agent{
    //Comportamiento c = new Comportamiento();
    Comportamiento2 c2 = new Comportamiento2();
    protected void setup(){
        //this.addBehaviour(c);
        this.addBehaviour(c2);
    }
}

    
