package View.Generic;

import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventarioMagicoView {
    private final Stage stage;
    private final Inventario<String> inventarioMagico = new Inventario<>();
    private final Inventario<Integer> inventarioNumerico = new Inventario<>();
    private final ObservableList<String> itensListaMagico = FXCollections.observableArrayList();
    private final ObservableList<String> itensListaNumerico = FXCollections.observableArrayList();

    public InventarioMagicoView(Stage stage) {
        this.stage = stage;
        configurarLayout();
    }

    private void configurarLayout() {
        VBox root = new VBox(10);
        root.setStyle("-fx-background-color: #d9f1ff;");
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20)); // Adiciona recuo (margem interna)

        // Inventário Mágico
        Label tituloMagico = new Label("Inventário Mágico");
        tituloMagico.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        TextField campoItemMagico = new TextField();
        campoItemMagico.setPromptText("Digite um item");
        
        Button btnAdicionarMagico = new Button("Adicionar Item");
        btnAdicionarMagico.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnAdicionarMagico.setOnAction(e -> {
            String item = campoItemMagico.getText();
            if (!item.isEmpty()) {
                inventarioMagico.adicionar(item);
                itensListaMagico.setAll(inventarioMagico.listar());
                campoItemMagico.clear();
            }
        });
        
        Button btnRemoverMagico = new Button("Remover Item");
        btnRemoverMagico.setStyle("-fx-background-color:rgb(204, 46, 46); -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnRemoverMagico.setOnAction(e -> {
            String item = campoItemMagico.getText();
            if (inventarioMagico.remover(item)) {
                itensListaMagico.setAll(inventarioMagico.listar());
                campoItemMagico.clear();
            }
        });
        
        ListView<String> listViewMagico = new ListView<>(itensListaMagico);
        
        // Inventário Numérico
        Label tituloNumerico = new Label("Inventário Numérico (Inteiros)");
        tituloNumerico.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        TextField campoItemNumerico = new TextField();
        campoItemNumerico.setPromptText("Digite um número inteiro");
        
        Button btnAdicionarNumerico = new Button("Adicionar Número");
        btnAdicionarNumerico.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnAdicionarNumerico.setOnAction(e -> {
            String input = campoItemNumerico.getText();
            try {
                int numero = Integer.parseInt(input);
                inventarioNumerico.adicionar(numero);
                itensListaNumerico.setAll(inventarioNumerico.listar().stream().map(String::valueOf).collect(Collectors.toList()));
                campoItemNumerico.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Apenas números inteiros são permitidos!", ButtonType.OK);
                alert.showAndWait();
            }
        });
        
        Button btnRemoverNumerico = new Button("Remover Número");
        btnRemoverNumerico.setStyle("-fx-background-color:rgb(204, 46, 46); -fx-text-fill: white; "
        + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnRemoverNumerico.setOnAction(e -> {
            String input = campoItemNumerico.getText();
            try {
                int numero = Integer.parseInt(input);
                if (inventarioNumerico.remover(numero)) {
                    itensListaNumerico.setAll(inventarioNumerico.listar().stream().map(String::valueOf).collect(Collectors.toList()));
                    campoItemNumerico.clear();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Apenas números inteiros são permitidos!", ButtonType.OK);
                alert.showAndWait();
            }
        });
        
        ListView<String> listViewNumerico = new ListView<>(itensListaNumerico);
        
        root.getChildren().addAll(
            tituloMagico, campoItemMagico, btnAdicionarMagico, btnRemoverMagico, listViewMagico,
            tituloNumerico, campoItemNumerico, btnAdicionarNumerico, btnRemoverNumerico, listViewNumerico
        );
        
        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Inventário Mágico e Numérico");
        stage.show();
    }

    private static class Inventario<T> {
        private final ObservableList<T> itens = FXCollections.observableArrayList();
        
        public void adicionar(T item) {
            itens.add(item);
        }
        
        public boolean remover(T item) {
            return itens.remove(item);
        }
        
        public ObservableList<T> listar() {
            return FXCollections.observableArrayList(itens);
        }
    }
}
