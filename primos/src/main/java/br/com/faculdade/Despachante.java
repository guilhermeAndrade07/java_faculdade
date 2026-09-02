package br.com.faculdade;

public class Despachante extends Thread{
    private int inicio, fim;
    private Operario[] operarios;
    private int total;
    private int numero;
    public Despachante(int inicio, int fim, int operarios) {
        this.inicio = inicio;
        this.fim = fim;
        this.operarios = new Operario[operarios];
        this.numero = inicio;
    }
    @Override
    public void run() {
        for (int i = 0; i < operarios.length; i++) {
            this.operarios[i] = new Operario(this);
            this.operarios[i].start();
        }
        for (int i = 0; i < operarios.length; i++) {
            try{
                this.operarios[i].join();
                total += this.operarios[i].primos();
            } catch (Exception e) {

            }
        }
    }
    public synchronized int getNumero() {
        if (numero > fim)
            return -1;
        else
            return numero++;
    }
    public int total() {
        return total;
    }
}
