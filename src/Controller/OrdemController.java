/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.OrdemJogo;
import View.OrdemView;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Platform;
/**
 *
 * @author Cliente
 */
public class OrdemController implements OrdemJogo.AtualizacaoTabuleiro {

    private OrdemJogo.Tabuleiro tabuleiro;
    private OrdemJogo.Ordenacao ordenacao;
    private OrdemView view;
    private boolean ordenacaoEmExecucao = false;

    public OrdemController(OrdemView view) {
        this.tabuleiro = new OrdemJogo.Tabuleiro();
        this.view = view;
        this.ordenacao = new OrdemJogo.Ordenacao(this); // Atualiza a interface do tabuleiro
    }

    public OrdemJogo.Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    // Método para executar a ordenação com base no tipo selecionado
    public void executarOrdem(String tipo) {
        if (ordenacaoEmExecucao) {
            System.out.println("A ordenação já está em execução.");
            return;
        }

        ordenacaoEmExecucao = true; // Marca que a ordenação está em execução
        view.bloquearBotoes(); // Bloqueia os botões enquanto a ordenação ocorre

        // Chama o método de ordenação correspondente
        Runnable metodoDeOrdenacao = obterMetodoOrdenacao(tipo);
        if (metodoDeOrdenacao != null) {
            executarOrdenacaoComDelay(metodoDeOrdenacao); // Executa o método com delay
        } else {
            System.out.println("Método de ordenação inválido.");
            finalizarOrdenacao(); // Finaliza a ordenação se tipo inválido
        }
    }

    // Método que retorna o método de ordenação correspondente ao tipo
    private Runnable obterMetodoOrdenacao(String tipo) {
        switch (tipo) {
            case "bubbleSort":
                return () -> ordenacao.bubbleSort(tabuleiro);
            case "selectionSort":
                return () -> ordenacao.selectionSort(tabuleiro);
            case "insertionSort":
                return () -> ordenacao.insertionSort(tabuleiro);
            case "quickSort":
                return () -> ordenacao.quickSort(tabuleiro, 0, tabuleiro.getTamanho() - 1);
            case "shellSort":
                return () -> ordenacao.shellSort(tabuleiro);
            case "heapSort":
                return () -> ordenacao.heapSort(tabuleiro);
            default:
                return null; // Retorna null se o tipo de ordenação for inválido
        }
    }

    // Método para execução de ordenação com delay
    private void executarOrdenacaoComDelay(Runnable metodoDeOrdenacao) {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.5)); // Delay de meio segundo entre as mudanças
        delay.setOnFinished(event -> {
            metodoDeOrdenacao.run();
            if (!tabuleiro.estaOrdenado()) { // Se o tabuleiro não estiver ordenado, continua
                executarOrdenacaoComDelay(metodoDeOrdenacao); // Chama recursivamente até terminar a ordenação
            } else {
                finalizarOrdenacao(); // Finaliza a ordenação
            }
        });
        delay.play();
    }

    // Método para finalizar a ordenação e reabilitar a interface
    private void finalizarOrdenacao() {
        ordenacaoEmExecucao = false; // Libera a ordenação
        view.desbloquearBotoes(); // Reativa os botões após a conclusão
    }

    // Método que atualiza o tabuleiro na interface
    @Override
    public void atualizarTabuleiro(int i, char letra) {
        // Calcular a posição x e y no grid a partir do índice 'i' (0-7)
        int x = i / 4;  // Linha do grid (0 ou 1)
        int y = i % 4;  // Coluna do grid (0-3)

        System.out.println("Letra atualizada na posição: " + i + " para: " + letra);
        // Atualiza a interface com a nova letra
        Platform.runLater(() -> view.atualizarTabuleiro(x, y, letra));
    }
}