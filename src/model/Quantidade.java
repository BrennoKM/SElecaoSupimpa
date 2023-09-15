package model;

import java.io.Serializable;

import unidades.UnidadeMedida;

public class Quantidade implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -31529394463824976L;
	private double valor;
    private UnidadeMedida unidade;

    public Quantidade(double valor, UnidadeMedida unidade) {
        this.valor = valor;
        this.unidade = unidade;
    }

    public double getValor() {
        return valor;
    }

    public UnidadeMedida getUnidade() {
        return unidade;
    }

    public boolean maiorOuIgualA(Quantidade outraQuantidade) {
        if (this.unidade.equals(outraQuantidade.unidade)) {
            return this.valor >= outraQuantidade.valor;
        } else {
            double valorConvertido = this.unidade.converterPara(outraQuantidade.unidade, this.valor);
            return valorConvertido >= outraQuantidade.valor;
        }
    }

	public Class<? extends UnidadeMedida> getUnidadeMedida() {
		return unidade.getClass();
	}
}