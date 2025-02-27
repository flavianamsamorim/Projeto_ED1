
package Controller;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import View.PilhaView;
import View.GenericView;
import java.util.ArrayList;
import java.util.List;

public class JogoController {
    private List<Button> botoes;

    public JogoController() {
        botoes = new ArrayList<>();
        
        String[] nomesJogos = {
            "Entrada e Saída", "Tipos de Dados", "Operadores", "Condição e Repetição",
            "Classes e Objetos", "Recursividade", "Vetores, Matrizes, Strings",
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
            case "Pilhas":
                new PilhaView(stage);
                break;
            case "Filas":
                new GenericView("Jogo de Filas", stage);
                break;
            case "Listas Encadeadas":
                new GenericView("Jogo de Listas Encadeadas", stage);
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
            default:
                // Para jogos não especificados, usaremos o GenericView
                new GenericView(nomeJogo, stage);
                break;
        }
    }
}
