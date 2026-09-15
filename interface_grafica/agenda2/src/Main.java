import javax.swing.*;
import java.awt.event.*;

public class Main {
    private static EasyJFrame cadastro; 
    private static JLabel labelId, labelNome, labelSexo, labelTelefone;
    private static JTextField textFieldId, textFieldNome, textFieldTelefone;
    private static JRadioButton radioButtonPessoa, radioButtonEmpresa;
    private static JComboBox comboBoxSexo;
    private static JCheckBox checkBoxAmigo, checkBoxCliente; 
    private static JButton buttonInserir, buttonCancelar;
    
    public static void main( String[] args) {
        cadastro = new EasyJFrame("Agenda - Cadastro", 400, 400);
        criarElementosGUI();
        posicionaElementosGUI();
        configuraEventosGUI();
        cadastro.setVisible(true);
    }

    private static void criarElementosGUI() {
        labelId = cadastro.criarLabel("ID     ");
        labelNome = cadastro.criarLabel("Nome    ");
        labelSexo = cadastro.criarLabel("Sexo");
        labelTelefone = cadastro.criarLabel("Telefone");

        textFieldId = cadastro.criarTextField(5);
        textFieldNome = cadastro.criarTextField(25);
        textFieldTelefone = cadastro.criarTextField(15);

        radioButtonPessoa = cadastro.criarRadioButton("Pessoa", true);
        radioButtonEmpresa = cadastro.criarRadioButton("Empresa", false);
        ButtonGroup groupPessoaEmpresa = new ButtonGroup();
        groupPessoaEmpresa.add(radioButtonPessoa);
        groupPessoaEmpresa.add(radioButtonEmpresa);

        comboBoxSexo = cadastro.criarJComboBox("Masculino", "Feminino");

        checkBoxAmigo = cadastro.criarJCheckBox("Amigo");
        checkBoxCliente = cadastro.criarJCheckBox("Cliente  ");

        buttonInserir = cadastro.criarJButton("Inserir");
        buttonCancelar = cadastro.criarJButton("Cancelar");
    }

    private static void posicionaElementosGUI() {
        cadastro.setSpacing(10, 10);
        cadastro.addRow(labelId, textFieldId, radioButtonPessoa, radioButtonEmpresa);
        cadastro.addRow(labelNome, textFieldNome);
        cadastro.addRow(labelSexo, comboBoxSexo, labelTelefone, textFieldTelefone);
        cadastro.addRow(checkBoxAmigo);
        cadastro.addRow(checkBoxCliente);
        cadastro.addRow(buttonInserir, buttonCancelar);
    }
    private static void configuraEventosGUI(){
        buttonInserir.addActionListener(Main::buttonInserir_click);
        buttonCancelar.addActionListener(Main::buttonCancelar_click);
    }
    private static void buttonInserir_click(ActionEvent e) {
        String msg = "ID: " + textFieldId.getText() + "\n" +
                "Tipo: " + (radioButtonPessoa.isSelected() ? "Pessoa" : "Empresa") + "\n" +
                "Nome: " + textFieldNome.getText() + "\n" +
                "Sexo: " + comboBoxSexo.getSelectedItem() + "\n" +
                "Telefone: " + textFieldTelefone.getText() + "\n" +
                "Amigo: " + (checkBoxAmigo.isSelected() ? "Sim" : "Não") + "\n" +
                "Cliente: " + (checkBoxCliente.isSelected() ? "Sim" : "Não");
        JOptionPane.showMessageDialog(null, msg, "Agenda", JOptionPane.INFORMATION_MESSAGE);
    }
    private static void buttonCancelar_click(ActionEvent e) {
        cadastro.dispose();
    }
}
