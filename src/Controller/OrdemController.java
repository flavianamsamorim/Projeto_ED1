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
/**
 *
 * @author Cliente
 */
public class OrdemController implements OrdemJogo.AtualizacaoTabuleiro {

    private OrdemJogo.Tabuleiro tabuleiro;
    private OrdemJogo.Ordenacao ordenacao;
    private OrdemView view;
    private boolean ordenacaoEmExecucao = false; // Para evitar execução simultânea

    public OrdemController(OrdemView view) {
        this.tabuleiro = new OrdemJogo.Tabuleiro();
        this.view = view;
        this.ordenacao = new OrdemJogo.Ordenacao(this); // 'this' agora é do tipo AtualizacaoTabuleiro
    }

    // Método para executar a ordenação com base no tipo selecionado
    public void executarOrdem(String tipo) {
        // Impede que a ordenação seja executada se já estiver em execução
        if (ordenacaoEmExecucao) {
            System.out.println("A ordenação já está em execução.");
            return;
        }

        ordenacaoEmExecucao = true; // Indica que a ordenação está em execução
        view.bloquearBotoes(); // Bloqueia todos os botões enquanto a ordenação ocorre

        // Chama o método de ordenação correspondente
        switch (tipo) {
            case "bubbleSort":
                executarOrdenacaoComDelay(() -> ordenacao.bubbleSort(tabuleiro));
                break;
            case "selectionSort":
                executarOrdenacaoComDelay(() -> ordenacao.selectionSort(tabuleiro));
                break;
            case "insertionSort":
                executarOrdenacaoComDelay(() -> ordenacao.insertionSort(tabuleiro));
                break;
            case "quickSort":
                executarOrdenacaoComDelay(() -> ordenacao.quickSort(tabuleiro, 0, tabuleiro.getTamanho() - 1));
                break;
            case "shellSort":
                executarOrdenacaoComDelay(() -> ordenacao.shellSort(tabuleiro));
                break;
            case "heapSort":
                executarOrdenacaoComDelay(() -> ordenacao.heapSort(tabuleiro));
                break;
            default:
                System.out.println("Método de ordenação inválido.");
                ordenacaoEmExecucao = false; // Libera a ordenação se o tipo não for reconhecido
                view.desbloquearBotoes(); // Reativa os botões
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
                ordenacaoEmExecucao = false; // Libera a ordenação quando termina
                view.desbloquearBotoes(); // Reativa os botões após a conclusão
            }
        });
        delay.play();
    }

    // Método que atualiza o tabuleiro na interface
    @Override
    public void atualizarTabuleiro(int i, char letra) {
        System.out.println("Letra atualizada na posição: " + i + " para: " + letra);
        view.atualizarTabuleiro(i, letra); // Atualiza a interface com a nova letra
    }
}
