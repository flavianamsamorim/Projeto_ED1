/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ListaController;
import Model.ListaJogo.Carta;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author Cliente
 */
public class ListaView {
    private ListaController controller;
    private Button[][] botoes;
    private GridPane tabuleiroGrid;
    private Stage stage;
    private final String CAMINHO_IMAGENS = "file:src/imagens/folder/";

    public ListaView(Stage stage) {
        this.stage = stage;
        controller = new ListaController(this);
        stage.setTitle("🎯 Jogo da Memória 🚀");
        
        // Criando o título da interface
        Text titulo = new Text("Escolha e dimensão \ndo Jogo");
        titulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titulo.setFill(Color.DARKBLUE);
        
        // Criando os botões com estilo melhorado
        Button btn2x2 = criarBotao("2x2");
        Button btn2x3 = criarBotao("2x3");
        Button btn2x4 = criarBotao("2x4");
        Button btn2x5 = criarBotao("2x5");
        Button btn2x6 = criarBotao("2x6");
        Button btn2x7 = criarBotao("2x7");
        Button btn4x4 = criarBotao("4x4");

        // Configurando eventos dos botões
        btn2x2.setOnAction(e -> iniciarJogo(2, 2));
        btn2x3.setOnAction(e -> iniciarJogo(2, 3));
        btn2x4.setOnAction(e -> iniciarJogo(2, 4));
        btn2x5.setOnAction(e -> iniciarJogo(2, 5));
        btn2x6.setOnAction(e -> iniciarJogo(2, 6));
        btn2x7.setOnAction(e -> iniciarJogo(2, 7));
        btn4x4.setOnAction(e -> iniciarJogo(4, 4));

        // Organizando os botões horizontalmente
        HBox botoesLayout = new HBox(10, btn2x2, btn2x3, btn2x4, btn2x5, btn2x6, btn2x7, btn4x4);
        botoesLayout.setAlignment(Pos.CENTER);

        // Criando um layout vertical para alinhar tudo no centro
        VBox layout = new VBox(15, titulo, botoesLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: lightgray; -fx-padding: 20;");

        // Configurando a cena
        stage.setScene(new Scene(layout, 400, 150));
        stage.show();
    }
    
    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        botao.setMinSize(60, 30);
        botao.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5;");
        botao.setOnMouseEntered(e -> botao.setStyle("-fx-background-color: #45a049; -fx-text-fill: white;"));
        botao.setOnMouseExited(e -> botao.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        return botao;
    }
    

    private void iniciarJogo(int linhas, int colunas) {
        Stage novoStage = new Stage();
        controller.iniciarJogo(linhas, colunas, novoStage);
    }

    public void exibirTabuleiro(Carta[] cartas, int linhas, int colunas, Stage novoStage) {
        tabuleiroGrid = new GridPane();
        botoes = new Button[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int index = i * colunas + j;
                Button botao = new Button();
                botao.setMinSize(100, 100);
                botao.setOnAction(e -> controller.virarCarta(index));

                botoes[i][j] = botao;
                tabuleiroGrid.add(botao, j, i);
            }
        }

        Scene tabuleiroScene = new Scene(tabuleiroGrid);
        Platform.runLater(() -> {
            novoStage.setScene(tabuleiroScene);
            novoStage.setTitle("🎯 Jogo da Memória - " + linhas + "x" + colunas);
            novoStage.show();
        });
    }

    public void atualizarBotao(int index, String imagem, boolean virada) {
        int linhas = botoes.length;
        int colunas = botoes[0].length;

        int i = index / colunas;
        int j = index % colunas;

        if (virada) {
            botoes[i][j].setGraphic(new ImageView(new Image(CAMINHO_IMAGENS + imagem, 80, 80, true, true)));
        } else {
            botoes[i][j].setGraphic(null);
        }
    }
    
    
}