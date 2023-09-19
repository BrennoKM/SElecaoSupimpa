package unidades;

public class Colher extends UnidadeMedida {
    private static final long serialVersionUID = 1L;

    public Colher() {
        super("Colher", 15.0); // 1 colher = 15 gramas
    }

    public String getNome() {
        return "Colher(es)";
    }
    
	public double converterParaBase(double valor) {
		return valor;
	}
}
