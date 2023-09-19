package unidades;

public class ColherDeCha extends UnidadeMedida {
    private static final long serialVersionUID = 1L;

    public ColherDeCha() {
        super("Colher de Chá", 5.0); // 1 colher de chá = 5 gramas
    }

    public String getNome() {
        return "Colher(es) de Chá";
    }
    
	public double converterParaBase(double valor) {
		return valor;
	}
}

