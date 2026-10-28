import javax.swing.*;

public class Widgets {
    public JLabel criarJLabel(String mensagem) {
        JLabel label = new JLabel(mensagem);
        label.setSize(label.getPreferredSize());
        return label;
    }
    public JTextField criarJTextField(int tamanho) {
        JTextField textField = new JTextField(tamanho);
        textField.setSize(textField.getPreferredSize());
        return textField;
    }
    public JRadioButton criarJRadioButton(String mensagem, boolean selecionado) {
        JRadioButton radioButton = new JRadioButton(mensagem);
        radioButton.setSize(radioButton.getPreferredSize());
        radioButton.setSelected(selecionado);
        return radioButton;
    }
    public JComboBox criarJComboBox(String... valores) {
        JComboBox comboBox = new JComboBox();
        for (String valor : valores) {
            comboBox.addItem(valor);
        }
        comboBox.setSize(comboBox.getPreferredSize());
        return comboBox;
    }
    public JCheckBox criarJCheckBox(String mensagem) {
        JCheckBox checkBox = new JCheckBox(mensagem);
        checkBox.setSize(checkBox.getPreferredSize());
        return checkBox;
    }
    public JButton criarJButton(String mensagem) {
        JButton button = new JButton(mensagem);
        button.setSize(button.getPreferredSize());
        return button;
    }
}
