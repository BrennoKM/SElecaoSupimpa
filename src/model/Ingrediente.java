package model;

import java.io.Serializable;

import unidades.UnidadeMedida;

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

    public Ingrediente(String nomeIngrediente) {
		this.nome = nomeIngrediente;
	}

	public String getNome() {
        return nome;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Quantidade quantidade) {
    	this.quantidade = quantidade;
    }
    
    public UnidadeMedida getUnidade() {
    	return quantidade.getUnidade();
    }

	public void setNome(String novoNomeIngrediente) {
		this.nome = novoNomeIngrediente;
		
	}
	
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome);

        if (quantidade != null) {
            sb.append(", Quantidade: ").append(quantidade.getValor());
            sb.append(" ").append(quantidade.getUnidade().getNome());
        }

        return sb.toString();
    }
}