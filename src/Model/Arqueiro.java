package Model;

//classe do jogo de classes e objetos
public class Arqueiro extends Personagem {
    // Construtor padrão
    public Arqueiro(String nome) {
        super(nome, 90, 50, 18, 5);
    }

    // Construtor alternativo
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
