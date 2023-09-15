package unidades;

public class Grama extends UnidadeMedida {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6597367068358738325L;

	public Grama() {
    	super("Grama", 1.0);
    }

    public double converterParaBase(double valor) {
        return valor;
    }
}
