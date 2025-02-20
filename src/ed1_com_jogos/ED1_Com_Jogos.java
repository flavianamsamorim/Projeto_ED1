/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed1_com_jogos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Cliente
 */
public class ED1_Com_Jogos extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Jogos Educativos de Programação");
        
        // Botões para acessar os jogos
        Button btnEntradaSaida = new Button("Entrada e Saída");
        Button btnTiposDeDados = new Button("Tipos de Dados");
        Button btnOperadores = new Button("Operadores");
        Button btnCondicaoRepeticao = new Button("Condição e Repetição");
        Button btnClassesObjetos = new Button("Classes e Objetos");
        Button btnRecursividade = new Button("Recursividade");
        Button btnVetoresMatrizes = new Button("Vetores, Matrizes, Strings");
        Button btnArquivos = new Button("Arquivos");
        Button btnGeneric = new Button("Generic");
        Button btnCollection = new Button("Collection");
        Button btnPilhas = new Button("Pilhas");
        Button btnFilas = new Button("Filas");
        Button btnListasEncadeadas = new Button("Listas Encadeadas");
        Button btnComplexidade = new Button("Complexidade de Algoritmos");
        Button btnBusca = new Button("Algoritmos de Busca");
        Button btnOrdenacao = new Button("Algoritmos de Ordenação");
        
        // Layout principal
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
            btnEntradaSaida, btnTiposDeDados, btnOperadores, btnCondicaoRepeticao,
            btnClassesObjetos, btnRecursividade, btnVetoresMatrizes, btnArquivos,
            btnGeneric, btnCollection, btnPilhas, btnFilas, btnListasEncadeadas,
            btnComplexidade, btnBusca, btnOrdenacao
        );
        
        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
