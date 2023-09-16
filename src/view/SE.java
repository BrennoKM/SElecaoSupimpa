package view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import model.Ingrediente;
import model.Quantidade;
import model.Receita;
import model.SElecaoSupimpa;

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
				//JOptionPane.showMessageDialog(null, "Opção inválida para escolha do tipo de usuário.");
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
				editarReceita(se);
				break;
			case 2:
				removerReceita(se);
				break;
			case 3:
				continuar = false; // Para sair do loop
				break;
			default:
				//JOptionPane.showMessageDialog(null, "Opção inválida para administrador.");
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
				//JOptionPane.showMessageDialog(null, "Opção inválida para usuário comum.");
				return;
			}
		}
	}

	public static int exibirMenuAdmin() {
		String[] opcoesAdmin = { "Adicionar Nova Receita", "Editar Receita", "Remover Receita", "Salvar Receitas",
				"Voltar" };
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
		JOptionPane.showMessageDialog(null, "Implementar a lógica para adicionar uma nova receita.");
	}

	public static void editarReceita(SElecaoSupimpa se) {
		JOptionPane.showMessageDialog(null, "Implementar a lógica para editar uma receita existente.");
	}
	
	public static void salvarReceitas(SElecaoSupimpa se) {
		JOptionPane.showMessageDialog(null, "Implementar a lógica para remover uma receita existente.");
	}

	public static void removerReceita(SElecaoSupimpa se) {
		JOptionPane.showMessageDialog(null, "Implementar a lógica para remover uma receita existente.");
	}

	public static void iniciarSistemaEspecialista(SElecaoSupimpa se) {
		 // Exibe a lista de ingredientes
		se.recarregarReceitasCliente();
		Set<Ingrediente> ingredientes = new HashSet<>(se.getIngredientes(se.getReceitasCliente())); 

		 boolean ingredientesModificados;

		    do {
		        ingredientesModificados = false;

		        for (Ingrediente ingrediente : ingredientes) {
		            int escolha = JOptionPane.showConfirmDialog(
		                null,
		                "Você possui o ingrediente " + ingrediente.getNome() + "?",
		                "Possui o Ingrediente?",
		                JOptionPane.YES_NO_OPTION
		            );

		            if (escolha != JOptionPane.YES_OPTION) {
		                se.removerReceitasIngredienteCliente(ingrediente);
		                ingredientes = new HashSet<>(se.getIngredientes(se.getReceitasCliente())); 
		                ingredientesModificados = true;
		                break; // Saia do loop e comece novamente após a modificação
		            } else {
		                Quantidade quantidade = obterQuantidade(ingrediente);
		                ingrediente.setQuantidade(quantidade);
		                se.addIngredienteCliente(ingrediente);
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

	// Método para obter a quantidade de um ingrediente
	public static Quantidade obterQuantidade(Ingrediente ingrediente) {
	    Quantidade quantidade;
	    try {
	        double qnt = Double.parseDouble(JOptionPane.showInputDialog("Digite a quantidade de " + ingrediente.getNome() +"("+ ingrediente.getUnidade().getNome()+"):"));
	        quantidade = new Quantidade(qnt, ingrediente.getUnidade());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Quantidade inválida. Insira um número válido.");
	        quantidade = obterQuantidade(ingrediente); // Chama novamente se a entrada for inválida
	    }
	    return quantidade;
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
}
