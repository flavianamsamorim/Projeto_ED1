package Model;

public class Guerreiro extends Personagem {
    // Construtor padrão (valores fixos)
    public Guerreiro(String nome) {
        super(nome, 120, 30, 15, 10);
    }

    // Construtor alternativo (para carregar do arquivo texto)
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
