/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import comprador.Comprador;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mggo9
 */
public class GuiComprador extends JFrame {
    Comprador comp;
    private JTextField libroParaComprar;

    public GuiComprador(Comprador comprador) {
        super(comprador.getLocalName());

        comp = comprador;

        JPanel panel = new javax.swing.JPanel();
        
        panel.setLayout(new GridLayout(2, 5));
        panel.setBackground(new java.awt.Color(255, 204, 204));
        panel.add(new JLabel("Nombre del comprador: "));
        panel.add(new JLabel(comp.getAID().getName()));

        panel.add(new JLabel("Libro que desea comprar: "));
        libroParaComprar = new JTextField();
        panel.add(libroParaComprar);


        getContentPane().add(panel, BorderLayout.CENTER);

        JButton comprar = new JButton("Comprar");
        panel.add(comprar);
        comprar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String libroC = libroParaComprar.getText().trim();

                    Object[] objects = new Object[1];
                    objects[0] = libroC;
                    comp.setLibroC(objects);
                    comp.isReady();

                    libroParaComprar.setText("");
                }catch(Exception e) {
                    JOptionPane.showMessageDialog(GuiComprador.this, "Invalid values","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel = new JPanel();
        
        panel.setBackground(new java.awt.Color(255, 205, 205));
        panel.add(comprar);
        getContentPane().add(panel, BorderLayout.SOUTH);

        System.out.println(comp.getTitulo() + "...");

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                comp.doDelete();
            }
        });
    }

    public void mostrar() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;

        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}
