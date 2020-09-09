/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agente1;

import jade.core.behaviours.Behaviour;

/**
 *
 * @author mggo9
 */

    public class Comportamiento extends Behaviour {

    public void action(){
        System.out.printf("HOLA MUNDO");
        
    }
            
    public boolean done(){
        return true;
    }

}

