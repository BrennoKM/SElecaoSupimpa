package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

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
				// JOptionPane.showMessageDialog(null, "Opção inválida para escolha do tipo de
				// usuário.");
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
				visualizarIngredientes(se);
				break;
			case 2:
				adicionarIngrediente(se);
				break;
			case 3:
				removerIngrediente(se);
				break;
			case 4:
				editarIngrediente(se);
				break;
			case 5:
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
		String[] opcoesComum = { "Iniciar Sistema Especialista", "Visualizar Ingredientes", "Adicionar Ingrediente",
				"Remover Ingrediente", "Editar Ingrediente", "Voltar" };
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

	private static void visualizarReceitas(SElecaoSupimpa se) {
	    List<Receita> receitas = se.getReceitas(); // Obtenha a lista de receitas do sistema

	    if (receitas.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Não há receitas disponíveis para visualização.");
	    } else {
	        String[] nomesReceitas = new String[receitas.size()];
	        for (int i = 0; i < receitas.size(); i++) {
	            nomesReceitas[i] = receitas.get(i).getNome();
	        }

	        String escolha = (String) JOptionPane.showInputDialog(
	                null,
	                "Selecione uma receita para visualizar:",
	                "Visualizar Receitas",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                nomesReceitas,
	                nomesReceitas[0]);

	        if (escolha != null) {
	            for (Receita receita : receitas) {
	                if (receita.getNome().equals(escolha)) {
	                    JOptionPane.showMessageDialog(null, receita, "Receita: " + receita.getNome(), JOptionPane.PLAIN_MESSAGE);
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

	    // Solicite ao usuário que escolha uma receita
	    String[] nomesReceitas = receitas.stream().map(Receita::getNome).toArray(String[]::new);
	    String receitaSelecionada = (String) JOptionPane.showInputDialog(
	            null,
	            "Escolha a receita que deseja editar:",
	            "Editar Receita",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            nomesReceitas,
	            nomesReceitas[0] // Valor padrão
	    );

	    if (receitaSelecionada == null) {
	        return; // O usuário cancelou a seleção
	    }

	    // Encontre a receita selecionada
	    Receita receitaExistente = receitas.stream().filter(receita -> receita.getNome().equals(receitaSelecionada))
	            .findFirst().orElse(null);

	    if (receitaExistente == null) {
	        JOptionPane.showMessageDialog(null, "A receita não foi encontrada.");
	        return;
	    }

	    while (true) {
	        // Solicite ao usuário ação desejada
	        String[] opcoesAcao = {
	            "Visualizar Receita",
	            "Editar Nome da Receita",
	            "Editar Ingredientes",
	            "Editar Instruções",
	            "Editar Categorias",
	            "Concluir Edição"
	        };
	        int escolhaAcao = JOptionPane.showOptionDialog(
	            null,
	            "O que deseja fazer com a receita?",
	            "Editar Receita",
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            opcoesAcao,
	            opcoesAcao[0]
	        );

	        switch (escolhaAcao) {
	            case 0:
	                // Exiba a receita atual para o usuário
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
	            default:
	                return; // O usuário cancelou a edição
	        }
	    }
	}


	public static void salvarReceitas(SElecaoSupimpa se) {
	    String[] opcoes = {"Salvar Receitas", "Carregar Receitas", "Resetar Receitas"};
	    int escolha = JOptionPane.showOptionDialog(
	        null,
	        "Escolha uma opção:",
	        "Salvar ou Carregar Receitas",
	        JOptionPane.DEFAULT_OPTION,
	        JOptionPane.QUESTION_MESSAGE,
	        null,
	        opcoes,
	        opcoes[0]
	    );

	    if (escolha == 0) {
	        // Salvar Receitas
	        String nomeArquivoSalvar = JOptionPane.showInputDialog("Digite o nome do arquivo para salvar as receitas (receitas.bin é o padrão):");
	        if (nomeArquivoSalvar != null && !nomeArquivoSalvar.isEmpty()) {
	            se.salvarReceitas(se.getReceitas(), nomeArquivoSalvar);
	            JOptionPane.showMessageDialog(null, "Receitas salvas com sucesso!");
	        }
	    } else if (escolha == 1) {
	        // Carregar Receitas
	        String nomeArquivoCarregar = JOptionPane.showInputDialog("Digite o nome do arquivo para carregar as receitas (receitas.bin é o padrão):");
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

	    // Solicite ao usuário que escolha uma receita para remover
	    String[] nomesReceitas = receitas.stream().map(Receita::getNome).toArray(String[]::new);
	    String receitaSelecionada = (String) JOptionPane.showInputDialog(
	            null,
	            "Escolha a receita que deseja remover:",
	            "Remover Receita",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            nomesReceitas,
	            nomesReceitas[0] // Valor padrão
	    );

	    if (receitaSelecionada == null) {
	        return; // O usuário cancelou a seleção
	    }

	    // Encontre a receita selecionada
	    Receita receitaExistente = receitas.stream().filter(receita -> receita.getNome().equals(receitaSelecionada))
	            .findFirst().orElse(null);

	    if (receitaExistente == null) {
	        JOptionPane.showMessageDialog(null, "A receita não foi encontrada.");
	        return;
	    }

	    // Confirmação de exclusão
	    int confirmacao = JOptionPane.showConfirmDialog(
	            null,
	            "Tem certeza que deseja remover a receita: " + receitaExistente.getNome() + "?",
	            "Confirmar Remoção",
	            JOptionPane.YES_NO_OPTION
	    );

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

		// Pergunta ao usuário qual categoria deseja
		String categoriaEscolhida = (String) JOptionPane.showInputDialog(null, "Escolha a categoria de receitas:",
				"Escolher Categoria", JOptionPane.QUESTION_MESSAGE, null, categoriasArrayComNenhumaCategoria,
				categoriasArrayComNenhumaCategoria[0] // Valor padrão
		);

		if (!"Não especificar".equals(categoriaEscolhida)) {
			se.filtrarReceitasIngredienteCategoria(categoriaEscolhida);
		}
		Set<Ingrediente> ingredientes = new HashSet<>(se.getIngredientes(se.getReceitasCliente()));

		// Use um mapa para rastrear os ingredientes já perguntados
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
					break; // Saia do loop e comece novamente após a modificação
				} else {
					Quantidade quantidade = obterQuantidade(ingrediente);
					Ingrediente i = new Ingrediente(ingrediente.getNome(), quantidade);
					ingredientesPerguntados.put(ingrediente.getNome(), i);
					se.addIngredienteCliente(i);
				}
			}
		} while (ingredientesModificados);

		List<Receita> receitasCompativeis = se.encontrarReceitasCompativeis();

		// Exibe as receitas filtradas para o usuário
		if (!receitasCompativeis.isEmpty()) {
			System.out.println("Receitas Compativeis:");
			for (Receita receita : receitasCompativeis) {
				System.out.println(receita.getNome());
				System.out.println(receita);
			}
		} else {
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
				// O botão de cancelar foi pressionado
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
				// O botão de cancelar foi pressionado
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
		JOptionPane.showMessageDialog(null, "Implementar a lógica para visualizar os ingredientes do usuário comum.");
	}

	public static void adicionarIngrediente(SElecaoSupimpa se) {
		JOptionPane.showMessageDialog(null, "Implementar a lógica para adicionar um novo ingrediente.");
	}

	public static void removerIngrediente(SElecaoSupimpa se) {
		JOptionPane.showMessageDialog(null, "Implementar a lógica para remover um ingrediente.");
	}

	public static void editarIngrediente(SElecaoSupimpa se) {
		JOptionPane.showMessageDialog(null, "Implementar a lógica para editar um ingrediente.");
	}

	public static List<Ingrediente> editarIngredientes(List<Ingrediente> ingredientes) {
	    List<Ingrediente> ingredientesEditados = new ArrayList<>(ingredientes);

	    while (true) {
	        // Exiba a lista de ingredientes e permita ao usuário escolher o que editar
	        String[] nomesIngredientes = ingredientesEditados.stream()
	                .map(Ingrediente::getNome)
	                .toArray(String[]::new);
	        String ingredienteSelecionado = (String) JOptionPane.showInputDialog(
	                null,
	                "Escolha o ingrediente que deseja editar:",
	                "Editar Ingredientes",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                nomesIngredientes,
	                nomesIngredientes[0]
	        );

	        if (ingredienteSelecionado == null) {
	            break; // O usuário cancelou a edição de ingredientes
	        }

	        // Encontre o ingrediente selecionado
	        Ingrediente ingredienteExistente = ingredientesEditados.stream()
	                .filter(ingrediente -> ingrediente.getNome().equals(ingredienteSelecionado))
	                .findFirst().orElse(null);

	        if (ingredienteExistente == null) {
	            JOptionPane.showMessageDialog(null, "O ingrediente não foi encontrado.");
	            break;
	        }

	        // Solicite ao usuário ação desejada para o ingrediente
	        String[] opcoesAcao = {
	            "Editar Nome do Ingrediente",
	            "Editar Quantidade do Ingrediente",
	            "Concluir Edição"
	        };
	        int escolhaAcao = JOptionPane.showOptionDialog(
	                null,
	                "O que deseja editar no ingrediente?",
	                "Editar Ingrediente",
	                JOptionPane.DEFAULT_OPTION,
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opcoesAcao,
	                opcoesAcao[0]
	        );

	        switch (escolhaAcao) {
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
	            case 2:
	                break; // Concluir a edição do ingrediente
	            default:
	                break; // O usuário cancelou a edição do ingrediente
	        }
	    }

	    return ingredientesEditados;
	}

	public static List<Ingrediente> editarListaIngredientes(List<Ingrediente> ingredientes) {
		List<Ingrediente> ingredientesEditados = new ArrayList<>(ingredientes);

		// Exiba a lista de ingredientes e permita ao usuário escolher qual editar
		String[] nomesIngredientes = ingredientesEditados.stream().map(Ingrediente::getNome).toArray(String[]::new);
		String ingredienteSelecionado = (String) JOptionPane.showInputDialog(null,
				"Escolha o ingrediente que deseja editar:", "Editar Ingredientes", JOptionPane.QUESTION_MESSAGE, null,
				nomesIngredientes, nomesIngredientes[0] // Valor padrão
		);

		if (ingredienteSelecionado != null) {
			// Encontre o ingrediente selecionado
			Ingrediente ingredienteExistente = ingredientesEditados.stream()
					.filter(ingrediente -> ingrediente.getNome().equalsIgnoreCase(ingredienteSelecionado)).findFirst()
					.orElse(null);

			if (ingredienteExistente != null) {
				// Solicite ao usuário as edições desejadas para o ingrediente
				UnidadeMedida unidadeEditada = obterUnidade(ingredienteExistente.getNome());
				if (unidadeEditada != null) {
					Quantidade quantidadeEditada = obterQuantidade(ingredienteExistente.getNome(), unidadeEditada);
					if (quantidadeEditada != null) {
						ingredienteExistente.setQuantidade(quantidadeEditada);
					}
				}
			}
		}

		return ingredientesEditados;
	}

	public static List<String> editarInstrucoes(List<String> instrucoes) {
	    List<String> instrucoesEditadas = new ArrayList<>(instrucoes);

	    while (true) {
	        // Exiba a lista de instruções e permita ao usuário escolher o que editar
	        String[] instrucoesArray = instrucoesEditadas.toArray(new String[0]);
	        String instrucaoSelecionada = (String) JOptionPane.showInputDialog(
	                null,
	                "Escolha a instrução que deseja editar:",
	                "Editar Instruções",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                instrucoesArray,
	                instrucoesArray[0]
	        );

	        if (instrucaoSelecionada == null) {
	            break;
	        }

	        // Encontre a instrução selecionada
	        int indiceInstrucaoSelecionada = instrucoesEditadas.indexOf(instrucaoSelecionada);
	        if (indiceInstrucaoSelecionada == -1) {
	            JOptionPane.showMessageDialog(null, "A instrução não foi encontrada.");
	            break;
	        }

	        // Solicite ao usuário a nova versão da instrução
	        String novaInstrucaoEditada = JOptionPane.showInputDialog("Digite a nova versão da instrução:",
	                instrucaoSelecionada);

	        if (novaInstrucaoEditada != null) {
	            if (!novaInstrucaoEditada.isEmpty()) {
	                instrucoesEditadas.set(indiceInstrucaoSelecionada, novaInstrucaoEditada);
	            } else {
	                // Remova a instrução se a nova versão for uma string vazia
	                instrucoesEditadas.remove(indiceInstrucaoSelecionada);
	            }
	        }
	    }

	    return instrucoesEditadas;
	}

	public static List<String> editarListaInstrucoes(List<String> instrucoes) {
		List<String> instrucoesEditadas = new ArrayList<>(instrucoes);

		// Exiba a lista de instruções e permita ao usuário escolher qual editar
		String[] instrucoesArray = instrucoesEditadas.toArray(new String[0]);
		String instrucaoSelecionada = (String) JOptionPane.showInputDialog(null,
				"Escolha a instrução que deseja editar:", "Editar Instruções", JOptionPane.QUESTION_MESSAGE, null,
				instrucoesArray, instrucoesArray[0] // Valor padrão
		);

		if (instrucaoSelecionada != null) {
			// Encontre a instrução selecionada
			int indiceInstrucaoSelecionada = instrucoesEditadas.indexOf(instrucaoSelecionada);
			if (indiceInstrucaoSelecionada >= 0) {
				// Solicite ao usuário a nova versão da instrução
				String novaInstrucaoEditada = JOptionPane.showInputDialog("Digite a nova versão da instrução:");
				if (novaInstrucaoEditada != null && !novaInstrucaoEditada.isEmpty()) {
					instrucoesEditadas.set(indiceInstrucaoSelecionada, novaInstrucaoEditada);
				}
			}
		}

		return instrucoesEditadas;
	}

	public static List<String> editarCategorias(List<String> categorias) {
	    List<String> categoriasEditadas = new ArrayList<>(categorias);

	    while (true) {
	        // Exiba a lista de categorias e permita ao usuário escolher o que editar
	        String[] categoriasArray = categoriasEditadas.toArray(new String[0]);
	        String categoriaSelecionada = (String) JOptionPane.showInputDialog(
	                null,
	                "Escolha a categoria que deseja editar:",
	                "Editar Categorias",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                categoriasArray,
	                categoriasArray[0]
	        );

	        if (categoriaSelecionada == null) {
	            break; // O usuário cancelou a edição de categorias
	        }

	        // Encontre a categoria selecionada
	        int indiceCategoriaSelecionada = categoriasEditadas.indexOf(categoriaSelecionada);
	        if (indiceCategoriaSelecionada == -1) {
	            JOptionPane.showMessageDialog(null, "A categoria não foi encontrada.");
	            break;
	        }

	        // Solicite ao usuário a nova versão da categoria
	        String novaCategoriaEditada = JOptionPane.showInputDialog("Digite a nova versão da categoria:",
	                categoriaSelecionada);

	        if (novaCategoriaEditada != null) {
	            if (!novaCategoriaEditada.isEmpty()) {
	                categoriasEditadas.set(indiceCategoriaSelecionada, novaCategoriaEditada);
	            } else {
	                // Remova a categoria se a nova versão for uma string vazia
	                categoriasEditadas.remove(indiceCategoriaSelecionada);
	            }
	        }
	    }

	    return categoriasEditadas;
	}

	public static List<String> editarListaCategorias(List<String> categorias) {
		List<String> categoriasEditadas = new ArrayList<>(categorias);
		// Exiba a lista de categorias e permita ao usuário escolher qual editar
		String[] categoriasArray = categoriasEditadas.toArray(new String[0]);
		String categoriaSelecionada = (String) JOptionPane.showInputDialog(null,
				"Escolha a categoria que deseja editar:", "Editar Categorias", JOptionPane.QUESTION_MESSAGE, null,
				categoriasArray, categoriasArray[0] // Valor padrão
		);

		if (categoriaSelecionada != null) {
			// Encontre a categoria selecionada
			int indiceCategoriaSelecionada = categoriasEditadas.indexOf(categoriaSelecionada);
			if (indiceCategoriaSelecionada >= 0) {
				// Solicite ao usuário a nova versão da categoria
				String novaCategoriaEditada = JOptionPane.showInputDialog("Digite a nova versão da categoria:");
				if (novaCategoriaEditada != null && !novaCategoriaEditada.isEmpty()) {
					categoriasEditadas.set(indiceCategoriaSelecionada, novaCategoriaEditada);
				}
			}
		}

		return categoriasEditadas;
	}

	public static int exibirMenuEditar(String titulo) {
		String[] opcoesEditar = { "Adicionar", "Editar Existente", "Remover", "Concluir" };
		return exibirMenu(opcoesEditar, titulo);
	}

}
