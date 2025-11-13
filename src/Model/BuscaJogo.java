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
 * Classe responsável pela lógica do jogo de busca de navios.
 * Inclui classes internas para representar células e tabuleiros,
 * e métodos para realizar buscas e ataques.
 */
public class BuscaJogo {

    /**
     * Classe interna que representa uma célula no tabuleiro.
     * Cada célula pode ter um navio e pode ser atingida.
     */
    public static class Celula {

        private boolean temNavio; // Indica se a célula tem um navio
        private boolean atingido; // Indica se a célula foi atingida

        // Construtor que inicializa a célula com ou sem navio
        public Celula(boolean temNavio) {
            this.temNavio = temNavio;
            this.atingido = false;
        }

        // Retorna se a célula tem um navio
        public boolean temNavio() {
            return temNavio;
        }

        // Retorna se a célula foi atingida
        public boolean foiAtingido() {
            return atingido;
        }

        // Marca a célula como atingida
        public void atacar() {
            this.atingido = true;
        }
    }

    /**
     * Classe interna que representa o tabuleiro do jogo.
     * Contém uma matriz de células e métodos para inicializar e manipular o tabuleiro.
     */
    public static class Tabuleiro {

        private static final int TAMANHO = 5; // Tamanho do tabuleiro (5x5)
        private Celula[][] grid; // Matriz de células que representa o tabuleiro

        // Construtor que inicializa o tabuleiro e posiciona os navios aleatoriamente
        public Tabuleiro() {
            grid = new Celula[TAMANHO][TAMANHO];
            inicializarMapaAleatorio();
        }

        // Inicializa o mapa do tabuleiro com navios posicionados aleatoriamente
        private void inicializarMapaAleatorio() {
            // Inicializa a matriz do mapa com todas as células como "false" (sem navio)
            boolean[][] mapa = new boolean[TAMANHO][TAMANHO];

            Random rand = new Random();
            int naviosColocados = 0;
            int totalNavios = 7; // Número total de navios que você quer posicionar

            // Posiciona os navios aleatoriamente no mapa
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

        // Retorna a célula na posição especificada (x, y) ou null se fora dos limites
        public Celula getCelula(int x, int y) {
            if (x >= 0 && x < TAMANHO && y >= 0 && y < TAMANHO) {
                return grid[x][y];
            }
            return null;
        }

        // Retorna o tamanho do tabuleiro
        public int getTamanho() {
            return TAMANHO;
        }

        // Verifica se todas as células foram atacadas
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

    // Interface para atualizar o tabuleiro após um ataque
    public interface AtualizacaoTabuleiro {
        void atualizarTabuleiro(int x, int y, boolean atingiuNavio);
    }

    /**
     * Classe que realiza a busca e os ataques no tabuleiro.
     * Utiliza uma interface de callback para atualizar a visualização do tabuleiro.
     */
    public static class Busca {

        private AtualizacaoTabuleiro callback;
        private int ataquesRealizados = 0;  // Contador de ataques realizados

        // Construtor que inicializa a busca com um callback de atualização
        public Busca(AtualizacaoTabuleiro callback) {
            this.callback = callback;
        }

        // Realiza uma busca aleatória no tabuleiro
        public void buscaAleatoria(Tabuleiro tabuleiro) {
            Random rand = new Random();
            int delay = 0;

            // Loop para realizar ataques até que todos sejam realizados
            for (int i = 0; i < tabuleiro.getTamanho() * tabuleiro.getTamanho(); i++) {
                if (ataquesRealizados >= tabuleiro.getTamanho() * tabuleiro.getTamanho()) {
                    break;  // Se já foram feitos ataques suficientes, sai do loop
                }

                // Cria uma pausa entre os ataques para visualização
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

        // Realiza uma busca sequencial no tabuleiro
        public void buscaSequencial(Tabuleiro tabuleiro) {
            int delay = 0;

            // Loop para realizar ataques até que todos sejam realizados
            for (int i = 0; i < tabuleiro.getTamanho(); i++) {
                for (int j = 0; j < tabuleiro.getTamanho(); j++) {
                    if (ataquesRealizados >= tabuleiro.getTamanho() * tabuleiro.getTamanho()) {
                        break;  // Se já foram feitos ataques suficientes, sai do loop
                    }

                    if (!tabuleiro.getCelula(i, j).foiAtingido()) {
                        int finalI = i;
                        int finalJ = j;
                        // Cria uma pausa entre os ataques para visualização
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