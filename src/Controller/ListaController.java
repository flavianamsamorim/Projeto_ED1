/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ListaJogo.Carta;
import Model.ListaJogo.Tabuleiro;
import View.ListaView;
import javafx.application.Platform;
/**
 *
 * @author Cliente
 */
public class ListaController {
    private ListaView view;
    private Tabuleiro tabuleiro;
    private int primeiroClique = -1;
    
    private final String[] imagens = {
        "ctrl0.jpeg", "ctrl1.jpeg", "ctrl2.jpeg", "ctrl3.jpeg",
        "ctrl4.jpeg", "ctrl5.jpeg", "ctrl6.jpeg", "ctrl7.jpeg"
    };

    public ListaController(ListaView view) {
        this.view = view;
    }

    public void iniciarJogo(int tamanho) {
        tabuleiro = new Tabuleiro(imagens, tamanho);
        view.exibirTabuleiro(gerarArrayDeCartas());
    }

    public void virarCarta(int index) {
        Carta carta = tabuleiro.getCarta(index);
        
        if (carta.isVirada()) {
            return;
        }

        carta.virar();
        view.atualizarBotao(index, carta.getImagem(), carta.isVirada());

        if (primeiroClique == -1) {
            primeiroClique = index;
        } else {
            int segundoClique = index;
            Carta primeiraCarta = tabuleiro.getCarta(primeiroClique);

            if (!primeiraCarta.getImagem().equals(carta.getImagem())) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}
                    
                    Platform.runLater(() -> {
                        primeiraCarta.virar();
                        carta.virar();
                        view.atualizarBotao(primeiroClique, primeiraCarta.getImagem(), primeiraCarta.isVirada());
                        view.atualizarBotao(segundoClique, carta.getImagem(), carta.isVirada());
                    });
                }).start();
            }
            primeiroClique = -1;
        }
    }

    private Carta[] gerarArrayDeCartas() {
        Carta[] cartas = new Carta[tabuleiro.getTamanho()];
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = tabuleiro.getCarta(i);
        }
        return cartas;
    }
}
