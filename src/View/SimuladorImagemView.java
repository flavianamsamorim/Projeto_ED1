package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;

public class SimuladorImagemView {

    private static final int SIZE = 10;
    private static final int PIXEL_SIZE = 30;
    private final int[][] matriz = new int[SIZE][SIZE];
    private final Canvas canvas = new Canvas(SIZE * PIXEL_SIZE, SIZE * PIXEL_SIZE);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Text matrizText = new Text();
    private final Stage stage;

    public SimuladorImagemView(Stage stage) {
        this.stage = stage;
        configurarLayout(stage);
    }

    private void configurarLayout(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f0f0, #ffffff);");

        // Grade de pixels (Canvas)
        desenharMatriz();
        canvas.setOnMouseClicked(e -> {
            int x = (int) e.getX() / PIXEL_SIZE;
            int y = (int) e.getY() / PIXEL_SIZE;
            matriz[y][x] = (matriz[y][x] == 0) ? 1 : 0;
            desenharMatriz();
            atualizarTextoMatriz();
        });

        // Botão de inversão de cores
        Button btnInverter = new Button("Inverter Cores");
        btnInverter.setOnAction(e -> {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    matriz[i][j] = (matriz[i][j] == 0) ? 1 : 0;
                }
            }
            desenharMatriz();
            atualizarTextoMatriz();
        });

        // Botão para alternar para o jogo de frases
        Button btnTrocarParaString = new Button("Trocar para Jogo de Strings");
        btnTrocarParaString.setOnAction(e -> trocarParaJogoDeStrings());

        // Layout para exibir índices alinhados ao grid
        Canvas indexCanvas = new Canvas((SIZE + 1) * PIXEL_SIZE, (SIZE + 1) * PIXEL_SIZE);
        desenharIndices(indexCanvas.getGraphicsContext2D());

        StackPane canvasContainer = new StackPane(indexCanvas, canvas);

        // Layout inferior com exibição da matriz
        VBox bottomBox = new VBox(10, matrizText, btnInverter, btnTrocarParaString);
        bottomBox.setAlignment(Pos.CENTER);

        root.setCenter(canvasContainer);
        root.setBottom(bottomBox);
        atualizarTextoMatriz();

        Scene scene = new Scene(root, 450, 500);
        stage.setScene(scene);
        stage.setTitle("Simulador de Imagem em Pixels");
        stage.show();
    }

    private void desenharMatriz() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                gc.setFill(matriz[i][j] == 1 ? Color.BLACK : Color.WHITE);
                gc.fillRect(j * PIXEL_SIZE, i * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
                gc.setStroke(Color.GRAY);
                gc.strokeRect(j * PIXEL_SIZE, i * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    private void desenharIndices(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));

        // Índices de colunas (acima da grade)
        for (int i = 0; i < SIZE; i++) {
            gc.fillText(String.valueOf(i), (i + 1) * PIXEL_SIZE + 10, 15);
        }

        // Índices de linhas (à esquerda da grade)
        for (int i = 0; i < SIZE; i++) {
            gc.fillText(String.valueOf(i), 5, (i + 1) * PIXEL_SIZE + 10);
        }
    }

    private void atualizarTextoMatriz() {
        StringBuilder sb = new StringBuilder();
        for (int[] linha : matriz) {
            sb.append("{");
            for (int j = 0; j < SIZE; j++) {
                sb.append(linha[j]);
                if (j < SIZE - 1) sb.append(", ");
            }
            sb.append("},\n");
        }
        matrizText.setText(sb.toString());
    }

    private void trocarParaJogoDeStrings() {
        new JogoDeStringsView(stage);
    }
}

class JogoDeStringsView {
    private final Stage stage;
    private final String[] sujeitos = {"O gato", "A professora", "O cientista"};
    private final String[] verbos = {"comeu", "descobriu", "derrubou"};
    private final String[] objetos = {"um livro", "um mistério", "o café"};
    private final Text fraseText = new Text();

    public JogoDeStringsView(Stage stage) {
        this.stage = stage;
        configurarLayout();
    }

    private void configurarLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f0f0, #ffffff);");

        Button btnGerarFrase = new Button("Gerar Frase");
        btnGerarFrase.setOnAction(e -> gerarFrase());
        
        Button btnTrocarParaPixels = new Button("Voltar para Pixels");
        btnTrocarParaPixels.setOnAction(e -> new SimuladorImagemView(stage));

        VBox vbox = new VBox(20, fraseText, btnGerarFrase, btnTrocarParaPixels);
        vbox.setAlignment(Pos.CENTER);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 450, 300);
        stage.setScene(scene);
        stage.setTitle("Criador de Frases Aleatórias");
        stage.show();
    }

    private void gerarFrase() {
        Random random = new Random();
        String frase = sujeitos[random.nextInt(sujeitos.length)] + " " +
                       verbos[random.nextInt(verbos.length)] + " " +
                       objetos[random.nextInt(objetos.length)] + ".";
        fraseText.setText(frase);
    }
}
