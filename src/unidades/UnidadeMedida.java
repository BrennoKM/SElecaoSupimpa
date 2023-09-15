package unidades;

import java.io.Serializable;

public abstract class UnidadeMedida implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1991519923516620749L;
	protected double fatorConversao;
    protected String unidade;

    public UnidadeMedida(String unidade, double fatorConversao) {
		this.unidade = unidade;
		this.fatorConversao = fatorConversao;
		
	}

	public abstract double converterParaBase(double valor);

	public String getNome() {
		return unidade;
	}
	
    public double converterPara(UnidadeMedida outraUnidade, double valor) {
        double valorNaBase = converterParaBase(valor);
        return valorNaBase / outraUnidade.fatorConversao;
    }
}