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

    public PilhaJogo() {
        pilha = new Stack<>();
    }

    public void empilhar(String item) {
        pilha.push(item);
    }

    public String desempilhar() {
        return pilha.isEmpty() ? "Pilha vazia!" : pilha.pop();
    }

    public String verTopo() {
        return pilha.isEmpty() ? "Pilha vazia!" : pilha.peek();
    }

    public boolean estaVazia() {
        return pilha.isEmpty();
    }
}
