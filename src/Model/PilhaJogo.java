/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.util.Stack;
/**
 *
 * @author Cliente
 */
public class PilhaJogo {
    private Stack<String> pilha;
    private int contadorEmpilhadas;
    private int contadorDesempilhadas;
    private long tempoInicio;
    private int pontos;
    private boolean tempoCongelado;

    public PilhaJogo() {
        pilha = new Stack<>();
        contadorEmpilhadas = 0;
        contadorDesempilhadas = 0;
        pontos = 0;
        tempoCongelado = false;
    }

    public void empilhar(String pedra) {
        pilha.push(pedra);
        contadorEmpilhadas++;
        pontos += 10; // Ganha pontos por empilhar
    }

    public String desempilhar() {
        if (!pilha.isEmpty()) {
            contadorDesempilhadas++;
            pontos -= 5; // Perde pontos por desempilhar
            return pilha.pop();
        }
        return "Pilha vazia!";
    }

    public String verTopo() {
        if (!pilha.isEmpty()) {
            return pilha.peek();
        }
        return "Pilha vazia!";
    }

    public boolean estaVazia() {
        return pilha.isEmpty();
    }

    public int getContadorEmpilhadas() {
        return contadorEmpilhadas;
    }

    public int getContadorDesempilhadas() {
        return contadorDesempilhadas;
    }

    public int getPontos() {
        return pontos;
    }

    public void iniciarTempo() {
        tempoInicio = System.currentTimeMillis();
    }

    public long getTempoDecorrido() {
        if (tempoCongelado) {
            return tempoInicio;
        }
        return System.currentTimeMillis() - tempoInicio;
    }

    public void congelarTempo() {
        tempoCongelado = true;
    }

    public void descongelarTempo() {
        tempoCongelado = false;
        tempoInicio = System.currentTimeMillis();
    }

    public void resetar() {
        pilha.clear();
        contadorEmpilhadas = 0;
        contadorDesempilhadas = 0;
        pontos = 0;
        tempoCongelado = false;
    }
}