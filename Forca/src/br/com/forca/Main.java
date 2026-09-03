package br.com.forca;


import br.com.forca.view.JanelaForca;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JanelaForca::new);
    }
}