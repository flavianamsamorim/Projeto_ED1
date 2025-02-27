/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.scene.control.Button;
import Model.Jogo;
import java.util.ArrayList;
import java.util.List;

public class JogoController {
    private List<Jogo> jogos;
    private List<Button> botoes;

    public JogoController() {
        jogos = new ArrayList<>();
        botoes = new ArrayList<>();

        String[] nomesJogos = {
            "Entrada e Saída", "Tipos de Dados", "Operadores", "Condição e Repetição",
            "Classes e Objetos", "Recursividade", "Vetores, Matrizes, Strings",
            "Arquivos", "Generic", "Collection", "Pilhas", "Filas",
            "Listas Encadeadas", "Complexidade de Algoritmos", "Algoritmos de Busca",
            "Algoritmos de Ordenação"
        };

        for (String nome : nomesJogos) {
            Jogo jogo = new Jogo(nome);
            jogos.add(jogo);
            Button btn = new Button(jogo.getNome());
            btn.setOnAction(e -> System.out.println("Abrindo jogo: " + jogo.getNome()));
            botoes.add(btn);
        }
    }

    public List<Button> getBotoes() {
        return botoes;
    }
}