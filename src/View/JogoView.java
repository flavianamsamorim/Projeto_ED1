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
//import javafx.stage.Stage;
import java.util.List;


public class JogoView {
    private VBox layout;
    private Scene scene;

    public JogoView(List<Button> botoes) {
        layout = new VBox(10); // Espaçamento entre os botões
        layout.setAlignment(Pos.CENTER); // Centraliza verticalmente e horizontalmente
        layout.getChildren().addAll(botoes);
        
        scene = new Scene(layout, 400, 600);
    }

    public Scene getScene() {
        return scene;
    }
}