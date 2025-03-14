/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ComplexAlgoController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Cliente
 */
public class ComplexAlgoView {
    private Stage stage;
    private VBox layout;
    private Label lblPergunta, lblFeedback;
    private Button btnA, btnB, btnC, btnProxima;
    private ComplexAlgoController controller;

    public ComplexAlgoView(Stage stage) {
        controller = new ComplexAlgoController(this);
        layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #e0f7fa);"
                + "-fx-padding: 20;");

        lblPergunta = new Label();
        lblFeedback = new Label();
        btnA = new Button();
        btnB = new Button();
        btnC = new Button();
        btnProxima = new Button("Próxima Pergunta ->");
        
        // Ações dos botões
        btnA.setOnAction(e -> controller.verificarResposta("A"));
        btnB.setOnAction(e -> controller.verificarResposta("B"));
        btnC.setOnAction(e -> controller.verificarResposta("C"));
        btnProxima.setOnAction(e -> controller.carregarPergunta());

        // Adicionando os elementos à cena
        layout.getChildren().addAll(lblPergunta, btnA, btnB, btnC, lblFeedback, btnProxima);
        Scene scene = new Scene(layout, 450, 400);
        stage.setScene(scene);
        stage.setTitle("Quiz Complexidade de Algoritmos");
        stage.show();
        
        // Carrega a primeira pergunta
        controller.carregarPergunta();
    }

    // Atualiza a pergunta e suas opções
    public void atualizarPergunta(String pergunta, String opcaoA, String opcaoB, String opcaoC) {
        lblPergunta.setText(pergunta);
        btnA.setText(opcaoA);
        btnB.setText(opcaoB);
        btnC.setText(opcaoC);
    }
    
    // Desativa os botões de resposta após o usuário escolher uma opção
    public void desativarOpcoes() {
        btnA.setDisable(true);
        btnB.setDisable(true);
        btnC.setDisable(true);
    }

    // Habilita os botões de resposta para a próxima pergunta
    public void ativarOpcoes() {
        btnA.setDisable(false);
        btnB.setDisable(false);
        btnC.setDisable(false);
    }

    // Mostra o feedback após a resposta
    public void mostrarFeedback(String mensagem) {
        lblFeedback.setText(mensagem);
    }

    // Limpa o feedback para a próxima pergunta
    public void limparFeedback() {
        lblFeedback.setText("");
    }
}