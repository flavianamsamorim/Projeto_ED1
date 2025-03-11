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

/**
 * Torre de Hanói em JavaFX (sem pilhas),
 * exibindo recursão aninhada (empilhando/desempilhando)
 * para a chamada que move N discos, com 3s de delay
 * e botão de Pausar/Continuar.
 */
public class TorreHanoiView {

    private final int numDiscos;         // Quantidade de discos
    private final int[] discPositions;   // discPositions[i] = poste onde está o disco (i+1)

    // Lista de "eventos": cada evento pode ser uma String (log) ou um Move (movimento)
    private List<Object> eventos;
    private int eventoAtual = 0;

    private Canvas canvas;
    private GraphicsContext gc;
    private TextArea recursionLog;

    // Classe interna para representar um movimento de disco
    private static class Move {
        int disco, origem, destino;
        Move(int disco, int origem, int destino) {
            this.disco = disco;
            this.origem = origem;
            this.destino = destino;
        }
    }

    // Variáveis para pausar/continuar
    private Timeline timeline;  // Armazenamos o Timeline para controlar pausa
    private boolean isPaused = false;

    public TorreHanoiView(Stage stage, int numDiscos) {
        this.numDiscos = numDiscos;
        this.discPositions = new int[numDiscos];
        configurarLayout(stage);
    }

    private void configurarLayout(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #d9f1ff, #ffffff);");


        Label lblTitulo = new Label("Torre de Hanói - Recursão Aninhada");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox topBox = new HBox(lblTitulo);
        topBox.setAlignment(Pos.CENTER);

        canvas = new Canvas(600, 300);
        gc = canvas.getGraphicsContext2D();

        // Botão para iniciar a animação
        Button btnIniciar = new Button("Iniciar");
        btnIniciar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-background-radius: 8;");
        btnIniciar.setOnAction(e -> iniciarAnimacao());

        // Botão para pausar/continuar
        Button btnPauseResume = new Button("Pausar");
        btnPauseResume.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-background-radius: 8;");
        btnPauseResume.setOnAction(e -> {
            if (!isPaused) {
                // Se não está pausado, então pausa
                if (timeline != null) {
                    timeline.pause();
                }
                isPaused = true;
                btnPauseResume.setText("Continuar");
            } else {
                // Se está pausado, então continua
                if (timeline != null) {
                    timeline.play();
                }
                isPaused = false;
                btnPauseResume.setText("Pausar");
            }
        });

        // Área de texto para log
        recursionLog = new TextArea();
        recursionLog.setEditable(false);
        recursionLog.setPrefWidth(300);
        recursionLog.setPrefHeight(300);
        recursionLog.setWrapText(true);

        HBox bottomBox = new HBox(10, btnIniciar, btnPauseResume);
        bottomBox.setAlignment(Pos.CENTER);

        // Layout: Canvas ao centro, log à direita
        root.setTop(topBox);
        root.setCenter(canvas);
        root.setRight(recursionLog);
        root.setBottom(bottomBox);

        Scene scene = new Scene(root, 900, 350);
        stage.setScene(scene);
        stage.setTitle("Torre de Hanói (Recursão + Delay + Pausa)");
        stage.show();

        inicializarDiscos();
        desenhar(); // Desenho inicial
    }

    /**
     * Define que todos os discos começam no poste 0.
     */
    private void inicializarDiscos() {
        for (int i = 0; i < numDiscos; i++) {
            discPositions[i] = 0; // poste 0
        }
    }

    /**
     * Inicia a animação: gera os eventos (log + movimentos) e executa cada um a cada 3s.
     */
    private void iniciarAnimacao() {
        eventos = new ArrayList<>();
        eventoAtual = 0;
        recursionLog.clear();

        // Gera eventos recursivamente (agora com logs de chamada inteira)
        gerarEventosRecursivo(numDiscos, 0, 2, 1, eventos);

        recursionLog.appendText("Total de eventos: " + eventos.size() + "\n");

        // Cria o Timeline com 3s de intervalo
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> executarProximoEvento()));
        timeline.setCycleCount(eventos.size());
        timeline.play();
    }

    /**
     * Gera eventos refletindo a CHAMADA RECURSIVA completa:
     *   - "empilhando n" (quando entra na função que move n discos)
     *   - se n==1: Move(1, origem, destino)
     *   - se n>1: gerarEventosRecursivo(n-1, ...), Move(n,...), gerarEventosRecursivo(n-1, ...)
     *   - "desempilhando n" (quando sai da função que move n discos)
     */
    private void gerarEventosRecursivo(int n, int origem, int destino, int auxiliar, List<Object> lista) {
        // Log de entrada (chamada recursiva para mover n discos)
        lista.add("empilhando " + n);

        if (n == 1) {
            // Caso base: mover disco 1
            lista.add(new Move(1, origem, destino));
        } else {
            // Mover n-1 do 'origem' para 'auxiliar'
            gerarEventosRecursivo(n - 1, origem, auxiliar, destino, lista);

            // Mover o disco n
            lista.add(new Move(n, origem, destino));

            // Mover n-1 do 'auxiliar' para 'destino'
            gerarEventosRecursivo(n - 1, auxiliar, destino, origem, lista);
        }

        // Log de saída (voltando da função que move n discos)
        lista.add("desempilhando " + n);
    }

    /**
     * Executa o próximo evento da lista (String ou Move).
     */
    private void executarProximoEvento() {
        if (eventoAtual < eventos.size()) {
            Object ev = eventos.get(eventoAtual);

            if (ev instanceof String) {
                // É uma mensagem de log
                recursionLog.appendText(ev.toString() + "\n");
            } else if (ev instanceof Move) {
                // É um movimento
                Move m = (Move) ev;
                // Atualiza a posição do disco
                discPositions[m.disco - 1] = m.destino;
                desenhar();
            }

            eventoAtual++;
        }
    }

    /**
     * Desenha os postes e os discos no Canvas.
     */
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
            double largura = (numDiscos == 1)
                    ? maxLargura
                    : minLargura + (maxLargura - minLargura) * (disco - 1) / (numDiscos - 1);

            double y = baseY - (nivel[post] + 1) * discoAltura;
            double x = centerX - (largura / 2);

            gc.setFill(Color.hsb(disco * 40, 0.7, 0.9));
            gc.fillRoundRect(x, y - discoAltura, largura, discoAltura, 10, 10);

            nivel[post]++;
        }
    }
}
