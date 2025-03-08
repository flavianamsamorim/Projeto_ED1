package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TorreHanoiView {

    private final int numDiscos;          // Quantidade de discos
    private final int[] discPositions;    // discPositions[i] = poste onde o disco (i+1) está
    private Canvas canvas;
    private GraphicsContext gc;

    // Movimentos gerados pela recursão: {disco, origem, destino}
    private List<int[]> movimentos;
    private int movimentoAtual = 0;

    // TextArea para exibir as mensagens “empilhando” e “desempilhando”
    private TextArea recursionLog;

    public TorreHanoiView(Stage stage, int numDiscos) {
        this.numDiscos = numDiscos;
        this.discPositions = new int[numDiscos];
        configurarLayout(stage);
    }

    private void configurarLayout(Stage stage) {
        BorderPane root = new BorderPane();

        Label lblTitulo = new Label("Torre de Hanói - Recursividade (Delay 3s)");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox topBox = new HBox(lblTitulo);
        topBox.setAlignment(Pos.CENTER);

        canvas = new Canvas(600, 300);
        gc = canvas.getGraphicsContext2D();

        // Botão para iniciar
        Button btnIniciar = new Button("Iniciar");
        btnIniciar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-background-radius: 8;");
        btnIniciar.setOnAction(e -> iniciarAnimacao());

        // Área de texto para log da recursividade
        recursionLog = new TextArea();
        recursionLog.setEditable(false);
        recursionLog.setPrefWidth(300);
        recursionLog.setPrefHeight(300);
        recursionLog.setWrapText(true);

        HBox bottomBox = new HBox(btnIniciar);
        bottomBox.setAlignment(Pos.CENTER);

        // Layout: Canvas ao centro, log à direita
        root.setTop(topBox);
        root.setCenter(canvas);
        root.setRight(recursionLog);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 900, 350);
        stage.setScene(scene);
        stage.setTitle("Torre de Hanói (Recursão com Delay e Log Simplificado)");
        stage.show();

        inicializarDiscos();
        desenhar(); // Desenho inicial
    }

    private void inicializarDiscos() {
        // Todos os discos começam no poste 0
        for (int i = 0; i < numDiscos; i++) {
            discPositions[i] = 0;
        }
    }

    private void iniciarAnimacao() {
        movimentos = new ArrayList<>();
        movimentoAtual = 0;

        // Limpa o log
        recursionLog.clear();

        // Gera os movimentos recursivamente
        gerarMovimentos(numDiscos, 0, 2, 1, movimentos);

        // Exibe quantos movimentos teremos
        recursionLog.appendText("\nTotal de movimentos: " + movimentos.size() + "\n");

        // Timeline com delay de 3 segundos entre cada movimento
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(3), e -> executarProximoMovimento())
        );
        timeline.setCycleCount(movimentos.size());
        timeline.play();
    }

    /**
     * Método recursivo que gera a sequência de movimentos para
     * mover n discos de 'origem' p/ 'destino', usando 'auxiliar'.
     * Exibe mensagens “empilhando n” e “desempilhando n” no log.
     */
    private void gerarMovimentos(int n, int origem, int destino, int auxiliar, List<int[]> lista) {
        // Ao entrar na chamada, mostramos “empilhando n”
        recursionLog.appendText("empilhando " + n + "\n");

        if (n == 1) {
            // Mover disco 1
            lista.add(new int[]{n, origem, destino});
        } else {
            gerarMovimentos(n - 1, origem, auxiliar, destino, lista);
            lista.add(new int[]{n, origem, destino});
            gerarMovimentos(n - 1, auxiliar, destino, origem, lista);
        }

        // Ao sair da chamada, mostramos “desempilhando n”
        recursionLog.appendText("desempilhando " + n + "\n");
    }

    private void executarProximoMovimento() {
        if (movimentoAtual < movimentos.size()) {
            int[] mov = movimentos.get(movimentoAtual);
            int disco = mov[0];
            int from = mov[1];
            int to = mov[2];

            // Atualiza a posição do disco
            discPositions[disco - 1] = to;

            movimentoAtual++;
            desenhar();
        }
    }

    private void desenhar() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        // Desenha 3 postes
        double baseY = h - 20;
        double gap = w / 3;
        double posteLargura = 10;
        double posteAltura = 200;

        for (int i = 0; i < 3; i++) {
            double centerX = gap * (i + 0.5);
            double x = centerX - (posteLargura / 2);
            double y = baseY - posteAltura;

            gc.setFill(Color.DARKGRAY);
            gc.fillRect(x, y, posteLargura, posteAltura);

            gc.setFill(Color.BLACK);
            gc.fillRect(centerX - 50, baseY, 100, 5);
        }

        // Precisamos saber quantos discos já foram desenhados em cada poste
        int[] nivel = new int[3];

        // Desenhar do maior para o menor
        for (int disco = numDiscos; disco >= 1; disco--) {
            int post = discPositions[disco - 1];
            double centerX = gap * (post + 0.5);

            double maxLargura = 120;
            double minLargura = 30;
            double discoAltura = 15;
            double largura = minLargura + (maxLargura - minLargura)
                             * (disco - 1) / (numDiscos - 1);

            double y = baseY - (nivel[post] + 1) * discoAltura;
            double x = centerX - (largura / 2);

            gc.setFill(Color.hsb(disco * 40, 0.7, 0.9));
            gc.fillRoundRect(x, y - discoAltura, largura, discoAltura, 10, 10);

            nivel[post]++;
        }
    }
}
