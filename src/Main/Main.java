/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.JogoController;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import View.JogoView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("🎮 Estruturas de Dados Gamificadas 🎮");

        JogoController controller = new JogoController();
        JogoView view = new JogoView(controller.getBotoes());

        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}