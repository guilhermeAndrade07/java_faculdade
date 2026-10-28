public class Contato {
    private static int contador = 0;
    private int id;
    private char tipo; // 'P' = Pessoa, 'E' = Empresa
    private String nome;
    private char sexo; // 'M' = Masculino, 'F' = Feminino, ' ' = Para empresas
    private String telefone;
    private boolean amigo;
    private boolean cliente;
    public Contato() {
        this.id = ++contador;
        this.tipo = 'P';
        this.sexo = 'M';
    }
    public Contato(int id, char tipo, String nome, char sexo,
                   String telefone, boolean amigo, boolean cliente) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.sexo = sexo;
        this.telefone = telefone;
        this.amigo = amigo;
        this.cliente = cliente;
    }
    public int getId() {
        return id;
    }
    public char getTipo() {
        return tipo;
    }
    public String getNome() {
        return nome;
    }
    public char getSexo() {
        return sexo;
    }
    public String getTelefone() {
        return telefone;
    }
    public boolean isAmigo() {
        return amigo;
    }
    public boolean isCliente() {
        return cliente;
    }
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setAmigo(boolean amigo) {
        this.amigo = amigo;
    }
    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }
}
