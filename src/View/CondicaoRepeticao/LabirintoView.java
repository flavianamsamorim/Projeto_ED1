package View.CondicaoRepeticao;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LabirintoView {
    private final int ROWS = 5;
    private final int COLS = 5;
    private final int CELL_SIZE = 60;
    private int faseAtual = 0;
    private int ultimaFase = 4;  // Ou qualquer número que represente o total de fases no jogo
    private boolean respostaSelecionada = false;
    private boolean respostaCorreta = false;
    private int[][][] fases = {
        {
            {3, 1, 0, 0, 0},
            {4, 1, 0, 1, 0},
            {4, 4, 4, 1, 0},
            {1, 1, 4, 1, 0},
            {0, 0, 4, 4, 2}
        },
        {
            {0, 0, 1, 0, 0},
            {1, 3, 1, 0, 1},
            {1, 4, 4, 4, 4},
            {1, 1, 1, 1, 4},
            {0, 0, 0, 0, 2}
        },
        {
            {0, 0, 0, 1, 2},
            {0, 1, 0, 1, 4},
            {0, 1, 1, 1, 4},
            {0, 0, 4, 4, 4},
            {0, 0, 3, 1, 1}
        },
        {
            {2, 4, 4, 1, 0},
            {0, 1, 4, 1, 0},
            {0, 1, 4, 4, 0},
            {0, 1, 1, 4, 3},
            {0, 0, 0, 0, 0}
        },
        {
            {4, 4, 4, 1, 3},
            {4, 1, 4, 4, 4},
            {4, 1, 1, 1, 1},
            {4, 0, 0, 0, 0},
            {2, 0, 0, 0, 0}
        }
    };

    private int[][] maze;
    private int[][] direcoes;
    private int robotRow;
    private int robotCol;
    
    private final int[] respostasCorretas = {3, 1, 4, 2, 1}; // Índices das respostas corretas por fase
    private Stage stage;
    private Canvas canvas;
    private ToggleGroup tg;
    private Button btnExecutar, btnProxima, btnFinalizar;
    private Label resultadoLabel;

    public LabirintoView(Stage stage) {
        this.stage = stage;
        
        iniciarFase();
    }

    private void iniciarFase() {
        
        maze = fases[faseAtual];
        direcoes = new int[ROWS][COLS];

        // Encontrar a posição inicial do robô (valor 3 na matriz)
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (maze[row][col] == 3) {
                    robotRow = row;
                    robotCol = col;
                    break;
                }
            }
        }

        respostaSelecionada = false;
        respostaCorreta = false;
        configurarLayout();
    }

    private void configurarLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #d9f1ff;");

        canvas = new Canvas(COLS * CELL_SIZE, ROWS * CELL_SIZE);
        desenharLabirinto();

        Label legenda = new Label(
            "Objetivo: levar o círculo vermelho até o quadrado verde.\n" +
            "Escolha o algoritmo que permite com que isso seja possível.\n" +
            "Regras:\nQuadrados brancos indicam por onde o círculo pode se mover\n" +
            "Quadrados escuros são paredes."
        );
        

        legenda.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #d9f1ff; -fx-padding: 50;");
        legenda.setWrapText(true);

        tg = new ToggleGroup();

                

            // Opções de código para cada fase
        String[][] opcoesCodigo = {
            {
                "Código 1: \nwhile (naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t} \r\n" + //
                                    "}",
                "Código 2: \nfor(i=0; i<5; i++){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 3: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverDireita()){\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverBaixo()\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 4: \nmoverBaixo(); \r\n" + //
                                    "moverBaixo(); \r\n" + //
                                    "moverDireita(); \r\n" + //
                                    "moverDireita();"
            },
            {
                "Código 1: \nmoverBaixo(); \r\n" + //
                                    "moverDireita(); \r\n" + //
                                    "moverDireita(); \r\n" + //
                                    "moverDireita();\r\n" + //
                                    "moverBaixo();\r\n" + //
                                    "moverBaixo();",
                "Código 2: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverDireta()){\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t} \r\n" + //
                                    "}",
                "Código 3: \nfor(i=0; i<4; i++){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t\tif(podeMoverCima()){\r\n" + //
                                    "\t\t\tmoverCima();\r\n" + //
                                    "\t\t}\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 4: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverBaixo()\r\n" + //
                                    "\t}\r\n" + //
                                    "}"
            },
            {
                "Código 1: \nfor(i=0; i<4; i++){ \r\n" + //
                                    "\tif(podeMoverEsquerda()){\r\n" + //
                                    "\t\tmoverEsquerda();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t\tif(podeMoverDireita()){\r\n" + //
                                    "\t\t\tmoverDireita();\r\n" + //
                                    "\t\t}\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 2: \nfor(i=0; i<4; i++){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverBaixo()\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 3: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverCima()){\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tif(podeMoverEsquerda){\r\n" + //
                                    "\t\t\tmoverEsquerda();\r\n" + //
                                    "\t\t}else{\r\n" + //
                                    "\t\t\tmoverBaixo();\r\n" + //
                                    "\t\t}\r\n" + //
                                    "\t} \r\n" + //
                                    "}",
                "Código 4: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverDireta()){\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t} \r\n" + //
                                    "}"
            },
            {
                "Código 1: \nfor(i=0; i<6; i++){ \r\n" + //
                                    "\tif(podeMoverDireita()){\r\n" + //
                                    "\t\tmoverDireita();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 2: \nfor(i=0; i<7; i++){ \r\n" + //
                                    "\tif(podeMoverEsquerda()){\r\n" + //
                                    "\t\tmoverEsquerda();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t}\r\n" + //
                                    "}",
                "Código 3: \ndo{\r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tMoverDireta();\r\n" + //
                                    "\t}\r\n" + //
                                    "    }while (naoEstaNoObjetivo());",
                "Código 4: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverCima()){\r\n" + //
                                    "\t\tmoverCima();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t} \r\n" + //
                                    "}"
            },
            {
                "Código 1: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\r\n" + //
                                    "\t\tif(podeMoverEsquerda()){\r\n" + //
                                    "\t\t\tmoverEsquerda();\r\n" + //
                                    "\t\t}else{\r\n" + //
                                    "\t\t\tmoverCima();\r\n" + //
                                    "\t\t}\r\n" + //
                                    "\t} \r\n" + //
                                    "}",
                "Código 2: \nwhile(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\t\t\r\n" + //
                                    "\t\tif(podeMoverEsquerda())\r\n" + //
                                    "\t\t\tmoverEsquerda();\r\n" + //
                                    "\t\t}else{\r\n" + //
                                    "\t\t\tmoverDireita();\r\n" + //
                                    "\t\t}\r\n" + //
                                    "\t} \r\n" + //
                                    "}",
                "Código 3: \nmoverBaixo(); \r\n" + //
                                    "moverEsquerda(); \r\n" + //
                                    "moverEsquerda(); \r\n" + //
                                    "moverCima();\r\n" + //
                                    "moverEsquerda();\r\n" + //
                                    "moverEsquerda();\r\n" + //
                                    "moverBaixo();\r\n" + //
                                    "moverBaixo();\r\n" + //
                                    "moverBaixo();\r\n" + //
                                    "moverEsquerda();",
                "Código 4: \n\r\n" + //
                                    "while(naoEstaNoObjetivo()){ \r\n" + //
                                    "\tif(podeMoverBaixo()){\r\n" + //
                                    "\t\tmoverBaixo();\r\n" + //
                                    "\t}else{\t\t\r\n" + //
                                    "\t\tif(podeMoverEsquerda())\r\n" + //
                                    "\t\t\tmoverEsquerda();\r\n" + //
                                    "\t\t}else{\r\n" + //
                                    "\t\t\tmoverCima();\r\n" + //
                                    "\t\t}\r\n" + //
                                    "\t} \r\n" + //
                                    "}"
            }
        };


        
    // Criar os botões dinâmicos de acordo com a fase atual
    RadioButton[] botoes = new RadioButton[4];

    for (int i = 0; i < 4; i++) {
        botoes[i] = new RadioButton(opcoesCodigo[faseAtual][i]);
        botoes[i].setToggleGroup(tg);

    }

        btnExecutar = new Button("Executar Código");
        btnExecutar.setStyle("-fx-background-color:rgb(30, 90, 55); " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8;");
        btnExecutar.setOnAction(e -> executarCodigo());


         // Botão "Jogar Novamente"
         btnFinalizar = new Button("Finalizar");
         btnFinalizar.setStyle("-fx-background-color: #2980b9; " +
             "-fx-text-fill: white; " +
             "-fx-font-weight: bold; " +
             "-fx-font-size: 14px; " +
             "-fx-background-radius: 8;");
        
             btnFinalizar.setVisible(false);
             btnFinalizar.setOnAction(e -> {
            // Obtém a janela atual e a fecha
            Stage stage = (Stage) btnFinalizar.getScene().getWindow();
            stage.close();
        });
        

        btnProxima = new Button("Próxima Pergunta");
        btnProxima.setStyle( "-fx-background-color: #27ae60; " +
        "-fx-text-fill: white; " +
        "-fx-font-weight: bold; " +
        "-fx-font-size: 14px; " +
        "-fx-background-radius: 8;");
        btnProxima.setVisible(false);
        btnProxima.setOnAction(e -> {
            if (faseAtual < 4) {
                faseAtual++;
                
                iniciarFase();
            }
        });

        resultadoLabel = new Label("");
        resultadoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");


        VBox vbox = new VBox(10, legenda, botoes[0], botoes[1], botoes[2], botoes[3], btnExecutar, resultadoLabel, btnProxima, btnFinalizar);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #d9f1ff; -fx-padding: 70;");

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(canvas, legenda);
        StackPane.setAlignment(legenda, Pos.TOP_CENTER); // Alinha a legenda no topo do labirinto
    
        root.setCenter(stackPane); // Adiciona o StackPane com o labirinto e legenda
        root.setRight(vbox);

        Scene scene = new Scene(root, 1000, 1000);
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
                
                if (respostaCorreta) {
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font(20));

                        switch (faseAtual) {
                            case 0:
                                if (maze[row][col] == 4) {
                                    gc.setFill(Color.BLUE);
                                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                                }
                                break;

                            case 1:
                                if (maze[row][col] == 4) {
                                    gc.setFill(Color.BLUE);
                                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                                }
                                break;

                            case 2:
                                if (maze[row][col] == 4) {
                                    gc.setFill(Color.BLUE);
                                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                                }
                                break;

                            case 3:
                                if (maze[row][col] == 4) {
                                    gc.setFill(Color.BLUE);
                                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                                }
                                break;

                            case 4: 
                                if (maze[row][col] == 4) {
                                    gc.setFill(Color.BLUE);
                                    gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                                }
                                break;

                            default:
                                break;
                        }

                    
                }
                if (row == robotRow && col == robotCol) {
                    gc.setFill(Color.RED);
                    gc.fillOval(x + 10, y + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                }
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    
    private void executarCodigo() {

        
        if (respostaSelecionada){
            return;
        }
        
        respostaSelecionada = false;
        direcoes = new int[ROWS][COLS];
    
        if (tg == null || tg.getSelectedToggle() == null) {
            resultadoLabel.setText("Por favor, selecione uma opção.");
            return;  // Impede a execução se nada for selecionado
        }

        RadioButton selected = (RadioButton) tg.getSelectedToggle();
        String textoSelecionado = selected.getText();
    
        // Verifica qual alternativa foi escolhida (1, 2, 3 ou 4)
        int alternativaSelecionada = 0;
        if (textoSelecionado.contains("Código 1")) alternativaSelecionada = 1;
        else if (textoSelecionado.contains("Código 2")) alternativaSelecionada = 2;
        else if (textoSelecionado.contains("Código 3")) alternativaSelecionada = 3;
        else if (textoSelecionado.contains("Código 4")) alternativaSelecionada = 4;
    
        // Compara com a resposta correta da fase atual
        respostaCorreta = (alternativaSelecionada == respostasCorretas[faseAtual]);
    
        resultadoLabel.setText(respostaCorreta ? "Correto!" : "Errado!");

    
        if (faseAtual != 4) {
            btnProxima.setVisible(true);
            
        }
        
        if (faseAtual == ultimaFase) {
            btnFinalizar.setVisible(true);
        }
        
        for (Toggle toggle : tg.getToggles()) {
            ((Node) toggle).setDisable(true);  // Desabilita todas as opções de resposta
        }

        desenharLabirinto();

    }
    

    private boolean podeMover(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS && maze[row][col] != 1;
    }

    
    
}
