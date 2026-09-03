package br.com.forca.model;

import java.util.HashSet;
import java.util.Set;

public class Palavra {
    private final String palavraSecreta;
    private final Set<Character> letrasTentadas = new HashSet<>();
    private int errosMaximos = 6;

    public Palavra(String palavraSecreta) {
        this.palavraSecreta = palavraSecreta.toUpperCase();
    }

    public void tentarLetra(char letra) {
        letrasTentadas.add(Character.toUpperCase(letra));
    }

    public String revelar() {
        StringBuilder sb = new StringBuilder();
        for (char c : palavraSecreta.toCharArray()) {
            if (letrasTentadas.contains(c)) {
                sb.append(c);
            } else {
                sb.append('_');
            }
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    public int contarErros() {
        int erros = 0;
        for (char letra : letrasTentadas) {
            if (palavraSecreta.indexOf(letra) == -1) {
                erros++;
            }
        }
        return erros;
    }

    public boolean venceu() {
        for (char c : palavraSecreta.toCharArray()) {
            if (!letrasTentadas.contains(c)) return false;
        }
        return true;
    }

    public boolean perdeu() {
        return contarErros() >= errosMaximos;
    }

    public EstadoJogo getEstado() {
        if (venceu()) return EstadoJogo.VENCEU;
        if (perdeu()) return EstadoJogo.PERDEU;
        return EstadoJogo.JOGANDO;
    }

    public String getPalavraSecreta() {
        return palavraSecreta;
    }
}