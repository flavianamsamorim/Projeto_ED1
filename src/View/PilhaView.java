/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.PilhaController;
import EstruturasDeDados.Pilha.Pilha;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PilhaView {
    private PilhaController controller;
    private Label lblTitulo, lblTopo, lblContadorEmpilhadas, lblContadorDesempilhadas, lblTempo, lblPontos, lblMensagemFinal;
    private VBox layout, pilhaContainer;
    private Pilha<VBox> pilhaPedras; // Alterado para Stack para garantir LIFO
    private int tempoRestante = 30;
    private boolean tarefaConcluida = false;
    private long tempoInicio, tempoFinal;

    public PilhaView(Stage stage) {
        controller = new PilhaController(this);
        pilhaPedras = new Pilha<>(5);

        lblTitulo = new Label("Objetivo: Empilhe e Desempilhe 5 pedras o mais rápido!");
        lblTopo = new Label("Topo da Pilha: (vazio)");
        lblContadorEmpilhadas = new Label("Pedras Empilhadas: 0");
        lblContadorDesempilhadas = new Label("Pedras Desempilhadas: 0");
        lblTempo = new Label("Tempo: 30s");
        lblPontos = new Label("Pontos: 0");
        lblMensagemFinal = new Label(""); // Mensagem final de sucesso ou fracasso

        Button btnEmpilhar = new Button("Empilhar Pedra");
        Button btnDesempilhar = new Button("Desempilhar Pedra");
        Button btnVoltar = new Button("Voltar");

        btnEmpilhar.setOnAction(e -> controller.empilharElemento());
        btnDesempilhar.setOnAction(e -> controller.desempilharElemento());
        btnVoltar.setOnAction(e -> stage.close());

        pilhaContainer = new VBox();
        pilhaContainer.setAlignment(Pos.BOTTOM_CENTER);

        layout = new VBox(10, lblTitulo, lblTopo, lblContadorEmpilhadas, lblContadorDesempilhadas, lblTempo, lblPontos, pilhaContainer, btnEmpilhar, btnDesempilhar, btnVoltar, lblMensagemFinal);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 500);
        stage.setScene(scene);
        stage.setTitle("Pilha de Pedras");
        stage.show();

        iniciarContagemRegressiva();
    }

    public void atualizarTopo(String topo) {
        lblTopo.setText("Topo da Pilha: " + topo);
    }

    public void atualizarContadores(int empilhadas, int desempilhadas, int pontos) {
        lblContadorEmpilhadas.setText("Pedras Empilhadas: " + empilhadas);
        lblContadorDesempilhadas.setText("Pedras Desempilhadas: " + desempilhadas);
        lblPontos.setText("Pontos: " + pontos);

        // Se todas as pedras foram desempilhadas, limpar o topo
        if (empilhadas == desempilhadas) {
            lblTopo.setText("Topo da Pilha: (vazio)");
        }

        // Se conseguiu desempilhar todas as 5 pedras, exibe a mensagem de sucesso
        if (desempilhadas == 5 && !tarefaConcluida) {
            tarefaConcluida = true;
            tempoFinal = System.currentTimeMillis();
            long tempoGasto = 30 - tempoRestante;
            exibirMensagemFinal(true, tempoGasto);
        }
    }

    public void atualizarTempo(int tempo) {
    tempoRestante = tempo;
    Platform.runLater(() -> {
        if (tempo > 0) {
            lblTempo.setText("Tempo: " + tempo + "s");
        } else {
            lblTempo.setText("Tempo Esgotado!");
            exibirMensagemFinal(false, 0);
        }
    });
}

    public void iniciarContagemRegressiva() {
    tempoInicio = System.currentTimeMillis();
    new Thread(() -> {
        while (tempoRestante > 0 && !tarefaConcluida) {
            try {
                Thread.sleep(1000);
                tempoRestante--;
                Platform.runLater(() -> atualizarTempo(tempoRestante));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
}

    public void animarEmpilhar(String elemento) {
    Platform.runLater(() -> {
        if (pilhaPedras.size() < 5) {
            ImageView pedraImg = new ImageView(new Image("file:src/imagens/folder/pedra.jpg"));
            pedraImg.setFitWidth(50);
            pedraImg.setFitHeight(50);

            Label legenda = new Label(elemento);
            VBox pedraComLegenda = new VBox(pedraImg, legenda);
            pedraComLegenda.setAlignment(Pos.CENTER);

            pilhaPedras.push(pedraComLegenda);
            pilhaContainer.getChildren().add(0, pedraComLegenda);

            atualizarTopo(elemento);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), pedraComLegenda);
            transition.setFromY(30);
            transition.setToY(0);
            transition.setCycleCount(1);
            transition.play();
        }
    });
}

    public void animarDesempilhar() {
    Platform.runLater(() -> {
        if (!pilhaPedras.isEmpty()) {
            VBox pedraComLegenda = pilhaPedras.pop();
            pilhaContainer.getChildren().remove(pedraComLegenda);

            if (!pilhaPedras.isEmpty()) {
                Label novaLegenda = (Label) pilhaPedras.peek().getChildren().get(1);
                atualizarTopo(novaLegenda.getText());
            } else {
                atualizarTopo("(vazio)");
            }
        }
    });
}

    private void exibirMensagemFinal(boolean sucesso, long tempoGasto) {
        if (sucesso) {
            lblMensagemFinal.setText("🏆 Parabéns! Você concluiu a tarefa em " + tempoGasto + " segundos!");
        } else {
            lblMensagemFinal.setText("❌ Não alcançou o objetivo! Tente novamente.");
        }
    }
}