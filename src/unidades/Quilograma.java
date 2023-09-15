package unidades;

public class Quilograma extends UnidadeMedida {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3065663352284805087L;

	public Quilograma() {
    	super("Quilograma", 1000.0);
    }

    public double converterParaBase(double valor) {
        return valor * fatorConversao;
    }
}
