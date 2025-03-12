package View.Arquivos;

import Model.ClassesObjetos.Arqueiro;
import Model.ClassesObjetos.Guerreiro;
import Model.ClassesObjetos.Mago;
import Model.ClassesObjetos.Personagem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivosView {
    private Stage stage;
    private List<Personagem> listaPersonagens = new ArrayList<>();
    
    private ListView<Personagem> listView;  // Mostra os personagens
    private TextField txtVida, txtMana, txtAtaque, txtDefesa; // Edita atributos

    public ArquivosView(Stage stage) {
        this.stage = stage;
        configurarLayout();
        carregarPersonagensTexto(); // Carrega personagens do arquivo texto (pode adaptar para binário se quiser)
    }

    private void configurarLayout() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #d9f1ff;");

        Label lblTitulo = new Label("Jogo de Arquivos - Edição de Personagens");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ListView para exibir personagens
        listView = new ListView<>();
        listView.setPrefHeight(150);

        // Quando o usuário seleciona um personagem, exibimos os atributos
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                preencherCampos(newVal);
            }
        });

        // GridPane para editar atributos
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // Rótulos
        Label lblVida = new Label("Vida:");
        lblVida.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label lblMana = new Label("Mana:");
        lblMana.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label lblAtaque = new Label("Ataque:");
        lblAtaque.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label lblDefesa = new Label("Defesa:");
        lblDefesa.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Campos de texto
        txtVida = new TextField();
        txtMana = new TextField();
        txtAtaque = new TextField();
        txtDefesa = new TextField();

        // Adiciona ao grid
        grid.add(lblVida,   0, 0);
        grid.add(txtVida,   1, 0);
        grid.add(lblMana,   0, 1);
        grid.add(txtMana,   1, 1);
        grid.add(lblAtaque, 0, 2);
        grid.add(txtAtaque, 1, 2);
        grid.add(lblDefesa, 0, 3);
        grid.add(txtDefesa, 1, 3);

        // Botão de Aplicar (recria o personagem com atributos novos e salva)
        Button btnAplicar = new Button("Aplicar Alterações");
        btnAplicar.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAplicar.setOnAction(e -> aplicarAlteracoes());

        // (Opcional) Você pode ter um botão adicional para “Recarregar” do TXT se quiser
        Button btnRecarregar = new Button("Recarregar do TXT");
        btnRecarregar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRecarregar.setOnAction(e -> {
            carregarPersonagensTexto();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Recarregar", "Lista recarregada do arquivo texto.");
        });

        HBox hboxBotoes = new HBox(10, btnAplicar, btnRecarregar);
        hboxBotoes.setAlignment(Pos.CENTER);

        root.getChildren().addAll(lblTitulo, listView, grid, hboxBotoes);

        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
        stage.setTitle("Editar Personagens (Arquivos)");
        stage.show();
    }

    // Preenche os campos de texto com os atributos do personagem selecionado
    private void preencherCampos(Personagem p) {
        txtVida.setText(String.valueOf(p.getVida()));
        txtMana.setText(String.valueOf(p.getMana()));
        txtAtaque.setText(String.valueOf(p.getAtaque()));
        txtDefesa.setText(String.valueOf(p.getDefesa()));
    }

    // Aplica alterações no personagem selecionado e salva em ambos os formatos
    private void aplicarAlteracoes() {
        Personagem selecionado = listView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Personagem Selecionado",
                          "Selecione um personagem na lista para alterar atributos.");
            return;
        }

        try {
            int novaVida = Integer.parseInt(txtVida.getText());
            int novaMana = Integer.parseInt(txtMana.getText());
            int novoAtaque = Integer.parseInt(txtAtaque.getText());
            int novaDefesa = Integer.parseInt(txtDefesa.getText());

            // Recria o objeto de acordo com a subclasse
            Personagem pAtualizado = null;
            if (selecionado instanceof Guerreiro) {
                pAtualizado = new Guerreiro(selecionado.getNome(), novaVida, novaMana, novoAtaque, novaDefesa);
            } else if (selecionado instanceof Mago) {
                pAtualizado = new Mago(selecionado.getNome(), novaVida, novaMana, novoAtaque, novaDefesa);
            } else if (selecionado instanceof Arqueiro) {
                pAtualizado = new Arqueiro(selecionado.getNome(), novaVida, novaMana, novoAtaque, novaDefesa);
            }

            // Substitui na lista
            if (pAtualizado != null) {
                int index = listaPersonagens.indexOf(selecionado);
                listaPersonagens.set(index, pAtualizado);
                listView.getItems().set(index, pAtualizado); // atualiza visual

                // Assim que atualizamos, salvamos em TXT e Binário
                salvarPersonagensTexto();
                salvarPersonagensBinario();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Alterações Aplicadas",
                              "Atributos atualizados e arquivos salvos!");
            }
        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato",
                          "Digite valores numéricos válidos para Vida, Mana, Ataque e Defesa.");
        }
    }

    // Carrega personagens do arquivo texto (personagens.txt)
    private void carregarPersonagensTexto() {
        listaPersonagens.clear();
        File file = new File("personagens.txt");
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length != 6) continue;

                String tipo = partes[0];
                String nome = partes[1];
                int vida   = Integer.parseInt(partes[2]);
                int mana   = Integer.parseInt(partes[3]);
                int ataque = Integer.parseInt(partes[4]);
                int defesa = Integer.parseInt(partes[5]);

                Personagem p = null;
                switch (tipo) {
                    case "Guerreiro":
                        p = new Guerreiro(nome, vida, mana, ataque, defesa);
                        break;
                    case "Mago":
                        p = new Mago(nome, vida, mana, ataque, defesa);
                        break;
                    case "Arqueiro":
                        p = new Arqueiro(nome, vida, mana, ataque, defesa);
                        break;
                }
                if (p != null) {
                    listaPersonagens.add(p);
                }
            }
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Carregar",
                          "Não foi possível ler o arquivo: " + e.getMessage());
        }

        // Atualiza ListView
        listView.getItems().setAll(listaPersonagens);
    }

    // Salva personagens em modo texto (personagens.txt)
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
                String linha = String.format("%s;%s;%d;%d;%d;%d",
                        tipo, p.getNome(), p.getVida(), p.getMana(), p.getAtaque(), p.getDefesa());
                pw.println(linha);
            }
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar",
                          "Não foi possível salvar o arquivo: " + e.getMessage());
        }
    }

    // Salva personagens em modo binário (personagens.dat)
    @SuppressWarnings("unchecked")
    private void salvarPersonagensBinario() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personagens.dat"))) {
            oos.writeObject(listaPersonagens);
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar (Binário)",
                          "Não foi possível salvar o arquivo: " + e.getMessage());
        }
    }

    // Método utilitário para exibir alertas
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo, msg);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
