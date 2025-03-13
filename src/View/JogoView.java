package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import Controller.JogoController;

import java.util.List;

public class JogoView {
    private Pane mainLayout;
    private Scene scene;

    public JogoView(JogoController controller, Stage stage) {
        mainLayout = new Pane();
        mainLayout.setStyle("-fx-background-color: #d9f1ff;");

        List<Button> botoes = controller.getBotoes();
        scene = new Scene(mainLayout, 600, 600);
        stage.setMaximized(true); // Faz a janela abrir maximizada
        stage.setScene(scene);

        if (!botoes.isEmpty()) {
            organizarArvore(botoes, scene.getWidth(), scene.getHeight());
        }

        // Adiciona um listener para atualizar a árvore quando a janela for redimensionada
        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            mainLayout.getChildren().clear(); // Limpa os elementos antigos
            organizarArvore(botoes, newWidth.doubleValue(), oldWidth.doubleValue()); // Reorganiza os botões
        });

        stage.setScene(scene);
    }

    private void organizarArvore(List<Button> botoes, double larguraJanela, double comprimentoJanela) {
        if (botoes.isEmpty()) return;

        double startX = (larguraJanela /2)-50; // Centraliza horizontalmente (compensando o tamanho do botao)
        double startY = (comprimentoJanela/2)/2;  
        double offsetX = larguraJanela / 4; // Espaçamento inicial ajustável
        double offsetY = comprimentoJanela/4;  

        adicionarNo(botoes, 0, startX, startY, offsetX, offsetY, 0);
    }

    private void adicionarNo(List<Button> botoes, int index, double x, double y, double offsetX, double offsetY, int profundidade) {
        if (index >= botoes.size()) return;

        Button botao = botoes.get(index);
        botao.setLayoutX(x);
        botao.setLayoutY(y);
        mainLayout.getChildren().add(botao);

        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        double novoOffsetX = Math.max(offsetX / 2, 50); // Garante espaçamento mínimo

        if (leftChild < botoes.size()) {
            double childX = x - novoOffsetX;
            double childY = y + offsetY;
            desenharLinha(x, y, childX, childY);
            adicionarNo(botoes, leftChild, childX, childY, novoOffsetX, offsetY, profundidade + 1);
        }
        if (rightChild < botoes.size()) {
            double childX = x + novoOffsetX;
            double childY = y + offsetY;
            desenharLinha(x, y, childX, childY);
            adicionarNo(botoes, rightChild, childX, childY, novoOffsetX, offsetY, profundidade + 1);
        }
    }

    private void desenharLinha(double startX, double startY, double endX, double endY) {
        double buttonSize = 50; // Supondo que os botões sejam 50x50 pixels
        double centerXStart = startX + (buttonSize / 2);
        double centerYStart = startY + (buttonSize / 2);
        double centerXEnd = endX + (buttonSize / 2);
        double centerYEnd = endY + (buttonSize / 2);

        Line line = new Line(centerXStart, centerYStart, centerXEnd, centerYEnd);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        mainLayout.getChildren().add(line);
    }

    public Scene getScene() {
        return scene;
    }
}
