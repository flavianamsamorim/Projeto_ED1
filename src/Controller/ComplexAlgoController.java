/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ComplexAlgoQuiz;
import View.ComplexAlgoView;
import java.util.LinkedHashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Cliente
 */
public class ComplexAlgoController {
    private ComplexAlgoQuiz model;
    private ComplexAlgoView view;

    public ComplexAlgoController(ComplexAlgoView view) {
        // Criando perguntas e respostas
        Map<String, String[]> perguntas = new LinkedHashMap<>();
        perguntas.put("1-O que a complexidade de algoritmos avalia \nprincipalmente?", new String[]{"A) Tamanho da entrada", "B) Número de iterações", "C) Complexidade do espaço"});
        perguntas.put("2-O que a análise de pior caso considera?", new String[]{"A) Complexidad de espaço", "B) Tempo mais longo de execução", "C) Melhor desempenho possível"});
        perguntas.put("3-Qual é a complexidade de tempo de um algoritmo \nque executa um número fixo de operações?", new String[]{"A) O(1)", "B) O(n)", "C) O(log n)"});
        perguntas.put("4-O que significa dizer que um problema é \n\"intratável\"?", new String[]{"A) Não pode ser resolvido em tempo polinomial", "B) Não tem solução", "C) Tem várias soluções"});
        perguntas.put("5-Qual é a complexidade de tempo de um algoritmo de \nbusca binária em uma lista ordenada de n elementos?", new String[]{"A) O(logn)", "B) O(n)", "C) O(1)"});
        perguntas.put("6-Qual classe de complexidade inclui problemas \nsolúveis em tempo polinomial?", new String[]{"A) NP", "B) P", "C) NP-completo"});
        perguntas.put("7-Qual é a notação para limite inferior que depende \ndo problema?", new String[]{"A) Ômega", "B) O grande", "C) Theta"});
        perguntas.put("8-Qual é a notação para limite superior que depende \ndo algoritmo?", new String[]{"A) Theta", "B) O grande", "C) Ômega"});
        perguntas.put("9-Qual é a complexidade  típica quando se reduz um \nproblema em subproblemas?", new String[]{"A) O(n)", "B) log n", "C) n log n"});
        perguntas.put("10-Qual é a complexidade de um algoritmo de força bruta \npara resolver o Problema do Caixeiro Viajante?", new String[]{"A) O(log n)", "B) O(2^ⁿ)", "C) O(n)"});

        String[] respostasCorretas = {"A", "B","A", "A","A", "B","A", "C","C", "B"}; // As respostas corretas

        // Criando o quiz
        this.model = new ComplexAlgoQuiz(perguntas, respostasCorretas);
        this.view = view;
    }

    public void carregarPergunta() {
    if (model.isFimQuiz()) {
        view.mostrarFeedback("Parabéns! Você concluiu o quiz!");
    } else {
        Map.Entry<String, String[]> perguntaEntry = model.getPerguntaAtual();
        view.atualizarPergunta(perguntaEntry.getKey(), perguntaEntry.getValue()[0], perguntaEntry.getValue()[1], perguntaEntry.getValue()[2]);
        
        view.ativarOpcoes(); // Reativa os botões para a nova pergunta
        view.limparFeedback(); // Limpa a mensagem da resposta anterior
    }
}

    // Método para verificar a resposta do usuário
    public void verificarResposta(String resposta) {
        boolean correta = model.verificarResposta(resposta);
        
        if (model.isFimQuiz()) {
            // Se for o fim do quiz, exibe um alerta em vez de feedback no label
            Alert alertaFimQuiz = new Alert(AlertType.INFORMATION);
            alertaFimQuiz.setTitle("Fim do Quiz");
            alertaFimQuiz.setHeaderText("Parabéns!");
            alertaFimQuiz.setContentText("Você completou o quiz. Deseja reiniciar?");
            alertaFimQuiz.showAndWait(); // Mostra o alerta e aguarda o fechamento
            
            // Aqui você pode reiniciar o quiz ou encerrar, conforme preferir.
            // Para reiniciar o quiz, você pode chamar:
            model.reiniciarQuiz();
            carregarPergunta();
        } else {
            // Caso contrário, apenas exibe o feedback normal
            if (correta) {
                view.mostrarFeedback("Resposta correta! 🎉");
            } else {
                view.mostrarFeedback("Resposta errada! ❌");
            }
            view.desativarOpcoes(); // Desativa os botões após a resposta
            model.avancarPergunta(); // Avança para a próxima pergunta
        }
    }
}