import javax.swing.*;

public class AgendaWidgets extends Widgets {
    //
    // Componentes da barra de ferramentas
    //
    private JButton btnPrimeiro, btnAnterior, btnProximo, btnUltimo;
    private JButton btnIncluir, btnExcluir, btnEditar;
    private JButton btnSalvar, btnCancelar;
    private JLabel lblPosicao;
    //
    // Componentes da área de dados
    //
    private JLabel lblId, lblNome, lblSexo, lblTelefone;
    private JTextField txtId, txtNome, txtTelefone;
    private JRadioButton rbPessoa, rbEmpresa;
    private JComboBox cmbSexo;
    private JCheckBox chkAmigo, chkCliente;
    public AgendaWidgets() {
        criarComponentesBarraFerramentas();
        criarComponentesAreaDados();
    }
    private void criarComponentesBarraFerramentas() {
        btnPrimeiro = criarJButton("|<");
        btnAnterior = criarJButton("<");
        btnProximo = criarJButton(">");
        btnUltimo = criarJButton(">|");
        btnIncluir = criarJButton("+");
        btnExcluir = criarJButton("-");
        btnEditar = criarJButton("Δ");
        btnSalvar = criarJButton("✓");
        btnCancelar = criarJButton("↶");
        lblPosicao = criarJLabel("0/0");
    }
    private void criarComponentesAreaDados() {
        lblId = criarJLabel("Id");
        lblNome = criarJLabel("Nome");
        lblSexo = criarJLabel("Sexo");
        lblTelefone = criarJLabel("Telefone");
        txtId = criarJTextField(5);
        txtNome = criarJTextField(40);
        txtTelefone = criarJTextField(15);
        rbPessoa = criarJRadioButton("Pessoa", true);
        rbEmpresa = criarJRadioButton("Empresa", false);
        ButtonGroup grpPessoaEmpresa = new ButtonGroup();
        grpPessoaEmpresa.add(rbPessoa);
        grpPessoaEmpresa.add(rbEmpresa);
        cmbSexo = criarJComboBox("Masculino", "Feminino");
        chkAmigo = criarJCheckBox("Amigo");
        chkCliente = criarJCheckBox("Cliente");
    }

    public JButton getBtnPrimeiro() {
        return btnPrimeiro;
    }

    public JButton getBtnAnterior() {
        return btnAnterior;
    }

    public JButton getBtnProximo() {
        return btnProximo;
    }

    public JButton getBtnUltimo() {
        return btnUltimo;
    }

    public JButton getBtnIncluir() {
        return btnIncluir;
    }

    public JButton getBtnExcluir() {
        return btnExcluir;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public JLabel getLblPosicao() {
        return lblPosicao;
    }

    public JLabel getLblId() {
        return lblId;
    }

    public JLabel getLblNome() {
        return lblNome;
    }

    public JLabel getLblSexo() {
        return lblSexo;
    }

    public JLabel getLblTelefone() {
        return lblTelefone;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }

    public JTextField getTxtTelefone() {
        return txtTelefone;
    }

    public JRadioButton getRbPessoa() {
        return rbPessoa;
    }

    public JRadioButton getRbEmpresa() {
        return rbEmpresa;
    }

    public JComboBox getCmbSexo() {
        return cmbSexo;
    }

    public JCheckBox getChkAmigo() {
        return chkAmigo;
    }

    public JCheckBox getChkCliente() {
        return chkCliente;
    }
}
