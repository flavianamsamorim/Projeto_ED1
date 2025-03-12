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
public class OrdemJogo {

    public static class Celula {
        private char letra;
        private boolean ordenada;

        public Celula(char letra) {
            this.letra = letra;
            this.ordenada = false;
        }

        public char getLetra() {
            return letra;
        }

        public void setLetra(char letra) {
            this.letra = letra;
        }

        public boolean isOrdenada() {
            return ordenada;
        }

        public void setOrdenada(boolean ordenada) {
            this.ordenada = ordenada;
        }
    }

    public static class Tabuleiro {
        private static int TAMANHO = 8; // Agora o grid é 2x4, ou seja, 8 células
        private Celula[] grid;

        public Tabuleiro() {
            grid = new Celula[TAMANHO];
            inicializarTabuleiroAleatorio();
        }

        // Inicializa o tabuleiro com letras de 'A' a 'H'
        private void inicializarTabuleiroAleatorio() {
            Random rand = new Random();
            // Preenche as células com letras aleatórias entre 'A' e 'H'
            for (int i = 0; i < TAMANHO; i++) {
                char letra = (char) ('A' + i); // Gera letras de A até H
                grid[i] = new Celula(letra);
            }
        }

        public Celula getCelula(int i) {
            if (i >= 0 && i < TAMANHO) {
                return grid[i];
            }
            return null;
        }

        public void atualizarTabuleiro(int i, char letra) {
            grid[i].setLetra(letra); // Atualiza a letra da célula
        }

        public int getTamanho() {
            return TAMANHO;
        }

        public Celula[] getGrid() {
            return grid;
        }

        // Método para verificar se o tabuleiro está ordenado
        public boolean estaOrdenado() {
            for (int i = 0; i < TAMANHO - 1; i++) {
                if (grid[i].getLetra() > grid[i + 1].getLetra()) {
                    return false; // Se uma célula for maior que a próxima, o tabuleiro não está ordenado
                }
            }
            return true; // O tabuleiro está ordenado
        }
    }

    public interface AtualizacaoTabuleiro {
        void atualizarTabuleiro(int i, char letra);
    }

    public static class Ordenacao {

        private AtualizacaoTabuleiro callback;
        private int swapsRealizados = 0;

        public Ordenacao(AtualizacaoTabuleiro callback) {
            this.callback = callback;
        }

        // Bubble Sort
        public void bubbleSort(Tabuleiro tabuleiro) {
            int delay = 0;
            for (int i = 0; i < tabuleiro.getTamanho() - 1; i++) {
                for (int j = 0; j < tabuleiro.getTamanho() - i - 1; j++) {
                    if (tabuleiro.getCelula(j).getLetra() > tabuleiro.getCelula(j + 1).getLetra()) {
                        char temp = tabuleiro.getCelula(j).getLetra();
                        tabuleiro.atualizarTabuleiro(j, tabuleiro.getCelula(j + 1).getLetra());
                        tabuleiro.atualizarTabuleiro(j + 1, temp);

                        // Criar variáveis finais para uso nas lambdas
                        final int finalJ = j;
                        final int finalJPlusOne = j + 1;

                        PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                        pause.setOnFinished(event -> {
                            callback.atualizarTabuleiro(finalJ, tabuleiro.getCelula(finalJ).getLetra());
                            callback.atualizarTabuleiro(finalJPlusOne, tabuleiro.getCelula(finalJPlusOne).getLetra());
                            swapsRealizados++;
                        });
                        pause.play();
                        delay++;
                    }
                }
            }
        }

        // Selection Sort
        public void selectionSort(Tabuleiro tabuleiro) {
            int delay = 0;
            for (int i = 0; i < tabuleiro.getTamanho() - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < tabuleiro.getTamanho(); j++) {
                    if (tabuleiro.getCelula(j).getLetra() < tabuleiro.getCelula(minIdx).getLetra()) {
                        minIdx = j;
                    }
                }
                if (minIdx != i) {
                    char temp = tabuleiro.getCelula(i).getLetra();
                    tabuleiro.atualizarTabuleiro(i, tabuleiro.getCelula(minIdx).getLetra());
                    tabuleiro.atualizarTabuleiro(minIdx, temp);

                    final int finalI = i;
                    final int finalMinIdx = minIdx;

                    PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                    pause.setOnFinished(event -> {
                        callback.atualizarTabuleiro(finalI, tabuleiro.getCelula(finalI).getLetra());
                        callback.atualizarTabuleiro(finalMinIdx, tabuleiro.getCelula(finalMinIdx).getLetra());
                        swapsRealizados++;
                    });
                    pause.play();
                    delay++;
                }
            }
        }

        // Insertion Sort
        public void insertionSort(Tabuleiro tabuleiro) {
            int delay = 0;
            for (int i = 1; i < tabuleiro.getTamanho(); i++) {
                char key = tabuleiro.getCelula(i).getLetra();
                int j = i - 1;
                while (j >= 0 && tabuleiro.getCelula(j).getLetra() > key) {
                    tabuleiro.atualizarTabuleiro(j + 1, tabuleiro.getCelula(j).getLetra());
                    j = j - 1;

                    final int finalJ = j + 1;

                    PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                    pause.setOnFinished(event -> {
                        callback.atualizarTabuleiro(finalJ, tabuleiro.getCelula(finalJ).getLetra());
                        swapsRealizados++;
                    });
                    pause.play();
                    delay++;
                }
                tabuleiro.atualizarTabuleiro(j + 1, key);
            }
        }

        // Quick Sort
        public void quickSort(Tabuleiro tabuleiro, int low, int high) {
            int delay = 0;
            if (low < high) {
                int pi = partition(tabuleiro, low, high);

                final int finalPi = pi;

                PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                pause.setOnFinished(event -> {
                    callback.atualizarTabuleiro(finalPi, tabuleiro.getCelula(finalPi).getLetra());
                    swapsRealizados++;
                });
                pause.play();
                delay++;

                quickSort(tabuleiro, low, pi - 1);
                quickSort(tabuleiro, pi + 1, high);
            }
        }

        private int partition(Tabuleiro tabuleiro, int low, int high) {
            char pivot = tabuleiro.getCelula(high).getLetra();
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (tabuleiro.getCelula(j).getLetra() < pivot) {
                    i++;
                    char temp = tabuleiro.getCelula(i).getLetra();
                    tabuleiro.atualizarTabuleiro(i, tabuleiro.getCelula(j).getLetra());
                    tabuleiro.atualizarTabuleiro(j, temp);
                }
            }
            char temp = tabuleiro.getCelula(i + 1).getLetra();
            tabuleiro.atualizarTabuleiro(i + 1, tabuleiro.getCelula(high).getLetra());
            tabuleiro.atualizarTabuleiro(high, temp);
            return i + 1;
        }

        // Shell Sort
        public void shellSort(Tabuleiro tabuleiro) {
            int delay = 0;
            int n = tabuleiro.getTamanho();
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    char temp = tabuleiro.getCelula(i).getLetra();
                    int j = i;
                    while (j >= gap && tabuleiro.getCelula(j - gap).getLetra() > temp) {
                        tabuleiro.atualizarTabuleiro(j, tabuleiro.getCelula(j - gap).getLetra());
                        j -= gap;
                    }
                    tabuleiro.atualizarTabuleiro(j, temp);

                    final int finalJ = j;

                    PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                    pause.setOnFinished(event -> {
                        callback.atualizarTabuleiro(finalJ, tabuleiro.getCelula(finalJ).getLetra());
                        swapsRealizados++;
                    });
                    pause.play();
                    delay++;
                }
            }
        }

        // Heap Sort
        public void heapSort(Tabuleiro tabuleiro) {
            int delay = 0;
            int n = tabuleiro.getTamanho();
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(tabuleiro, n, i);
            }
            for (int i = n - 1; i >= 0; i--) {
                char temp = tabuleiro.getCelula(0).getLetra();
                tabuleiro.atualizarTabuleiro(0, tabuleiro.getCelula(i).getLetra());
                tabuleiro.atualizarTabuleiro(i, temp);
                heapify(tabuleiro, i, 0);

                final int finalI = 0;
                final int finalIdx = i;

                PauseTransition pause = new PauseTransition(Duration.seconds(delay * 0.5));
                pause.setOnFinished(event -> {
                    callback.atualizarTabuleiro(finalI, tabuleiro.getCelula(finalI).getLetra());
                    callback.atualizarTabuleiro(finalIdx, tabuleiro.getCelula(finalIdx).getLetra());
                    swapsRealizados++;
                });
                pause.play();
                delay++;
            }
        }

        private void heapify(Tabuleiro tabuleiro, int n, int i) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < n && tabuleiro.getCelula(left).getLetra() > tabuleiro.getCelula(largest).getLetra()) {
                largest = left;
            }
            if (right < n && tabuleiro.getCelula(right).getLetra() > tabuleiro.getCelula(largest).getLetra()) {
                largest = right;
            }
            if (largest != i) {
                char swap = tabuleiro.getCelula(i).getLetra();
                tabuleiro.atualizarTabuleiro(i, tabuleiro.getCelula(largest).getLetra());
                tabuleiro.atualizarTabuleiro(largest, swap);
                heapify(tabuleiro, n, largest);
            }
        }
    }
}