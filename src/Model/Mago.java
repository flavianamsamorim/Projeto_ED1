package Model;

//classe do jogo de classes e objetos
public class Mago extends Personagem {
    // Construtor padrão
    public Mago(String nome) {
        super(nome, 80, 100, 12, 5);
    }

    // Construtor alternativo
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
