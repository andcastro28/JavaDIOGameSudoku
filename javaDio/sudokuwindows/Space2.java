
/*
 cada espaço do jogo sudoku
 valor atual
 valor esperado
 fixo - true ou false posicao no quadro
*/
public class Space2
 {

    private int vatual;
    private int vesperado;
    private boolean fixo;

    public Space2(final int actual, final int expected, final boolean fixed) {
        this.vatual = actual;
        this.vesperado = expected;
        this.fixo = fixed;
    }

    public int getAtual() {
        return vatual;
    }

    public void setAtual(final int actual) {
         this.vatual = actual;
    }
   
    public void clearSpace(){
        setAtual(0);
    }

    public int getEsperado() {
        return vesperado;
    }

    public void setEsperado(final int esperado) {
        this.vesperado=esperado;
    }
    public void setFixo(final boolean f)
    {
		this.fixo=f;
	}
	public boolean getFixo()
    {
        return fixo;
    }
}//space 2
