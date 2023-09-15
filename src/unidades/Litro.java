package unidades;

public class Litro extends UnidadeMedida {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6126234358940482919L;

	public Litro() {
    	super("Litro", 1000.0);
    }

	public String getNome() {
        return "Litros";
    }
	
    public double converterParaBase(double valor) {
        return valor * fatorConversao;
    }
}