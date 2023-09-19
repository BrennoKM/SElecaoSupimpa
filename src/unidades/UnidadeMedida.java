package unidades;

import java.io.Serializable;
import java.util.Objects;

public abstract class UnidadeMedida implements Serializable {
    private static final long serialVersionUID = -1991519923516620749L;
    protected double fatorConversao;
    protected String unidade;

    public UnidadeMedida(String unidade, double fatorConversao) {
        if (fatorConversao <= 0) {
            throw new IllegalArgumentException("O fator de conversão deve ser maior que zero.");
        }
        this.unidade = unidade;
        this.fatorConversao = fatorConversao;
    }

    public abstract double converterParaBase(double valor);

    public String getNome() {
        return unidade;
    }

    public double converterPara(UnidadeMedida outraUnidade, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor não pode ser negativo.");
        }
        double valorNaBase = converterParaBase(valor);
        return valorNaBase / outraUnidade.fatorConversao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnidadeMedida that = (UnidadeMedida) o;
        return Double.compare(that.fatorConversao, fatorConversao) == 0 &&
                Objects.equals(unidade, that.unidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fatorConversao, unidade);
    }

    @Override
    public String toString() {
        return unidade;
    }
}
