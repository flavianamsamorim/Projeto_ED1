package View.TiposBasicos;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

import EstruturasDeDados.Lista.Lista;

public class QuizView {
    private Stage stage;
    private VBox layout;
    private Label lblPergunta, lblFeedback, lblRanking, titulo;
    private Button btnA, btnB, btnC, btnProxima, btnJogarNovamente;
    private Lista<Map.Entry<String, String[]>> perguntasOrdenadas;
    private String[] respostasCorretas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private List<Integer> ranking = new ArrayList<>();
    private boolean respostaSelecionada = false;

    public QuizView(Stage stage) {
        this.stage = stage;
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #d9f1ff, #ffffff);");

        lblPergunta = new Label();
        lblPergunta.setWrapText(true);
        lblPergunta.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        lblPergunta.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-wrap-text: true;");

        lblFeedback = new Label();
        lblFeedback.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        
        lblRanking = new Label("Ranking:");
        lblRanking.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        titulo = new Label("Quiz sobre Tipos Básicos");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        btnA = new Button();
        btnB = new Button();
        btnC = new Button();
        
        btnProxima = new Button("Próxima Pergunta");
        btnProxima.setStyle( "-fx-background-color: #27ae60; " +
        "-fx-text-fill: white; " +
        "-fx-font-weight: bold; " +
        "-fx-font-size: 14px; " +
        "-fx-background-radius: 8;");

        btnJogarNovamente = new Button("Jogar Novamente");
        btnJogarNovamente.setStyle("-fx-background-color: #e67e22; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 14px; " +
            "-fx-background-radius: 8;");

        btnProxima.setOnAction(e -> carregarPergunta());
        btnJogarNovamente.setOnAction(e -> reiniciarQuiz());

        configurarPerguntas();
        carregarPergunta();
        configurarEstiloBotao(btnA);
        configurarEstiloBotao(btnB);
        configurarEstiloBotao(btnC);

        btnA.setOnAction(e -> verificarResposta("A"));
        btnB.setOnAction(e -> verificarResposta("B"));
        btnC.setOnAction(e -> verificarResposta("C"));

        layout.getChildren().addAll(titulo, lblPergunta, btnA, btnB, btnC, lblFeedback, btnProxima, lblRanking);
        btnProxima.setVisible(false);
        btnJogarNovamente.setVisible(false);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Quiz sobre Tipos Básicos");
        stage.show();
    }

    private void configurarPerguntas() {
        Map<String, String[]> perguntas = new LinkedHashMap<>();
        respostasCorretas = new String[]{
            "A", "B", "B", "A", "A", "B", "A", "B", "B", "B",
            "B", "C", "B", "B", "B", "B"
        };

        perguntas.put("Pergunta (1/15)- Qual comando usamos para exibir um texto no console?", new String[]{"A) System.out.print()", "B) Scanner.nextLine()", "C) System.in.read()"});
        perguntas.put("Pergunta (2/15)- Qual classe usamos para ler entrada do usuário via teclado?", new String[]{"A) BufferedReader", "B) Scanner", "C) FileReader"});
        perguntas.put("Pergunta (3/15)- Qual comando imprime uma linha pulando para a próxima automaticamente?", new String[]{"A) System.out.print()", "B) System.out.println()", "C) System.out.write()"});
        perguntas.put("Pergunta (4/15)- Qual método do Scanner lê um número inteiro?", new String[]{"A) nextInt()", "B) nextLine()", "C) nextDouble()"});
        perguntas.put("Pergunta (5/15)- Qual é o tipo de dado para armazenar ‘A’?", new String[]{"A) char", "B) String", "C) int"});
        perguntas.put("Pergunta (6/15)- Qual tipo usamos para armazenar números com casas decimais?", new String[]{"A) int", "B) double", "C) boolean"});
        perguntas.put("Pergunta (7/15)- Qual é o valor padrão de um boolean em Java?", new String[]{"A) false", "B) true", "C) null"});
        perguntas.put("Pergunta (8/15)- Qual tipo usamos para armazenar textos?", new String[]{"A) char", "B) String", "C) byte"});
        perguntas.put("Pergunta (9/15)- O que este operador faz: ==?", new String[]{"A) Atribuição", "B) Comparação", "C) Incremento"});
        perguntas.put("Pergunta (10/15)- Qual operador usamos para 'E lógico' em Java?", new String[]{"A) ||", "B) &&", "C) !"});
        perguntas.put("Pergunta (11/15)- Qual será o resultado da expressão 5 + 3 * 2?", new String[]{"A) 16", "B) 11", "C) 13"});
        perguntas.put("Pergunta (12/15)- Qual comando usamos para repetir um bloco de código enquanto uma condição for verdadeira?", new String[]{"A) if", "B) for", "C) while"});
        perguntas.put("Pergunta (13/15)- Qual é a estrutura correta de um if em Java?", new String[]{"A) if {condicao} {}", "B) if (condicao) {}", "C) if [condicao] {}"});
        perguntas.put("Pergunta (14/15)- Qual estrutura de repetição é mais indicada quando sabemos o número exato de iterações?", new String[]{"A) while", "B) for", "C) do-while"});
        perguntas.put("Pergunta (15/15)- Qual índice tem o primeiro elemento de um array em Java?", new String[]{"A) 1", "B) 0", "C) -1"});
               
        perguntasOrdenadas = new Lista<>();
        for (Map.Entry<String, String[]> entry : perguntas.entrySet()) {
            perguntasOrdenadas.addLast(entry);
        }

    }

    private void carregarPergunta() {
        respostaSelecionada = false;
        if (perguntaAtual < perguntasOrdenadas.getSize()) {
            Map.Entry<String, String[]> perguntaEntry = perguntasOrdenadas.get(perguntaAtual);
            lblPergunta.setText(perguntaEntry.getKey());
            btnA.setText(perguntaEntry.getValue()[0]);
            btnB.setText(perguntaEntry.getValue()[1]);
            btnC.setText(perguntaEntry.getValue()[2]);
            lblFeedback.setText("");
            btnProxima.setVisible(false);
        } else {
            ranking.add(pontuacao);
            ordenarRanking();
            lblPergunta.setText("Parabéns! Você concluiu o quiz!");
            layout.getChildren().removeAll(btnA, btnB, btnC, btnProxima);
            layout.getChildren().add(btnJogarNovamente);
            btnJogarNovamente.setVisible(true);
            exibirRanking();
        }
    }

    private void verificarResposta(String respostaEscolhida) {
        if (!respostaSelecionada) {
            respostaSelecionada = true;
            if (respostaEscolhida.equals(respostasCorretas[perguntaAtual])) {
                lblFeedback.setText("Resposta correta! 🎉");
                pontuacao++;
            } else {
                lblFeedback.setText("Resposta errada! ❌");
            }
            perguntaAtual++;
            btnProxima.setVisible(true);
        }
    }

    private void reiniciarQuiz() {
        perguntaAtual = 0;
        pontuacao = 0;
        layout.getChildren().remove(btnJogarNovamente);
        layout.getChildren().addAll(btnA, btnB, btnC, btnProxima);
        
        btnA.setDisable(false);
        btnB.setDisable(false);
        btnC.setDisable(false);
        
        carregarPergunta();
    }
    
    private void ordenarRanking() {
        ranking.sort(Collections.reverseOrder());
    }
    
    private void exibirRanking() {
        StringBuilder sb = new StringBuilder("Ranking:\n");
        for (int i = 0; i < ranking.size(); i++) {
            sb.append((i + 1)).append("º Lugar: ").append(ranking.get(i)).append(" pontos\n");
        }
        lblRanking.setText(sb.toString());
    }
    
    private void configurarEstiloBotao(Button btn) {
        btn.setStyle(
            "-fx-background-color: #3498db; " +   // Cor de fundo (azul)
            "-fx-text-fill: white; " +           // Cor do texto (branca)
            "-fx-font-weight: bold; " +          // Negrito
            "-fx-font-size: 14px; " +            // Tamanho da fonte
            "-fx-background-radius: 8; " +       // Arredonda as bordas
            "-fx-padding: 8 16 8 16;"            // Espaçamento interno
        );
    }
}
