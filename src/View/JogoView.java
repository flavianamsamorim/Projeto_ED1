package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controller.JogoController;
import EstruturasDeDados.Lista.Lista;

public class JogoView {
    private Pane mainLayout;
    private Scene scene;

    public JogoView(JogoController controller, Stage stage) {
        mainLayout = new Pane();
        mainLayout.setStyle("-fx-background-color: #d9f1ff;");

        Label titulo = new Label("Estrutura de Dados");
        titulo.setStyle("-fx-font-size: 65px; -fx-font-weight: bold;");

        Label instrucao = new Label("Clique em cada nó para obter seus conhecimentos");
        instrucao.setStyle("-fx-font-size: 25px;");

        Lista<Button> botoes = controller.getBotoes();
        scene = new Scene(mainLayout, 800, 600);
        stage.setMaximized(true); // Faz a janela abrir maximizado
        stage.setScene(scene);

        if (!botoes.isEmpty()) {
            organizarBotoes(botoes);
        }

        VBox layoutPrincipal = new VBox(20, titulo, organizarBotoes(botoes), instrucao);
        layoutPrincipal.setAlignment(Pos.CENTER);
        layoutPrincipal.prefWidthProperty().bind(scene.widthProperty());
        layoutPrincipal.prefHeightProperty().bind(scene.heightProperty());

        mainLayout.getChildren().add(layoutPrincipal);
        stage.setScene(scene);
    }

    private HBox organizarBotoes(Lista<Button> botoes) {
        VBox coluna1 = new VBox(10);
        coluna1.setAlignment(Pos.CENTER);
        Label unidade1 = new Label("Unidade 1");
        unidade1.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        coluna1.getChildren().add(unidade1);
        for (int i = 0; i < 9 && i < botoes.getSize(); i++) {
            coluna1.getChildren().add(botoes.get(i));
        }

        VBox coluna2 = new VBox(10);
        coluna2.setAlignment(Pos.CENTER);
        Label unidade2 = new Label("Unidade 2");
        unidade2.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        coluna2.getChildren().add(unidade2);
        for (int i = 9; i < 12 && i < botoes.getSize(); i++) {
            coluna2.getChildren().add(botoes.get(i));
        }

        VBox coluna3 = new VBox(10);
        coluna3.setAlignment(Pos.CENTER);
        Label unidade3 = new Label("Unidade 3");
        unidade3.setStyle("-fx-font-size: 20px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        coluna3.getChildren().add(unidade3);
        for (int i = 12; i < 15 && i < botoes.getSize(); i++) {
            coluna3.getChildren().add(botoes.get(i));
        }

        HBox layoutBotoes = new HBox(50, coluna1, coluna2, coluna3);
        layoutBotoes.setAlignment(Pos.CENTER);
        return layoutBotoes;
    }

    public Scene getScene() {
        return scene;
    }
}
