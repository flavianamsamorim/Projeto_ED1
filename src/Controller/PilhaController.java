/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PilhaJogo;
import View.PilhaView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
/**
 *
 * @author Cliente
 */
public class PilhaController {
    private PilhaJogo pilha;
    private PilhaView view;
    private Timeline timer;
    private int tempoRestante;
    private long tempoInicio; // Armazena o tempo inicial
    private int tempoGasto;   // Armazena o tempo decorrido corretamente

    public PilhaController(PilhaView view) {
        this.pilha = new PilhaJogo();
        this.view = view;
        this.tempoRestante = 30;
    }

    public void empilharElemento() {
        if (pilha.getContadorEmpilhadas() < 5) {
            String pedra = "Pedra " + (pilha.getContadorEmpilhadas() + 1);
            pilha.empilhar(pedra);
            view.animarEmpilhar(pedra);
            atualizarInterface();

            if (pilha.getContadorEmpilhadas() == 1) {
                iniciarContagemRegressiva();
            }
        }
    }

    public void desempilharElemento() {
        if (!pilha.estaVazia()) {
            String topo = pilha.desempilhar();
            view.animarDesempilhar();
            atualizarInterface();

            // Se a pilha ficar vazia após desempilhar, parar o timer e corrigir tempo
            if (pilha.estaVazia() && timer != null) {
                timer.stop();

                // Recalcular tempo gasto e ajustar tempo restante para garantir soma correta
                tempoGasto = (int) ((System.currentTimeMillis() - tempoInicio) / 1000);
                tempoRestante = Math.max(30 - tempoGasto, 0); // Garante que nunca fique negativo
                
                Platform.runLater(() -> view.atualizarTempo(tempoRestante));
            }
        }
    }

    private void iniciarContagemRegressiva() {
        tempoInicio = System.currentTimeMillis(); // Captura o tempo inicial
        tempoRestante = 30;
        tempoGasto = 0;
        view.atualizarTempo(tempoRestante);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            long tempoAtual = System.currentTimeMillis();
            tempoGasto = (int) ((tempoAtual - tempoInicio) / 1000); // Calcula tempo gasto
            tempoRestante = Math.max(30 - tempoGasto, 0); // Ajusta tempo restante para garantir soma = 30

            Platform.runLater(() -> view.atualizarTempo(tempoRestante));

            if (tempoRestante <= 0) {
                timer.stop();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void atualizarInterface() {
        Platform.runLater(() -> {
            view.atualizarTopo(pilha.verTopo());
            view.atualizarContadores(pilha.getContadorEmpilhadas(), pilha.getContadorDesempilhadas(), pilha.getPontos());
        });
    }
}