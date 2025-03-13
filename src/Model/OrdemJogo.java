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
        private static final int TAMANHO = 8;
        private Celula[] grid;

        public Tabuleiro() {
            grid = new Celula[TAMANHO];
            inicializarTabuleiroAleatorio();
        }

        private void inicializarTabuleiroAleatorio() {
            Random rand = new Random();
            for (int i = 0; i < TAMANHO; i++) {
                char letra = (char) ('A' + i);
                grid[i] = new Celula(letra);
            }
        }

        public Celula getCelula(int i) {
            return (i >= 0 && i < TAMANHO) ? grid[i] : null;
        }

        public void atualizarTabuleiro(int i, char letra) {
            grid[i].setLetra(letra);
        }

        public int getTamanho() {
            return TAMANHO;
        }

        public Celula[] getGrid() {
            return grid;
        }

        public boolean estaOrdenado() {
            for (int i = 0; i < TAMANHO - 1; i++) {
                if (grid[i].getLetra() > grid[i + 1].getLetra()) {
                    return false;
                }
            }
            return true;
        }
    }

    public interface AtualizacaoTabuleiro {
        void atualizarTabuleiro(int i, char letra);
    }

    public static class Ordenacao {
        private final AtualizacaoTabuleiro callback;
        private int swapsRealizados = 0;

        public Ordenacao(AtualizacaoTabuleiro callback) {
            this.callback = callback;
        }

        public void bubbleSort(Tabuleiro tabuleiro) {
            for (int i = 0; i < tabuleiro.getTamanho() - 1; i++) {
                boolean trocado = false;
                for (int j = 0; j < tabuleiro.getTamanho() - i - 1; j++) {
                    if (tabuleiro.getCelula(j).getLetra() > tabuleiro.getCelula(j + 1).getLetra()) {
                        swap(tabuleiro, j, j + 1);
                        trocado = true;
                    }
                }
                if (!trocado) break;
            }
        }

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

        public void quickSort(Tabuleiro tabuleiro, int low, int high) {
            if (low < high) {
                int pi = partition(tabuleiro, low, high);
                quickSort(tabuleiro, low, pi - 1);
                quickSort(tabuleiro, pi + 1, high);
            }
        }

        private int partition(Tabuleiro tabuleiro, int low, int high) {
            char pivot = tabuleiro.getCelula(high).getLetra();
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

        public void shellSort(Tabuleiro tabuleiro) {
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
                }
            }
        }

        public void heapSort(Tabuleiro tabuleiro) {
            int n = tabuleiro.getTamanho();
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(tabuleiro, n, i);
            }
            for (int i = n - 1; i > 0; i--) {
                swap(tabuleiro, 0, i);
                heapify(tabuleiro, i, 0);
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
                swap(tabuleiro, i, largest);
                heapify(tabuleiro, n, largest);
            }
        }

        private void swap(Tabuleiro tabuleiro, int i, int j) {
            char temp = tabuleiro.getCelula(i).getLetra();
            tabuleiro.atualizarTabuleiro(i, tabuleiro.getCelula(j).getLetra());
            tabuleiro.atualizarTabuleiro(j, temp);
            swapsRealizados++;
        }
    }
}