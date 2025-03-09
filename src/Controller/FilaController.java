/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.FilaJogo;
import View.FilaView;
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
                processarResultado(vencedor);
            }
        }
    }

    private void processarResultado(String vencedor) {
        if (vencedor.equals("X")) {
            placarX++;
        } else if (vencedor.equals("O")) {
            placarO++;
        }

        if (!vencedor.equals("Empate")) {
            partidasJogadas++;
        }

        if (partidasJogadas >= LIMITE_PARTIDAS) {
            determinarCampeao();
        } else {
            view.exibirAlertaVencedor("JOGO ENCERRADO! " + (vencedor.equals("Empate") ? "Empate!" : "Vencedor: " + vencedor));
            novoJogo();
        }
    }

    private void determinarCampeao() {
        String mensagem;

        if (placarX > placarO) {
            mensagem = "FIM DA RODADA! O jogador X venceu!\nPlacar Final:\nVitórias do Jogador X: " + placarX + " \nVitórias do Jogador O: " + placarO;
        } else if (placarO > placarX) {
            mensagem = "FIM DA RODADA! O jogador O venceu!\nPlacar Final:\nVitórias do Jogador X: " + placarX + " \nVitórias do Jogador O: " + placarO;
        } else {
            mensagem = "Empate! Uma partida extra será jogada.";
            LIMITE_PARTIDAS++;
            view.exibirAlertaVencedor(mensagem);
            return;
        }

        view.exibirAlertaVencedor(mensagem);
        resetPlacar();
    }

    private void resetPlacar() {
        placarX = 0;
        placarO = 0;
        partidasJogadas = 0;
        novoJogo();
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
        view.atualizarTabuleiro(model.getEstadoTabuleiro());
        view.atualizarPlacar(placarX, placarO);
    }
}