/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.AlgoritmoHuffman;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import java.io.File;
import java.io.IOException;

import Model.AlgoritmoHuffman.HuffmanCompressor;
import Model.AlgoritmoHuffman.HuffmanCompressor.ProgressListener;
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

    // Método para inicializar a compressão de um arquivo
    public void compressFile(File inputFile, File outputFile, ProgressBar progressBar) {
        // Exibe a barra de progresso
        progressBar.setVisible(true);

        // Cria uma tarefa para a compressão do arquivo
        Task<Void> compressionTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Configura o listener de progresso para atualizar a barra de progresso
                compressor.setProgressListener(new ProgressListener() {
                    @Override
                    public void onProgress(String message, double progress) {
                        // Atualiza a barra de progresso na interface gráfica
                        Platform.runLater(() -> progressBar.setProgress(progress));
                    }
                });

                try {
                    // Chama o método do compressor para comprimir o arquivo
                    compressor.compressFile(inputFile, outputFile);
                    Platform.runLater(() -> showAlert("Sucesso", "Arquivo comprimido com sucesso: " + outputFile.getAbsolutePath()));
                } catch (IOException e) {
                    Platform.runLater(() -> showAlert("Erro", "Falha ao comprimir o arquivo."));
                }

                // Após a compressão, torna a barra de progresso invisível
                Platform.runLater(() -> progressBar.setVisible(false));

                return null;
            }
        };

        // Executa a tarefa em segundo plano
        Thread compressionThread = new Thread(compressionTask);
        compressionThread.setDaemon(true);
        compressionThread.start();
    }

    // Método para inicializar a descompressão de um arquivo
    public void decompressFile(File compressedFile, ProgressBar progressBar) {
        // Exibe a barra de progresso
        progressBar.setVisible(true);

        // Cria uma tarefa para a descompressão do arquivo
        Task<Void> decompressionTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Configura o listener de progresso para mostrar o andamento
                compressor.setProgressListener(new ProgressListener() {
                    @Override
                    public void onProgress(String message, double progress) {
                        // Atualiza o progresso na interface gráfica
                        Platform.runLater(() -> progressBar.setProgress(progress));
                    }
                });

                // Abre o seletor de diretório para escolher onde salvar o arquivo descomprimido
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File destinationFolder = directoryChooser.showDialog(stage);

                if (destinationFolder != null) {
                    String originalName = compressedFile.getName().replace(".huff", "");
                    File outputFile = new File(destinationFolder, originalName);
                    try {
                        // Chama o método do compressor para descomprimir o arquivo
                        compressor.decompressFile(compressedFile, outputFile);
                        Platform.runLater(() -> showAlert("Sucesso", "Arquivo descomprimido com sucesso: " + outputFile.getAbsolutePath()));
                    } catch (IOException | ClassNotFoundException e) {
                        Platform.runLater(() -> showAlert("Erro", "Falha ao descomprimir o arquivo."));
                    }
                }

                // Após a descompressão, torna a barra de progresso invisível
                Platform.runLater(() -> progressBar.setVisible(false));

                return null;
            }
        };

        // Executa a tarefa de descompressão em segundo plano
        Thread decompressionThread = new Thread(decompressionTask);
        decompressionThread.setDaemon(true);
        decompressionThread.start();
    }

    // Método para mostrar alertas na interface gráfica
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método auxiliar para abrir o seletor de arquivos e iniciar a compressão
    public void openFileChooserForCompression(ProgressBar progressBar) {
        // Abre o seletor de arquivos para escolher o arquivo a ser comprimido
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            // Cria o arquivo de saída com extensão .huff
            File outputFile = new File(file.getParent(), file.getName() + ".huff");

            // Inicia o processo de compressão
            compressFile(file, outputFile, progressBar);
        } else {
            showAlert("Erro", "Nenhum arquivo válido selecionado.");
        }
    }

    // Método auxiliar para abrir o seletor de arquivos e iniciar a descompressão
    public void openFileChooserForDecompression(ProgressBar progressBar) {
        // Abre o seletor de arquivos para escolher o arquivo a ser descomprimido
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Huffman Files", "*.huff"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null && file.exists()) {
            // Chama o método do compressor para descomprimir o arquivo
            decompressFile(file, progressBar);
        } else {
            showAlert("Erro", "Nenhum arquivo válido selecionado.");
        }
    }
}