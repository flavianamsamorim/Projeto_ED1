package Model.ClassesObjetos;

import java.util.Random;

public class Guerreiro extends Personagem {
    private static final Random rand = new Random();

    // Método auxiliar para gerar valores dentro de um intervalo [min, max]
    private static int randomBetween(int min, int max) {
        // nextInt((max - min) + 1) gera um número entre 0 e (max-min) inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

    // Construtor padrão: gera atributos aleatórios dentro de faixas
    public Guerreiro(String nome) {
        super(
            nome,
            randomBetween(110, 150), // Vida entre 110 e 130
            randomBetween(20, 50),   // Mana entre 20 e 40
            randomBetween(10, 40),   // Ataque entre 14 e 20
            randomBetween(8, 18)     // Defesa entre 8 e 12
        );
    }

    // Construtor alternativo (para carregar do arquivo texto/binário, mantendo atributos exatos)
    public Guerreiro(String nome, int vida, int mana, int ataque, int defesa) {
        super(nome, vida, mana, ataque, defesa);
    }

    @Override
    public void habilidadeEspecial(Personagem alvo) {
        if (this.mana >= 10) {
            int dano = (this.ataque * 2) - alvo.getDefesa();
            if (dano < 0) dano = 0;
            alvo.receberDano(dano);
            this.mana -= 10;
        }
    }
}
