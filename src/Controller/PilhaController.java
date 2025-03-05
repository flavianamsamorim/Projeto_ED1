/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.PilhaJogo;
import View.PilhaView;
/**
 *
 * @author Cliente
 */
public class PilhaController {
    private PilhaJogo pilha;
    private PilhaView view;

    public PilhaController(PilhaView view) {
        pilha = new PilhaJogo();
        this.view = view;
    }

    public void empilharElemento() {
        String pedra = "Pedra " + (pilha.getContadorEmpilhadas() + 1); // Cria a pedra com um número sequencial
        pilha.empilhar(pedra);
        view.animarEmpilhar(pedra);
        view.atualizarTopo(pilha.verTopo());
        view.atualizarContadores(pilha.getContadorEmpilhadas(), pilha.getContadorDesempilhadas(), pilha.getPontos());

        // Inicia o tempo se for o primeiro empilhamento
        if (pilha.getContadorEmpilhadas() == 1) {
            pilha.iniciarTempo();
        }

        view.atualizarTempo(pilha.getTempoDecorrido());
    }

    public void desempilharElemento() {
        String topo = pilha.desempilhar();
        view.animarDesempilhar();
        view.atualizarTopo(topo);
        view.atualizarContadores(pilha.getContadorEmpilhadas(), pilha.getContadorDesempilhadas(), pilha.getPontos());
        view.atualizarTempo(pilha.getTempoDecorrido());
    }

    public void congelarTempo() {
        pilha.congelarTempo();
        view.exibirCongelarTempo();
    }

    public void reiniciarJogo() {
        pilha.resetar();
        view.atualizarTopo(pilha.verTopo());
        view.atualizarContadores(pilha.getContadorEmpilhadas(), pilha.getContadorDesempilhadas(), pilha.getPontos());
        view.atualizarTempo(0);
    }
}