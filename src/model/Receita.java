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
    
    public void imprimirReceita() {
        System.out.println("Nome da Receita: " + nome);
        System.out.println("Categorias: " + String.join(", ", categorias));
        
        System.out.println("Ingredientes:");
        for (Ingrediente ingrediente : ingredientes) {
            Quantidade quantidade = ingrediente.getQuantidade();
            String unidade = quantidade.getUnidade().getNome();
            System.out.println("- " + ingrediente.getNome() + ": " + quantidade.getValor() + " " + unidade);
        }
        
        System.out.println("Instruções:");
        for (int i = 0; i < instrucoes.size(); i++) {
            System.out.println((i + 1) + ". " + instrucoes.get(i));
        }
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
}
