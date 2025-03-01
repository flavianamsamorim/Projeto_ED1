package View;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LabirintoView {
    private final int ROWS = 5;
    private final int COLS = 5;
    private final int CELL_SIZE = 60;
    private int faseAtual = 0;

    private int[][][] fases = {
        {
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 0, 1, 2}
        },
        {
            {0, 0, 0, 1, 2},
            {1, 1, 0, 1, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        }
    };

    private int[][] maze;
    private int[][] direcoes;
    private int robotRow;
    private int robotCol;

    private Stage stage;
    private Canvas canvas;
    private RadioButton rbCodigo1, rbCodigo2;
    private Button btnExecutar, btnProxima;

    public LabirintoView(Stage stage) {
        this.stage = stage;
        iniciarFase();
    }

    private void iniciarFase() {
        maze = fases[faseAtual];
        direcoes = new int[ROWS][COLS];
        robotRow = 0;
        robotCol = 0;
        configurarLayout();
    }

    private void configurarLayout() {
        BorderPane root = new BorderPane();
        canvas = new Canvas(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        desenharLabirinto();

        rbCodigo1 = new RadioButton("Código 1: while (naoEstaNoObjetivo) { if (podeMoverDireita) moverDireita(); else moverBaixo(); }");
        rbCodigo2 = new RadioButton("Código 2: for (int i=0; i<3; i++) { moverBaixo(); }");

        ToggleGroup tg = new ToggleGroup();
        rbCodigo1.setToggleGroup(tg);
        rbCodigo2.setToggleGroup(tg);

        btnExecutar = new Button("Executar Código");
        btnExecutar.setOnAction(e -> executarCodigo());

        btnProxima = new Button("Próxima Pergunta");
        btnProxima.setVisible(false);
        btnProxima.setOnAction(e -> {
            if (faseAtual < fases.length - 1) {
                faseAtual++;
                iniciarFase();
            }
        });

        VBox vbox = new VBox(10, rbCodigo1, rbCodigo2, btnExecutar, btnProxima);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");

        root.setCenter(canvas);
        root.setRight(vbox);

        Scene scene = new Scene(root, 600, 320);
        stage.setScene(scene);
        stage.setTitle("Labirinto - Condição e Repetição");
        stage.show();
    }

    private void desenharLabirinto() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                double x = col * CELL_SIZE;
                double y = row * CELL_SIZE;
                gc.setFill(Color.WHITE);
                gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                if (maze[row][col] == 1) {
                    gc.setFill(Color.GRAY);
                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
                if (maze[row][col] == 2) {
                    gc.setFill(Color.LIGHTGREEN);
                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                }
                if (row == robotRow && col == robotCol) {
                    gc.setFill(Color.RED);
                    gc.fillOval(x + 10, y + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                }
                if (direcoes[row][col] != 0) {
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font(20));
                    gc.fillText(getSeta(direcoes[row][col]), x + 25, y + 35);
                }
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private String getSeta(int direcao) {
        switch (direcao) {
            case 1: return "→";
            case 2: return "↓";
            default: return "";
        }
    }

    private void executarCodigo() {
        if (rbCodigo1.isSelected()) {
            executarCodigo1();
        } else if (rbCodigo2.isSelected()) {
            executarCodigo2();
        }
        btnProxima.setVisible(true);
        desenharLabirinto();
    }

    private void executarCodigo1() {
        int r = 0, c = 0;
        while (r < ROWS && c < COLS && maze[r][c] != 2) {
            if (podeMover(r, c + 1)) {
                direcoes[r][c] = 1;
                c++;
            } else if (podeMover(r + 1, c)) {
                direcoes[r][c] = 2;
                r++;
            } else {
                break;
            }
        }
    }

    private void executarCodigo2() {
        int r = 0, c = 0;
        for (int i = 0; i < 3 && r < ROWS && maze[r][c] != 2; i++) {
            if (podeMover(r + 1, c)) {
                direcoes[r][c] = 2;
                r++;
            }
        }
    }

    private boolean podeMover(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS && maze[row][col] != 1;
    }
}
