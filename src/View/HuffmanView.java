/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.HuffmanController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
/**
 *
 * @author Cliente
 */
public class HuffmanView {
    private HuffmanController controller;
    private Stage stage;

    // A interface de progresso
    private ProgressBar progressBar;

    public HuffmanView(Stage stage) {
        this.stage = stage;
        this.controller = new HuffmanController(stage);
        initializeUI();
    }

    private void initializeUI() {
        // Botões para compressão e descompressão
        Button btnCompress = new Button("Comprimir Arquivo");
        btnCompress.setOnAction(e -> controller.openFileChooserForCompression(progressBar));

        Button btnDecompress = new Button("Descomprimir Arquivo");
        btnDecompress.setOnAction(e -> controller.openFileChooserForDecompression(progressBar));

        // Barra de progresso para mostrar o progresso da compressão
        progressBar = new ProgressBar(0);
        progressBar.setVisible(false);  // Inicialmente invisível, até que a compressão comece

        // Layout da cena
        VBox root = new VBox(10, btnCompress, btnDecompress, progressBar);
        Scene scene = new Scene(root, 300, 150);

        // Configurações da janela
        stage.setTitle("Compressão Huffman");
        stage.setScene(scene);
        stage.show();
    }

    // Método para mostrar alertas na interface
    public void showAlert(String title, String message) {
        // Método para mostrar alertas na interface
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para exibir a barra de progresso
    public void showProgressBar(boolean visible) {
        // Torna a barra de progresso visível ou invisível
        Platform.runLater(() -> progressBar.setVisible(visible));
    }

    // Método para atualizar o progresso na interface
    public void updateProgressBar(double progress) {
        // Atualiza a barra de progresso
        Platform.runLater(() -> progressBar.setProgress(progress));
    }

    // Método para esconder a barra de progresso quando o processo terminar
    public void hideProgressBar() {
        // Torna a barra de progresso invisível após a operação
        Platform.runLater(() -> progressBar.setVisible(false));
    }
}