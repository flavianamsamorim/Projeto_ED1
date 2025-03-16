package View.Generic;

import EstruturasDeDados.Lista.Lista;  // Importe sua implementação de Lista
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class QuizGeneric<T> {
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

    public QuizGeneric(Stage stage) {
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
        
        titulo = new Label("Quiz sobre Generics");
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
        stage.setTitle("Quiz sobre Generics");
        stage.show();
    }

    private void configurarPerguntas() {
        Map<String, T[]> perguntas = new LinkedHashMap<>();
        respostasCorretas = (T[]) new Object[] {
            (T) "A", (T) "B", (T) "A", (T) "B", (T) "C", (T) "A", (T) "B", (T) "A", (T) "B", (T) "C",
            (T) "A", (T) "B", (T) "C", (T) "A", (T) "B", (T) "A"
        };

        perguntas.put("1 - O que são Generics em Java?", (T[]) new String[] {"A) Uma forma de criar métodos e classes com tipos genéricos", "B) Um tipo específico de coleção", "C) Uma classe que pode ser instanciada com tipos específicos"});
        perguntas.put("2 - Qual é a principal vantagem do uso de Generics?", (T[]) new String[] {"A) Evitar conversões de tipo (casting)", "B) Aumentar o número de classes disponíveis", "C) Tornar o código mais complexo"});
        perguntas.put("3 - Em Java, qual sintaxe é usada para declarar um método genérico?", (T[]) new String[] {"A) public <T> void metodo(T parametro)", "B) public void metodo<T>(T parametro)", "C) public void metodo(T parametro<T>)"});
        perguntas.put("4 - Quando devemos usar Generics em Java?", (T[]) new String[] {"A) Quando o tipo de dados não é conhecido durante a escrita do código", "B) Quando se quer evitar o uso de interfaces", "C) Quando se precisa de múltiplos tipos de classes"});
        perguntas.put("5 - O que o símbolo `T` normalmente representa em Generics?", (T[]) new String[] {"A) Um tipo genérico", "B) Uma string", "C) Um tipo primitivo"});
        perguntas.put("6 - Como o compilador trata os tipos genéricos durante a execução?", (T[]) new String[] {"A) Com o uso de erasure (apagamento de tipo)", "B) Ele cria novas instâncias para cada tipo", "C) Ele gera diferentes classes para cada tipo especificado"});
        perguntas.put("7 - Qual das opções abaixo é um exemplo de classe genérica?", (T[]) new String[] {"A) public class Caixa<T> {}", "B) public class Caixa {}", "C) public class Caixa{T} "});
        perguntas.put("8 - O que é 'bounded type' em Generics?", (T[]) new String[] {"A) Um tipo genérico restrito a uma subclasse de um tipo específico", "B) Um tipo que aceita apenas valores primitivos", "C) Um tipo que pode ser qualquer classe"});
        perguntas.put("9 - Qual a função do operador `? extends T` em Generics?", (T[]) new String[] {"A) Restringe o tipo para uma classe que estenda T", "B) Aceita qualquer tipo de dado", "C) Especifica um tipo primitivo"});
        perguntas.put("10 - Qual o erro mais comum ao usar Generics?", (T[]) new String[] {"A) Tentativa de usar tipos incompatíveis", "B) Não especificar o tipo de dado", "C) Esquecer de implementar interfaces genéricas"});
        perguntas.put("11 - O que acontece se tentarmos adicionar um tipo errado em uma coleção genérica?", (T[]) new String[] {"A) O código não compila", "B) O código compila, mas lança um erro em tempo de execução", "C) O código compila, mas ignora o valor"});
        perguntas.put("12 - Em Java, qual é a principal diferença entre `List<T>` e `Set<T>`?", (T[]) new String[] {"A) `List<T>` permite elementos duplicados, enquanto `Set<T>` não permite", "B) `Set<T>` permite elementos duplicados, enquanto `List<T>` não permite", "C) Não há diferença"});
        perguntas.put("13 - Como é possível garantir que uma classe genérica só possa aceitar tipos específicos em Java?", (T[]) new String[] {"A) Usando limites com `extends`", "B) Declarando o tipo como `Object`", "C) Usando `?` sem qualquer limite"});
        perguntas.put("14 - O que o tipo `T` representa em uma classe genérica?", (T[]) new String[] {"A) Qualquer tipo de dado", "B) Um tipo fixo de dado", "C) Um tipo primitivo específico"});
        perguntas.put("15 - O que é `erasure` (apagamento de tipo) em Java?", (T[]) new String[] {"A) O processo de remover o tipo genérico após a compilação", "B) O processo de verificar tipos em tempo de execução", "C) O processo de compilar o código"});
        
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
