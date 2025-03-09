/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.FilaJogo;
import View.FilaView;
import javafx.application.Platform;

/**
 *
 * @author Cliente
 */
public class FilaController {
    private FilaJogo model;
    private FilaView view;
    private int placarX = 0;
    private int placarO = 0;
    private int partidasJogadas = 0;
    private int LIMITE_PARTIDAS = 5;

    public FilaController(FilaView view) {
        this.model = new FilaJogo();
        this.view = view;
    }

    public void jogar(int posicao) {
        if (model.jogar(posicao)) {
            atualizarView();
            String vencedor = model.verificarVencedor();

            if (vencedor != null) {
                // Atualiza o placar corretamente, sem duplicação
                if (vencedor.equals("X")) {
                    placarX++;
                } else if (vencedor.equals("O")) {
                    placarO++;
                }

                // Contabiliza a partida APENAS se houver um vencedor (empates não contam)
                if (!vencedor.equals("Empate")) {
                    partidasJogadas++;
                }

                // Verifica se já atingiu o limite de partidas válidas
                if (partidasJogadas >= LIMITE_PARTIDAS) {
                    if (placarX > placarO) {
                        view.exibirAlertaVencedor("Fim do jogo! O jogador X venceu com placar:\n"
                                + "Jogador X: " + placarX + "\n"
                                + "Jogador O: " + placarO);
                    } else if (placarO > placarX) {
                        view.exibirAlertaVencedor("Fim do jogo! O jogador O venceu com placar:\n"
                                + "Jogador X: " + placarX + "\n"
                                + "Jogador O: " + placarO);
                    } else {
                        // Se houver empate no placar final, joga uma partida extra
                        view.exibirAlertaVencedor("Empate! Uma partida extra será jogada.");
                        LIMITE_PARTIDAS++; // Aumenta o limite para permitir o desempate
                        return; // Sai do método para continuar o jogo
                    }

                    // Reinicia o placar para nova rodada
                    placarX = 0;
                    placarO = 0;
                    partidasJogadas = 0;
                }

                novoJogo();
            }
        }
    }

    public void novoJogo() {
        model.reset();
        atualizarView();
    }

    public void desfazerJogada() {
        model.desfazerJogada();
        atualizarView();
    }

    private void atualizarView() {
        String[] estado = new String[9];
        for (int i = 0; i < 9; i++) {
            estado[i] = model.getSimbolo(i);
        }
        view.atualizarTabuleiro(estado);
        view.atualizarPlacar(placarX, placarO);
    }
}
