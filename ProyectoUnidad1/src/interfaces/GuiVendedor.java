/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;
import vendedor.Vendedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author mggo9
 */
public class GuiVendedor extends JFrame {
    private Vendedor myAgent;

    private JTextField titulo;
    private JTextField precio;

    public GuiVendedor(Vendedor vendedor) {
        super(vendedor.getLocalName());

        myAgent = vendedor;

        JPanel pv = new JPanel();
        pv.setLayout(new GridLayout(2, 2));
        pv.setBackground(new java.awt.Color(204, 255, 204));
        pv.add(new JLabel("Titulo del libro:"));
        titulo = new JTextField(15);
        pv.add(titulo);
        pv.add(new JLabel("Precio:"));
        precio = new JTextField(15);
        pv.add(precio);

        getContentPane().add(pv, BorderLayout.CENTER);

        JButton addButton = new JButton("Agregar");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = titulo.getText().trim();
                    String price = precio.getText().trim();

                    myAgent.updateCatalogue(title, Integer.parseInt(price));

                    titulo.setText("");
                    precio.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(GuiVendedor.this, "Invalid values", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        pv = new JPanel();
        
        pv.setBackground(new java.awt.Color(204, 255, 204));
        pv.add(addButton);
        getContentPane().add(pv, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        });

        setResizable(false);
    }

    public void mostrar() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) screenSize.getWidth() / 2;
        int centerY = (int) screenSize.getHeight() / 2;

        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}
