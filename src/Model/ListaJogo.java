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
            List<Carta> cartasLista = new ArrayList<>();
            No atual = cabeca;

            while (atual != null) {
                cartasLista.add(atual.carta);
                atual = atual.proximo;
            }

            Collections.shuffle(cartasLista);

            atual = cabeca;
            for (Carta carta : cartasLista) {
                atual.carta = carta;
                atual = atual.proximo;
            }
        }

        public int getTamanho() {
            return tamanho;
        }
    }

    // Classe Tabuleiro
    public static class Tabuleiro {
        private ListaEncadeada cartas;
        private int linhas, colunas;

        public Tabuleiro(String[] imagens, int linhas, int colunas) {
            this.linhas = linhas;
            this.colunas = colunas;
            int totalCartas = linhas * colunas;

            if (totalCartas % 2 != 0) {
                throw new IllegalArgumentException("O tabuleiro deve ter um número par de cartas!");
            }

            cartas = new ListaEncadeada();
            List<String> imagensDuplicadas = new ArrayList<>();

            for (int i = 0; i < totalCartas / 2; i++) {
                imagensDuplicadas.add(imagens[i % imagens.length]);
                imagensDuplicadas.add(imagens[i % imagens.length]);
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

        public int getLinhas() {
            return linhas;
        }

        public int getColunas() {
            return colunas;
        }
    }
}