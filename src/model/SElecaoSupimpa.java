package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import unidades.UnidadeMedida;

public class SElecaoSupimpa {

	List<Receita> receitas;
	List<Receita> receitasCliente;
	Map<String, Ingrediente> ingredientesCliente;

	public SElecaoSupimpa() {
		receitas = carregarReceitas("receitas.bin");
		ingredientesCliente = new HashMap<>();
		// if (receitas == null) {
		// receitas = inicializarReceitas();
		// }
		recarregarReceitasCliente();
	}

	public void addIngredienteCliente(Ingrediente ingrediente) {
		ingredientesCliente.put(ingrediente.getNome(), ingrediente);
	}
	
	public void removerIngredienteCliente(String nomeIngrediente) {
	    if (ingredientesCliente.containsKey(nomeIngrediente)) {
	        ingredientesCliente.remove(nomeIngrediente);
	    } 
	}

	public Map<String, Ingrediente> getIngredientesCliente() {
		return ingredientesCliente;
	}

	public List<Receita> recarregarReceitasCliente() {
		receitasCliente = new ArrayList<>(receitas);
		return receitasCliente;
	}

	public void removerReceitasIngredienteCliente(Ingrediente ingrediente) {
		Iterator<Receita> iterator = receitasCliente.iterator();
		while (iterator.hasNext()) {
			Receita receita = iterator.next();
			if (receita.contemIngrediente(ingrediente)) {
				iterator.remove();
			}
		}
	}

	public void filtrarReceitasIngredienteCategoria(String categoria) {
		Iterator<Receita> iterator = receitasCliente.iterator();
		while (iterator.hasNext()) {
			Receita receita = iterator.next();
			if (!receita.getCategorias().contains(categoria)) {
				iterator.remove();
			}
		}
	}

	public List<Receita> encontrarReceitasCompativeis() {
		return encontrarReceitasCompativeis(getIngredientesCliente(), receitas);
	}

	private List<Receita> encontrarReceitasCompativeis(Map<String, Ingrediente> ingredientesDisponiveis,
			List<Receita> receitas) {
		List<Receita> receitasCompativeis = new ArrayList<>();

		for (Receita receita : receitas) {
			List<Ingrediente> ingredientesReceita = receita.getIngredientes();
			boolean receitaCompativel = true;

			for (Ingrediente ingredienteReceita : ingredientesReceita) {
				Quantidade quantidadeReceita = ingredienteReceita.getQuantidade();
				boolean ingredienteEncontrado = false;

				// Verifica se o ingrediente está disponível no mapa de ingredientes do cliente
				Ingrediente ingredienteDisponivel = ingredientesDisponiveis.get(ingredienteReceita.getNome());

				if (ingredienteDisponivel != null) {
					Quantidade quantidadeDisponivel = ingredienteDisponivel.getQuantidade();

					if (quantidadeDisponivel.maiorOuIgualA(quantidadeReceita)) {
						ingredienteEncontrado = true;
					}
				}

				if (!ingredienteEncontrado) {
					receitaCompativel = false;
					break;
				}
			}

			if (receitaCompativel) {
				receitasCompativeis.add(receita);
			}
		}

		return receitasCompativeis;
	}

	public Set<String> getCategorias(List<Receita> receitas) {
		Set<String> categorias = new HashSet<>();
		for (Receita receita : receitas) {
			categorias.addAll(receita.getCategorias());
		}
		return categorias;
	}

	public Set<Ingrediente> getIngredientes(List<Receita> receitas) {
		Set<Ingrediente> ingredientes = new HashSet<>();
		for (Receita receita : receitas) {
			for (Ingrediente ingrediente : receita.getIngredientes()) {
				ingredientes.add(ingrediente);
			}
		}
		return ingredientes;
	}

	public Map<String, UnidadeMedida> criarMapaIngredientesUnidadeMedida(List<Receita> receitas) {
		Map<String, UnidadeMedida> mapa = new HashMap<>();
		for (Receita receita : receitas) {
			for (Ingrediente ingrediente : receita.getIngredientes()) {
				String nomeIngrediente = ingrediente.getNome();
				UnidadeMedida unidadeMedida = ingrediente.getQuantidade().getUnidade();
				mapa.put(nomeIngrediente, unidadeMedida);
			}
		}
		return mapa;
	}

	public Set<Ingrediente> coletarIngredientesDoCliente() {
		Set<Ingrediente> ingredientesCliente = new HashSet<>();
		Map<String, UnidadeMedida> ingredientesUnidadeMedida = criarMapaIngredientesUnidadeMedida(receitas);
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("Ingredientes disponíveis:");

				int i = 1;
				for (String nomeIngrediente : ingredientesUnidadeMedida.keySet()) {
					System.out.println(i + ". " + nomeIngrediente);
					i++;
				}
				System.out.print(
						"\nDigite o número correspondente ao ingrediente que você tem (ou 'sair' para encerrar): ");
				String escolha = scanner.nextLine();

				if (escolha.equalsIgnoreCase("sair")) {
					break; // Encerra a coleta de ingredientes
				}

				int escolhaNumero = Integer.parseInt(escolha);

				if (escolhaNumero < 1 || escolhaNumero > ingredientesUnidadeMedida.size()) {
					System.out.println("Opção inválida. Por favor, escolha um número válido.");
					continue;
				}

				List<String> nomesIngredientes = new ArrayList<>(ingredientesUnidadeMedida.keySet());
				String nomeIngrediente = nomesIngredientes.get(escolhaNumero - 1);
				UnidadeMedida unidadeMedida = ingredientesUnidadeMedida.get(nomeIngrediente);

				System.out.print("Digite a quantidade que você possui (" + unidadeMedida.getNome() + "): ");
				double quantidade = Double.parseDouble(scanner.nextLine());

				// Cria o objeto Ingrediente com as informações fornecidas
				Ingrediente ingrediente = new Ingrediente(nomeIngrediente, new Quantidade(quantidade, unidadeMedida));
				ingredientesCliente.add(ingrediente);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return ingredientesCliente;
	}

	public List<Receita> inicializarReceitas() {
		List<Receita> receitas = InicializadorReceitas.inicializarReceitas();

		return receitas;
	}

	public List<Receita> inicializarReceitas(String nomeArquivo) {
		return carregarReceitas(nomeArquivo);
	}

	public void adicionarReceita(Receita receita) {
		receitas.add(receita);
	}

	public void salvarReceitas(List<Receita> receitas, String nomeArquivo) {
		try {
			FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(receitas);
			out.close();
			fileOut.close();
			System.out.println("Receitas salvas em " + nomeArquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Receita> carregarReceitas(String nomeArquivo) {
		try {
			FileInputStream fileIn = new FileInputStream(nomeArquivo);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			@SuppressWarnings("unchecked")
			List<Receita> receitas = (List<Receita>) in.readObject();
			in.close();
			fileIn.close();
			return receitas;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null; //
		}
	}

	public void resetarReceitas() {
		receitas = inicializarReceitas();
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public List<Receita> getReceitasCliente() {
		return receitasCliente;
	}

	public void atualizarReceita(Receita receitaExistente) {

	}

	public void removerReceita(Receita receitaExistente) {
		Iterator<Receita> iterator = receitas.iterator();
		while (iterator.hasNext()) {
			Receita receita = iterator.next();
			if (receita.equals(receitaExistente)) {
				iterator.remove();
				System.out.println("Receita removida com sucesso: " + receitaExistente.getNome());
				salvarReceitas(receitas, "receitas.bin"); // Salva as receitas atualizadas no arquivo
				return;
			}
		}
		System.out.println("Receita não encontrada: " + receitaExistente.getNome());
	}
}
