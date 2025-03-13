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

    // Classe que representa uma célula com uma letra e um estado de ordenação
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

    // Classe que representa o tabuleiro com 8 células
    public static class Tabuleiro {
        private static final int TAMANHO = 8;
        private Celula[] grid;

        // Construtor do tabuleiro que inicializa com letras de 'A' a 'H'
        public Tabuleiro() {
            grid = new Celula[TAMANHO];
            inicializarTabuleiroAleatorio();
        }

        // Inicializa o tabuleiro com letras de 'A' a 'H'
        private void inicializarTabuleiroAleatorio() {
            Random rand = new Random();
            for (int i = 0; i < TAMANHO; i++) {
                char letra = (char) ('A' + i);
                grid[i] = new Celula(letra);
            }
        }

        // Obtém uma célula específica
        public Celula getCelula(int i) {
            return (i >= 0 && i < TAMANHO) ? grid[i] : null;
        }

        // Atualiza o conteúdo de uma célula específica
        public void atualizarTabuleiro(int i, char letra) {
            grid[i].setLetra(letra);
        }

        // Retorna o tamanho do tabuleiro (constante 8)
        public int getTamanho() {
            return TAMANHO;
        }

        // Retorna o array de células do tabuleiro
        public Celula[] getGrid() {
            return grid;
        }

        // Verifica se o tabuleiro está ordenado
        public boolean estaOrdenado() {
            for (int i = 0; i < TAMANHO - 1; i++) {
                if (grid[i].getLetra() > grid[i + 1].getLetra()) {
                    return false;
                }
            }
            return true;
        }
    }

    // Interface para callback de atualização do tabuleiro
    public interface AtualizacaoTabuleiro {
        void atualizarTabuleiro(int i, char letra);
    }

    // Classe de ordenação que executa os algoritmos e atualiza o tabuleiro
    public static class Ordenacao {
        private final AtualizacaoTabuleiro callback;
        private int swapsRealizados = 0;

        public Ordenacao(AtualizacaoTabuleiro callback) {
            this.callback = callback;
        }

        // Algoritmo de Bubble Sort
        public void bubbleSort(Tabuleiro tabuleiro) {
            for (int i = 0; i < tabuleiro.getTamanho() - 1; i++) {
                boolean trocado = false;
                for (int j = 0; j < tabuleiro.getTamanho() - i - 1; j++) {
                    if (tabuleiro.getCelula(j).getLetra() > tabuleiro.getCelula(j + 1).getLetra()) {
                        swap(tabuleiro, j, j + 1);
                        trocado = true;
                    }
                }
                if (!trocado) break; // Se não houve trocas, o vetor já está ordenado
            }
        }

        // Algoritmo de Selection Sort
        public void selectionSort(Tabuleiro tabuleiro) {
            for (int i = 0; i < tabuleiro.getTamanho() - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < tabuleiro.getTamanho(); j++) {
                    if (tabuleiro.getCelula(j).getLetra() < tabuleiro.getCelula(minIdx).getLetra()) {
                        minIdx = j;
                    }
                }
                if (minIdx != i) {
                    swap(tabuleiro, i, minIdx);
                }
            }
        }

        // Algoritmo de Insertion Sort
        public void insertionSort(Tabuleiro tabuleiro) {
            for (int i = 1; i < tabuleiro.getTamanho(); i++) {
                char key = tabuleiro.getCelula(i).getLetra();
                int j = i - 1;
                while (j >= 0 && tabuleiro.getCelula(j).getLetra() > key) {
                    tabuleiro.atualizarTabuleiro(j + 1, tabuleiro.getCelula(j).getLetra());
                    j--;
                }
                tabuleiro.atualizarTabuleiro(j + 1, key);
            }
        }

        // Algoritmo de Quick Sort
        public void quickSort(Tabuleiro tabuleiro, int low, int high) {
            if (low < high) {
                int pi = partition(tabuleiro, low, high);
                quickSort(tabuleiro, low, pi - 1);
                quickSort(tabuleiro, pi + 1, high);
            }
        }

        // Particionamento usado no Quick Sort
        private int partition(Tabuleiro tabuleiro, int low, int high) {
            char pivot = medianOfThree(tabuleiro, low, high);
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (tabuleiro.getCelula(j).getLetra() < pivot) {
                    i++;
                    swap(tabuleiro, i, j);
                }
            }
            swap(tabuleiro, i + 1, high);
            return i + 1;
        }

        // Mediana de três elementos para escolher o pivô
        private char medianOfThree(Tabuleiro tabuleiro, int low, int high) {
            int mid = (low + high) / 2;
            char first = tabuleiro.getCelula(low).getLetra();
            char middle = tabuleiro.getCelula(mid).getLetra();
            char last = tabuleiro.getCelula(high).getLetra();

            if ((first > middle) != (first > last)) {
                return first;
            } else if ((middle > first) != (middle > last)) {
                return middle;
            } else {
                return last;
            }
        }

        // Algoritmo de Shell Sort
        public void shellSort(Tabuleiro tabuleiro) {
            int n = tabuleiro.getTamanho();
            int[] gaps = {1, 5, 19, 41, 109, 301, 601}; // Exemplo de sequência de Sedgewick
            for (int gap : gaps) {
                if (gap >= n) continue;  // Ignore gaps maiores que o tamanho
                for (int i = gap; i < n; i++) {
                    char temp = tabuleiro.getCelula(i).getLetra();
                    int j = i;
                    while (j >= gap && tabuleiro.getCelula(j - gap).getLetra() > temp) {
                        tabuleiro.atualizarTabuleiro(j, tabuleiro.getCelula(j - gap).getLetra());
                        j -= gap;
                    }
                    tabuleiro.atualizarTabuleiro(j, temp);
                }
            }
        }

        // Função que troca os valores de duas células no tabuleiro
        private void swap(Tabuleiro tabuleiro, int i, int j) {
            if (i != j) {  // Apenas troca se os índices forem diferentes
                char temp = tabuleiro.getCelula(i).getLetra();
                tabuleiro.atualizarTabuleiro(i, tabuleiro.getCelula(j).getLetra());
                tabuleiro.atualizarTabuleiro(j, temp);
                swapsRealizados++; // Incrementa apenas se uma troca de fato ocorrer
            }
        }

        // Função de Heap Sort
        public void heapSort(Tabuleiro tabuleiro) {
            int n = tabuleiro.getTamanho();
            // Construir o heap (max heap)
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(tabuleiro, n, i);
            }
            // Extrair elementos um por um do heap
            for (int i = n - 1; i > 0; i--) {
                // Mover a raiz (máximo) para o final
                swap(tabuleiro, 0, i);
                // Chamar heapify no heap reduzido
                heapify(tabuleiro, i, 0);
            }
        }

        // Função heapify que assegura a propriedade do heap
        private void heapify(Tabuleiro tabuleiro, int n, int i) {
            int largest = i;  // Inicializa largest como a raiz
            int left = 2 * i + 1;  // Esquerda
            int right = 2 * i + 2;  // Direita

            // Verifica se o filho da esquerda é maior que a raiz
            if (left < n && tabuleiro.getCelula(left).getLetra() > tabuleiro.getCelula(largest).getLetra()) {
                largest = left;
            }

            // Verifica se o filho da direita é maior que a raiz ou o filho da esquerda
            if (right < n && tabuleiro.getCelula(right).getLetra() > tabuleiro.getCelula(largest).getLetra()) {
                largest = right;
            }

            // Se o maior não for a raiz, troca e continua heapifying
            if (largest != i) {
                swap(tabuleiro, i, largest);
                heapify(tabuleiro, n, largest);
            }
        }

        // Obtém o número de swaps realizados
        public int getSwapsRealizados() {
            return swapsRealizados;
        }
    }
}