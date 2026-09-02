package br.com.faculdade;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        Despachante despachante = new Despachante(1, 500_000, 2);
        despachante.start();
        try {
            despachante.join();
        } catch (Exception e) {

        }
        System.out.print("Total: " + despachante.total());
    }
}
