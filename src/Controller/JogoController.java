package Controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import View.Arquivos.ArquivosView;
import View.ClassesObjetos.PersonagemView;
import View.Collection.QuizColecoesJava;
import View.CondicaoRepeticao.LabirintoView;
import View.Generic.QuizGeneric;
import View.Operadores.OperadoresView;
import View.Recursividade.TorreHanoiView;
import View.TiposBasicos.QuizView;
import View.VetoresMatriz.SimuladorImagemView;
import View.PilhaView;
import View.FilaView;
import View.ListaView;
import View.ComplexAlgoView;
import View.BuscaView;
import View.OrdemView;

import EstruturasDeDados.Lista.Lista;

public class JogoController {

    private Lista<Button> botoes;

    public JogoController() {
        botoes = new Lista<>();

        String[] nomesJogos = {
            "Q-Tipos Básicos", "Q-Operadores", "Condição e Repetição", "Classes e Objetos",
            "Recursividade", "Vetores e Matrizes", "Arquivos", "Q-Generic", "Q-Collection",
            "Pilhas", "Filas", "Listas Encadeadas", "Complexidade de Algoritmos", "Algoritmos de Busca",
            "Algoritmos de Ordenação"
        };

        for (int i = 0; i < nomesJogos.length; i++) {
    String nome = nomesJogos[i];
    Button btn = new Button(nome);

    // Defina a cor de acordo com a unidade (com base no índice)
    if (i < 9) { // Unidade 1
        //btn.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btn.getStyleClass().add("btn-unidade1");
    } else if (i < 12) { // Unidade 2
        //btn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btn.getStyleClass().add("btn-unidade2");
    } else { // Unidade 3
        //btn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 8;");
        btn.getStyleClass().add("btn-unidade3");
    }

    btn.setOnAction(e -> abrirJogo(nome));
    botoes.addLast(btn);
}
    }

    public Lista<Button> getBotoes() {
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
                new QuizGeneric(stage);
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
                new OrdemView(stage);
                break;

        }
    }
}
