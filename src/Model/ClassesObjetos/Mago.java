package Model.ClassesObjetos;

import java.util.Random;

public class Mago extends Personagem {
    private static final Random rand = new Random();

    // Método auxiliar para gerar valores dentro de um intervalo [min, max]
    private static int randomBetween(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

    // Construtor padrão: gera atributos aleatórios dentro de faixas
    public Mago(String nome) {
        super(
            nome,
            randomBetween(60, 130),   // Vida entre 70 e 90
            randomBetween(80, 140),  // Mana entre 80 e 120
            randomBetween(30, 70),   // Ataque entre 10 e 15
            randomBetween(3, 7)      // Defesa entre 3 e 7
        );
    }

    // Construtor alternativo (para carregar do arquivo texto/binário)
    public Mago(String nome, int vida, int mana, int ataque, int defesa) {
        super(nome, vida, mana, ataque, defesa);
    }

    @Override
    public void habilidadeEspecial(Personagem alvo) {
        if (this.mana >= 20) {
            int dano = (this.ataque * 3) - alvo.getDefesa();
            if (dano < 0) dano = 0;
            alvo.receberDano(dano);
            this.mana -= 20;
        }
    }
}
