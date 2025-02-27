/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author Cliente
 */
public class GenericView {
    public GenericView(String titulo, Stage stage) {
        // Criação de um layout básico para o jogo
        VBox layout = new VBox(10);

        // Título do jogo
        Button lblTitulo = new Button(titulo);
        layout.getChildren().add(lblTitulo);

        // Botão de Voltar
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> stage.close());
        layout.getChildren().add(btnVoltar);

        // Criando a cena
        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();
    }
}
