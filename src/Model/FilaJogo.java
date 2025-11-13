/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import EstruturasDeDados.Fila.Fila;

/**
 * Classe que representa um jogo da velha (tic-tac-toe) utilizando uma fila para armazenar as jogadas.
 */
public class FilaJogo {
    private String[] tabuleiro; // Armazena o estado do tabuleiro do jogo
    private Fila<Integer> filaJogadas; // Fila que armazena as posições das jogadas
    private boolean turnoX; // Indica de quem é o turno (true para "X", false para "O")
    private int jogadas; // Contador de jogadas realizadas

    // Construtor que inicializa o tabuleiro e a fila de jogadas
    public FilaJogo() {
        tabuleiro = new String[9]; // Tabuleiro com 9 posições (3x3)
        filaJogadas = new Fila<>(); // Inicializa a fila de jogadas
        reset(); // Reseta o jogo
    }

    /**
     * Realiza uma jogada na posição especificada.
     * 
     * @param posicao A posição da jogada (0 a 8)
     * @return true se a jogada for válida, false caso contrário
     * @throws Exception se ocorrer um erro na fila
     */
    public boolean jogar(int posicao) throws Exception {
        if (tabuleiro[posicao] == null) { // Verifica se a posição está vazia
            tabuleiro[posicao] = turnoX ? "X" : "O"; // Insere "X" ou "O" dependendo do turno
            turnoX = !turnoX; // Alterna o turno
            jogadas++; // Incrementa o contador de jogadas
            filaJogadas.add(posicao); // Adiciona a posição na fila de jogadas
            return true;
        }
        return false; // Retorna false se a posição já estiver ocupada
    }

    /**
     * Verifica se há um vencedor ou se o jogo terminou em empate.
     * 
     * @return "X" ou "O" se houver um vencedor, "Empate" se houver empate, null se o jogo ainda não terminou
     */
    public String verificarVencedor() {
        // Combinacoes de posições que representam linhas, colunas e diagonais vencedoras
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

        // Verifica cada linha, coluna ou diagonal para ver se há um vencedor
        for (String[] linha : combinacoes) {
            if (linha[0] != null && linha[0].equals(linha[1]) && linha[1].equals(linha[2])) {
                return linha[0]; // Retorna "X" ou "O" se houver vencedor
            }
        }

        // Retorna "Empate" se todas as posições estiverem preenchidas, caso contrário, retorna null
        return (jogadas == 9) ? "Empate" : null;
    }

    /**
     * Reseta o jogo, limpando o tabuleiro e a fila de jogadas.
     */
    public void reset() {
        for (int i = 0; i < 9; i++) {
            tabuleiro[i] = null; // Limpa todas as posições do tabuleiro
        }
        filaJogadas.clear(); // Limpa a fila de jogadas
        turnoX = true; // Define que o turno inicial é do "X"
        jogadas = 0; // Reseta o contador de jogadas
    }

    /**
     * Desfaz a última jogada realizada.
     */
    public void desfazerJogada() {
        if (!filaJogadas.isEmpty()) { // Verifica se há jogadas para desfazer
            int ultimaJogada = filaJogadas.poll(); // Remove a última jogada da fila
            tabuleiro[ultimaJogada] = null; // Limpa a posição no tabuleiro
            turnoX = !turnoX; // Alterna o turno
            jogadas--; // Decrementa o contador de jogadas
        }
    }

    /**
     * Retorna o símbolo na posição especificada do tabuleiro.
     * 
     * @param posicao A posição no tabuleiro (0 a 8)
     * @return O símbolo ("X", "O" ou "") na posição especificada
     */
    public String getSimbolo(int posicao) {
        return tabuleiro[posicao] == null ? "" : tabuleiro[posicao]; // Retorna o símbolo ou vazio se a posição estiver vazia
    }

    /**
     * Retorna o estado atual do tabuleiro.
     * 
     * @return Uma cópia do tabuleiro
     */
    public String[] getEstadoTabuleiro() {
        return tabuleiro.clone(); // Retorna uma cópia do tabuleiro para evitar modificações externas
    }
}