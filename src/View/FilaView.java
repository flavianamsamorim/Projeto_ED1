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
    private Label placarX;
    private Label placarO;
    private Button novoJogo;
    private Button desfazerJogada;
    private FilaController controller;

    public FilaView(Stage stage) {
        controller = new FilaController(this);

        // Layout principal (HBox para dividir placar e tabuleiro)
        HBox layoutPrincipal = new HBox(30);
        layoutPrincipal.setPadding(new Insets(20));
        layoutPrincipal.setAlignment(Pos.CENTER);

        // Vbox para placar e botões (lado esquerdo)
        VBox painelEsquerdo = new VBox(20);
        painelEsquerdo.setAlignment(Pos.CENTER_LEFT);

        placarX = new Label("Jogador X: 0");
        placarO = new Label("Jogador O: 0");
        placarX.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        placarO.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Criando botões e aplicando cores
        novoJogo = new Button("Novo Jogo");
        desfazerJogada = new Button("Desfazer");

        novoJogo.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 15px;");
        desfazerJogada.setStyle("-fx-font-size: 16px; -fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 10px 15px;");

        novoJogo.setOnAction(e -> controller.novoJogo());
        desfazerJogada.setOnAction(e -> controller.desfazerJogada());

        painelEsquerdo.getChildren().addAll(placarX, placarO, novoJogo, desfazerJogada);

        // GridPane para o tabuleiro (lado direito)
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-border-color: black; -fx-border-width: 3px; -fx-padding: 15px; -fx-background-color: #ddd;");

        botoes = new Button[9];

        int cont = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botoes[cont] = new Button();
                botoes[cont].setMinSize(120, 120);
                botoes[cont].setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-background-color: white;");
                int posicao = cont;
                botoes[cont].setOnAction(e -> controller.jogar(posicao));
                grid.add(botoes[cont], j, i);
                cont++;
            }
        }

        // Adiciona os elementos ao layout principal
        layoutPrincipal.getChildren().addAll(painelEsquerdo, grid);

        // Criando e aplicando a cena
        Scene scene = new Scene(layoutPrincipal, 600, 450);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("❌ Jogo da Velha ⭕");
        stage.show();
    }

    public void atualizarTabuleiro(String[] estado) {
        for (int i = 0; i < 9; i++) {
            botoes[i].setText(estado[i]);
            if ("X".equals(estado[i])) {
                botoes[i].setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: blue; -fx-background-color: white;");
            } else if ("O".equals(estado[i])) {
                botoes[i].setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: red; -fx-background-color: white;");
            }
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