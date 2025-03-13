/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BuscaController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Random;

/**
 *
 * @author Cliente
 */
public class BuscaView {

    private BuscaController controller;
    private Label statusLabel;
    private Label ataqueLabel;
    private ImageView[][] gridImages;
    private Image aguaImg, navioImg1, navioImg2, navioImg3, explosaoImg, backImg;
    private Button btnAleatoria, btnSequencial;

    public BuscaView(Stage stage) {
        controller = new BuscaController(this);

        // Inicializar componentes
        carregarImagens();
        inicializarInterface(stage);
    }

    private void carregarImagens() {
        backImg = new Image("file:src/imagens/folder/back.jpeg");
        aguaImg = new Image("file:src/imagens/folder/agua.jpeg");
        navioImg1 = new Image("file:src/imagens/folder/navio0.jpg");
        navioImg2 = new Image("file:src/imagens/folder/navio1.jpg");
        navioImg3 = new Image("file:src/imagens/folder/navio2.jpg");
        explosaoImg = new Image("file:src/imagens/folder/bomba.jpeg");
    }

    private void inicializarInterface(Stage stage) {
        statusLabel = new Label("Escolha um método de busca");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        ataqueLabel = new Label("");
        ataqueLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Criar botões
        btnAleatoria = criarBotao("🔀 Busca Aleatória", "aleatoria");
        btnSequencial = criarBotao("📍 Busca Sequencial", "sequencial");

        HBox buttonBox = new HBox(20, btnAleatoria, btnSequencial);
        buttonBox.setAlignment(Pos.CENTER);

        // Criar tabuleiro
        GridPane gridPane = criarTabuleiro();

        VBox vbox = new VBox(20, statusLabel, buttonBox, gridPane, ataqueLabel); // ataqueLabel agora está no final
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: #87CEEB;");

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 500, 550);
        stage.setTitle("🚢 Busca: Batalha Naval 💣");
        stage.setScene(scene);
        stage.show();
    }

    private Button criarBotao(String texto, String tipoBusca) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-font-size: 14px; -fx-background-radius: 10px;");
        botao.setOnAction(e -> controller.executarBusca(tipoBusca));
        return botao;
    }

    private GridPane criarTabuleiro() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setAlignment(Pos.CENTER);

        gridImages = new ImageView[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView imgView = new ImageView(backImg);
                imgView.setFitWidth(50);
                imgView.setFitHeight(50);
                imgView.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
                gridPane.add(imgView, j, i);
                gridImages[i][j] = imgView;
            }
        }
        return gridPane;
    }

    public void atualizarTabuleiro(int x, int y, boolean atingiuNavio) {
        if (atingiuNavio) {
            gridImages[x][y].setImage(explosaoImg);

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                gridImages[x][y].setImage(obterImagemNavio());
            }));
            timeline.setCycleCount(1);
            timeline.play();
        } else {
            gridImages[x][y].setImage(aguaImg);
        }
        ataqueLabel.setText("Ataque em: (" + x + ", " + y + ")");
    }

    private Image obterImagemNavio() {
        Image[] navios = {navioImg1, navioImg2, navioImg3};
        return navios[new Random().nextInt(navios.length)];
    }

    public void bloquearBotao(String tipoSelecionado) {
        if (tipoSelecionado.equals("aleatoria")) {
            btnSequencial.setDisable(true);
        } else {
            btnAleatoria.setDisable(true);
        }
    }

    public void desbloquearBotoes() {
        btnAleatoria.setDisable(false);
        btnSequencial.setDisable(false);
        ataqueLabel.setText("");
    }
}