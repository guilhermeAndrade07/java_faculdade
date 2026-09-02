package br.com.faculdade;

public class Operario extends  Thread{
    private int primos;
    private Despachante despachante;
    public Operario(Despachante despachante) {
        this.despachante = despachante;
    }
    @Override
    public void run() {
        while (true) {
            int numero = despachante.getNumero();
            if (numero == -1)
                break;
            if (ehPrimo(numero)) primos++;
        }
    }
    public int primos() {
        return primos;
    }
    private static boolean ehPrimo(int n) {
        if (n == 1) return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
