package unidades;

public class Mililitro extends UnidadeMedida {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8310137107396280529L;

	public Mililitro() {
    	super("Mililitro", 1.0);
    }

	public String getNome() {
        return "Mililitros";
    }
	
    public double converterParaBase(double valor) {
        return valor;
    }
}