/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 *
 * @author Cliente
 */
public class BuscaJogo {

    public static class Celula {

        private boolean temNavio;
        private boolean atingido;

        public Celula(boolean temNavio) {
            this.temNavio = temNavio;
            this.atingido = false;
        }

        public boolean temNavio() {
            return temNavio;
        }

        public boolean foiAtingido() {
            return atingido;
        }

        public void atacar() {
            this.atingido = true;
        }
    }

    public static class Tabuleiro {

        private static final int TAMANHO = 5;
        private Celula[][] grid;

        public Tabuleiro() {
            grid = new Celula[TAMANHO][TAMANHO];
            inicializarMapaAleatorio();
        }

        private void inicializarMapaAleatorio() {
    // Inicializa a matriz do mapa com todas as células como "false" (sem navio)
    boolean[][] mapa = new boolean[TAMANHO][TAMANHO];

    Random rand = new Random();
    int naviosColocados = 0;
    int totalNavios = 7; // Número total de navios que você quer posicionar

    while (naviosColocados < totalNavios) {
        int x = rand.nextInt(TAMANHO); // Posição aleatória para X (linha)
        int y = rand.nextInt(TAMANHO); // Posição aleatória para Y (coluna)

        // Verifica se já existe um navio naquela posição
        if (!mapa[x][y]) {
            mapa[x][y] = true; // Coloca um navio nessa posição
            naviosColocados++; // Incrementa o contador de navios colocados
        }
    }

    // Preenche o grid com as posições dos navios (mapeado por verdadeiro)
    for (int i = 0; i < TAMANHO; i++) {
        for (int j = 0; j < TAMANHO; j++) {
            grid[i][j] = new Celula(mapa[i][j]);
        }
    }
}

        public Celula getCelula(int x, int y) {
            if (x >= 0 && x < TAMANHO && y >= 0 && y < TAMANHO) {
                return grid[x][y];
            }
            return null;
        }

        public int getTamanho() {
            return TAMANHO;
        }

        public boolean todosForamAtacados() {
            for (int i = 0; i < TAMANHO; i++) {
                for (int j = 0; j < TAMANHO; j++) {
                    if (!grid[i][j].foiAtingido()) {
                        return false; // Ainda há células não atacadas
                    }
                }
            }
            return true; // Todas as células foram atacadas
        }
    }

    public interface AtualizacaoTabuleiro {

        void atualizarTabuleiro(int x, int y, boolean atingiuNavio);
    }

    public static class Busca {

        private AtualizacaoTabuleiro callback;
        private int ataquesRealizados = 0;  // Agora, a variável de contagem de ataques está aqui, compartilhada entre os métodos

        public Busca(AtualizacaoTabuleiro callback) {
            this.callback = callback;
        }

        public void buscaAleatoria(Tabuleiro tabuleiro) {
            Random rand = new Random();
            int delay = 0;

            // O loop continuará até que todos os ataques necessários sejam realizados
            for (int i = 0; i < tabuleiro.getTamanho() * tabuleiro.getTamanho(); i++) {
                if (ataquesRealizados >= tabuleiro.getTamanho() * tabuleiro.getTamanho()) {
                    break;  // Se já foram feitos ataques suficientes, sai do loop
                }

                PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                pause.setOnFinished(event -> {
                    int x, y;
                    // Seleciona uma célula aleatória não atacada
                    do {
                        x = rand.nextInt(tabuleiro.getTamanho());
                        y = rand.nextInt(tabuleiro.getTamanho());
                    } while (tabuleiro.getCelula(x, y).foiAtingido());

                    // Atacar a célula e atualizar o tabuleiro
                    tabuleiro.getCelula(x, y).atacar();
                    callback.atualizarTabuleiro(x, y, tabuleiro.getCelula(x, y).temNavio());

                    // Contador de ataques realizados
                    ataquesRealizados++;

                    // Verifica se todos os ataques foram realizados
                    if (ataquesRealizados == tabuleiro.getTamanho() * tabuleiro.getTamanho()) {
                        System.out.println("Todos os ataques foram realizados!");
                    }
                });
                pause.play();
                delay++;
            }
        }

        public void buscaSequencial(Tabuleiro tabuleiro) {
            int delay = 0;

            // O loop continuará até que todos os ataques necessários sejam realizados
            for (int i = 0; i < tabuleiro.getTamanho(); i++) {
                for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                    if (ataquesRealizados >= tabuleiro.getTamanho() * tabuleiro.getTamanho()) {
                        break;  // Se já foram feitos ataques suficientes, sai do loop
                    }

                    if (!tabuleiro.getCelula(i, j).foiAtingido()) {
                        int finalI = i;
                        int finalJ = j;
                        PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                        pause.setOnFinished(event -> {
                            tabuleiro.getCelula(finalI, finalJ).atacar();
                            callback.atualizarTabuleiro(finalI, finalJ, tabuleiro.getCelula(finalI, finalJ).temNavio());

                            // Atualiza a contagem de ataques
                            ataquesRealizados++;

                            // Verifica se todos os ataques foram realizados
                            if (ataquesRealizados == tabuleiro.getTamanho() * tabuleiro.getTamanho()) {
                                System.out.println("Todos os ataques foram realizados!");
                            }
                        });
                        pause.play();
                        delay++;
                    }
                }
            }
        }

        // Método para resetar o contador de ataques
        public void resetarContador() {
            ataquesRealizados = 0;
        }
    }
}
