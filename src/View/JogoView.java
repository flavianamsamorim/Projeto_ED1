package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import Controller.JogoController;
import javafx.stage.Stage;

import java.util.List;

public class JogoView {
    private VBox mainLayout;
    private Scene scene;

    public JogoView(JogoController controller, Stage stage) {
        // Layout principal para centralizar a lista encadeada de botões
        mainLayout = new VBox(20);
        mainLayout.setStyle("-fx-background-color: #d9f1ff;");
        mainLayout.setAlignment(Pos.CENTER);

        // Obtém os botões do controlador
        List<Button> botoes = controller.getBotoes();
        
        if (botoes.isEmpty()) {
            scene = new Scene(mainLayout, 400, 600);
            return;
        }

        // Layout para organizar os botões em linhas com 3 botões por linha
        VBox listLayout = new VBox(20); // Layout para organizar os botões em lista
        listLayout.setAlignment(Pos.CENTER);

        // Adiciona os botões em grupos de 3 por linha
        int index = 0;
        while (index < botoes.size()) {
            // Criar uma linha de botões
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            for (int i = 0; i < 3 && index < botoes.size(); i++) {
                row.getChildren().add(botoes.get(index++));
            }
            listLayout.getChildren().add(row);

            // Se não for a última linha, adicionar as linhas conectando os botões
            if (index < botoes.size()) {
                HBox lineRow = new HBox(10);
                lineRow.setAlignment(Pos.CENTER);
                // Adiciona as linhas entre os botões
                for (int i = 0; i < row.getChildren().size() - 1; i++) {
                    // Criar uma linha que conecta o botão atual ao próximo
                    Button botaoInicial = (Button) row.getChildren().get(i);
                    Button botaoFinal = (Button) row.getChildren().get(i + 1);

                    // Definir a linha para ligar as laterais dos botões
                    Line line = new Line();
                    line.setStartX(botaoInicial.getLayoutX() + botaoInicial.getWidth());
                    line.setStartY(botaoInicial.getLayoutY() + botaoInicial.getHeight() / 2);
                    line.setEndX(botaoFinal.getLayoutX());
                    line.setEndY(botaoFinal.getLayoutY() + botaoFinal.getHeight() / 2);
                    line.setStroke(Color.BLACK); // Cor da linha
                    line.setStrokeWidth(2);

                    lineRow.getChildren().add(line);
                }
                listLayout.getChildren().add(lineRow);
            }
        }

        // Adiciona a lista ao layout principal
        mainLayout.getChildren().add(listLayout);

        // Criando a cena
        scene = new Scene(mainLayout, 600, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
