package Controller;

import View.ArquivosView;
import View.FilaView;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import View.PilhaView;
import View.QuizView;
import View.SimuladorImagemView;
import View.TorreHanoiView;
import View.GenericView;
import View.LabirintoView;
import View.OperadoresView;
import View.PersonagemView;
import View.ListaView;

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
                new PersonagemView(stage);
                break;

            case "Collection":
                new PersonagemView(stage);
                break;

            case "Pilhas":
                new PilhaView(stage);
                break;

            case "Filas":
                new FilaView(stage);
                break;

            case "Listas Encadeadas":
                new PersonagemView(stage);
                break;

            case "Complexidade de Algoritmos":
                new GenericView("Jogo de Complexidade", stage);
                break;

            case "Algoritmos de Busca":
                new GenericView("Jogo de Busca", stage);
                break;

            case "Algoritmos de Ordenação":
                new GenericView("Jogo de Ordenação", stage);
                break;

            case "Algoritmo de Huffman":
                new GenericView("Jogo de Ordenação", stage);
                break;
        }
    }
}
