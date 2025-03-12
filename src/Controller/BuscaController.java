/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BuscaJogo;
import View.BuscaView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 *
 * @author Cliente
 */
public class BuscaController implements BuscaJogo.AtualizacaoTabuleiro {

    private BuscaJogo.Tabuleiro tabuleiro;
    private BuscaJogo.Busca busca;
    private BuscaView view;
    private boolean buscaEmExecucao = false; // Para evitar execução simultânea

    public BuscaController(BuscaView view) {
        this.tabuleiro = new BuscaJogo.Tabuleiro();
        this.view = view;
        this.busca = new BuscaJogo.Busca(this);
    }

    public void executarBusca(String tipo) {
    view.bloquearBotao(tipo); // Bloqueia o outro botão

    switch (tipo) {
        case "aleatoria":
            busca.buscaAleatoria(tabuleiro);
            break;
        case "sequencial":
            busca.buscaSequencial(tabuleiro);
            break;
        default:
            System.out.println("Método de busca inválido.");
    }
}

    private void executarBuscaAleatoria() {
        realizarAtaquesProgressivos(() -> busca.buscaAleatoria(tabuleiro));
    }

    private void executarBuscaSequencial() {
        realizarAtaquesProgressivos(() -> busca.buscaSequencial(tabuleiro));
    }

    private void realizarAtaquesProgressivos(Runnable metodoDeBusca) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> {
            metodoDeBusca.run();
            if (!tabuleiro.todosForamAtacados()) { // Se ainda houver células não atacadas, continua
                realizarAtaquesProgressivos(metodoDeBusca);
            } else {
                buscaEmExecucao = false; // Libera a busca quando termina
                view.desbloquearBotoes(); // Reativa os botões após conclusão
            }
        });
        delay.play();
    }

    @Override
    public void atualizarTabuleiro(int x, int y, boolean atingiuNavio) {
        System.out.println("Ataque realizado em: (" + x + ", " + y + ")");
        System.out.println(atingiuNavio ? "Acertou um navio!" : "Água!");

        view.atualizarTabuleiro(x, y, atingiuNavio);
    }
}
