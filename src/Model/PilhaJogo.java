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
    private boolean tempoAtivo;

    public PilhaJogo() {
        pilha = new Stack<>();
        contadorEmpilhadas = 0;
        contadorDesempilhadas = 0;
        pontos = 0;
        tempoAtivo = false;
    }

    public void empilhar(String pedra) {
        if (pilha.size() < 5) { // Limite de 5 pedras
            pilha.push(pedra);
            contadorEmpilhadas++;
            pontos += 10; // Ganha 10 pontos por empilhar
            if (!tempoAtivo) {
                iniciarTempo();
            }
        }
    }

    public String desempilhar() {
        if (!pilha.isEmpty()) {
            contadorDesempilhadas++;
            pontos += 5; // Ganha 5 pontos por desempilhar corretamente
            return pilha.pop();
        }
        return null;
    }

    public String verTopo() {
        return pilha.isEmpty() ? "Pilha vazia!" : pilha.peek();
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

    private void iniciarTempo() {
        tempoInicio = System.currentTimeMillis();
        tempoAtivo = true;
    }

    public long getTempoRestante() {
        long tempoDecorrido = System.currentTimeMillis() - tempoInicio;
        return Math.max(30000 - tempoDecorrido, 0);
    }

    public boolean tempoEsgotado() {
        return getTempoRestante() == 0;
    }

    public void resetar() {
        pilha.clear();
        contadorEmpilhadas = 0;
        contadorDesempilhadas = 0;
        pontos = 0;
        tempoAtivo = false;
    }
}
