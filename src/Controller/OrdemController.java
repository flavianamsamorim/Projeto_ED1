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
    
    private OrdemJogo.Ordenacao ordenacao;
    private OrdemJogo.Tabuleiro tabuleiro;
    private OrdemView view;
    private boolean ordenacaoEmExecucao = false;

    // Construtor atualizado, agora com OrdemController como parâmetro
    public OrdemController(OrdemView view) {
        this.view = view;
        this.tabuleiro = new OrdemJogo.Tabuleiro(view.carregarImagensTabuleiro());
        this.ordenacao = new OrdemJogo.Ordenacao(this, this);  // Instanciando a classe Ordenacao
    }

    // Método para iniciar a ordenação, chamado pela view
    public void executarOrdem(String tipo) {
        if (ordenacaoEmExecucao) {
            System.out.println("A ordenação já está em execução.");
            return;
        }

        ordenacaoEmExecucao = true;
        view.bloquearBotoes();

        // Passa o tabuleiro e a callback ao chamar obterMetodoOrdenacao
        Runnable metodoDeOrdenacao = obterMetodoOrdenacao(tipo);
        if (metodoDeOrdenacao != null) {
            executarOrdenacaoComDelay(metodoDeOrdenacao); // Começa a execução com delay
        } else {
            System.out.println("Método de ordenação inválido.");
            finalizarOrdenacao();
        }
    }

    // Obtém o método de ordenação com base no tipo
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

    // Método que executa a ordenação com um delay
    public void executarOrdenacaoComDelay(Runnable metodoDeOrdenacao) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1.0)); // Delay de 1 segundo entre cada execução
        delay.setOnFinished(event -> {
            metodoDeOrdenacao.run(); // Executa o método de ordenação
            atualizarTabuleiroVisual(); // Atualiza o tabuleiro
            if (!tabuleiro.estaOrdenado()) {
                executarOrdenacaoComDelay(metodoDeOrdenacao); // Repete a execução com delay
            } else {
                finalizarOrdenacao(); // Finaliza a ordenação
            }
        });
        delay.play();
    }

    // Finaliza a ordenação e desbloqueia os botões
    private void finalizarOrdenacao() {
        ordenacaoEmExecucao = false;
        view.desbloquearBotoes();
    }

    // Atualiza todas as células do tabuleiro visualmente
    private void atualizarTabuleiroVisual() {
        for (int i = 0; i < tabuleiro.getLetras().size(); i++) {
            char letra = tabuleiro.getLetras().get(i);
            view.atualizarTabuleiro(i / 4, i % 4, letra); // Atualiza cada célula com a letra
        }
    }

    // Implementação do método da interface AtualizacaoTabuleiro
    @Override
    public void atualizarTabuleiro(int i, int j, Image imagem) {
        // Aqui, associamos a imagem à letra
        char letra = obterLetraAssociada(imagem);
        view.atualizarTabuleiro(i, j, letra); // Atualiza a célula no tabuleiro visual
    }

    // Método para associar a imagem à letra correspondente
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
        return ' '; // Retorna espaço se a imagem não for encontrada
    }

    // Novo método para trocar as letras no tabuleiro e chamar a atualização interativa
    public void onTrocarButtonClick(int i, int j) {
        // Chama a troca interativa no Modelo, usando a instância de Ordenacao
        ordenacao.swap(tabuleiro, i, j);  // O modelo cuida da troca e da atualização
    }
}