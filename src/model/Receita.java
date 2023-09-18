package model;

import java.io.Serializable;
import java.util.List;

public class Receita implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6545758111418610138L;
	private String nome;
    private List<Ingrediente> ingredientes;
    private List<String> instrucoes;
    private List<String> categorias;

    public Receita(String nome, List<Ingrediente> ingredientes, List<String> instrucoes, List<String> categorias) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.instrucoes = instrucoes;
        this.categorias = categorias;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\nNome da Receita: ").append(nome).append("\n");
        sb.append("Categorias: ").append(String.join(", ", categorias)).append("\n");
        
        sb.append("Ingredientes:\n");
        for (Ingrediente ingrediente : ingredientes) {
            Quantidade quantidade = ingrediente.getQuantidade();
            String unidade = quantidade.getUnidade().getNome();
            sb.append("- ").append(ingrediente.getNome()).append(": ").append(quantidade.getValor()).append(" ").append(unidade).append("\n");
        }
        
        sb.append("Instruções:\n");
        for (int i = 0; i < instrucoes.size(); i++) {
            sb.append(instrucoes.get(i)).append("\n");
        }

        return sb.toString();
    }


    public String getNome() {
        return nome;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public List<String> getInstrucoes() {
        return instrucoes;
    }

    public List<String> getCategorias() {
        return categorias;
    }
    
    public boolean contemIngrediente(Ingrediente ingrediente) {
	    for (Ingrediente i : getIngredientes()) {
	        if (i.getNome().equals(ingrediente.getNome())) {
	            return true;
	        }
	    }
	    return false;
	}

	public void setNome(String novoNomeReceita) {
		this.nome = novoNomeReceita;
	}

	public void setInstrucoes(List<String> instrucoesEditadas) {
		this.instrucoes = instrucoesEditadas;
		
	}

	public void setIngredientes(List<Ingrediente> ingredientesEditados) {
		this.ingredientes = ingredientesEditados;
		
	}

	public void setCategorias(List<String> categoriasEditadas) {
		this.categorias = categoriasEditadas;
		
	}
}
