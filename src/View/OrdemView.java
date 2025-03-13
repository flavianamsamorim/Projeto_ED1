/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.OrdemController;
import java.util.Random;
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
public class OrdemView {

    private OrdemController controller;
    private Label statusLabel;
    private Label ordenacaoLabel;
    private ImageView[] gridImages;
    private Image abcImg, letraAImg, letraBImg, letraCImg, letraDImg, letraEImg, letraFImg, letraGImg, letraHImg;
    private Button btnBubbleSort, btnSelectionSort, btnInsertionSort, btnQuickSort, btnShellSort, btnHeapSort;

    public OrdemView(Stage stage) {
        controller = new OrdemController(this);

        // Inicializar componentes
        carregarImagens();
        inicializarInterface(stage);
    }

    private void carregarImagens() {
        abcImg = new Image("file:src/imagens/folder/abc.png");
        letraAImg = new Image("file:src/imagens/folder/letraA.jpg");
        letraBImg = new Image("file:src/imagens/folder/letraB.jpg");
        letraCImg = new Image("file:src/imagens/folder/letraC.jpg");
        letraDImg = new Image("file:src/imagens/folder/letraD.jpg");
        letraEImg = new Image("file:src/imagens/folder/letraE.jpg");
        letraFImg = new Image("file:src/imagens/folder/letraF.jpg");
        letraGImg = new Image("file:src/imagens/folder/letraG.jpg");
        letraHImg = new Image("file:src/imagens/folder/letraH.jpg");
    }

    private void inicializarInterface(Stage stage) {
        statusLabel = new Label("Escolha um método de ordenação");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ordenacaoLabel = new Label("");
        ordenacaoLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        // Criar botões de ordenação
        btnBubbleSort = criarBotao("🔴 Bubble Sort", "bubbleSort");
        btnSelectionSort = criarBotao("🔴 Selection Sort", "selectionSort");
        btnInsertionSort = criarBotao("🔴 Insertion Sort", "insertionSort");
        btnQuickSort = criarBotao("🔴 Quick Sort", "quickSort");
        btnShellSort = criarBotao("🔴 Shell Sort", "shellSort");
        btnHeapSort = criarBotao("🔴 Heap Sort", "heapSort");

        // Botões em uma VBox alinhada à direita
        VBox buttonBox = new VBox(10, btnBubbleSort, btnSelectionSort, btnInsertionSort, btnQuickSort, btnShellSort, btnHeapSort);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Criar a imagem principal fixa e centralizada
        ImageView imgViewABC = new ImageView(abcImg);
        imgViewABC.setPreserveRatio(true);
        imgViewABC.setFitWidth(300);

        HBox abcBox = new HBox(imgViewABC);
        abcBox.setAlignment(Pos.CENTER);

        // Criar tabuleiro
        GridPane gridPane = criarTabuleiro();

        // Layout principal
        HBox mainLayout = new HBox(30, gridPane, buttonBox);
        mainLayout.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, abcBox, statusLabel, mainLayout, ordenacaoLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20px; -fx-background-color: #87CEEB;");

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // Definir a cena
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("🔢 Ordenação: Jogo da Ordem");
        stage.setScene(scene);
        stage.show();
    }

    private Button criarBotao(String texto, String tipoOrdenacao) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-font-size: 14px; -fx-background-radius: 10px;");
        botao.setOnAction(e -> controller.executarOrdem(tipoOrdenacao));
        return botao;
    }

    private GridPane criarTabuleiro() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER_LEFT);

        gridImages = new ImageView[8];
        Image[] imagens = {letraAImg, letraBImg, letraCImg, letraDImg, letraEImg, letraFImg, letraGImg, letraHImg};
        Random random = new Random();

        int index = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                ImageView imgView = new ImageView(abcImg);
                imgView.setFitWidth(50);
                imgView.setFitHeight(50);
                imgView.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
                gridPane.add(imgView, col, row);
                gridImages[index++] = imgView;
            }
        }
        return gridPane;
    }

    public void atualizarTabuleiro(int i, char letra) {
        Image letraImg = obterImagemLetra(letra);
        gridImages[i].setImage(letraImg);
    }

    private Image obterImagemLetra(char letra) {
        switch (letra) {
            case 'A': return letraAImg;
            case 'B': return letraBImg;
            case 'C': return letraCImg;
            case 'D': return letraDImg;
            case 'E': return letraEImg;
            case 'F': return letraFImg;
            case 'G': return letraGImg;
            case 'H': return letraHImg;
            default: return abcImg;
        }
    }

    public void bloquearBotoes() {
        btnBubbleSort.setDisable(true);
        btnSelectionSort.setDisable(true);
        btnInsertionSort.setDisable(true);
        btnQuickSort.setDisable(true);
        btnShellSort.setDisable(true);
        btnHeapSort.setDisable(true);
    }

    public void desbloquearBotoes() {
        btnBubbleSort.setDisable(false);
        btnSelectionSort.setDisable(false);
        btnInsertionSort.setDisable(false);
        btnQuickSort.setDisable(false);
        btnShellSort.setDisable(false);
        btnHeapSort.setDisable(false);
        ordenacaoLabel.setText("Ordenação concluída!");
    }
}
