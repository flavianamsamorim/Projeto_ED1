package View;

import Model.Arqueiro;
import Model.Guerreiro;
import Model.Mago;
import Model.Personagem;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonagemView {
    private Stage stage;
    private List<Personagem> listaPersonagens = new ArrayList<>();

    public PersonagemView(Stage stage) {
        this.stage = stage;
        configurarLayout();
    }

    private void configurarLayout() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffffff, #e0f7fa);"
                + "-fx-padding: 20;");

        Label lblTitulo = new Label("Criação de Personagens");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do personagem");

        ComboBox<String> cbClasse = new ComboBox<>();
        cbClasse.getItems().addAll("Guerreiro", "Mago", "Arqueiro");
        cbClasse.setValue("Guerreiro");

        // Botão de CRIAR
        Button btnCriar = new Button("Criar Personagem");
        btnCriar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnCriar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String classe = cbClasse.getValue();

            if (!nome.isEmpty()) {
                Personagem p = null;
                switch (classe) {
                    case "Guerreiro":
                        p = new Guerreiro(nome);
                        break;
                    case "Mago":
                        p = new Mago(nome);
                        break;
                    case "Arqueiro":
                        p = new Arqueiro(nome);
                        break;
                }
                if (p != null) {
                    listaPersonagens.add(p);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucesso");
                    alert.setHeaderText(null);
                    alert.setContentText("Personagem criado: " + p.toString());
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "Digite um nome para o personagem.");
                alert.setTitle("Aviso");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        });

        // Botão para ir à tela de Batalha
        Button btnBatalha = new Button("Ir para a Batalha");
        btnBatalha.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnBatalha.setOnAction(e -> new BatalhaView(new Stage(), listaPersonagens));

        // =========================
        // UM ÚNICO BOTÃO PARA SALVAR (BINÁRIO + TEXTO)
        // =========================
        Button btnSalvar = new Button("Salvar Personagens");
        btnSalvar.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnSalvar.setOnAction(e -> salvarPersonagens());

        // =========================
        // UM ÚNICO BOTÃO PARA CARREGAR (SOMENTE BINÁRIO)
        // =========================
        Button btnCarregar = new Button("Carregar Personagens");
        btnCarregar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btnCarregar.setOnAction(e -> carregarPersonagensBinario());

        // Adiciona tudo ao layout
        root.getChildren().addAll(
                lblTitulo,
                txtNome,
                cbClasse,
                btnCriar,
                btnBatalha,
                btnSalvar,
                btnCarregar
        );

        Scene scene = new Scene(root, 450, 400);
        stage.setScene(scene);
        stage.setTitle("Jogo - Classes e Objetos");
        stage.show();
    }

    // ============================================================
    // MÉTODO ÚNICO DE SALVAR: Chama binário e texto
    // ============================================================
    private void salvarPersonagens() {
        // Salva em binário
        salvarPersonagensBinario();
        // Salva em texto
        salvarPersonagensTexto();

        // Mensagem de sucesso unificada (opcional)
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Personagens salvos em binário e texto com sucesso!");
        alert.setTitle("Salvar Personagens");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // ============================================================
    // SALVAR EM BINÁRIO (personagens.dat)
    // ============================================================
    private void salvarPersonagensBinario() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personagens.dat"))) {
            oos.writeObject(listaPersonagens);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Erro ao salvar (binário): " + e.getMessage());
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    // ============================================================
    // SALVAR EM TEXTO (personagens.txt)
    // ============================================================
    private void salvarPersonagensTexto() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("personagens.txt"))) {
            for (Personagem p : listaPersonagens) {
                String tipo;
                if (p instanceof Guerreiro) {
                    tipo = "Guerreiro";
                } else if (p instanceof Mago) {
                    tipo = "Mago";
                } else if (p instanceof Arqueiro) {
                    tipo = "Arqueiro";
                } else {
                    tipo = p.getClass().getSimpleName();
                }
                // tipo;nome;vida;mana;ataque;defesa
                String linha = String.format("%s;%s;%d;%d;%d;%d",
                        tipo, p.getNome(), p.getVida(), p.getMana(), p.getAtaque(), p.getDefesa());
                pw.println(linha);
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Erro ao salvar (texto): " + e.getMessage());
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    // ============================================================
    // CARREGAR APENAS EM BINÁRIO (personagens.dat)
    // ============================================================
    @SuppressWarnings("unchecked")
    private void carregarPersonagensBinario() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("personagens.dat"))) {
            listaPersonagens = (List<Personagem>) ois.readObject();
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "Personagens carregados (binário) com sucesso!");
            alert.setTitle("Carregar Personagens");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Erro ao carregar (binário): " + e.getMessage());
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}
