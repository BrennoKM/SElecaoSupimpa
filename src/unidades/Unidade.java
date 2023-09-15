package unidades;

public class Unidade extends UnidadeMedida {
    /**
	 * 
	 */
	private static final long serialVersionUID = -855543178929079650L;

	public Unidade() {
        super("Unidade", 1.0); // Aqui, 1 unidade é equivalente a 1 unidade.
    }

    public double converterParaBase(double valor) {
        return valor; // Unidades como "Unidade" não precisam de conversão
    }
}
