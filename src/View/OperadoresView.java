package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class OperadoresView {
    private Stage stage;
    private VBox layout;
    private Label lblPergunta, lblFeedback, lblPontuacao, lblVidas, lblCronometro;
    private Button btnA, btnB, btnC, btnProxima, btnJogarNovamente;
    private List<Map.Entry<String, String[]>> perguntas;
    private String[] respostasCorretas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private int vidas = 3;
    private int tempoRestante = 10;
    private Timeline cronometro;

    public OperadoresView(Stage stage) {
        this.stage = stage;
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        lblPergunta = new Label();
        lblFeedback = new Label();
        lblPontuacao = new Label("Pontuação: 0");
        lblVidas = new Label("Vidas: 3");
        lblCronometro = new Label("Tempo restante: 10s");
        btnA = new Button();
        btnB = new Button();
        btnC = new Button();
        btnProxima = new Button("Próxima Pergunta");
        btnJogarNovamente = new Button("Jogar Novamente");

        btnProxima.setOnAction(e -> carregarPergunta());
        btnJogarNovamente.setOnAction(e -> reiniciarJogo());

        configurarPerguntas();
        carregarPergunta();

        btnA.setOnAction(e -> verificarResposta("A"));
        btnB.setOnAction(e -> verificarResposta("B"));
        btnC.setOnAction(e -> verificarResposta("C"));

        layout.getChildren().addAll(lblPergunta, btnA, btnB, btnC, lblFeedback, lblPontuacao, lblVidas, lblCronometro, btnProxima);
        btnProxima.setVisible(false);
        btnJogarNovamente.setVisible(false);

        Scene scene = new Scene(layout, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Desafio dos Operadores");
        stage.show();
    }

    private void configurarPerguntas() {
        Map<String, String[]> perguntasMap = new LinkedHashMap<>();
        respostasCorretas = new String[]{
            "B", "A", "B", "C", "A", "B", "C", "B", "A", "C",
            "B", "A", "C", "A", "B"
        };

        perguntasMap.put("5 ? 3 = 15", new String[]{"A) +", "B) *", "C) -"});
        perguntasMap.put("10 ? 5 retorna true", new String[]{"A) >", "B) <", "C) =="});
        perguntasMap.put("true ? false retorna false", new String[]{"A) ||", "B) &&", "C) !"});
        perguntasMap.put("8 ? 4 = 2", new String[]{"A) +", "B) *", "C) /"});
        perguntasMap.put("7 ? 3 = 10", new String[]{"A) +", "B) -", "C) *"});
        perguntasMap.put("15 ? 5 = 0", new String[]{"A) /", "B) %", "C) *"});
        perguntasMap.put("5 > 2 ? true : false", new String[]{"A) !", "B) &&", "C) >"});
        perguntasMap.put("6 ? 6 retorna true", new String[]{"A) !=", "B) ==", "C) >"});
        perguntasMap.put("true ? true retorna true", new String[]{"A) &&", "B) ||", "C) !"});
        perguntasMap.put("9 ? 3 = 3", new String[]{"A) *", "B) +", "C) /"});
        perguntasMap.put("20 ? 10 = 2", new String[]{"A) +", "B) /", "C) -"});
        perguntasMap.put("false ? true retorna true", new String[]{"A) ||", "B) &&", "C) !"});
        perguntasMap.put("8 ? 2 = 16", new String[]{"A) +", "B) -", "C) *"});
        perguntasMap.put("4 ? 2 = 2", new String[]{"A) /", "B) *", "C) +"});
        perguntasMap.put("15 ? 3 = 5", new String[]{"A) *", "B) /", "C) -"});

        perguntas = new ArrayList<>(perguntasMap.entrySet());
    }

    private void carregarPergunta() {
        if (perguntaAtual < perguntas.size() && vidas > 0) {
            Map.Entry<String, String[]> perguntaEntry = perguntas.get(perguntaAtual);
            lblPergunta.setText(perguntaEntry.getKey());
            btnA.setText(perguntaEntry.getValue()[0]);
            btnB.setText(perguntaEntry.getValue()[1]);
            btnC.setText(perguntaEntry.getValue()[2]);
            lblFeedback.setText("");
            btnProxima.setVisible(false);
            iniciarCronometro();
        } else {
            lblPergunta.setText("Fim de jogo! Sua pontuação: " + pontuacao);
            layout.getChildren().removeAll(btnA, btnB, btnC, btnProxima);
            layout.getChildren().add(btnJogarNovamente);
            btnJogarNovamente.setVisible(true);
        }
    }

    private void verificarResposta(String respostaEscolhida) {
        cronometro.stop();
        if (respostaEscolhida.equals(respostasCorretas[perguntaAtual])) {
            lblFeedback.setText("Resposta correta! 🎉");
            pontuacao++;
            lblPontuacao.setText("Pontuação: " + pontuacao);
        } else {
            lblFeedback.setText("Resposta errada! ❌");
            vidas--;
            lblVidas.setText("Vidas: " + vidas);
        }
        perguntaAtual++;
        btnProxima.setVisible(true);
    }

    private void iniciarCronometro() {
        tempoRestante = 10;
        lblCronometro.setText("Tempo restante: 10s");
        cronometro = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tempoRestante--;
            lblCronometro.setText("Tempo restante: " + tempoRestante + "s");
            if (tempoRestante <= 0) {
                cronometro.stop();
                verificarResposta("");
            }
        }));
        cronometro.setCycleCount(10);
        cronometro.play();
    }

    private void reiniciarJogo() {
        perguntaAtual = 0;
        pontuacao = 0;
        vidas = 3;
        layout.getChildren().remove(btnJogarNovamente);
        layout.getChildren().addAll(btnA, btnB, btnC, btnProxima);
        carregarPergunta();
    }
}
