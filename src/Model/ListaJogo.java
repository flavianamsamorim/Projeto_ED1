/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Cliente
 */
public class ListaJogo {
   // Classe Carta
    public static class Carta {
        private String imagem;
        private boolean virada;

        public Carta(String imagem) {
            this.imagem = imagem;
            this.virada = false;
        }

        public String getImagem() {
            return imagem;
        }

        public boolean isVirada() {
            return virada;
        }

        public void virar() {
            this.virada = !this.virada;
        }
    }

    // Classe Nó (Elemento da Lista Encadeada)
    public static class No {
        public Carta carta;
        public No proximo;

        public No(Carta carta) {
            this.carta = carta;
            this.proximo = null;
        }
    }

    // Classe ListaEncadeada
    public static class ListaEncadeada {
        private No cabeca;
        private int tamanho;

        public void adicionar(Carta carta) {
            No novoNo = new No(carta);
            if (cabeca == null) {
                cabeca = novoNo;
            } else {
                No atual = cabeca;
                while (atual.proximo != null) {
                    atual = atual.proximo;
                }
                atual.proximo = novoNo;
            }
            tamanho++;
        }

        public Carta get(int index) {
            if (index < 0 || index >= tamanho) return null;
            No atual = cabeca;
            for (int i = 0; i < index; i++) {
                atual = atual.proximo;
            }
            return atual.carta;
        }

        public void embaralhar() {
            Random rand = new Random();
            Carta[] cartas = new Carta[tamanho];
            No atual = cabeca;
            int i = 0;
            while (atual != null) {
                cartas[i++] = atual.carta;
                atual = atual.proximo;
            }
            for (int j = tamanho - 1; j > 0; j--) {
                int k = rand.nextInt(j + 1);
                Carta temp = cartas[j];
                cartas[j] = cartas[k];
                cartas[k] = temp;
            }
            atual = cabeca;
            i = 0;
            while (atual != null) {
                atual.carta = cartas[i++];
                atual = atual.proximo;
            }
        }

        public int getTamanho() {
            return tamanho;
        }
    }

    // Classe Tabuleiro (Gerenciador do Jogo)
    public static class Tabuleiro {
        private ListaEncadeada cartas;

        public Tabuleiro(String[] imagens, int tamanho) {
            cartas = new ListaEncadeada();
            List<String> imagensDuplicadas = new ArrayList<>();
            for (int i = 0; i < tamanho * tamanho / 2; i++) {
                imagensDuplicadas.add(imagens[i]);
                imagensDuplicadas.add(imagens[i]);
            }
            Collections.shuffle(imagensDuplicadas);
            for (String img : imagensDuplicadas) {
                cartas.adicionar(new Carta(img));
            }
        }

        public Carta getCarta(int index) {
            return cartas.get(index);
        }

        public int getTamanho() {
            return cartas.getTamanho();
        }
    } 
    
}
