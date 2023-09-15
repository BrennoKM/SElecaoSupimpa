package view;

import javax.swing.JOptionPane;

import model.SElecaoSupimpa;

public class SE {
	
	public static void main(String[] args) {
        // Exibe uma caixa de diálogo com botões para escolher entre admin e usuário
        Object[] options = { "Administrador", "Usuário Comum" };
        int escolha = JOptionPane.showOptionDialog(
                null, // Componente pai (null para centralizar na tela)
                "Escolha como você deseja entrar:", // Mensagem
                "Tela de Início", // Título da janela
                JOptionPane.YES_NO_OPTION, // Tipo de opções (sim e não)
                JOptionPane.QUESTION_MESSAGE, // Tipo de mensagem (pergunta)
                null, // Ícone personalizado (null para usar o padrão)
                options, // Opções disponíveis
                options[0] // Opção padrão selecionada
        );

        if (escolha == JOptionPane.YES_OPTION) {
            // O usuário escolheu entrar como administrador
            System.out.println("Você entrou como administrador.");
            // Coloque sua lógica para administrador aqui
        } else if (escolha == JOptionPane.NO_OPTION) {
            // O usuário escolheu entrar como usuário comum
            System.out.println("Você entrou como usuário comum.");
            // Coloque sua lógica para usuário comum aqui
        } else {
            // O usuário fechou a caixa de diálogo (ou pressionou Esc)
            System.out.println("Você fechou a caixa de diálogo.");
            // Coloque sua lógica para essa situação aqui
        }
    }
}
