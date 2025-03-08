package Model;

import java.util.Random;

public class Arqueiro extends Personagem {
    private static final Random rand = new Random();

    // Método auxiliar para gerar valores dentro de um intervalo [min, max]
    private static int randomBetween(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    // Construtor padrão: gera atributos aleatórios dentro de faixas
    public Arqueiro(String nome) {
        super(
            nome,
            randomBetween(70, 120),  // Vida entre 80 e 100
            randomBetween(40, 70),   // Mana entre 40 e 60
            randomBetween(20, 50),   // Ataque entre 16 e 20
            randomBetween(3, 15)      // Defesa entre 3 e 7
        );
    }

    // Construtor alternativo (para carregar do arquivo texto/binário)
    public Arqueiro(String nome, int vida, int mana, int ataque, int defesa) {
        super(nome, vida, mana, ataque, defesa);
    }

    @Override
    public void habilidadeEspecial(Personagem alvo) {
        if (this.mana >= 15) {
            int dano = (this.ataque * 2) - alvo.getDefesa();
            if (dano < 0) dano = 0;
            alvo.receberDano(dano);
            this.mana -= 15;
        }
    }
}
