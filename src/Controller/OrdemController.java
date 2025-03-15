/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.OrdemJogo;
import View.OrdemView;
import Model.OrdemJogo.AtualizacaoTabuleiro;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;

/**
 *
 * @author Cliente
 */
public class OrdemController implements AtualizacaoTabuleiro {

    private OrdemJogo.Tabuleiro tabuleiro;
    private OrdemView view;
    private OrdemJogo.Ordenacao ordenacao;
    private boolean ordenacaoEmExecucao = false;

    public OrdemController(OrdemView view) {
    this.view = view;
    this.tabuleiro = new OrdemJogo.Tabuleiro(view.carregarImagensTabuleiro());
    this.ordenacao = new OrdemJogo.Ordenacao(this);
}
    
    
    public void executarOrdem(String tipo) {
        if (ordenacaoEmExecucao) {
            System.out.println("A ordenação já está em execução.");
            return;
        }

        ordenacaoEmExecucao = true;
        view.bloquearBotoes();

        // Passa o tabuleiro e a callback ao chamar obterMetodoOrdenacao
        Runnable metodoDeOrdenacao = obterMetodoOrdenacao(tipo, tabuleiro, this);
        if (metodoDeOrdenacao != null) {
            executarOrdenacaoComDelay(metodoDeOrdenacao);
        } else {
            System.out.println("Método de ordenação inválido.");
            finalizarOrdenacao();
        }
    }

    private Runnable obterMetodoOrdenacao(String tipo, OrdemJogo.Tabuleiro tabuleiro, OrdemJogo.AtualizacaoTabuleiro callback) {
        OrdemJogo.Ordenacao ordenacao = new OrdemJogo.Ordenacao(callback);

        switch (tipo) {
            case "bubbleSort":
                return () -> ordenacao.bubbleSort(tabuleiro);
            case "selectionSort":
                return () -> ordenacao.selectionSort(tabuleiro);
            case "insertionSort":
                return () -> ordenacao.insertionSort(tabuleiro);
            case "quickSort":
                return () -> ordenacao.quickSort(tabuleiro, 0, tabuleiro.getLetras().size() - 1);
            case "shellSort":
                return () -> ordenacao.shellSort(tabuleiro);
            case "heapSort":
                return () -> ordenacao.heapSort(tabuleiro);
            default:
                return null;
        }
    }

    private void executarOrdenacaoComDelay(Runnable metodoDeOrdenacao) {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.5)); // Delay de meio segundo
        delay.setOnFinished(event -> {
            metodoDeOrdenacao.run();
            atualizarTabuleiroVisual();
            if (!tabuleiro.estaOrdenado()) {
                executarOrdenacaoComDelay(metodoDeOrdenacao);
            } else {
                finalizarOrdenacao();
            }
        });
        delay.play();
    }

    private void finalizarOrdenacao() {
    ordenacaoEmExecucao = false;
    view.desbloquearBotoes();
    int swapsRealizados = getOrdenacao().getSwapsRealizados();
    view.exibirAlertaOrdenacao(swapsRealizados);
}

    private void atualizarTabuleiroVisual() {
        // Atualiza todas as células do tabuleiro
        for (int i = 0; i < tabuleiro.getLetras().size(); i++) {
            char letra = tabuleiro.getLetras().get(i);
            // Dividindo o índice do tabuleiro para linhas e colunas (2x4)
            view.atualizarTabuleiro(i / 4, i % 4, letra);
        }
    }

    @Override
    public void atualizarTabuleiro(int i, int j, Image imagem) {
        // Aqui você pode obter a letra associada à imagem
        char letra = obterLetraAssociada(imagem);
        // Chame a função para atualizar a célula no tabuleiro com a letra
        view.atualizarTabuleiro(i, j, letra);
    }
    
    public OrdemJogo.Ordenacao getOrdenacao() {
    return ordenacao;
}

    private char obterLetraAssociada(Image imagem) {
        if (imagem == view.getLetraAImg()) {
            return 'A';
        }
        if (imagem == view.getLetraBImg()) {
            return 'B';
        }
        if (imagem == view.getLetraCImg()) {
            return 'C';
        }
        if (imagem == view.getLetraDImg()) {
            return 'D';
        }
        if (imagem == view.getLetraEImg()) {
            return 'E';
        }
        if (imagem == view.getLetraFImg()) {
            return 'F';
        }
        if (imagem == view.getLetraGImg()) {
            return 'G';
        }
        if (imagem == view.getLetraHImg()) {
            return 'H';
        }
        return ' '; // Se a imagem não for encontrada, retornamos um espaço
    }
}
