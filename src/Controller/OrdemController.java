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
import javafx.scene.image.Image;

/**
 *
 * @author Cliente
 */
public class OrdemController implements OrdemJogo.AtualizacaoTabuleiro {

    private OrdemJogo.Ordenacao ordenacao;
    private OrdemJogo.Tabuleiro tabuleiro;
    private OrdemView view;
    private boolean ordenacaoEmExecucao = false;

    // Construtor com inicialização de Tabuleiro e Ordenacao
    public OrdemController(OrdemView view) {
        this.view = view;
        this.tabuleiro = new OrdemJogo.Tabuleiro(view.carregarImagensTabuleiro());
        this.ordenacao = new OrdemJogo.Ordenacao(this); // Passa o Controller como callback
    }

    // Método para iniciar a ordenação, chamado pela View
    public void executarOrdem(String tipo) {
        if (ordenacaoEmExecucao) {
            System.out.println("A ordenação já está em execução.");
            return;
        }

        ordenacaoEmExecucao = true;
        view.bloquearBotoes();

        // Obtém o método de ordenação com base no tipo selecionado
        Runnable metodoDeOrdenacao = obterMetodoOrdenacao(tipo);
        if (metodoDeOrdenacao != null) {
            executarOrdenacaoComDelay(metodoDeOrdenacao); // Inicia a ordenação com delay
        } else {
            System.out.println("Método de ordenação inválido.");
            finalizarOrdenacao();
        }
    }

    // Obtém o método de ordenação com base no tipo selecionado
    public Runnable obterMetodoOrdenacao(String tipo) {
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

    // Método que executa a ordenação com um delay entre as etapas
    public void executarOrdenacaoComDelay(Runnable metodoDeOrdenacao) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.0)); // Delay de 1 segundo
        delay.setOnFinished(event -> {
            metodoDeOrdenacao.run(); // Executa o próximo passo do algoritmo de ordenação
            atualizarTabuleiroVisual(); // Atualiza o tabuleiro visualmente na View
            if (!tabuleiro.estaOrdenado()) {
                executarOrdenacaoComDelay(metodoDeOrdenacao); // Continua o ciclo
            } else {
                finalizarOrdenacao(); // Finaliza o processo
            }
        });
        delay.play();
    }

    // Finaliza a ordenação e desbloqueia os botões
    private void finalizarOrdenacao() {
        ordenacaoEmExecucao = false;
        view.desbloquearBotoes();
        System.out.println("Ordenação concluída!");
    }

    // Atualiza todas as células do tabuleiro visualmente
    private void atualizarTabuleiroVisual() {
        for (int i = 0; i < tabuleiro.getLetras().size(); i++) {
            char letra = tabuleiro.getLetras().get(i);
            Image imagem = obterImagemAssociada(letra); // Obtém a imagem correspondente à letra
            view.atualizarTabuleiroImg(i / 4, i % 4, imagem); // Atualiza cada célula com a imagem
        }
    }

    // Método para associar uma imagem à sua letra correspondente
    private Image obterImagemAssociada(char letra) {
        switch (letra) {
            case 'A': return view.getLetraAImg();
            case 'B': return view.getLetraBImg();
            case 'C': return view.getLetraCImg();
            case 'D': return view.getLetraDImg();
            case 'E': return view.getLetraEImg();
            case 'F': return view.getLetraFImg();
            case 'G': return view.getLetraGImg();
            case 'H': return view.getLetraHImg();
            default: return null; // Retorna nulo caso a letra não tenha correspondência
        }
    }

    // Implementação do método da interface AtualizacaoTabuleiro
    @Override
    public void atualizarTabuleiroImg(int linha, int coluna, Image imagem) {
        view.atualizarTabuleiroImg(linha, coluna, imagem); // Atualiza a célula no tabuleiro visual
    }

    // Método adicional para execução interativa de trocas (exemplo: botão de ação)
    public void onTrocarButtonClick(int i, int j) {
        ordenacao.swap(tabuleiro, i, j); // Realiza a troca diretamente no Modelo
    }
}