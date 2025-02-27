/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;
import Controller.PilhaController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author Cliente
 */
public class PilhaView {
    private PilhaController controller;
    private Label lblTopo;
    private TextField txtElemento;

    public PilhaView(Stage stage) {
        controller = new PilhaController(this);
        
        Label lblTitulo = new Label("Jogo da Pilha 📦");
        lblTopo = new Label("Topo da Pilha: (vazio)");
        txtElemento = new TextField();
        txtElemento.setPromptText("Digite um valor");

        Button btnEmpilhar = new Button("Empilhar");
        Button btnDesempilhar = new Button("Desempilhar");
        Button btnVoltar = new Button("Voltar");

        btnEmpilhar.setOnAction(e -> controller.empilharElemento(txtElemento.getText()));
        btnDesempilhar.setOnAction(e -> controller.desempilharElemento());
        btnVoltar.setOnAction(e -> stage.close());

        VBox layout = new VBox(10, lblTitulo, txtElemento, btnEmpilhar, btnDesempilhar, lblTopo, btnVoltar);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 400);
        stage.setScene(scene);
        stage.setTitle("Jogo da Pilha");
        stage.show();
    }

    public void atualizarTopo(String valor) {
        lblTopo.setText("Topo da Pilha: " + valor);
    }
}
