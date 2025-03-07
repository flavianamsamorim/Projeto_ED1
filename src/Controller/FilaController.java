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

    public FilaController(FilaView view) {
        this.model = new FilaJogo();
        this.view = view;
    }

    public void jogar(int posicao) {
        if (model.jogar(posicao)) {
            atualizarView();
            String vencedor = model.verificarVencedor();

            if (vencedor != null) {
                if (vencedor.equals("X")) {
                    placarX++;
                } else if (vencedor.equals("O")) {
                    placarO++;
                }
                novoJogo();
            }
        }
    }

    public void novoJogo() {
        model.reset();
        atualizarView();
    }

    public void zerarPlacar() {
        placarX = 0;
        placarO = 0;
        novoJogo();
        view.atualizarPlacar(placarX, placarO);
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