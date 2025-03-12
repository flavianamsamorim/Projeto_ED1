/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import EstruturasDeDados.Fila.Fila;
/**
 *
 * @author Cliente
 */
public class FilaJogo {
    private String[] tabuleiro;
    private Fila<Integer> filaJogadas;
    private boolean turnoX;
    private int jogadas;

    public FilaJogo() {
        tabuleiro = new String[9];
        filaJogadas = new Fila<>();
        reset();
    }

    public boolean jogar(int posicao) throws Exception {
        if (tabuleiro[posicao] == null) {
            tabuleiro[posicao] = turnoX ? "X" : "O";
            turnoX = !turnoX;
            jogadas++;
            filaJogadas.add(posicao);
            return true;
        }
        return false;
    }

    public String verificarVencedor() {
        String[][] combinacoes = {
            {tabuleiro[0], tabuleiro[1], tabuleiro[2]},
            {tabuleiro[3], tabuleiro[4], tabuleiro[5]},
            {tabuleiro[6], tabuleiro[7], tabuleiro[8]},
            {tabuleiro[0], tabuleiro[3], tabuleiro[6]},
            {tabuleiro[1], tabuleiro[4], tabuleiro[7]},
            {tabuleiro[2], tabuleiro[5], tabuleiro[8]},
            {tabuleiro[0], tabuleiro[4], tabuleiro[8]},
            {tabuleiro[2], tabuleiro[4], tabuleiro[6]}
        };

        for (String[] linha : combinacoes) {
            if (linha[0] != null && linha[0].equals(linha[1]) && linha[1].equals(linha[2])) {
                return linha[0]; // Retorna "X" ou "O"
            }
        }

        return (jogadas == 9) ? "Empate" : null;
    }

    public void reset() {
        for (int i = 0; i < 9; i++) {
            tabuleiro[i] = null;
        }
        filaJogadas.clear();
        turnoX = true;
        jogadas = 0;
    }

    public void desfazerJogada() {
        if (!filaJogadas.isEmpty()) {
            int ultimaJogada = filaJogadas.poll();
            tabuleiro[ultimaJogada] = null;
            turnoX = !turnoX;
            jogadas--;
        }
    }

    public String getSimbolo(int posicao) {
        return tabuleiro[posicao] == null ? "" : tabuleiro[posicao];
    }

    //Método adicionado para retornar o estado do tabuleiro
    public String[] getEstadoTabuleiro() {
        return tabuleiro.clone(); // Retorna uma cópia para evitar modificações externas
    }
}