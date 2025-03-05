/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.PilhaController;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cliente
 */
public class PilhaView {
    private PilhaController controller;
    private Label lblTopo, lblContadorEmpilhadas, lblContadorDesempilhadas, lblTempo, lblPontos;
    private VBox layout;
    private List<Button> botoesPedras;
    private long tempoInicial;

    public PilhaView(Stage stage) {
        controller = new PilhaController(this);
        botoesPedras = new ArrayList<>();

        lblTopo = new Label("Topo da Pilha: (vazio)");
        lblContadorEmpilhadas = new Label("Pedras Empilhadas: 0");
        lblContadorDesempilhadas = new Label("Pedras Desempilhadas: 0");
        lblTempo = new Label("Tempo: 0");
        lblPontos = new Label("Pontos: 0");

        Button btnEmpilhar = new Button("Empilhar Pedra");
        Button btnDesempilhar = new Button("Desempilhar Pedra");
        Button btnCongelarTempo = new Button("Congelar Tempo");
        Button btnVoltar = new Button("Voltar");

        btnEmpilhar.setOnAction(e -> controller.empilharElemento());
        btnDesempilhar.setOnAction(e -> controller.desempilharElemento());
        btnCongelarTempo.setOnAction(e -> controller.congelarTempo());
        btnVoltar.setOnAction(e -> stage.close());

        layout = new VBox(10, lblTopo, lblContadorEmpilhadas, lblContadorDesempilhadas, lblTempo, lblPontos, btnEmpilhar, btnDesempilhar, btnCongelarTempo, btnVoltar);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 400);
        stage.setScene(scene);
        stage.setTitle("A Caverna das Pilhas");
        stage.show();
    }

    public void atualizarTopo(String topo) {
        lblTopo.setText("Topo da Pilha: " + topo);
    }

    public void atualizarContadores(int empilhadas, int desempilhadas, int pontos) {
        lblContadorEmpilhadas.setText("Pedras Empilhadas: " + empilhadas);
        lblContadorDesempilhadas.setText("Pedras Desempilhadas: " + desempilhadas);
        lblPontos.setText("Pontos: " + pontos);
    }

    public void atualizarTempo(long tempo) {
        lblTempo.setText("Tempo: " + tempo / 1000 + "s");
    }

    public void animarEmpilhar(String elemento) {
        Button btnPedra = new Button(elemento);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), btnPedra);
        transition.setByY(-30);
        transition.setCycleCount(1);
        transition.play();

        layout.getChildren().add(btnPedra);
    }

    public void animarDesempilhar() {
        if (!botoesPedras.isEmpty()) {
            Button btnPedra = botoesPedras.remove(botoesPedras.size() - 1);
            layout.getChildren().remove(btnPedra);

            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), btnPedra);
            transition.setByY(30);
            transition.setCycleCount(1);
            transition.play();
        }
    }

    public void exibirCongelarTempo() {
        lblTempo.setText("Tempo Congelado!");
    }

    public void esconderCongelarTempo() {
        lblTempo.setText("Tempo: 0");
    }
}