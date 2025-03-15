/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Controller.OrdemController;

/**
 *
 * @author Cliente
 */
public class OrdemJogo {

    // Classe que representa o tabuleiro com 8 letras
    public static class Tabuleiro {

        private List<Character> letras; // Lista de letras que serão associadas às imagens
        private List<Image> imagens; // Lista de imagens associadas às letras

        public Tabuleiro(List<Image> imagens) {
            this.imagens = imagens;
            this.letras = new ArrayList<>();
            inicializarTabuleiro();
        }

        private void inicializarTabuleiro() {
            letras.add('A');
            letras.add('B');
            letras.add('C');
            letras.add('D');
            letras.add('E');
            letras.add('F');
            letras.add('G');
            letras.add('H');
            Collections.shuffle(letras);
            Collections.shuffle(imagens); // Embaralha as imagens também
        }

        public List<Character> getLetras() {
            return letras;
        }

        public List<Image> getImagens() {
            return imagens;
        }

        public boolean estaOrdenado() {
            for (int i = 0; i < letras.size() - 1; i++) {
                if (letras.get(i) > letras.get(i + 1)) {
                    return false;  // Não está ordenado
                }
            }
            return true;  // Está ordenado
        }

        // Atualiza as letras e as imagens no tabuleiro
        public void atualizarTabuleiro(int index, char letra) {
            letras.set(index, letra); // Atualiza a letra
            // Reorganiza a imagem associada à letra após a troca
            Collections.sort(imagens, (img1, img2) -> {
                // Ordena imagens de acordo com as letras
                return letras.indexOf(img1) - letras.indexOf(img2);
            });
        }
    }

    // Interface para callback de atualização do tabuleiro
    public interface AtualizacaoTabuleiro {

        void atualizarTabuleiro(int row, int col, Image image);
    }

    // Classe de ordenação que executa os algoritmos e atualiza o tabuleiro
    public static class Ordenacao {

        private final AtualizacaoTabuleiro callback;
        private final OrdemController controller;
        private int swapsRealizados = 0;

        // Modificado para receber o controller
        public Ordenacao(AtualizacaoTabuleiro callback, OrdemController controller) {
            this.callback = callback;
            this.controller = controller;  // Guarda a referência do controller
        }

        // Algoritmo de Bubble Sort
        public void bubbleSort(Tabuleiro tabuleiro) {
            for (int i = 0; i < tabuleiro.getLetras().size() - 1; i++) {
                boolean trocado = false;
                for (int j = 0; j < tabuleiro.getLetras().size() - i - 1; j++) {
                    if (tabuleiro.getLetras().get(j) > tabuleiro.getLetras().get(j + 1)) {
                        swap(tabuleiro, j, j + 1);
                        controller.executarOrdenacaoComDelay(() -> {});
                        trocado = true;
                    }
                }
                if (!trocado) {
                    break;
                }
            }
        }

        // Algoritmo de Selection Sort
        public void selectionSort(Tabuleiro tabuleiro) {
            for (int i = 0; i < tabuleiro.getLetras().size() - 1; i++) {
                int minIdx = i;
                for (int j = i + 1; j < tabuleiro.getLetras().size(); j++) {
                    if (tabuleiro.getLetras().get(j) < tabuleiro.getLetras().get(minIdx)) {
                        minIdx = j;
                    }
                }
                if (minIdx != i) {
                    swap(tabuleiro, i, minIdx);
                    controller.executarOrdenacaoComDelay(() -> {});
                }
            }
        }

        // Algoritmo de Insertion Sort
        public void insertionSort(Tabuleiro tabuleiro) {
            for (int i = 1; i < tabuleiro.getLetras().size(); i++) {
                char key = tabuleiro.getLetras().get(i);
                int j = i - 1;
                while (j >= 0 && tabuleiro.getLetras().get(j) > key) {
                    tabuleiro.getLetras().set(j + 1, tabuleiro.getLetras().get(j));
                    controller.executarOrdenacaoComDelay(() -> {});
                    j--;
                }
                tabuleiro.getLetras().set(j + 1, key);
                controller.executarOrdenacaoComDelay(() -> {});
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
            char pivot = tabuleiro.getLetras().get(high);
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (tabuleiro.getLetras().get(j) < pivot) {
                    i++;
                    swap(tabuleiro, i, j);
                    controller.executarOrdenacaoComDelay(() -> {});
                }
            }
            swap(tabuleiro, i + 1, high);
            controller.executarOrdenacaoComDelay(() -> {});
            return i + 1;
        }

        // Algoritmo de Shell Sort
        public void shellSort(Tabuleiro tabuleiro) {
            int n = tabuleiro.getLetras().size();
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    char temp = tabuleiro.getLetras().get(i);
                    int j = i;
                    while (j >= gap && tabuleiro.getLetras().get(j - gap) > temp) {
                        tabuleiro.getLetras().set(j, tabuleiro.getLetras().get(j - gap));
                        j -= gap;
                    }
                    tabuleiro.getLetras().set(j, temp);
                }
            }
        }

        // Algoritmo de Heap Sort
        public void heapSort(Tabuleiro tabuleiro) {
            int n = tabuleiro.getLetras().size();
            for (int i = n / 2 - 1; i >= 0; i--) {
                heapify(tabuleiro, n, i);
            }
            for (int i = n - 1; i > 0; i--) {
                swap(tabuleiro, 0, i);
                controller.executarOrdenacaoComDelay(() -> {});
                heapify(tabuleiro, i, 0);
            }
        }

        // Função de heapify para o Heap Sort
        private void heapify(Tabuleiro tabuleiro, int n, int i) {
            int largest = i;
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && tabuleiro.getLetras().get(left) > tabuleiro.getLetras().get(largest)) {
                largest = left;
            }
            if (right < n && tabuleiro.getLetras().get(right) > tabuleiro.getLetras().get(largest)) {
                largest = right;
            }
            if (largest != i) {
                swap(tabuleiro, i, largest);
                controller.executarOrdenacaoComDelay(() -> {});
                heapify(tabuleiro, n, largest);
            }
        }

        // Função que troca os valores de duas letras no tabuleiro
        public void swap(Tabuleiro tabuleiro, int i, int j) {
            // Troca as letras
            char temp = tabuleiro.getLetras().get(i);
            tabuleiro.getLetras().set(i, tabuleiro.getLetras().get(j));
            tabuleiro.getLetras().set(j, temp);

            // Troca as imagens associadas às letras
            Image tempImage = tabuleiro.getImagens().get(i);
            tabuleiro.getImagens().set(i, tabuleiro.getImagens().get(j));
            tabuleiro.getImagens().set(j, tempImage);
            
            // Chama o método de atraso após cada troca
            controller.executarOrdenacaoComDelay(() -> {
            
            // Atualiza o tabuleiro visualmente após a troca
            callback.atualizarTabuleiro(i / 4, i % 4, tabuleiro.getImagens().get(i));
            callback.atualizarTabuleiro(j / 4, j % 4, tabuleiro.getImagens().get(j));
            
            });
            
            swapsRealizados++;

        }

        public int getSwapsRealizados() {
            return swapsRealizados;
        }
    }
}
