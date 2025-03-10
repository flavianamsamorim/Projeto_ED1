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
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Cliente
 */
public class ListaController {
    private ListaView view;
    private Tabuleiro tabuleiro;
    private int primeiroClique = -1;
    private int segundoClique = -1;
    private boolean aguardandoComparacao = false;
    private int pontuacao = 0;

    private final String[] imagens = {
        "ctrl0.jpeg", "ctrl1.jpeg", "ctrl2.jpeg", "ctrl3.jpeg",
        "ctrl4.jpeg", "ctrl5.jpeg", "ctrl6.jpeg", "ctrl7.jpeg"
    };

    public ListaController(ListaView view) {
        this.view = view;
    }

    public void iniciarJogo(int linhas, int colunas, Stage stage) {
        tabuleiro = new Tabuleiro(imagens, linhas, colunas);
        view.exibirTabuleiro(gerarArrayDeCartas(), linhas, colunas, stage);
        pontuacao = 0;
    }

    public void virarCarta(int index) {
        if (aguardandoComparacao) return;

        Carta carta = tabuleiro.getCarta(index);
        if (carta.isVirada()) return;

        carta.virar();
        view.atualizarBotao(index, carta.getImagem(), carta.isVirada());

        if (primeiroClique == -1) {
            primeiroClique = index;
        } else {
            segundoClique = index;
            aguardandoComparacao = true;
            verificarPar();
        }
    }

    private void verificarPar() {
        Carta primeiraCarta = tabuleiro.getCarta(primeiroClique);
        Carta segundaCarta = tabuleiro.getCarta(segundoClique);

        if (primeiraCarta.getImagem().equals(segundaCarta.getImagem())) {
            pontuacao++;
            resetarCliques();
            if (pontuacao == (tabuleiro.getTamanho() / 2)) {
                Platform.runLater(() -> exibirAlertaVencedor("Você encontrou todos os pares!"));
            }
        } else {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}

                Platform.runLater(() -> {
                    primeiraCarta.virar();
                    segundaCarta.virar();
                    view.atualizarBotao(primeiroClique, primeiraCarta.getImagem(), primeiraCarta.isVirada());
                    view.atualizarBotao(segundoClique, segundaCarta.getImagem(), segundaCarta.isVirada());
                    resetarCliques();
                });
            }).start();
        }
    }

    private void resetarCliques() {
        primeiroClique = -1;
        segundoClique = -1;
        aguardandoComparacao = false;
    }

    private Carta[] gerarArrayDeCartas() {
        Carta[] cartas = new Carta[tabuleiro.getTamanho()];
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = tabuleiro.getCarta(i);
        }
        return cartas;
    }

    private void exibirAlertaVencedor(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Parabéns! Sua memória está incrível!");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}