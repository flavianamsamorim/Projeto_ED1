package Controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import View.Arquivos.ArquivosView;
import View.ClassesObjetos.PersonagemView;
import View.Collection.QuizColecoesJava;
import View.CondicaoRepeticao.LabirintoView;
import View.Generic.InventarioMagicoView;
import View.Operadores.OperadoresView;
import View.Recursividade.TorreHanoiView;
import View.TiposBasicos.QuizView;
import View.VetoresMatriz.SimuladorImagemView;
import View.GenericView;
import View.PilhaView;
import View.FilaView;
import View.ListaView;
import View.ComplexAlgoView;
import View.BuscaView;
import View.HuffmanView;


import java.util.ArrayList;
import java.util.List;

public class JogoController {

    private List<Button> botoes;

    public JogoController() {
        botoes = new ArrayList<>();

        String[] nomesJogos = {
            "Tipos Básicos", "Operadores", "Condição e Repetição",
            "Classes e Objetos", "Recursividade", "Vetores e Matrizes",
            "Arquivos", "Generic", "Collection", "Pilhas", "Filas",
            "Listas Encadeadas", "Complexidade de Algoritmos", "Algoritmos de Busca",
            "Algoritmos de Ordenação", "Algoritmo de Huffman"
        };

        for (String nome : nomesJogos) {
            Button btn = new Button(nome);
            btn.setStyle("-fx-background-color:rgb(97, 168, 29); -fx-text-fill: white; "
            + "-fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
            // Definir ações para cada botão
            btn.setOnAction(e -> abrirJogo(nome));

            botoes.add(btn);
        }
    }

    public List<Button> getBotoes() {
        return botoes;
    }

    // Método para abrir o jogo correto
    private void abrirJogo(String nomeJogo) {
        Stage stage = new Stage();

        // Abrir o jogo conforme o nome
        switch (nomeJogo) {
            case "Tipos Básicos":
                new QuizView(stage);
                break;

            case "Operadores":
                new OperadoresView(stage);
                break;

            case "Condição e Repetição":
                new LabirintoView(stage);
                break;

            case "Classes e Objetos":
                new PersonagemView(stage);
                break;

            case "Recursividade":
                new TorreHanoiView(stage, 5);
                break;

            case "Vetores e Matrizes":
                new SimuladorImagemView(stage);
                break;

            case "Arquivos":
                new ArquivosView(stage);
                break;

            case "Generic":
                new InventarioMagicoView(stage);
                break;

            case "Collection":
                new QuizColecoesJava(stage);
                break;

            case "Pilhas":
                new PilhaView(stage);
                break;

            case "Filas":
                new FilaView(stage);
                break;

            case "Listas Encadeadas":
                new ListaView(stage);
                break;

            case "Complexidade de Algoritmos":
                new ComplexAlgoView(stage);
                break;

            case "Algoritmos de Busca":
                new BuscaView(stage);
                break;

            case "Algoritmos de Ordenação":
                new GenericView("Jogo de Ordenação", stage);
                break;

            case "Algoritmo de Huffman":
                new HuffmanView(stage);
                break;
        }
    }
}
