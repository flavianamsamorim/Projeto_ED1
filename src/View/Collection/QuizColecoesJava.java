package View.Collection;

import EstruturasDeDados.Lista.Lista;  // Importe sua implementação de Lista
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class QuizColecoesJava {
    private Stage stage;
    private VBox layout;
    private Label lblPergunta, lblFeedback, lblRanking, titulo;
    private Button btnA, btnB, btnC, btnProxima, btnJogarNovamente;
    private Lista<Map.Entry<String, String[]>> perguntasOrdenadas;  // Usando sua Lista personalizada
    private String[] respostasCorretas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private Lista<Integer> ranking = new Lista<>();  // Usando sua Lista personalizada para o ranking
    private boolean respostaSelecionada = false;

    public QuizColecoesJava(Stage stage) {
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
        
        titulo = new Label("Quiz sobre Coleções");
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
        stage.setTitle("Quiz sobre Coleções");
        stage.show();
    }

    private void configurarPerguntas() {
        Map<String, String[]> perguntas = new LinkedHashMap<>();
        respostasCorretas = new String[] {
            "A", "B", "A", "B", "C", "A", "B", "A", "B", "C",
            "A", "B", "C", "A", "B", "A"
        };

        perguntas.put("Pergunta (1/15)- Qual coleção do Java não permite elementos duplicados?", new String[]{"A) Set", "B) List", "C) Queue"});
        perguntas.put("Pergunta (2/15)- Qual coleção do Java armazena elementos em pares chave-valor?", new String[]{"A) List", "B) Map", "C) Set"});
        perguntas.put("Pergunta (3/15)- Qual implementação de Queue é mais eficiente para inserções e remoções?", new String[]{"A) LinkedList", "B) ArrayList", "C) PriorityQueue"});
        perguntas.put("Pergunta (4/15)- Qual coleção permite acessar elementos de forma sequencial?", new String[]{"A) Set", "B) List", "C) Map"});
        perguntas.put("Pergunta (5/15)- Qual classe representa a implementação de uma lista em Java?", new String[]{"A) ArrayList", "B) HashSet", "C) TreeMap"});
        perguntas.put("Pergunta (6/15)- Qual coleção armazena elementos sem uma ordem específica?", new String[]{"A) Set", "B) List", "C) Map"});
        perguntas.put("Pergunta (7/15)- Qual estrutura de dados Java é mais eficiente para representar uma pilha?", new String[]{"A) Stack", "B) LinkedList", "C) PriorityQueue"});
        perguntas.put("Pergunta (8/15)- Qual coleção do Java é mais indicada para representar uma fila?", new String[]{"A) Queue", "B) List", "C) Set"});
        perguntas.put("Pergunta (9/15)- Qual estrutura de dados Java é adequada para armazenar valores associados a chaves?", new String[]{"A) TreeMap", "B) ArrayList", "C) HashSet"});
        perguntas.put("Pergunta (10/15)- Como um HashSet armazena os elementos?", new String[]{"A) Em uma tabela hash", "B) Em uma lista encadeada", "C) Em um vetor"});
        perguntas.put("Pergunta (11/15)- Qual método da classe List permite adicionar elementos?", new String[]{"A) add()", "B) put()", "C) offer()"});
        perguntas.put("Pergunta (12/15)- Qual implementação de Map é mais eficiente para buscas rápidas?", new String[]{"A) TreeMap", "B) HashMap", "C) LinkedHashMap"});
        perguntas.put("Pergunta (13/15)- Em qual coleção podemos garantir a ordem de inserção dos elementos?", new String[]{"A) HashSet", "B) LinkedHashSet", "C) TreeSet"});
        perguntas.put("Pergunta (14/15)- Qual estrutura de dados armazena elementos de forma ordenada?", new String[]{"A) HashMap", "B) TreeSet", "C) LinkedList"});
        perguntas.put("Pergunta (15/15)- Em qual coleção não podemos acessar elementos diretamente via índice?", new String[]{"A) List", "B) Set", "C) Queue"});
        
        perguntasOrdenadas = new Lista<>();  // Inicializando a sua lista personalizada
        for (Map.Entry<String, String[]> entry : perguntas.entrySet()) {
            perguntasOrdenadas.addLast(entry);  // Usando o método da sua lista para adicionar perguntas
        }
    }

    private void carregarPergunta() {
        respostaSelecionada = false;
        if (perguntaAtual < perguntasOrdenadas.getSize()) {
            Map.Entry<String, String[]> perguntaEntry = perguntasOrdenadas.get(perguntaAtual);  // Usando o método da sua lista
            lblPergunta.setText(perguntaEntry.getKey());
            btnA.setText(perguntaEntry.getValue()[0]);
            btnB.setText(perguntaEntry.getValue()[1]);
            btnC.setText(perguntaEntry.getValue()[2]);
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
