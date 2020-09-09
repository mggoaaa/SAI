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
public class Comportamiento2 extends Behaviour {
    int numero=0;
    public void action(){
        
        System.out.printf("%d\n", numero);
        numero++;
    }
            
    public boolean done(){
        if(numero<=100)
            return false;
        else
            return true;
    }

}
