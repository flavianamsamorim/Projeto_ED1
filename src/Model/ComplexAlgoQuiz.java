	/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.*;
/**
 *
 * @author Cliente
 */
public class ComplexAlgoQuiz {
    private List<Map.Entry<String, String[]>> perguntasOrdenadas;
    private String[] respostasCorretas;
    private int perguntaAtual = 0;
    private int pontuacao = 0;
    private List<Integer> ranking = new ArrayList<>();
    private Map<String, String[]> perguntas;

    public ComplexAlgoQuiz(Map<String, String[]> perguntas, String[] respostas) {
        this.perguntasOrdenadas = new ArrayList<>(perguntas.entrySet());
        this.perguntas = perguntas;
        this.respostasCorretas = respostasCorretas;
        this.perguntaAtual = 0;
        this.pontuacao = 0;
        this.respostasCorretas = respostas;
    }

    public Map.Entry<String, String[]> getPerguntaAtual() {
        if (perguntaAtual < perguntasOrdenadas.size()) {
            return perguntasOrdenadas.get(perguntaAtual);
        }
        return null;
    }

    public boolean verificarResposta(String resposta) {
    if (perguntaAtual < 0 || perguntaAtual >= respostasCorretas.length) {
        System.err.println("Erro: Índice fora do limite! perguntaAtual = " + perguntaAtual);
        return false;
    }

    if (resposta.equals(respostasCorretas[perguntaAtual])) {
        pontuacao++;
        return true;
    }
    return false;
}

    public void avancarPergunta() {
    if (perguntaAtual < perguntas.size()) {
        perguntaAtual++;
        } 
    }

    public boolean isFimQuiz() {
        return perguntaAtual >= perguntasOrdenadas.size();
    }

    public void reiniciarQuiz() {
        perguntaAtual = 0;
        pontuacao = 0;
    }

    public int getPontuacao() {
        return pontuacao;
    }
}