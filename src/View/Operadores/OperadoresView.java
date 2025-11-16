package View.Operadores;

import EstruturasDeDados.Lista.Lista;  // Importe sua implementação de Lista
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class OperadoresView<T> {
    private Stage stage;
    private VBox layout;
    private Label lblPergunta, lblFeedback, lblRanking, titulo;
    private Button btnA, btnB, btnC, btnProxima, btnJogarNovamente;
    private Lista<Map.Entry<String, T[]>> perguntasOrdenadas;  // Usando sua Lista personalizada
    private T[] respostasCorretas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private Lista<Integer> ranking = new Lista<>();  // Usando sua Lista personalizada para o ranking
    private boolean respostaSelecionada = false;

    public OperadoresView(Stage stage) {
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
        
        titulo = new Label("Quiz sobre Operadores");
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
        stage.setTitle("Quiz sobre Operadores");
        stage.show();
    }

    private void configurarPerguntas() {
        Map<String, T[]> perguntas = new LinkedHashMap<>();
        respostasCorretas = (T[]) new Object[] {
            (T) "B", (T) "A", (T) "B", (T) "C", (T) "A", (T) "B", (T) "C", (T) "B", (T) "A", (T) "C",
            (T) "B", (T) "A", (T) "C", (T) "A", (T) "B"
        };

        perguntas.put("Pergunta (1/15)- Qual operador colocar no lugar da interrogação: 5 ? 3 = 15", (T[]) new String[]{"A) +", "B) *", "C) -"});
        perguntas.put("Pergunta (2/15)- Qual operador colocar no lugar da interrogação: 10 ? 5 retorna true", (T[]) new String[]{"A) >", "B) <", "C) =="});
        perguntas.put("Pergunta (3/15)- Qual operador colocar no lugar da interrogação: true ? false retorna false", (T[]) new String[]{"A) ||", "B) &&", "C) !"});
        perguntas.put("Pergunta (4/15)- Qual operador colocar no lugar da interrogação: 8 ? 4 = 2", (T[]) new String[]{"A) +", "B) *", "C) /"});
        perguntas.put("Pergunta (5/15)- Qual operador colocar no lugar da interrogação: 7 ? 3 = 10", (T[]) new String[]{"A) +", "B) -", "C) *"});
        perguntas.put("Pergunta (6/15)- Qual operador colocar no lugar da interrogação: 15 ? 5 = 0", (T[]) new String[]{"A) /", "B) %", "C) *"});
        perguntas.put("Pergunta (7/15)- Qual operador colocar no lugar da interrogação: 5 > 2 ? true : false", (T[]) new String[]{"A) !", "B) &&", "C) >"});
        perguntas.put("Pergunta (8/15)- Qual operador colocar no lugar da interrogação: 6 ? 6 retorna true", (T[]) new String[]{"A) !=", "B) ==", "C) >"});
        perguntas.put("Pergunta (9/15)- Qual operador colocar no lugar da interrogação: true ? true retorna true", (T[]) new String[]{"A) &&", "B) ||", "C) !"});
        perguntas.put("Pergunta (10/15)- Qual operador colocar no lugar da interrogação: 9 ? 3 = 3", (T[]) new String[]{"A) *", "B) +", "C) /"});
        perguntas.put("Pergunta (11/15)- Qual operador colocar no lugar da interrogação: 20 ? 10 = 2", (T[]) new String[]{"A) +", "B) /", "C) -"});
        perguntas.put("Pergunta (12/15)- Qual operador colocar no lugar da interrogação: false ? true retorna true", (T[]) new String[]{"A) ||", "B) &&", "C) !"});
        perguntas.put("Pergunta (13/15)- Qual operador colocar no lugar da interrogação: 8 ? 2 = 16", (T[]) new String[]{"A) +", "B) -", "C) *"});
        perguntas.put("Pergunta (14/15)- Qual operador colocar no lugar da interrogação: 4 ? 2 = 2", (T[]) new String[]{"A) /", "B) *", "C) +"});
        perguntas.put("Pergunta (15/15)- Qual operador colocar no lugar da interrogação: 15 ? 3 = 5", (T[]) new String[]{"A) *", "B) /", "C) -"});

        
        perguntasOrdenadas = new Lista<>();  // Inicializando a sua lista personalizada
        for (Map.Entry<String, T[]> entry : perguntas.entrySet()) {
            perguntasOrdenadas.addLast(entry);  // Usando o método da sua lista para adicionar perguntas
        }
    }

    private void carregarPergunta() {
        respostaSelecionada = false;
        if (perguntaAtual < perguntasOrdenadas.getSize()) {
            Map.Entry<String, T[]> perguntaEntry = perguntasOrdenadas.get(perguntaAtual);  // Usando o método da sua lista
            lblPergunta.setText(perguntaEntry.getKey());
            btnA.setText((String) perguntaEntry.getValue()[0]);
            btnB.setText((String) perguntaEntry.getValue()[1]);
            btnC.setText((String) perguntaEntry.getValue()[2]);
            lblFeedback.setText("");
            btnProxima.setVisible(false);
        } else {
            ranking.addLast(pontuacao);  // Usando o método da sua lista para adicionar pontuação
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
        bubbleSort(ranking);  // Chama o BubbleSort na lista de ranking
    }
    
    private void bubbleSort(Lista<Integer> lista) {
        int n = lista.getSize();
        boolean trocou;
    
        for (int i = 0; i < n - 1; i++) {
            trocou = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (lista.get(j) < lista.get(j + 1)) {  // Ordena de forma decrescente
                    // Troca os elementos diretamente na lista
                    int temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                    trocou = true;
                }
            }
    
            // Se nenhum elemento foi trocado, a lista já está ordenada
            if (!trocou) {
                break;
            }
        }
    }
    
    
    private void exibirRanking() {
        StringBuilder sb = new StringBuilder("Ranking:\n");
        for (int i = 0; i < ranking.getSize(); i++) {
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
