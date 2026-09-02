package br.com.faculdade;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    static void main(String[] args) {
        // Criando a janela
        JFrame janela = new JFrame();
        janela.setTitle("AGENDA");
        janela.setSize(400,400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(null);

        // Criando o label ID
        JLabel id = new JLabel("Id");
        id.setSize(id.getPreferredSize());
        id.setLocation(10,10);
        janela.add(id);

        // Criando o input ID
        JTextField inputId = new JTextField(10);
        inputId.setSize(inputId.getPreferredSize());
        int x = 10 + id.getSize().width + 10;
        inputId.setLocation(x, 10);
        janela.add(inputId);

        // Criando o label e input Radio entidade
        JRadioButton inputPessoa = new JRadioButton("Pessoa");
        JRadioButton inputEmpresa = new JRadioButton("Empresa");
        ButtonGroup inputRadios = new ButtonGroup();
        inputRadios.add(inputPessoa);
        inputRadios.add(inputEmpresa);

        inputPessoa.setSize(inputPessoa.getPreferredSize());
        inputEmpresa.setSize(inputEmpresa.getPreferredSize());

        int xRadio1 = x + inputId.getSize().width + 20;
        inputPessoa.setLocation(xRadio1, 10);
        janela.add(inputPessoa);

        int xRadio2 = xRadio1 + inputPessoa.getSize().width + 10;
        inputEmpresa.setLocation(xRadio2, 10);
        janela.add(inputEmpresa);

        // Criando label Nome
        JLabel name = new JLabel("Nome: ");
        name.setSize(name.getPreferredSize());
        int y = 10 + id.getSize().height + 10;
        name.setLocation(10, y);
        janela.add(name);

        // Criando input nome
        JTextField inputName = new JTextField(15);
        inputName.setSize(inputName.getPreferredSize());
        int xNome = 10 + name.getSize().width + 10;
        inputName.setLocation(xNome, y);
        janela.add(inputName);

        // Criando Label Sexo
        JLabel sexo = new JLabel("Sexo: ");
        sexo.setSize(sexo.getPreferredSize());
        int ySexo = y + inputName.getSize().height + 10;
        sexo.setLocation(10, ySexo );
        janela.add(sexo);

        // Criando Input Select Sexo
        String[] sexos = {"Masculino", "Femenino", "outro"};
        JComboBox<String> inputSexo = new JComboBox<>(sexos);

        inputSexo.setSize(inputSexo.getPreferredSize());
        int xSexo = 10 + sexo.getSize().width + 10;
        inputSexo.setLocation(xSexo, ySexo);
        janela.add(inputSexo);

        // Criando Label de Telefone
        JLabel telefone = new JLabel("Telefone    ");
        telefone.setSize(telefone.getPreferredSize());
        int xTelefone = xSexo + inputSexo.getSize().width + 20;
        telefone.setLocation(xTelefone, ySexo );
        janela.add(telefone);

        // Criando Input de Telefone
        JTextField inputTelefone = new JTextField(10);
        inputTelefone.setSize(inputTelefone.getPreferredSize());
        int xInputTelefone = xTelefone + telefone.getSize().width + 10;
        inputTelefone.setLocation(xInputTelefone, ySexo);
        janela.add(inputTelefone);

        // Criando Checkbox de Amigo e Cliente
        List<JCheckBox> listaOpcoes = new ArrayList<>();
        String[] opcoes = {"Amigo", "Cliente   "};

        int yCheckbox = ySexo + sexo.getSize().height + 10;
        int xCheckbox = xSexo;

        for (String opcao : opcoes) {
            JCheckBox cb = new JCheckBox(opcao);
            cb.setSize(cb.getPreferredSize());
            cb.setLocation(xCheckbox, yCheckbox);
            janela.add(cb);
            listaOpcoes.add(cb);
            yCheckbox += cb.getSize().height + 5;
        }

        JButton button1 = new JButton("Agendar");
        button1.setSize(button1.getPreferredSize());
        button1.setLocation(10, yCheckbox + 10);
        janela.add(button1);

        JButton buttonCancelar = new JButton("Cancelar");
        buttonCancelar.setSize(buttonCancelar.getPreferredSize());
        int xCancelar = 10 + button1.getSize().width + 10; // X do "ok" + largura do "ok" + espaçamento
        buttonCancelar.setLocation(xCancelar, yCheckbox + 10); // mesmo Y do botão "ok"
        janela.add(buttonCancelar);


        button1.addActionListener(e -> {
            String id_colect = inputId.getText();
            String entidade = "";
            if (inputPessoa.isSelected()) {
                entidade = "Pessoa";
            } else if (inputEmpresa.isSelected()) {
                entidade = "Empresa";
            }
            String nome = inputName.getText();
            String sexoSelecionado = (String) inputSexo.getSelectedItem();
            String tel = inputTelefone.getText();
            List<String> opcoesMarcadas = new ArrayList<>();
            for (JCheckBox cb : listaOpcoes) {
                if (cb.isSelected()) {
                    opcoesMarcadas.add(cb.getText());
                }
            }

            String mensagem = "Olá, " + nome + "!\n" +
                    "ID: " + id_colect + "\n" +
                    "Entidade: " + entidade + "\n" +
                    "Sexo: " + sexoSelecionado + "\n" +
                    "Telefone: " + tel + "\n" +
                    "Opções: " + opcoesMarcadas;

            JOptionPane.showMessageDialog(
                    null,
                    mensagem,
                    "Informações", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonCancelar.addActionListener(e -> {
            // Limpa os campos de texto
            inputId.setText("");
            inputName.setText("");
            inputTelefone.setText("");

            // Desmarca os radio buttons
            inputRadios.clearSelection();

            // Volta o combo box para a primeira opção
            inputSexo.setSelectedIndex(0);

            // Desmarca todos os checkboxes
            for (JCheckBox cb : listaOpcoes) {
                cb.setSelected(false);
            }
        });

        janela.setVisible(true);
    }
}
