package Model;

import java.io.Serializable;

public abstract class Personagem implements Serializable {
    private static final long serialVersionUID = 1L; // recomendado para serialização

    protected String nome;
    protected int vida;
    protected int mana;
    protected int ataque;
    protected int defesa;

    public Personagem(String nome, int vida, int mana, int ataque, int defesa) {
        this.nome = nome;
        this.vida = vida;
        this.mana = mana;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getMana() {
        return mana;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    //metodo para atacar
    public void atacar(Personagem alvo) {
        int dano = this.ataque - alvo.getDefesa();
        if (dano < 0) {
            dano = 0;
        }
        alvo.receberDano(dano);
    }

    public abstract void habilidadeEspecial(Personagem alvo);

    @Override
    public String toString() {
        return String.format("%s (HP: %d, MP: %d, ATK: %d, DEF: %d)",
                nome, vida, mana, ataque, defesa);
    }
}
