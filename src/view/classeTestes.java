package view;

import model.SElecaoSupimpa;

public class classeTestes {

	public static void main(String[] args) {
		SElecaoSupimpa se = new SElecaoSupimpa();
		
		//System.out.println(se.getReceitasCliente());
		
		se.filtrarReceitasIngredienteCategoria("Sobremesa");
		se.removerReceitasIngredienteCliente(se.getReceitas().get(0).getIngredientes().get(0));
		
		System.out.println("\n\n" + se.getReceitasCliente());
	}

}
