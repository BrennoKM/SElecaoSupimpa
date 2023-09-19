package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.Ingrediente;
import model.Quantidade;
import model.Receita;
import model.SElecaoSupimpa;
import unidades.Grama;
import unidades.Litro;
import unidades.Mililitro;
import unidades.Quilograma;
import unidades.Unidade;
import unidades.UnidadeMedida;

public class SE {

	public static final int ADMINISTRADOR = 0;
	public static final int USUARIO_COMUM = 1;
	static List<Receita> receitasCompativeis;

	public static void main(String[] args) {
		SElecaoSupimpa se = new SElecaoSupimpa();
		boolean continuar = true;

		while (continuar) {
			int escolhaTipoUsuario = exibirMenuTipoUsuario();

			switch (escolhaTipoUsuario) {
			case ADMINISTRADOR:
				menuAdministrador(se);
				break;
			case USUARIO_COMUM:
				menuUsuarioComum(se);
				break;
			default:
				continuar = false; // Para sair do loop
				break;
			}
		}
	}

	public static int exibirMenuTipoUsuario() {
		String[] opcoesTipoUsuario = { "Administrador", "Usuário Comum" };
		return exibirMenu(opcoesTipoUsuario, "Escolha o tipo de usuário:");
	}

	public static void menuAdministrador(SElecaoSupimpa se) {
		boolean continuar = true;

		while (continuar) {
			int escolhaAdmin = exibirMenuAdmin();
			switch (escolhaAdmin) {
			case 0:
				adicionarNovaReceita(se);
				break;
			case 1:
				visualizarReceitas(se);
				break;
			case 2:
				editarReceita(se);
				break;
			case 3:
				removerReceita(se);
				break;
			case 4:
				salvarReceitas(se);
				break;
			case 5:
				continuar = false; // Para sair do loop
				break;
			default:
				// JOptionPane.showMessageDialog(null, "Opção inválida para administrador.");
				return;
			}
		}
	}

	public static void menuUsuarioComum(SElecaoSupimpa se) {
		boolean continuar = true;

		while (continuar) {
			int escolhaComum = exibirMenuComum();
			switch (escolhaComum) {
			case 0:
				iniciarSistemaEspecialista(se);
				break;
			case 1:
				visualizarReceitas();
				break;
			case 2:
				visualizarIngredientes(se);
				break;
			case 3:
				adicionarIngrediente(se);
				break;
			case 4:
				removerIngrediente(se);
				break;
			case 5:
				editarIngrediente(se);
				break;
			case 6:
				continuar = false; // Para sair do loop
				break;
			default:
				// JOptionPane.showMessageDialog(null, "Opção inválida para usuário comum.");
				return;
			}
		}
	}

	public static int exibirMenuAdmin() {
		String[] opcoesAdmin = { "Adicionar Nova Receita", "Visualizar Receitas", "Editar Receita", "Remover Receita",
				"Salvar/Carregar Receitas", "Voltar" };
		return exibirMenu(opcoesAdmin, "Menu do Administrador:");
	}

	public static int exibirMenuComum() {
		String[] opcoesComum = { "Iniciar Sistema Especialista", "Visualizar Receitas Compativeis",
				"Visualizar Ingredientes", "Adicionar Ingrediente", "Remover Ingrediente", "Editar Ingrediente",
				"Voltar" };
		return exibirMenu(opcoesComum, "Menu do Usuário Comum:");
	}

	public static int exibirMenu(String[] opcoes, String titulo) {
		return JOptionPane.showOptionDialog(null, titulo, "Sistema Especialista", JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
	}

	public static void adicionarNovaReceita(SElecaoSupimpa se) {
		List<Ingrediente> ingredientes = new ArrayList<>();
		List<String> instrucoes = new ArrayList<>();
		List<String> categorias = new ArrayList<>();

		String nomeReceita = JOptionPane.showInputDialog("Digite o nome da nova receita:");

		while (true) {
			String nomeIngrediente = JOptionPane
					.showInputDialog("Digite o nome do ingrediente (ou deixe em branco para encerrar):");
			if (nomeIngrediente == null || nomeIngrediente.isEmpty()) {
				break; // O usuário deixou em branco ou pressionou Cancelar
			}

			UnidadeMedida unidade = obterUnidade(nomeIngrediente);

			if (unidade != null) {
				Quantidade quantidade = obterQuantidade(nomeIngrediente, unidade);
				if (quantidade != null) {
					Ingrediente ingrediente = new Ingrediente(nomeIngrediente, quantidade);
					ingredientes.add(ingrediente);
				}
			}
		}

		int numeroInstrucao = 1; // Contador para enumerar instruções
		while (true) {
			String instrucao = JOptionPane.showInputDialog("Digite uma instrução (ou deixe em branco para encerrar):");
			if (instrucao == null || instrucao.isEmpty()) {
				break; // O usuário deixou em branco ou pressionou Cancelar
			}
			instrucoes.add(numeroInstrucao + ". " + instrucao);
			numeroInstrucao++;
		}

		while (true) {
			String categoria = JOptionPane.showInputDialog("Digite uma categoria (ou deixe em branco para encerrar):");
			if (categoria == null || categoria.isEmpty()) {
				break;
			}
			categorias.add(categoria);
		}

		Receita novaReceita = new Receita(nomeReceita, ingredientes, instrucoes, categorias);

		se.adicionarReceita(novaReceita);

		JOptionPane.showMessageDialog(null, "Nova receita adicionada com sucesso!");
	}

	private static void visualizarReceitas() {

		if (receitasCompativeis == null) {
			JOptionPane.showMessageDialog(null, "Não há receitas disponíveis para visualização.");
			return;
		}

		List<Receita> receitas = new ArrayList<>(receitasCompativeis);

		if (receitas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há receitas disponíveis para visualização.");
		} else {
			String[] nomesReceitas = new String[receitas.size()];
			for (int i = 0; i < receitas.size(); i++) {
				nomesReceitas[i] = receitas.get(i).getNome();
			}

			String escolha = (String) JOptionPane.showInputDialog(null, "Selecione uma receita para visualizar:",
					"Visualizar Receitas", JOptionPane.PLAIN_MESSAGE, null, nomesReceitas, nomesReceitas[0]);

			if (escolha != null) {
				for (Receita receita : receitas) {
					if (receita.getNome().equals(escolha)) {
						JOptionPane.showMessageDialog(null, receita, "Receita: " + receita.getNome(),
								JOptionPane.PLAIN_MESSAGE);
						return;
					}
				}
			}
		}
	}

	private static void visualizarReceitas(SElecaoSupimpa se) {
		List<Receita> receitas = se.getReceitas();
		if (receitas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há receitas disponíveis para visualização.");
		} else {
			String[] nomesReceitas = new String[receitas.size()];
			for (int i = 0; i < receitas.size(); i++) {
				nomesReceitas[i] = receitas.get(i).getNome();
			}

			String escolha = (String) JOptionPane.showInputDialog(null, "Selecione uma receita para visualizar:",
					"Visualizar Receitas", JOptionPane.PLAIN_MESSAGE, null, nomesReceitas, nomesReceitas[0]);

			if (escolha != null) {
				for (Receita receita : receitas) {
					if (receita.getNome().equals(escolha)) {
						String[] opcoes = { "Editar", "Sair" };
						int escolhaOpcao = JOptionPane.showOptionDialog(null, receita, "Receita: " + receita.getNome(),
								JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

						if (escolhaOpcao == 0) {
							editarReceita(se, receita);
						}
						return;
					}
				}
			}
		}
	}

	public static void editarReceita(SElecaoSupimpa se) {
		List<Receita> receitas = se.getReceitas();

		if (receitas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há receitas para editar.");
			return;
		}

		String[] nomesReceitas = receitas.stream().map(Receita::getNome).toArray(String[]::new);
		String receitaSelecionada = (String) JOptionPane.showInputDialog(null, "Escolha a receita que deseja editar:",
				"Editar Receita", JOptionPane.QUESTION_MESSAGE, null, nomesReceitas, nomesReceitas[0] // Valor padrão
		);

		if (receitaSelecionada == null) {
			return; // O usuário cancelou a seleção
		}

		Receita receitaExistente = receitas.stream().filter(receita -> receita.getNome().equals(receitaSelecionada))
				.findFirst().orElse(null);

		if (receitaExistente == null) {
			JOptionPane.showMessageDialog(null, "A receita não foi encontrada.");
			return;
		}

		Receita r = new Receita(receitaExistente.getNome(), receitaExistente.getIngredientes(),
				receitaExistente.getInstrucoes(), receitaExistente.getCategorias());

		while (true) {
			String[] opcoesAcao = { "Visualizar Receita", "Editar Nome da Receita", "Editar Ingredientes",
					"Editar Instruções", "Editar Categorias", "Concluir Edição", "Cancelar" };
			int escolhaAcao = JOptionPane.showOptionDialog(null, "O que deseja fazer com a receita?", "Editar Receita",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesAcao, opcoesAcao[0]);

			switch (escolhaAcao) {
			case 0:
				String receitaAtual = receitaExistente.toString();

				JOptionPane.showMessageDialog(null, "Receita Atual:\n\n" + receitaAtual, "Visualizar Receita",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case 1:
				String novoNomeReceita = JOptionPane.showInputDialog("Digite o novo nome da receita:",
						receitaExistente.getNome());
				if (novoNomeReceita != null && !novoNomeReceita.isEmpty()) {
					receitaExistente.setNome(novoNomeReceita);
				}
				break;
			case 2:
				List<Ingrediente> ingredientesEditados = editarIngredientes(receitaExistente.getIngredientes());
				receitaExistente.setIngredientes(ingredientesEditados);
				break;
			case 3:
				List<String> instrucoesEditadas = editarInstrucoes(receitaExistente.getInstrucoes());
				receitaExistente.setInstrucoes(instrucoesEditadas);
				break;
			case 4:
				List<String> categoriasEditadas = editarCategorias(receitaExistente.getCategorias());
				receitaExistente.setCategorias(categoriasEditadas);
				break;
			case 5:
				se.atualizarReceita(receitaExistente);
				JOptionPane.showMessageDialog(null, "Receita editada com sucesso!");
				return;
			case 6:
				receitaExistente = new Receita(r.getNome(), r.getIngredientes(), r.getInstrucoes(), r.getCategorias());
				return;
			default:
				return; // O usuário cancelou a edição
			}
		}
	}

	public static void editarReceita(SElecaoSupimpa se, Receita receitaExistente) {
		Receita r = new Receita(receitaExistente.getNome(), receitaExistente.getIngredientes(),
				receitaExistente.getInstrucoes(), receitaExistente.getCategorias());
		while (true) {
			String[] opcoesAcao = { "Visualizar Receita", "Editar Nome da Receita", "Editar Ingredientes",
					"Editar Instruções", "Editar Categorias", "Concluir Edição", "Cancelar" };
			int escolhaAcao = JOptionPane.showOptionDialog(null, "O que deseja fazer com a receita?", "Editar Receita",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesAcao, opcoesAcao[0]);

			switch (escolhaAcao) {
			case 0:
				String receitaAtual = receitaExistente.toString();

				JOptionPane.showMessageDialog(null, "Receita Atual:\n\n" + receitaAtual, "Visualizar Receita",
						JOptionPane.INFORMATION_MESSAGE);
				break;
			case 1:
				String novoNomeReceita = JOptionPane.showInputDialog("Digite o novo nome da receita:",
						receitaExistente.getNome());
				if (novoNomeReceita != null && !novoNomeReceita.isEmpty()) {
					receitaExistente.setNome(novoNomeReceita);
				}
				break;
			case 2:
				List<Ingrediente> ingredientesEditados = editarIngredientes(receitaExistente.getIngredientes());
				receitaExistente.setIngredientes(ingredientesEditados);
				break;
			case 3:
				List<String> instrucoesEditadas = editarInstrucoes(receitaExistente.getInstrucoes());
				receitaExistente.setInstrucoes(instrucoesEditadas);
				break;
			case 4:
				List<String> categoriasEditadas = editarCategorias(receitaExistente.getCategorias());
				receitaExistente.setCategorias(categoriasEditadas);
				break;
			case 5:
				se.atualizarReceita(receitaExistente);
				JOptionPane.showMessageDialog(null, "Receita editada com sucesso!");
				return;
			case 6:
				receitaExistente = new Receita(r.getNome(), r.getIngredientes(), r.getInstrucoes(), r.getCategorias());
				return;
			default:
				return; // O usuário cancelou a edição
			}
		}
	}

	public static void salvarReceitas(SElecaoSupimpa se) {
		String[] opcoes = { "Salvar Receitas", "Carregar Receitas", "Resetar Receitas" };
		int escolha = JOptionPane.showOptionDialog(null, "Escolha uma opção:", "Salvar ou Carregar Receitas",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

		if (escolha == 0) {
			// Salvar Receitas
			String nomeArquivoSalvar = JOptionPane
					.showInputDialog("Digite o nome do arquivo para salvar as receitas (receitas.bin é o padrão):");
			if (nomeArquivoSalvar != null && !nomeArquivoSalvar.isEmpty()) {
				se.salvarReceitas(se.getReceitas(), nomeArquivoSalvar);
				JOptionPane.showMessageDialog(null, "Receitas salvas com sucesso!");
			}
		} else if (escolha == 1) {
			// Carregar Receitas
			String nomeArquivoCarregar = JOptionPane
					.showInputDialog("Digite o nome do arquivo para carregar as receitas (receitas.bin é o padrão):");
			if (nomeArquivoCarregar != null && !nomeArquivoCarregar.isEmpty()) {
				List<Receita> receitasCarregadas = se.carregarReceitas(nomeArquivoCarregar);
				if (receitasCarregadas != null) {
					se.getReceitas().clear();
					se.getReceitas().addAll(receitasCarregadas);
					JOptionPane.showMessageDialog(null, "Receitas carregadas com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possível carregar as receitas.");
				}
			}
		} else if (escolha == 2) {
			se.resetarReceitas();
		}
	}

	public static void removerReceita(SElecaoSupimpa se) {
		List<Receita> receitas = se.getReceitas();

		if (receitas.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há receitas para remover.");
			return;
		}

		String[] nomesReceitas = receitas.stream().map(Receita::getNome).toArray(String[]::new);
		String receitaSelecionada = (String) JOptionPane.showInputDialog(null, "Escolha a receita que deseja remover:",
				"Remover Receita", JOptionPane.QUESTION_MESSAGE, null, nomesReceitas, nomesReceitas[0] // Valor padrão
		);

		if (receitaSelecionada == null) {
			return; // O usuário cancelou a seleção
		}

		Receita receitaExistente = receitas.stream().filter(receita -> receita.getNome().equals(receitaSelecionada))
				.findFirst().orElse(null);

		if (receitaExistente == null) {
			JOptionPane.showMessageDialog(null, "A receita não foi encontrada.");
			return;
		}

		// Confirmação de exclusão
		int confirmacao = JOptionPane.showConfirmDialog(null,
				"Tem certeza que deseja remover a receita: " + receitaExistente.getNome() + "?", "Confirmar Remoção",
				JOptionPane.YES_NO_OPTION);

		if (confirmacao == JOptionPane.YES_OPTION) {
			// Remova a receita da lista
			se.removerReceita(receitaExistente);
			JOptionPane.showMessageDialog(null, "Receita removida com sucesso!");
		}
	}

	public static void iniciarSistemaEspecialista(SElecaoSupimpa se) {
		se.recarregarReceitasCliente();

		Set<String> categorias = se.getCategorias(se.getReceitasCliente());

		String[] categoriasArray = categorias.toArray(new String[0]);
		String[] categoriasArrayComNenhumaCategoria = Arrays.copyOf(categoriasArray, categoriasArray.length + 1);
		categoriasArrayComNenhumaCategoria[categoriasArray.length] = "Não especificar";

		String categoriaEscolhida = (String) JOptionPane.showInputDialog(null, "Escolha a categoria de receitas:",
				"Escolher Categoria", JOptionPane.QUESTION_MESSAGE, null, categoriasArrayComNenhumaCategoria,
				categoriasArrayComNenhumaCategoria[0] // Valor padrão
		);

		if (!"Não especificar".equals(categoriaEscolhida)) {
			se.filtrarReceitasIngredienteCategoria(categoriaEscolhida);
		}
		Set<Ingrediente> ingredientes = new HashSet<>(se.getIngredientes(se.getReceitasCliente()));

		Map<String, Ingrediente> ingredientesPerguntados = new HashMap<>();

		boolean ingredientesModificados;

		do {
			ingredientesModificados = false;

			for (Ingrediente ingrediente : ingredientes) {
				// Verifica se o ingrediente já foi perguntado
				if (ingredientesPerguntados.containsKey(ingrediente.getNome())) {
					continue; // Pule para o próximo ingrediente
				}

				int escolha = JOptionPane.showConfirmDialog(null,
						"Você possui o ingrediente " + ingrediente.getNome() + "?", "Possui o Ingrediente?",
						JOptionPane.YES_NO_OPTION);

				if (escolha != JOptionPane.YES_OPTION) {
					se.removerReceitasIngredienteCliente(ingrediente);
					ingredientes = new HashSet<>(se.getIngredientes(se.getReceitasCliente()));
					// Remove o ingrediente do mapa de ingredientes perguntados
					ingredientesPerguntados.remove(ingrediente.getNome());
					ingredientesModificados = true;
					break; // Sai do loop e comece novamente após a modificação
				} else {
					Quantidade quantidade = obterQuantidade(ingrediente);
					Ingrediente i = new Ingrediente(ingrediente.getNome(), quantidade);
					ingredientesPerguntados.put(ingrediente.getNome(), i);
					se.addIngredienteCliente(i);
				}
			}
		} while (ingredientesModificados);

		receitasCompativeis = se.encontrarReceitasCompativeis();

		if (!receitasCompativeis.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Receitas Compativeis foram encontradas!");
			System.out.println("Receitas Compativeis:");
			for (Receita receita : receitasCompativeis) {
				System.out.println(receita.getNome());
				System.out.println(receita);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Nenhuma receita compatível encontrada.");
			System.out.println("Nenhuma receita compatível encontrada.");
		}
	}

	public static Quantidade obterQuantidade(Ingrediente ingrediente) {
		Quantidade quantidade = null;
		try {
			String input = JOptionPane.showInputDialog("Digite a quantidade de " + ingrediente.getNome() + " ("
					+ ingrediente.getUnidade().getNome() + "):");
			if (input != null) {
				double qnt = Double.parseDouble(input);
				quantidade = new Quantidade(qnt, ingrediente.getUnidade());
			} else {
				quantidade = new Quantidade(0, ingrediente.getUnidade());
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Quantidade inválida. Insira um número válido.");
			quantidade = obterQuantidade(ingrediente); // Chama novamente se a entrada for inválida
		}
		return quantidade;
	}

	public static Quantidade obterQuantidade(String nomeIngrediente, UnidadeMedida unidade) {
		Quantidade quantidade = null;
		try {
			String input = JOptionPane
					.showInputDialog("Digite a quantidade de " + nomeIngrediente + " (" + unidade.getNome() + "):");
			if (input != null) {
				double qnt = Double.parseDouble(input);
				quantidade = new Quantidade(qnt, unidade);
			} else {
				quantidade = new Quantidade(0, unidade);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Quantidade inválida. Insira um número válido.");
			quantidade = obterQuantidade(nomeIngrediente, unidade); // Chama novamente se a entrada for inválida
		}
		return quantidade;
	}

	public static UnidadeMedida obterUnidade(String nomeIngrediente) {
		UnidadeMedida unidade = null;
		String[] opcoesUnidade = { "Quilograma", "Grama", "Litro", "Mililitro", "Unidade" };
		String unidadeEscolhida = (String) JOptionPane.showInputDialog(null,
				"Escolha a unidade de medida para " + nomeIngrediente + ":", "Escolher Unidade de Medida",
				JOptionPane.QUESTION_MESSAGE, null, opcoesUnidade, opcoesUnidade[0] // Valor padrão
		);

		if (unidadeEscolhida != null) {
			switch (unidadeEscolhida) {
			case "Quilograma":
				unidade = new Quilograma();
				break;
			case "Grama":
				unidade = new Grama();
				break;
			case "Litro":
				unidade = new Litro();
				break;
			case "Mililitro":
				unidade = new Mililitro();
				break;
			case "Unidade":
				unidade = new Unidade();
				break;
			default:
				JOptionPane.showMessageDialog(null, "Opção de unidade inválida.");
				unidade = obterUnidade(nomeIngrediente); // Chama novamente se a entrada for inválida
			}
		}
		return unidade;
	}

	public static void visualizarIngredientes(SElecaoSupimpa se) {
		Map<String, Ingrediente> ingredientesCliente = se.getIngredientesCliente();

		if (ingredientesCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Você ainda não possui ingredientes registrados.");
			return;
		}

		DefaultListModel<String> ingredientesListModel = new DefaultListModel<>();

		for (Ingrediente ingrediente : ingredientesCliente.values()) {
			ingredientesListModel.addElement(ingrediente.toString());
		}

		JList<String> ingredientesList = new JList<>(ingredientesListModel);
		ingredientesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(ingredientesList);
		scrollPane.setPreferredSize(new Dimension(400, 300));

		JOptionPane.showMessageDialog(null, scrollPane, "Visualizar Ingredientes", JOptionPane.PLAIN_MESSAGE);
	}

	public static void adicionarIngrediente(SElecaoSupimpa se) {
		Set<Ingrediente> ingredientesDisponiveis = se.getIngredientes(se.getReceitas());

		if (ingredientesDisponiveis.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há ingredientes disponíveis para adicionar.");
			return;
		}

		String[] nomesIngredientes = ingredientesDisponiveis.stream().map(Ingrediente::getNome).toArray(String[]::new);

		String ingredienteSelecionado = (String) JOptionPane.showInputDialog(null,
				"Escolha o ingrediente que deseja adicionar:", "Adicionar Ingrediente", JOptionPane.QUESTION_MESSAGE,
				null, nomesIngredientes, nomesIngredientes[0]);

		if (ingredienteSelecionado != null) {
			Ingrediente ingredienteEscolhido = ingredientesDisponiveis.stream()
					.filter(ingrediente -> ingrediente.getNome().equals(ingredienteSelecionado)).findFirst()
					.orElse(null);

			if (ingredienteEscolhido != null) {
				Ingrediente i = new Ingrediente(ingredienteEscolhido.getNome(), ingredienteEscolhido.getQuantidade());
				i.setQuantidade(obterQuantidade(ingredienteEscolhido));

				boolean verif = true;
				se.addIngredienteCliente(i);

				if (i.getQuantidade().getValor() == 0) {
					se.removerIngredienteCliente(i.getNome());
					verif = false;
				}

				if (verif == true) {
					JOptionPane.showMessageDialog(null, "Ingrediente adicionado com sucesso.");
				} else {
					JOptionPane.showMessageDialog(null, "Operaçao cancelada.");
				}

				verificarNovasReceitas(se);

			} else {
				JOptionPane.showMessageDialog(null, "Ingrediente não encontrado.");
			}
		}
	}

	public static void removerIngrediente(SElecaoSupimpa se) {
		Map<String, Ingrediente> ingredientesCliente = se.getIngredientesCliente();

		String[] nomesIngredientes = ingredientesCliente.keySet().toArray(new String[0]);

		String ingredienteSelecionado = (String) JOptionPane.showInputDialog(null,
				"Escolha o ingrediente que deseja remover:", "Remover Ingrediente", JOptionPane.QUESTION_MESSAGE, null,
				nomesIngredientes, nomesIngredientes[0]);

		if (ingredienteSelecionado != null) {
			se.removerIngredienteCliente(ingredienteSelecionado);

			verificarNovasReceitas(se);
		}
	}

	public static void editarIngrediente(SElecaoSupimpa se) {
		Map<String, Ingrediente> ingredientesCliente = se.getIngredientesCliente();

		if (ingredientesCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Não há ingredientes para editar.");
			return;
		}

		String[] nomesIngredientes = ingredientesCliente.keySet().toArray(new String[0]);

		while (true) {
			String ingredienteSelecionado = (String) JOptionPane.showInputDialog(null,
					"Escolha o ingrediente que deseja editar ou cancele para sair:", "Editar Ingrediente",
					JOptionPane.QUESTION_MESSAGE, null, nomesIngredientes, nomesIngredientes[0]);

			if (ingredienteSelecionado == null) {
				break; // O usuário escolheu cancelar
			}

			Ingrediente ingredienteExistente = ingredientesCliente.get(ingredienteSelecionado);
			Ingrediente i;
			if (ingredienteExistente != null) {
				i = new Ingrediente(ingredienteExistente.getNome(), ingredienteExistente.getQuantidade());
				while (true) {
					String[] opcoesAcao = { "Editar Quantidade", "Editar Unidade", "Concluir Edição",
							"Cancelar Edição" };
					int escolhaAcao = JOptionPane.showOptionDialog(null,
							"O que deseja fazer com o ingrediente?\n" + ingredienteExistente.toString(),
							"Editar Ingrediente", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							opcoesAcao, opcoesAcao[0]);

					switch (escolhaAcao) {
					case 0:
						// Editar Quantidade
						Quantidade qnt = ingredienteExistente.getQuantidade();
						Quantidade novaQuantidade = obterQuantidade(ingredienteExistente);
						if (novaQuantidade != null) {
							// i = new Ingrediente(ingredienteExistente.getNome(),
							// ingredienteExistente.getQuantidade());
							ingredienteExistente.setQuantidade(novaQuantidade);

							// ingredienteEscolhido.setQuantidade(obterQuantidade(ingredienteEscolhido));

							boolean verif = true;
							// se.addIngredienteCliente(ingredienteExistente);

							if (ingredienteExistente.getQuantidade().getValor() == 0) {
								ingredienteExistente.setQuantidade(qnt);
								verif = false;
							}

							if (verif == true) {
								JOptionPane.showMessageDialog(null, "Quantidade editada com sucesso.");
							} else {
								JOptionPane.showMessageDialog(null, "Operaçao cancelada.");
							}

							verificarNovasReceitas(se);

						}
						break;
					case 1:
						// Editar Unidade
						UnidadeMedida novaUnidade = obterUnidade(ingredienteExistente.getNome());
						if (novaUnidade != null) {
							ingredienteExistente.getQuantidade().setUnidade(novaUnidade);
							JOptionPane.showMessageDialog(null, "Unidade editada com sucesso.");
						}
						break;
					case 2:
						JOptionPane.showMessageDialog(null, "Edição concluída para " + ingredienteExistente.getNome());
						break;
					default:
						ingredienteExistente = new Ingrediente(i.getNome(), i.getQuantidade());
						break;
					}

					if (escolhaAcao == 2 || escolhaAcao == 3) {
						break; // Sai do loop interno se a edição for concluída ou cancelada
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ingrediente não encontrado.");
			}
			verificarNovasReceitas(se);
		}
	}

	public static void verificarNovasReceitas(SElecaoSupimpa se) {
		int tam = 0;
		if (receitasCompativeis != null) {
			if (!receitasCompativeis.isEmpty() && receitasCompativeis != null) {
				tam = receitasCompativeis.size();
			}
		}
		receitasCompativeis = se.encontrarReceitasCompativeis();
		if (receitasCompativeis != null) {
			if (tam != receitasCompativeis.size() && receitasCompativeis != null) {
				if (tam > receitasCompativeis.size()) {
					JOptionPane.showMessageDialog(null, "Alguma receita foi perdida.");
				} else {
					JOptionPane.showMessageDialog(null, "Nova receita encontrada!!");
				}
			}
		}
	}

	public static List<Ingrediente> editarIngredientes(List<Ingrediente> ingredientes) {
		List<Ingrediente> ingredientesEditados = new ArrayList<>(ingredientes);

		while (true) {
			String[] opcoesAcao = { "Editar Ingrediente", "Adicionar Ingrediente", "Remover Ingrediente",
					"Concluir Edição" };
			int escolhaAcao = JOptionPane.showOptionDialog(null, "O que deseja fazer com os ingredientes?",
					"Editar Ingredientes", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesAcao,
					opcoesAcao[0]);

			switch (escolhaAcao) {
			case 0:
				String[] nomesIngredientes = ingredientesEditados.stream().map(Ingrediente::getNome)
						.toArray(String[]::new);
				String ingredienteSelecionado = (String) JOptionPane.showInputDialog(null,
						"Escolha o ingrediente que deseja editar:", "Editar Ingrediente", JOptionPane.QUESTION_MESSAGE,
						null, nomesIngredientes, nomesIngredientes[0]);

				if (ingredienteSelecionado == null) {
					break;
				}

				Ingrediente ingredienteExistente = ingredientesEditados.stream()
						.filter(ingrediente -> ingrediente.getNome().equals(ingredienteSelecionado)).findFirst()
						.orElse(null);

				if (ingredienteExistente == null) {
					JOptionPane.showMessageDialog(null, "O ingrediente não foi encontrado.");
					break;
				}

				// Solicite ao usuário ação desejada para o ingrediente
				String[] opcoesEditar = { "Editar Nome do Ingrediente", "Editar Quantidade do Ingrediente" };
				int escolhaEditar = JOptionPane.showOptionDialog(null, "O que deseja editar no ingrediente?",
						"Editar Ingrediente", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						opcoesEditar, opcoesEditar[0]);

				switch (escolhaEditar) {
				case 0:
					String novoNomeIngrediente = JOptionPane.showInputDialog("Digite o novo nome do ingrediente:",
							ingredienteExistente.getNome());
					if (novoNomeIngrediente != null && !novoNomeIngrediente.isEmpty()) {
						ingredienteExistente.setNome(novoNomeIngrediente);
					}
					break;
				case 1:
					Quantidade quantidadeEditada = obterQuantidade(ingredienteExistente);
					if (quantidadeEditada != null) {
						ingredienteExistente.setQuantidade(quantidadeEditada);
					}
					break;
				default:
					break; // O usuário cancelou a edição do ingrediente
				}
				break;
			case 1:
				adicionarIngrediente(ingredientesEditados);
				break;
			case 2:
				removerIngrediente(ingredientesEditados);
				break;
			case 3:
				return ingredientesEditados; // Concluir a edição de ingredientes
			default:
				return ingredientes; // O usuário cancelou a edição de ingredientes
			}
		}
	}

	public static List<String> editarInstrucoes(List<String> instrucoes) {
		List<String> instrucoesEditadas = new ArrayList<>(instrucoes);

		while (true) {
			String[] opcoesAcao = { "Editar Instrução", "Adicionar Instrução", "Remover Instrução", "Concluir Edição" };
			int escolhaAcao = JOptionPane.showOptionDialog(null, "O que deseja fazer com as instruções?",
					"Editar Instruções", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesAcao,
					opcoesAcao[0]);

			switch (escolhaAcao) {
			case 0:
				String[] instrucoesArray = instrucoesEditadas.toArray(new String[0]);
				String instrucaoSelecionada = (String) JOptionPane.showInputDialog(null,
						"Escolha a instrução que deseja editar:", "Editar Instrução", JOptionPane.QUESTION_MESSAGE,
						null, instrucoesArray, instrucoesArray[0]);

				if (instrucaoSelecionada == null) {
					break;
				}

				int indiceInstrucaoSelecionada = instrucoesEditadas.indexOf(instrucaoSelecionada);
				if (indiceInstrucaoSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "A instrução não foi encontrada.");
					break;
				}

				String novaInstrucaoEditada = JOptionPane.showInputDialog("Digite a nova versão da instrução:",
						instrucaoSelecionada);

				if (novaInstrucaoEditada != null) {
					if (!novaInstrucaoEditada.isEmpty()) {
						instrucoesEditadas.set(indiceInstrucaoSelecionada, novaInstrucaoEditada);
					} else {
						instrucoesEditadas.remove(indiceInstrucaoSelecionada);
					}
				}
				break;
			case 1:
				adicionarInstrucao(instrucoesEditadas);
				break;
			case 2:
				removerInstrucao(instrucoesEditadas);
				break;
			case 3:
				return instrucoesEditadas; // Concluir a edição de instruções
			default:
				return instrucoes; // O usuário cancelou a edição de instruções
			}
		}
	}

	public static List<String> editarCategorias(List<String> categorias) {
		List<String> categoriasEditadas = new ArrayList<>(categorias);

		while (true) {
			String[] opcoesAcao = { "Editar Categoria", "Adicionar Categoria", "Remover Categoria", "Concluir Edição" };
			int escolhaAcao = JOptionPane.showOptionDialog(null, "O que deseja fazer com as categorias?",
					"Editar Categorias", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesAcao,
					opcoesAcao[0]);

			switch (escolhaAcao) {
			case 0:
				String[] categoriasArray = categoriasEditadas.toArray(new String[0]);
				String categoriaSelecionada = (String) JOptionPane.showInputDialog(null,
						"Escolha a categoria que deseja editar:", "Editar Categoria", JOptionPane.QUESTION_MESSAGE,
						null, categoriasArray, categoriasArray[0]);

				if (categoriaSelecionada == null) {
					break;
				}

				int indiceCategoriaSelecionada = categoriasEditadas.indexOf(categoriaSelecionada);
				if (indiceCategoriaSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "A categoria não foi encontrada.");
					break;
				}

				String novaCategoriaEditada = JOptionPane.showInputDialog("Digite a nova versão da categoria:",
						categoriaSelecionada);

				if (novaCategoriaEditada != null) {
					if (!novaCategoriaEditada.isEmpty()) {
						categoriasEditadas.set(indiceCategoriaSelecionada, novaCategoriaEditada);
					} else {
						categoriasEditadas.remove(indiceCategoriaSelecionada);
					}
				}
				break;
			case 1:
				adicionarCategoria(categoriasEditadas);
				break;
			case 2:
				removerCategoria(categoriasEditadas);
				break;
			case 3:
				return categoriasEditadas; // Concluir a edição de categorias
			default:
				return categorias; // O usuário cancelou a edição de categorias
			}
		}
	}

	public static void adicionarIngrediente(List<Ingrediente> ingredientes) {
		String nomeIngrediente = JOptionPane.showInputDialog("Digite o nome do novo ingrediente:");
		if (nomeIngrediente != null && !nomeIngrediente.isEmpty()) {
			UnidadeMedida unidade = obterUnidade(nomeIngrediente);
			Quantidade quantidade = obterQuantidade(nomeIngrediente, unidade);

			if (quantidade != null) {
				Ingrediente novoIngrediente = new Ingrediente(nomeIngrediente, quantidade);
				ingredientes.add(novoIngrediente);
			}
		}
	}

	public static void removerIngrediente(List<Ingrediente> ingredientes) {
		String[] nomesIngredientes = ingredientes.stream().map(Ingrediente::getNome).toArray(String[]::new);
		String ingredienteSelecionado = (String) JOptionPane.showInputDialog(null,
				"Escolha o ingrediente que deseja remover:", "Remover Ingrediente", JOptionPane.QUESTION_MESSAGE, null,
				nomesIngredientes, nomesIngredientes[0]);

		if (ingredienteSelecionado != null) {
			ingredientes.removeIf(ingrediente -> ingrediente.getNome().equals(ingredienteSelecionado));
		}
	}

	public static void adicionarInstrucao(List<String> instrucoes) {
		String novaInstrucao = JOptionPane.showInputDialog("Digite a nova instrução:");
		if (novaInstrucao != null && !novaInstrucao.isEmpty()) {
			instrucoes.add(novaInstrucao);
		}
	}

	public static void removerInstrucao(List<String> instrucoes) {
		String[] instrucoesArray = instrucoes.toArray(new String[0]);
		String instrucaoSelecionada = (String) JOptionPane.showInputDialog(null,
				"Escolha a instrução que deseja remover:", "Remover Instrução", JOptionPane.QUESTION_MESSAGE, null,
				instrucoesArray, instrucoesArray[0]);

		if (instrucaoSelecionada != null) {
			instrucoes.remove(instrucaoSelecionada);
		}
	}

	public static void adicionarCategoria(List<String> categorias) {
		String novaCategoria = JOptionPane.showInputDialog("Digite a nova categoria:");
		if (novaCategoria != null && !novaCategoria.isEmpty()) {
			categorias.add(novaCategoria);
		}
	}

	public static void removerCategoria(List<String> categorias) {
		String[] categoriasArray = categorias.toArray(new String[0]);
		String categoriaSelecionada = (String) JOptionPane.showInputDialog(null,
				"Escolha a categoria que deseja remover:", "Remover Categoria", JOptionPane.QUESTION_MESSAGE, null,
				categoriasArray, categoriasArray[0]);

		if (categoriaSelecionada != null) {
			categorias.remove(categoriaSelecionada);
		}
	}

	public static int exibirMenuEditar(String titulo) {
		String[] opcoesEditar = { "Adicionar", "Editar Existente", "Remover", "Concluir" };
		return exibirMenu(opcoesEditar, titulo);
	}

}
