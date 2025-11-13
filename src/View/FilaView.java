/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.FilaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author Cliente
 */
public class FilaView {
    private Button[] botoes;
    private Label tituloPlacar;
    private Label placarX;
    private Label placarO;
    private Button novoJogo;
    private Button desfazerJogada;
    private FilaController controller;

    public FilaView(Stage stage) {
        controller = new FilaController(this);

        HBox layoutPrincipal = new HBox(30);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setAlignment(Pos.CENTER);

        VBox painelEsquerdo = new VBox(20);
        painelEsquerdo.setAlignment(Pos.CENTER_LEFT);

        tituloPlacar = new Label("Placar \nMelhor de 5");
        tituloPlacar.getStyleClass().add("titulo-placar");

        placarX = new Label("Jogador X: 0");
        placarO = new Label("Jogador O: 0");
        placarX.getStyleClass().add("placar-label");
        placarO.getStyleClass().add("placar-label");

        novoJogo = criarBotao("Novo Jogo", "novo-jogo");
        desfazerJogada = criarBotao("Desfazer", "desfazer-jogada");

        novoJogo.setOnAction(e -> controller.novoJogo());
        desfazerJogada.setOnAction(e -> controller.desfazerJogada());

        painelEsquerdo.getChildren().addAll(tituloPlacar, placarX, placarO, novoJogo, desfazerJogada);

        GridPane grid = criarTabuleiro();

        layoutPrincipal.getChildren().addAll(painelEsquerdo, grid);

        Scene scene = new Scene(layoutPrincipal, 600, 450);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("❌ Jogo da Velha ⭕");
        stage.show();
    }

    private Button criarBotao(String texto, String estiloClasse) {
        Button botao = new Button(texto);
        botao.getStyleClass().add(estiloClasse);
        return botao;
    }

    private GridPane criarTabuleiro() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(0);
        grid.setVgap(0);
        grid.getStyleClass().add("grid-pane");

        botoes = new Button[9];
        for (int i = 0; i < 9; i++) {
            botoes[i] = criarBotaoTabuleiro(i);
            grid.add(botoes[i], i % 3, i / 3);
        }

        return grid;
    }

    private Button criarBotaoTabuleiro(int posicao) {
        Button botao = new Button();
        botao.setMinSize(120, 120);
        botao.getStyleClass().add("tabuleiro-button");
        botao.setOnAction(e -> controller.jogar(posicao));
        return botao;
    }

    public void atualizarTabuleiro(String[] estado) {
        for (int i = 0; i < 9; i++) {
            botoes[i].setText(estado[i]);
            estilizarBotaoTabuleiro(botoes[i], estado[i]);
        }
    }

    private void estilizarBotaoTabuleiro(Button botao, String valor) {
        if ("X".equals(valor)) {
            botao.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: blue; -fx-background-color: white;");
        } else if ("O".equals(valor)) {
            botao.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: red; -fx-background-color: white;");
        }
    }

    public void atualizarPlacar(int px, int po) {
        placarX.setText("Jogador X: " + px);
        placarO.setText("Jogador O: " + po);
    }

    public void exibirAlertaVencedor(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim de Jogo!");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}