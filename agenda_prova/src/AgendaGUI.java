import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.*;
import java.util.ArrayList;

public class AgendaGUI extends JFrame {
    private AgendaWidgets widgets = new AgendaWidgets();
    private ArrayList<Contato> contatos = new ArrayList<>();
    private int pos = -1; // pos é o contato atual, o índice na lista de contatos
    private String estado = "query"; // insert, update, query
    public AgendaGUI() {
        setTitle("Agenda");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        configurarLayout();
        configurarEvents();
        setVisible(true);
        atualizaGUI();
    }
    private void configurarLayout() {
        //
        // Cria barra de ferramentas e adiciona componentes nela
        //
        JPanel barraFerramentas = new JPanel();
        barraFerramentas.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        barraFerramentas.add(widgets.getBtnPrimeiro());
        barraFerramentas.add(widgets.getBtnAnterior());
        barraFerramentas.add(widgets.getLblPosicao());
        barraFerramentas.add(widgets.getBtnProximo());
        barraFerramentas.add(widgets.getBtnUltimo());
        barraFerramentas.add(widgets.getBtnIncluir());
        barraFerramentas.add(widgets.getBtnExcluir());
        barraFerramentas.add(widgets.getBtnEditar());
        barraFerramentas.add(widgets.getBtnSalvar());
        barraFerramentas.add(widgets.getBtnCancelar());
        //
        // Cria área de dados e adiciona componentes nela
        //
        RowPanel dados = new RowPanel();
        dados.setSpacing(10, 10);
        dados.addRow(widgets.getLblId(), widgets.getTxtId(), widgets.getRbPessoa(), widgets.getRbEmpresa());
        dados.addRow(widgets.getLblNome(), widgets.getTxtNome());
        dados.addRow(widgets.getLblSexo(), widgets.getCmbSexo(), widgets.getLblTelefone(), widgets.getTxtTelefone());
        dados.addRow(widgets.getChkAmigo());
        dados.addRow(widgets.getChkCliente());
        //
        // Configura o layout da janela como BorderLayout e:
        // - adiciona a barra de ferramentas na região norte
        // - adiciona a área de dados na região central
        //
        setLayout(new BorderLayout(5, 5));
        add(barraFerramentas, BorderLayout.NORTH);
        add(dados, BorderLayout.CENTER);
    }
    private void configurarEvents() {
        widgets.getBtnPrimeiro().addActionListener(this::btnPrimeiro_click);
        widgets.getBtnAnterior().addActionListener(this::btnAnterior_click);
        widgets.getBtnProximo().addActionListener(this::btnProximo_click);
        widgets.getBtnUltimo().addActionListener(this::btnUltimo_click);
        widgets.getBtnIncluir().addActionListener(this::btnIncluir_click);
        widgets.getBtnEditar().addActionListener(this::btnEditar_click);
        widgets.getBtnExcluir().addActionListener(this::btnExcluir_click);
        widgets.getBtnSalvar().addActionListener(this::btnSalvar_click);
        widgets.getBtnCancelar().addActionListener(this::btnCancelar_click);
        widgets.getRbPessoa().addItemListener(this::rbPessoa_click);
        widgets.getRbEmpresa().addItemListener(this::rbEmpresa_click);
    }
    private void btnPrimeiro_click(ActionEvent e) {
        pos = 0;
        atualizaGUI();
    }
    private void btnAnterior_click(ActionEvent e) {
        pos--;
        atualizaGUI();
    }
    private void btnProximo_click(ActionEvent e) {
        pos++;
        atualizaGUI();
    }
    private void btnUltimo_click(ActionEvent e) {
        pos = contatos.size() - 1;
        atualizaGUI();
    }
    private void btnIncluir_click(ActionEvent e) {
        estado = "insert";
        Contato contato = new Contato();
        contatos.add(contato);
        pos = contatos.size() - 1;
        atualizaGUI();
    }
    private void btnEditar_click(ActionEvent e) {
        estado = "update";
        atualizaGUI();
    }
    private void btnExcluir_click(ActionEvent e) {
        int resposta = JOptionPane.showConfirmDialog(null, "Excluir contato?", "Exclusão",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            contatos.remove(pos);
            if (pos > contatos.size() - 1) pos--;
        }
        atualizaGUI();
    }
    private void btnSalvar_click(ActionEvent e) {
        Contato contato;
        if (estado.equals("insert")) {
            contato = contatos.getLast();
        } else {
            contato = contatos.get(pos);
        }
        if (widgets.getRbPessoa().isSelected()) {
            contato.setTipo('P');
            if (widgets.getCmbSexo().getSelectedIndex() == 0) {
                contato.setSexo('M');
            } else {
                contato.setSexo('F');
            }
            contato.setAmigo(widgets.getChkAmigo().isSelected());
        } else {
            contato.setTipo('E');
            contato.setSexo(' ');
            contato.setAmigo(false);
        }
        contato.setNome(widgets.getTxtNome().getText());
        contato.setTelefone(widgets.getTxtTelefone().getText());
        contato.setCliente(widgets.getChkCliente().isSelected());
        estado = "query";
        atualizaGUI();
    }
    private void btnCancelar_click(ActionEvent e) {
        if (estado.equals("insert")) {
            contatos.remove(contatos.size() - 1);
            pos = contatos.size() - 1;
        }
        estado = "query";
        atualizaGUI();
    }
    private void rbPessoa_click(ItemEvent e) {
        if (estado.equals("insert") || estado.equals("update")) {
            widgets.getCmbSexo().setEnabled(true);
            widgets.getChkAmigo().setEnabled(true);
        }
    }
    private void rbEmpresa_click(ItemEvent e) {
        if (estado.equals("insert") || estado.equals("update")) {
            widgets.getCmbSexo().setEnabled(false);
            widgets.getChkAmigo().setEnabled(false);
        }
    }
    private void atualizaGUI() {
        int registros = contatos.size();
        widgets.getBtnPrimeiro().setEnabled(pos > 0 && estado.equals("query"));
        widgets.getBtnAnterior().setEnabled(pos > 0 && estado.equals("query"));
        widgets.getBtnProximo().setEnabled(pos < registros - 1 && estado.equals("query"));
        widgets.getBtnUltimo().setEnabled(pos < registros - 1 && estado.equals("query"));
        widgets.getLblPosicao().setText(pos + 1 + "/" + registros);
        widgets.getBtnIncluir().setEnabled(estado.equals("query"));
        widgets.getBtnEditar().setEnabled(estado.equals("query") && registros > 0);
        widgets.getBtnExcluir().setEnabled(estado.equals("query") && registros > 0);
        widgets.getBtnSalvar().setEnabled(estado.equals("insert") || estado.equals("update"));
        widgets.getBtnCancelar().setEnabled(estado.equals("insert") || estado.equals("update"));
        widgets.getTxtId().setEditable(false);
        boolean editable = estado.equals("insert") || estado.equals("update");
        widgets.getRbPessoa().setEnabled(editable);
        widgets.getRbEmpresa().setEnabled(editable);
        widgets.getTxtNome().setEditable(editable);
        widgets.getCmbSexo().setEnabled(editable);
        widgets.getTxtTelefone().setEditable(editable);
        widgets.getChkAmigo().setEnabled(editable);
        widgets.getChkCliente().setEnabled(editable);
        boolean isPessoa = widgets.getRbPessoa().isSelected() && editable;
        widgets.getCmbSexo().setEnabled(isPessoa);
        widgets.getChkAmigo().setEnabled(isPessoa);
        if (pos >= 0) {
            Contato contato = contatos.get(pos);
            widgets.getTxtId().setText(String.valueOf(contato.getId()));
            if (contato.getTipo() == 'P') {
                widgets.getRbPessoa().setSelected(true);
            } else {
                widgets.getRbEmpresa().setSelected(true);
            }
            widgets.getTxtNome().setText(contato.getNome());
            if (contato.getSexo() == 'M' || contato.getSexo() == ' ') {
                widgets.getCmbSexo().setSelectedIndex(0);
            } else {
                widgets.getCmbSexo().setSelectedIndex(1);
            }
            widgets.getTxtTelefone().setText(contato.getTelefone());
            widgets.getChkAmigo().setSelected(contato.isAmigo());
            widgets.getChkCliente().setSelected(contato.isCliente());
        } else {
            widgets.getTxtId().setText("");
            widgets.getRbPessoa().setSelected(true);
            widgets.getTxtNome().setText("");
            widgets.getCmbSexo().setSelectedIndex(0);
            widgets.getTxtTelefone().setText("");
            widgets.getChkAmigo().setSelected(false);
            widgets.getChkCliente().setSelected(false);
        }
    }
}
