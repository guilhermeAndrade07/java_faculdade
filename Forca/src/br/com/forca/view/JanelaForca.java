package br.com.forca.view;

import br.com.forca.model.Palavra;
import br.com.forca.util.BancoPalavras;

import javax.swing.*;
import java.awt.*;

public class JanelaForca extends JFrame {
    private Palavra palavra;
    private JLabel labelPalavra;
    private JLabel labelErros;

    public JanelaForca() {
        super("Jogo da Forca");
        BancoPalavras banco = new BancoPalavras();
        palavra = new Palavra(banco.sortear());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        labelPalavra = new JLabel(palavra.revelar(), SwingConstants.CENTER);
        labelPalavra.setFont(new Font("Arial", Font.BOLD, 32));
        add(labelPalavra, BorderLayout.CENTER);

        labelErros = new JLabel("Erros: 0/6", SwingConstants.CENTER);
        add(labelErros, BorderLayout.SOUTH);

        JPanel teclado = new JPanel(new GridLayout(3, 9, 3, 3));
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton botao = new JButton(String.valueOf(c));
            botao.setMargin(new Insets(2, 2, 2, 2));
            botao.addActionListener(e -> {
                palavra.tentarLetra(botao.getText().charAt(0));
                botao.setEnabled(false);
                atualizarTela();
            });
            teclado.add(botao);
        }
        add(teclado, BorderLayout.NORTH);

        setVisible(true);
    }

    private void atualizarTela() {
        labelPalavra.setText(palavra.revelar());
        labelErros.setText("Erros: " + palavra.contarErros() + "/6");

        switch (palavra.getEstado()) {
            case VENCEU -> JOptionPane.showMessageDialog(this, "Você venceu!");
            case PERDEU -> JOptionPane.showMessageDialog(this,
                    "Você perdeu! A palavra era: " + palavra.getPalavraSecreta());
            default -> {}
        }
    }
}
