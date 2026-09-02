package br.com.faculdade;

import javax.swing.*;
import java.awt.event.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main(String[] args) {
        JFrame janela = new JFrame();
        janela.setTitle("Janela Principal");
        janela.setSize(500,500);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(null);

        JLabel ola_mundo = new JLabel("Olá mundão  ");
        ola_mundo.setSize(ola_mundo.getPreferredSize());
        ola_mundo.setLocation(10,10);
        janela.add(ola_mundo);

        JLabel name = new JLabel("Qual o seu nome?");
        name.setSize(name.getPreferredSize());
        int y = 10 + ola_mundo.getSize().height + 10;
        name.setLocation(10, y);
        janela.add(name);

        JTextField inputName = new JTextField(30);
        inputName.setSize(inputName.getPreferredSize());
        int x = 10 + name.getSize().width + 10;
        inputName.setLocation(x, y);
        janela.add(inputName);

        JButton button = new JButton("ok");
        button.setSize(button.getPreferredSize());
        button.setLocation(10, y + inputName.getSize().height + 10);
        janela.add(button);

        button.addActionListener(e -> {
            String nome = inputName.getText();
            JOptionPane.showMessageDialog(null, "Olá, " + nome + "!", "Saudação", JOptionPane.INFORMATION_MESSAGE);
        });

        janela.setVisible(true);
    }

}
