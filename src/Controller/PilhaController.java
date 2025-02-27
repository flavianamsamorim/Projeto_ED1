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
        this.pilha = new PilhaJogo();
        this.view = view;
    }

    public void empilharElemento(String elemento) {
        if (!elemento.isEmpty()) {
            pilha.empilhar(elemento);
            view.atualizarTopo(pilha.verTopo());
        }
    }

    public void desempilharElemento() {
        pilha.desempilhar();
        view.atualizarTopo(pilha.verTopo());
    }
}
