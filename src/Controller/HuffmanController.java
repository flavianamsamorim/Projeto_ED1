/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.HuffmanCompressor;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import java.io.File;
import java.io.IOException;
/**
 *
 * @author Cliente
 */
public class HuffmanController {
    private HuffmanCompressor compressor;
    private Stage stage;

    public HuffmanController(Stage stage) {
        this.compressor = new HuffmanCompressor();
        this.stage = stage;
    }

    public void compressFile(File inputFile) {
        if (!inputFile.exists()) {
            showAlert("Erro", "O arquivo de entrada não existe.");
            return;
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File destinationFolder = directoryChooser.showDialog(stage);

        if (destinationFolder != null) {
            File outputFile = new File(destinationFolder, inputFile.getName() + ".huff");
            try {
                compressor.compressFile(inputFile, outputFile);
                showAlert("Sucesso", "Arquivo comprimido com sucesso: " + outputFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erro", "Falha ao comprimir o arquivo.");
            }
        }
    }

    public void decompressFile(File compressedFile) {
        if (!compressedFile.exists()) {
            showAlert("Erro", "O arquivo de entrada não existe.");
            return;
        }

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File destinationFolder = directoryChooser.showDialog(stage);

        if (destinationFolder != null) {
            String originalName = compressedFile.getName().replace(".huff", "");
            File outputFile = new File(destinationFolder, originalName);
            try {
                compressor.decompressFile(compressedFile, outputFile);
                showAlert("Sucesso", "Arquivo descomprimido com sucesso: " + outputFile.getAbsolutePath());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                showAlert("Erro", "Falha ao descomprimir o arquivo.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}