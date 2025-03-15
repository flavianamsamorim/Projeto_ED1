/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.OrdemJogo;
import Controller.OrdemController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Cliente
 */
public class OrdemView {

    private OrdemController controller;
    private Label statusLabel;
    private Label ordenacaoLabel;
    private ImageView[][] gridImages;
    private Image abcImg, letraAImg, letraBImg, letraCImg, letraDImg, letraEImg, letraFImg, letraGImg, letraHImg;
    private Button btnBubbleSort, btnSelectionSort, btnInsertionSort, btnQuickSort, btnShellSort, btnHeapSort;

    public OrdemView(Stage stage) {
        controller = new OrdemController(this);
        carregarImagens();
        inicializarInterface(stage);
    }

    private void carregarImagens() {
        abcImg = new Image("file:src/imagens/folder/abc.png");
        letraAImg = new Image("file:src/imagens/folder/letraA.png");
        letraBImg = new Image("file:src/imagens/folder/letraB.png");
        letraCImg = new Image("file:src/imagens/folder/letraC.png");
        letraDImg = new Image("file:src/imagens/folder/letraD.png");
        letraEImg = new Image("file:src/imagens/folder/letraE.png");
        letraFImg = new Image("file:src/imagens/folder/letraF.png");
        letraGImg = new Image("file:src/imagens/folder/letraG.png");
        letraHImg = new Image("file:src/imagens/folder/letraH.png");
    }

    private void inicializarInterface(Stage stage) {
        statusLabel = new Label("Escolha um método de ordenação");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ordenacaoLabel = new Label("");
        ordenacaoLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        btnBubbleSort = criarBotao("🔴 Bubble Sort", "bubbleSort");
        btnSelectionSort = criarBotao("🔴 Selection Sort", "selectionSort");
        btnInsertionSort = criarBotao("🔴 Insertion Sort", "insertionSort");
        btnQuickSort = criarBotao("🔴 Quick Sort", "quickSort");
        btnShellSort = criarBotao("🔴 Shell Sort", "shellSort");
        btnHeapSort = criarBotao("🔴 Heap Sort", "heapSort");

        VBox buttonBox = new VBox(10, btnBubbleSort, btnSelectionSort, btnInsertionSort, btnQuickSort, btnShellSort, btnHeapSort);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        ImageView imgViewABC = new ImageView(abcImg);
        imgViewABC.setPreserveRatio(true);
        imgViewABC.setFitWidth(300);

        HBox abcBox = new HBox(imgViewABC);
        abcBox.setAlignment(Pos.CENTER);

        GridPane tabuleiroGrid = criarTabuleiro();

        HBox mainLayout = new HBox(30, tabuleiroGrid, buttonBox);
        mainLayout.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, abcBox, statusLabel, mainLayout, ordenacaoLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #e0f7fa);"
                + "-fx-padding: 20;");

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Ordenação: Jogo da Ordem 🔢 ");
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
        GridPane tabuleiroGrid = new GridPane();
        tabuleiroGrid.setHgap(10);
        tabuleiroGrid.setVgap(10);
        tabuleiroGrid.setAlignment(Pos.CENTER_LEFT);

        gridImages = new ImageView[2][4]; // Grid 2x4
        List<Image> listaImagens = carregarImagensTabuleiro();

        int index = 0;
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 4; col++) {
                ImageView imgView = new ImageView(listaImagens.get(index));
                imgView.setFitWidth(70);
                imgView.setFitHeight(70);
                imgView.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: white;");
                tabuleiroGrid.add(imgView, col, row);
                gridImages[row][col] = imgView;
                index++;
            }
        }
        return tabuleiroGrid;
    }

    public List<Image> carregarImagensTabuleiro() {
        // Criando uma lista de imagens, para que o tabuleiro tenha as letras embaralhadas
        List<Image> imagens = new ArrayList<>();
        imagens.add(letraAImg);
        imagens.add(letraBImg);
        imagens.add(letraCImg);
        imagens.add(letraDImg);
        imagens.add(letraEImg);
        imagens.add(letraFImg);
        imagens.add(letraGImg);
        imagens.add(letraHImg);
        Collections.shuffle(imagens);
        return imagens;
    }
    
    // Método que atualiza a posição (x, y) do tabuleiro com a letra correspondente
    public void atualizarTabuleiro(int linha, int coluna, char letra) {
        // Obtém a imagem correspondente à letra
        Image imagemAtual = obterImagemLetra(letra);

        // Atualiza a imagem na posição específica do tabuleiro (matriz 2x4)
        gridImages[linha][coluna].setImage(imagemAtual);
    }

    public Image obterImagemLetra(char letra) {
        switch (letra) {
            case 'A':
                return letraAImg;
            case 'B':
                return letraBImg;
            case 'C':
                return letraCImg;
            case 'D':
                return letraDImg;
            case 'E':
                return letraEImg;
            case 'F':
                return letraFImg;
            case 'G':
                return letraGImg;
            case 'H':
                return letraHImg;
            default:
                return abcImg;
        }
    }

    // Método para bloquear os botões enquanto a ordenação está em execução
    public void bloquearBotoes() {
        btnBubbleSort.setDisable(true);
        btnSelectionSort.setDisable(true);
        btnInsertionSort.setDisable(true);
        btnQuickSort.setDisable(true);
        btnShellSort.setDisable(true);
        btnHeapSort.setDisable(true);
    }

    // Método para desbloquear os botões após a execução da ordenação
    public void desbloquearBotoes() {
        btnBubbleSort.setDisable(false);
        btnSelectionSort.setDisable(false);
        btnInsertionSort.setDisable(false);
        btnQuickSort.setDisable(false);
        btnShellSort.setDisable(false);
        btnHeapSort.setDisable(false);
        ordenacaoLabel.setText("Ordenação concluída!");
    }

    // Métodos para obter as imagens das letras. Eles são utilizados no Controller para obter a imagem correspondente à letra.
    public Image getLetraAImg() {
        return letraAImg;
    }

    public Image getLetraBImg() {
        return letraBImg;
    }

    public Image getLetraCImg() {
        return letraCImg;
    }

    public Image getLetraDImg() {
        return letraDImg;
    }

    public Image getLetraEImg() {
        return letraEImg;
    }

    public Image getLetraFImg() {
        return letraFImg;
    }

    public Image getLetraGImg() {
        return letraGImg;
    }

    public Image getLetraHImg() {
        return letraHImg;
    }
}
