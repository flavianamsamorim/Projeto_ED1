/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;
import java.util.*;
/**
 *
 * @author Cliente
 */
public class HuffmanCompressor {
    protected HuffmanNode root;  // A árvore de Huffman
    private Map<Character, String> huffmanCodes;
    private Map<String, Character> reverseCodes;
    private ProgressListener progressListener;

    public HuffmanCompressor() {
        this.huffmanCodes = new HashMap<>();
        this.reverseCodes = new HashMap<>();
    }

    // Permite ao Controller definir um listener de progresso
    public void setProgressListener(ProgressListener listener) {
        this.progressListener = listener;
    }

    // Constrói a árvore de Huffman a partir do mapa de frequências
    public void buildTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(left.frequency + right.frequency, left, right);
            pq.add(parent);
        }

        root = pq.poll(); // A raiz da árvore de Huffman
        generateCodes(root, "");  // Gera os códigos de Huffman a partir da árvore
    }

    // Gera os códigos de Huffman recursivamente
    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.character, code);
            reverseCodes.put(code, node.character);
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    // Comprime o arquivo de entrada e grava o arquivo comprimido de saída
    public void compressFile(File inputFile, File outputFile) throws IOException {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        StringBuilder content = new StringBuilder();

        // Lê o arquivo e calcula a frequência de caracteres
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int c;
            long totalChars = inputFile.length(); // Para calcular o progresso
            long processedChars = 0;
            while ((c = reader.read()) != -1) {
                char character = (char) c;
                content.append(character);
                frequencyMap.put(character, frequencyMap.getOrDefault(character, 0) + 1);
                processedChars++;

                // Notificar o progresso
                if (progressListener != null) {
                    double progress = (double) processedChars / totalChars;
                    progressListener.onProgress("Comprimindo...", progress);
                }
            }
        }

        // Construir a árvore de Huffman a partir do mapa de frequências
        buildTree(frequencyMap);

        // Tente usar o BitOutputStream para garantir o fechamento correto
        try (BitOutputStream out = new BitOutputStream(new FileOutputStream(outputFile))) {
            // Escreve o mapa de frequências
            out.writeObject(frequencyMap);

            // Escreve os bits comprimidos no arquivo de saída
            for (char c : content.toString().toCharArray()) {
                out.writeBits(huffmanCodes.get(c)); // Escreve os bits comprimidos
            }

            // Garantir que o último byte seja escrito corretamente
            out.flush();  // Força a gravação dos bits finais
        } catch (IOException e) {
            // Trate o erro adequadamente
            e.printStackTrace();
            throw new IOException("Erro ao tentar escrever no arquivo comprimido.", e);
        }
    }

    // Descomprime o arquivo e grava o arquivo descomprimido de volta
    public void decompressFile(File inputFile, File outputFile) throws IOException, ClassNotFoundException {
        try (BitInputStream in = new BitInputStream(new FileInputStream(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            Map<Character, Integer> frequencyMap = (Map<Character, Integer>) in.readObject();
            buildTree(frequencyMap);  // Reconstruir a árvore de Huffman

            StringBuilder encodedText = new StringBuilder();
            int bit;
            long totalBits = inputFile.length() * 8;  // Estima o total de bits (baseado no tamanho do arquivo)
            long processedBits = 0;
            while ((bit = in.readBit()) != -1) {
                encodedText.append(bit);
                processedBits++;

                // Notificar o progresso, se possível
                if (progressListener != null) {
                    double progress = (double) processedBits / totalBits;
                    progressListener.onProgress("Descomprimindo...", progress);
                }

                if (reverseCodes.containsKey(encodedText.toString())) {
                    writer.write(reverseCodes.get(encodedText.toString()));  // Escreve o caractere decodificado
                    encodedText.setLength(0);  // Reseta o StringBuilder
                }
            }
        }
    }

    // Interface para o progresso de operações de compressão/descompressão
    public interface ProgressListener {
        void onProgress(String message, double progress);
    }

    // Classe para escrever bits em um arquivo de saída
    class BitOutputStream implements AutoCloseable {
        private OutputStream out;
        private int currentByte;
        private int numBitsInCurrentByte;

        public BitOutputStream(OutputStream out) {
            this.out = out;
            this.currentByte = 0;
            this.numBitsInCurrentByte = 0;
        }

        // Escreve um único bit
        public void writeBit(int bit) throws IOException {
            if (bit != 0 && bit != 1) {
                throw new IllegalArgumentException("O bit deve ser 0 ou 1.");
            }

            currentByte = (currentByte << 1) | bit; // Desloca o bit para a posição correta
            numBitsInCurrentByte++;

            if (numBitsInCurrentByte == 8) {
                out.write(currentByte);  // Escreve o byte completo
                currentByte = 0;         // Reseta o byte
                numBitsInCurrentByte = 0;
            }
        }

        // Escreve múltiplos bits (por exemplo, um código de Huffman)
        public void writeBits(String bits) throws IOException {
            for (char bit : bits.toCharArray()) {
                writeBit(bit == '1' ? 1 : 0); // Escreve cada bit individualmente
            }
        }

        // Escreve um objeto (como o mapa de frequências)
        public void writeObject(Object obj) throws IOException {
            try (ObjectOutputStream objectOut = new ObjectOutputStream(out)) {
                objectOut.writeObject(obj);
            }
        }

        // Método para forçar a gravação de bits restantes
        public void flush() throws IOException {
            if (numBitsInCurrentByte > 0) {
                currentByte <<= (8 - numBitsInCurrentByte);  // Preenche os bits restantes com 0
                out.write(currentByte);
            }
        }

        // Fechar o stream e garantir que os dados remanescentes sejam escritos
        @Override
        public void close() throws IOException {
            flush();  // Chama flush para garantir que os bits finais sejam gravados
            out.close();
        }
    }

    // Classe para ler bits de um arquivo de entrada
    class BitInputStream implements AutoCloseable {
        private InputStream in;
        private int currentByte;
        private int numBitsInCurrentByte;

        public BitInputStream(InputStream in) throws IOException {
            this.in = in;
            this.numBitsInCurrentByte = 8; // Inicializa com 8 bits prontos para leitura
        }

        // Lê um único bit
        public int readBit() throws IOException {
            if (numBitsInCurrentByte == 8) {
                currentByte = in.read();  // Lê um byte do arquivo
                if (currentByte == -1) {
                    return -1;  // Fim do arquivo
                }
                numBitsInCurrentByte = 0;
            }

            int bit = (currentByte >> (7 - numBitsInCurrentByte)) & 1;  // Extrai o bit
            numBitsInCurrentByte++;
            return bit;
        }

        // Lê uma sequência de bits
        public String readBits(int numBits) throws IOException {
            StringBuilder bits = new StringBuilder();
            for (int i = 0; i < numBits; i++) {
                int bit = readBit();
                if (bit == -1) {
                    break;
                }
                bits.append(bit);
            }
            return bits.toString();
        }

        // Lê um objeto (como o mapa de frequências)
        public Object readObject() throws IOException, ClassNotFoundException {
            try (ObjectInputStream objectIn = new ObjectInputStream(in)) {
                return objectIn.readObject();
            }
        }

        // Fechar o stream
        @Override
        public void close() throws IOException {
            in.close();
        }
    }

    // Classe para representar os nós da árvore de Huffman
    protected class HuffmanNode implements Comparable<HuffmanNode> {
        char character;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(char character, int frequency) {
            this.character = character;
            this.frequency = frequency;
            this.left = null;
            this.right = null;
        }

        HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
            this.character = '\0'; // No interno não tem caractere
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(HuffmanNode o) {
            return Integer.compare(this.frequency, o.frequency);
        }
    }
}