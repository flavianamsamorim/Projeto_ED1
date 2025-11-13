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
import EstruturasDeDados.Lista.Lista;

public class ArquivosView {
    private Stage stage;
    private Lista<Personagem> listaPersonagens = new Lista<>();
    
    private ListView<String> listView;  // Agora listView é de Strings
    private TextField txtVida, txtMana, txtAtaque, txtDefesa;

    public ArquivosView(Stage stage) {
        this.stage = stage;
        configurarLayout();
        carregarPersonagensTexto(); // Carrega personagens do arquivo texto
        carregarPersonagensBinario(); // Carrega personagens do arquivo binário (se existir)
    }

    private void configurarLayout() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #d9f1ff;");

        Label lblTitulo = new Label("Jogo de Arquivos - Edição de Personagens");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // ListView para exibir nomes dos personagens
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
        Label lblMana = new Label("Mana:");
        Label lblAtaque = new Label("Ataque:");
        Label lblDefesa = new Label("Defesa:");

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

        // Botão de Aplicar
        Button btnAplicar = new Button("Aplicar Alterações");
        btnAplicar.setOnAction(e -> aplicarAlteracoes());

        // Botão de Recarregar
        Button btnRecarregar = new Button("Recarregar do TXT");
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

    private void preencherCampos(String nomePersonagem) {
        for (Personagem p : listaPersonagens) {
            if (p.getNome().equals(nomePersonagem)) {
                txtVida.setText(String.valueOf(p.getVida()));
                txtMana.setText(String.valueOf(p.getMana()));
                txtAtaque.setText(String.valueOf(p.getAtaque()));
                txtDefesa.setText(String.valueOf(p.getDefesa()));
                break;
            }
        }
    }

    private void aplicarAlteracoes() {
        String nomeSelecionado = listView.getSelectionModel().getSelectedItem();
        if (nomeSelecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhum Personagem Selecionado", "Selecione um personagem para alterar.");
            return;
        }

        try {
            int novaVida = Integer.parseInt(txtVida.getText());
            int novaMana = Integer.parseInt(txtMana.getText());
            int novoAtaque = Integer.parseInt(txtAtaque.getText());
            int novaDefesa = Integer.parseInt(txtDefesa.getText());

            Personagem pAtualizado = null;
            for (Personagem p : listaPersonagens) {
                if (p.getNome().equals(nomeSelecionado)) {
                    if (p instanceof Guerreiro) {
                        pAtualizado = new Guerreiro(p.getNome(), novaVida, novaMana, novoAtaque, novaDefesa);
                    } else if (p instanceof Mago) {
                        pAtualizado = new Mago(p.getNome(), novaVida, novaMana, novoAtaque, novaDefesa);
                    } else if (p instanceof Arqueiro) {
                        pAtualizado = new Arqueiro(p.getNome(), novaVida, novaMana, novoAtaque, novaDefesa);
                    }
                    listaPersonagens.remove(p);
                    listaPersonagens.addLast(pAtualizado);
                    break;
                }
            }

            atualizarListView();
            salvarPersonagensTexto();
            salvarPersonagensBinario();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Alterações Aplicadas", "Atributos atualizados e arquivos salvos!");

        } catch (NumberFormatException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro de Formato", "Digite valores numéricos válidos.");
        }
    }

    private void carregarPersonagensTexto() {
        listaPersonagens.clear();
        File file = new File("personagens.txt");
        if (!file.exists()) return;

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
                    case "Guerreiro": p = new Guerreiro(nome, vida, mana, ataque, defesa); break;
                    case "Mago": p = new Mago(nome, vida, mana, ataque, defesa); break;
                    case "Arqueiro": p = new Arqueiro(nome, vida, mana, ataque, defesa); break;
                }
                if (p != null) listaPersonagens.addLast(p);
            }
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Carregar", "Erro ao ler o arquivo texto.");
        }

        atualizarListView();
    }

    // Método para atualizar a ListView manualmente
    private void atualizarListView() {
        listView.getItems().clear();
        for (Personagem p : listaPersonagens) {
            listView.getItems().add(p.getNome()); // Agora apenas o nome é adicionado à ListView
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarPersonagensBinario() {
        File file = new File("personagens.dat");
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            listaPersonagens = (Lista<Personagem>) ois.readObject();
            atualizarListView();
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Carregar", "Erro ao ler o arquivo binário.");
        }
    }

    private void salvarPersonagensTexto() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("personagens.txt"))) {
            for (Personagem p : listaPersonagens) {
                String tipo = (p instanceof Guerreiro) ? "Guerreiro" :
                              (p instanceof Mago) ? "Mago" : "Arqueiro";
                String linha = String.format("%s;%s;%d;%d;%d;%d",
                        tipo, p.getNome(), p.getVida(), p.getMana(), p.getAtaque(), p.getDefesa());
                pw.println(linha);
            }
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Erro ao salvar arquivo texto.");
        }
    }

    private void salvarPersonagensBinario() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("personagens.dat"))) {
            oos.writeObject(listaPersonagens);
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro ao Salvar", "Erro ao salvar arquivo binário.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo, msg);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
