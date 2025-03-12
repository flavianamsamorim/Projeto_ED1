/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.BuscaController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author Cliente
 */
public class BuscaView {

    private BuscaController controller;
    private Label statusLabel;
    private ImageView[][] gridImages;
    private Image aguaImg, navioImg, explosaoImg;
    private Button btnAleatoria; // Declarar os botões como variáveis de classe
    private Button btnSequencial;

    public BuscaView(Stage stage) {
        controller = new BuscaController(this);

        statusLabel = new Label("Escolha um método de busca");

        // Carregar imagens
        aguaImg = new Image("file:src/imagens/folder/agua.jpeg");
        navioImg = new Image("file:src/imagens/folder/navio.jpg");
        explosaoImg = new Image("file:src/imagens/folder/bomba.jpeg");

        // Botões de busca
        btnAleatoria = new Button("🔀 Busca Aleatória");
        btnAleatoria.setOnAction(e -> controller.executarBusca("aleatoria"));

        btnSequencial = new Button("📍 Busca Sequencial");
        btnSequencial.setOnAction(e -> controller.executarBusca("sequencial"));

        HBox buttonBox = new HBox(20, btnAleatoria, btnSequencial);
        buttonBox.setAlignment(Pos.CENTER);

        // Tabuleiro (GridPane)
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0); // Remove o espaçamento horizontal
        gridPane.setVgap(0); // Remove o espaçamento vertical
        gridPane.setAlignment(Pos.CENTER);

        gridImages = new ImageView[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ImageView imgView = new ImageView(); // Inicialmente sem imagem
                imgView.setFitWidth(40);
                imgView.setFitHeight(40);

                // Adiciona uma borda discreta nas células
                imgView.setStyle("-fx-border-color: gray; -fx-border-width: 1px;");
                
                gridPane.add(imgView, j, i);
                gridImages[i][j] = imgView;
            }
        }

        VBox vbox = new VBox(15, statusLabel, buttonBox, gridPane);
        vbox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 400, 400);
        stage.setTitle("🚢 Busca: Batalha Naval 💣");
        stage.setScene(scene);
        stage.show();
    }

    // Método para atualizar a imagem no tabuleiro (grid)
    public void atualizarTabuleiro(int x, int y, boolean atingiuNavio) {
        if (atingiuNavio) {
            gridImages[x][y].setImage(explosaoImg);
            new Thread(() -> {
                try {
                    Thread.sleep(500); // Espera 500ms antes de trocar para navio
                    gridImages[x][y].setImage(navioImg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            gridImages[x][y].setImage(aguaImg);
        }
        statusLabel.setText("Ataque em: (" + x + ", " + y + ")");
    }

    // Método para bloquear os botões
    public void bloquearBotao(String tipoSelecionado) {
        if (tipoSelecionado.equals("aleatoria")) {
            btnSequencial.setDisable(true);
        } else {
            btnAleatoria.setDisable(true);
        }
    }

    // Método para desbloquear os botões
    public void desbloquearBotoes() {
        btnAleatoria.setDisable(false);
        btnSequencial.setDisable(false);
        statusLabel.setText("Escolha um método de busca");
    }
}
    