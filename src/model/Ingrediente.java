package model;

import java.io.Serializable;

public class Ingrediente implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 155109323256145948L;
	private String nome;
    private Quantidade quantidade;

    public Ingrediente(String nome, Quantidade quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }
}