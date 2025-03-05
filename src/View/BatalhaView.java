package View;

import Model.Personagem;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;


//classe do jogo de classes e objetos
public class BatalhaView {
    private Stage stage;
    private List<Personagem> personagens;
    private ComboBox<Personagem> cbPersonagem1;
    private ComboBox<Personagem> cbPersonagem2;
    private TextArea txtLog;

    public BatalhaView(Stage stage, List<Personagem> personagens) {
        this.stage = stage;
        this.personagens = personagens;
        configurarLayout();
    }

    private void configurarLayout() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #ffe0b2);"
                + "-fx-padding: 20;");

        Label lblTitulo = new Label("Batalha");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        cbPersonagem1 = new ComboBox<>();
        cbPersonagem2 = new ComboBox<>();

        // Preenche combos com personagens
        cbPersonagem1.getItems().addAll(personagens);
        cbPersonagem2.getItems().addAll(personagens);

        Button btnLutar = new Button("Iniciar Batalha");
        btnLutar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");

        btnLutar.setOnAction(e -> iniciarBatalha());

        txtLog = new TextArea();
        txtLog.setEditable(false);
        txtLog.setPrefHeight(200);

        root.getChildren().addAll(lblTitulo, cbPersonagem1, cbPersonagem2, btnLutar, txtLog);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Batalha - Classes e Objetos");
        stage.show();
    }

    private void iniciarBatalha() {
        Personagem p1 = cbPersonagem1.getValue();
        Personagem p2 = cbPersonagem2.getValue();

        if (p1 == null || p2 == null || p1 == p2) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Selecione dois personagens diferentes para a batalha.");
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.showAndWait();
            return;
        }

        txtLog.clear();
        txtLog.appendText("Batalha entre " + p1.getNome() + " e " + p2.getNome() + "!\n");

        // Simples simulação de turnos
        Personagem atacante = p1;
        Personagem defensor = p2;
        while (p1.estaVivo() && p2.estaVivo()) {
            // Atacante ataca
            atacante.atacar(defensor);
            txtLog.appendText(atacante.getNome() + " atacou " + defensor.getNome()
                    + " (HP restante: " + defensor.getVida() + ")\n");

            // Chance de usar habilidade especial
            if (Math.random() < 0.3 && defensor.estaVivo()) {
                atacante.habilidadeEspecial(defensor);
                txtLog.appendText(atacante.getNome() + " usou habilidade especial em "
                        + defensor.getNome() + " (HP restante: " + defensor.getVida() + ")\n");
            }

            if (!defensor.estaVivo()) break;

            // Troca turnos
            Personagem temp = atacante;
            atacante = defensor;
            defensor = temp;
        }

        Personagem vencedor = (p1.estaVivo()) ? p1 : p2;
        txtLog.appendText("\nVencedor: " + vencedor.getNome() + "!\n");
    }
}
