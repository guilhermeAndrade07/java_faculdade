import javax.swing.*;

public class EasyJFrame extends JFrame {
    private int x, y;
    private int horizontal, vertical;
    public EasyJFrame(String title, int width, int height) {
        setTitle((title));
        setSize(width, height);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public JLabel criarLabel(String mensagem) {
        JLabel label = new JLabel(mensagem);
        label.setSize(label.getPreferredSize());
        return label;
    }
    public JTextField criarTextField(int tamanho) {
        JTextField textField = new JTextField(tamanho);
        textField.setSize(textField.getPreferredSize());
        return textField;
    }
    public JRadioButton criarRadioButton(String mensagem, boolean selecionado) {
        JRadioButton radioButton = new JRadioButton(mensagem);
        radioButton.setSize(radioButton.getPreferredSize());
        radioButton.setSelected(selecionado);
        return radioButton;
    }
    public JComboBox criarJComboBox(String... valores){
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
    public void setSpacing(int horizontal, int vertical) {
        this.x = this.horizontal = horizontal;
        this.y = this.vertical = vertical;
    }
    public void addRow(JComponent... components) {
        int maisAlto = 0;
        for (JComponent component : components) {
            if (component.getSize().height > maisAlto) {
                maisAlto = component.getSize().height;
            }
        }
        for (JComponent component : components) {
            int h = component.getSize().height;
            component.setLocation(x, y + (maisAlto - h) / 2);
            add(component);
            x += component.getSize().width + horizontal;
        }
        y += maisAlto + vertical;
        x = horizontal;
    }
}
