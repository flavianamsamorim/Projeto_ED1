/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import Controller.JogoController;
import javafx.stage.Stage;

public class JogoView {
    private VBox layout;
    private Scene scene;

    public JogoView(JogoController controller, Stage stage) {
        // Layout para centralizar os botões
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        // Adiciona os botões ao layout
        for (Button btn : controller.getBotoes()) {
            layout.getChildren().add(btn);
        }

        // Criando a cena
        scene = new Scene(layout, 400, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
