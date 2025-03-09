/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ListaController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Model.ListaJogo.Carta;
/**
 *
 * @author Cliente
 */
public class ListaView {
    private ListaController controller;
    private Button[][] botoes;
    private int tamanhoTabuleiro;
    private final String CAMINHO_IMAGENS = "file:src/imagens/folder/";

    public ListaView(Stage stage) {
        controller = new ListaController(this);
        stage.setTitle("Jogo da Memória");

        GridPane menu = new GridPane();
        Button btn2x2 = new Button("2x2");
        Button btn3x3 = new Button("3x3");
        Button btn4x4 = new Button("4x4");

        btn2x2.setOnAction(e -> iniciarJogo(2));
        btn3x3.setOnAction(e -> iniciarJogo(3));
        btn4x4.setOnAction(e -> iniciarJogo(4));

        menu.add(btn2x2, 0, 0);
        menu.add(btn3x3, 1, 0);
        menu.add(btn4x4, 2, 0);

        stage.setScene(new Scene(menu, 200, 100));
        stage.show();
    }

    private void iniciarJogo(int tamanho) {
        this.tamanhoTabuleiro = tamanho;
        controller.iniciarJogo(tamanho);
    }

    public void exibirTabuleiro(Carta[] cartas) {
        Stage tabuleiroStage = new Stage();
        GridPane grid = new GridPane();
        botoes = new Button[tamanhoTabuleiro][tamanhoTabuleiro];
        
        int index = 0;
        for (int i = 0; i < tamanhoTabuleiro; i++) {
            for (int j = 0; j < tamanhoTabuleiro; j++) {
                int finalIndex = index;
                botoes[i][j] = new Button();
                botoes[i][j].setMinSize(100, 100);
                botoes[i][j].setOnAction(e -> controller.virarCarta(finalIndex));

                Image img = new Image(CAMINHO_IMAGENS + "back.jpeg");
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                botoes[i][j].setGraphic(imageView);

                grid.add(botoes[i][j], j, i);
                index++;
            }
        }

        tabuleiroStage.setScene(new Scene(grid));
        tabuleiroStage.setTitle("Jogo da Memória");
        tabuleiroStage.show();
    }

    public void atualizarBotao(int index, String imagem, boolean virada) {
        int i = index / tamanhoTabuleiro;
        int j = index % tamanhoTabuleiro;
        
        String caminhoImagem = virada ? CAMINHO_IMAGENS + imagem : CAMINHO_IMAGENS + "back.jpeg";
        Image img = new Image(caminhoImagem);
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        
        botoes[i][j].setGraphic(imageView);
    }
}
