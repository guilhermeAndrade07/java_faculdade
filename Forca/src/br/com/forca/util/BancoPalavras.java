package br.com.forca.util;

import java.util.List;
import java.util.Random;

public class BancoPalavras {
    private static final List<String> PALAVRAS = List.of(
            "JAVA", "COMPUTADOR", "TECLADO", "PROGRAMACAO",
            "INTERFACE", "VARIAVEL", "ALGORITMO", "FACULDADE"
    );

    private final Random random = new Random();

    public String sortear() {
        int indice = random.nextInt(PALAVRAS.size());
        return PALAVRAS.get(indice);
    }
}