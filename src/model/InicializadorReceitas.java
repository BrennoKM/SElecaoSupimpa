package model;

import java.util.ArrayList;
import java.util.List;

import unidades.Grama;
import unidades.Litro;
import unidades.Mililitro;
import unidades.Unidade;

public class InicializadorReceitas {
	public static List<Receita> inicializarReceitas() {
		List<Receita> receitas = new ArrayList<>();

		// Receita 1: Bolo Simples
		List<Ingrediente> ingredientesBolo = new ArrayList<>();
		ingredientesBolo.add(new Ingrediente("Farinha", new Quantidade(200, new Grama())));
		ingredientesBolo.add(new Ingrediente("Açúcar", new Quantidade(100, new Grama())));
		ingredientesBolo.add(new Ingrediente("Leite", new Quantidade(250, new Mililitro())));

		List<String> instrucoesBolo = new ArrayList<>();
		instrucoesBolo.add("1. Misture todos os ingredientes.");
		instrucoesBolo.add("2. Asse no forno a 180°C por 30 minutos.");
		instrucoesBolo.add("3. Deixe esfriar e sirva.");

		List<String> categoriasBolo = new ArrayList<>();
		categoriasBolo.add("Sobremesa");
		categoriasBolo.add("Bolo");

		Receita receitaBolo = new Receita("Bolo de Chocolate", ingredientesBolo, instrucoesBolo, categoriasBolo);
		receitas.add(receitaBolo);

		// Receita 2: Pudim
		List<Ingrediente> ingredientesPudim = new ArrayList<>();
		ingredientesPudim.add(new Ingrediente("Leite Condensado", new Quantidade(395, new Grama())));
		ingredientesPudim.add(new Ingrediente("Leite", new Quantidade(2, new Litro())));
		ingredientesPudim.add(new Ingrediente("Ovos", new Quantidade(3, new Unidade())));

		List<String> instrucoesPudim = new ArrayList<>();
		instrucoesPudim.add("1. Bata todos os ingredientes no liquidificador.");
		instrucoesPudim.add("2. Despeje a mistura em uma forma caramelizada.");
		instrucoesPudim.add("3. Asse em banho-maria a 180°C por 1 hora.");
		instrucoesPudim.add("4. Deixe esfriar e desenforme.");

		List<String> categoriasPudim = new ArrayList<>();
		categoriasPudim.add("Sobremesa");
		categoriasPudim.add("Pudim");

		Receita receitaPudim = new Receita("Pudim de Leite Condensado", ingredientesPudim, instrucoesPudim,
				categoriasPudim);
		receitas.add(receitaPudim);

		// Receita 3: Massa de Pizza
		List<Ingrediente> ingredientesPizza = new ArrayList<>();
		ingredientesPizza.add(new Ingrediente("Farinha de trigo", new Quantidade(300, new Grama())));
		ingredientesPizza.add(new Ingrediente("Água morna", new Quantidade(200, new Mililitro())));
		ingredientesPizza.add(new Ingrediente("Fermento biológico", new Quantidade(10, new Grama())));
		ingredientesPizza.add(new Ingrediente("Sal", new Quantidade(5, new Grama())));
		ingredientesPizza.add(new Ingrediente("Azeite de oliva", new Quantidade(30, new Mililitro())));

		List<String> instrucoesPizza = new ArrayList<>();
		instrucoesPizza.add("1. Dissolva o fermento na água morna.");
		instrucoesPizza.add("2. Em uma tigela grande, misture a farinha e o sal.");
		instrucoesPizza
				.add("3. Adicione a mistura de fermento e água à tigela e amasse até formar uma massa homogênea.");
		instrucoesPizza.add("4. Cubra a massa com um pano úmido e deixe descansar por 1 hora.");
		instrucoesPizza.add(
				"5. Abra a massa em uma superfície enfarinhada, adicione o molho de tomate e os ingredientes de sua escolha.");
		instrucoesPizza.add("6. Asse no forno a 220°C por 15-20 minutos.");

		List<String> categoriasPizza = new ArrayList<>();
		categoriasPizza.add("Prato Principal");
		categoriasPizza.add("Pizza");

		Receita receitaPizza = new Receita("Massa de Pizza", ingredientesPizza, instrucoesPizza, categoriasPizza);
		receitas.add(receitaPizza);

		// Receita 4: Salada Caesar
		List<Ingrediente> ingredientesSalada = new ArrayList<>();
		ingredientesSalada.add(new Ingrediente("Alface romana", new Quantidade(1, new Unidade())));
		ingredientesSalada.add(new Ingrediente("Croutons", new Quantidade(50, new Grama())));
		ingredientesSalada.add(new Ingrediente("Queijo parmesão ralado", new Quantidade(30, new Grama())));
		ingredientesSalada.add(new Ingrediente("Peito de frango grelhado", new Quantidade(200, new Grama())));
		ingredientesSalada.add(new Ingrediente("Molho Caesar", new Quantidade(60, new Mililitro())));

		List<String> instrucoesSalada = new ArrayList<>();
		instrucoesSalada.add("1. Lave e rasgue as folhas de alface.");
		instrucoesSalada.add("2. Misture as folhas de alface, croutons, queijo parmesão e peito de frango grelhado.");
		instrucoesSalada.add("3. Regue com molho Caesar e misture bem.");
		instrucoesSalada.add("4. Sirva imediatamente.");

		List<String> categoriasSalada = new ArrayList<>();
		categoriasSalada.add("Salada");
		categoriasSalada.add("Prato Principal");

		Receita receitaSalada = new Receita("Salada Caesar", ingredientesSalada, instrucoesSalada, categoriasSalada);
		receitas.add(receitaSalada);

		// Receita 5: Sopa de Abóbora
		List<Ingrediente> ingredientesSopa = new ArrayList<>();
		ingredientesSopa.add(new Ingrediente("Abóbora", new Quantidade(500, new Grama())));
		ingredientesSopa.add(new Ingrediente("Cebola", new Quantidade(1, new Unidade())));
		ingredientesSopa.add(new Ingrediente("Dente de Alho", new Quantidade(2, new Unidade())));
		ingredientesSopa.add(new Ingrediente("Caldo de galinha", new Quantidade(1, new Litro())));
		ingredientesSopa.add(new Ingrediente("Creme de leite", new Quantidade(200, new Mililitro())));
		ingredientesSopa.add(new Ingrediente("Azeite de oliva", new Quantidade(30, new Mililitro())));
		ingredientesSopa.add(new Ingrediente("Sal", new Quantidade(10, new Grama())));
		ingredientesSopa.add(new Ingrediente("Pimenta", new Quantidade(1, new Unidade())));

		List<String> instrucoesSopa = new ArrayList<>();
		instrucoesSopa.add("1. Descasque e corte a abóbora, a cebola e o alho.");
		instrucoesSopa.add("2. Em uma panela, aqueça o azeite de oliva e refogue a cebola e o alho até dourarem.");
		instrucoesSopa
				.add("3. Adicione a abóbora e o caldo de galinha à panela e cozinhe até que a abóbora esteja macia.");
		instrucoesSopa.add("4. Use um liquidificador ou mixer para triturar a sopa até ficar bem cremosa.");
		instrucoesSopa.add("5. Retorne a sopa à panela, adicione o creme de leite, e aqueça até ficar bem quente.");
		instrucoesSopa.add("6. Tempere com sal e pimenta a gosto.");
		instrucoesSopa.add("7. Sirva quente.");

		List<String> categoriasSopa = new ArrayList<>();
		categoriasSopa.add("Sopa");
		categoriasSopa.add("Prato Principal");

		Receita receitaSopa = new Receita("Sopa de Abóbora", ingredientesSopa, instrucoesSopa, categoriasSopa);
		receitas.add(receitaSopa);

		// Receita 6: Salada de Frutas
		List<Ingrediente> ingredientesSaladaFrutas = new ArrayList<>();
		ingredientesSaladaFrutas.add(new Ingrediente("Maçã", new Quantidade(2, new Unidade())));
		ingredientesSaladaFrutas.add(new Ingrediente("Banana", new Quantidade(2, new Unidade())));
		ingredientesSaladaFrutas.add(new Ingrediente("Laranja", new Quantidade(2, new Unidade())));
		ingredientesSaladaFrutas.add(new Ingrediente("Uva", new Quantidade(100, new Grama())));
		ingredientesSaladaFrutas.add(new Ingrediente("Mel", new Quantidade(30, new Mililitro())));
		ingredientesSaladaFrutas.add(new Ingrediente("Suco de limão", new Quantidade(30, new Mililitro())));

		List<String> instrucoesSaladaFrutas = new ArrayList<>();
		instrucoesSaladaFrutas.add("1. Descasque e corte as frutas em pedaços.");
		instrucoesSaladaFrutas.add("2. Misture as frutas em uma tigela.");
		instrucoesSaladaFrutas.add("3. Regue com suco de limão e mel.");
		instrucoesSaladaFrutas.add("4. Misture bem e leve à geladeira por 30 minutos antes de servir.");

		List<String> categoriasSaladaFrutas = new ArrayList<>();
		categoriasSaladaFrutas.add("Sobremesa");
		categoriasSaladaFrutas.add("Salada de Frutas");

		Receita receitaSaladaFrutas = new Receita("Salada de Frutas", ingredientesSaladaFrutas, instrucoesSaladaFrutas,
				categoriasSaladaFrutas);
		receitas.add(receitaSaladaFrutas);

		return receitas;
	}

}
