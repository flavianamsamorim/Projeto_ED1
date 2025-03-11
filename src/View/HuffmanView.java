/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.HuffmanController;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.layout.VBox;
/**
 *
 * @author Cliente
 */
public class HuffmanView {
    private HuffmanController controller;
    private Stage stage;

    public HuffmanView(Stage stage) {
        this.stage = stage;
        this.controller = new HuffmanController(stage);  // Criação do controller aqui
        initializeUI();
    }

    private void initializeUI() {
        Button btnCompress = new Button("Comprimir Arquivo");
        btnCompress.setOnAction(e -> compressFile());

        Button btnDecompress = new Button("Descomprimir Arquivo");
        btnDecompress.setOnAction(e -> decompressFile());

        VBox root = new VBox(10, btnCompress, btnDecompress);
        Scene scene = new Scene(root, 300, 150);

        stage.setTitle("Compressão Huffman");
        stage.setScene(scene);
        stage.show();
    }

    private void compressFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            controller.compressFile(file);
        } else {
            showAlert("Erro", "Nenhum arquivo válido selecionado.");
        }
    }

    private void decompressFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Huffman Files", "*.huff"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            controller.decompressFile(file);
        } else {
            showAlert("Erro", "Nenhum arquivo válido selecionado.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}