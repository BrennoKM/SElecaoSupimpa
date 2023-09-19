package unidades;

public class Xicara extends UnidadeMedida {
    private static final long serialVersionUID = 1L;

    public Xicara() {
        super("Xícara", 240.0); // 1 xícara = 240 gramas
    }

    public String getNome() {
        return "Xícara(s)";
    }
    
    
	public double converterParaBase(double valor) {
		return valor;
	}
}