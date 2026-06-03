/*
 cada espaço do jogo sudoku
 valor atual
 valor esperado
 fixo - true ou false posicao no quadro
*/
public class Space
 {

    private int lin;
    private int col;
    private int vatual;
    private final int vesperado;
    private final boolean fixo;

    public Space(final int expected, final boolean fixed, final int lin, final int col)
    {
        this.lin=lin;
        this.col=col;
        this.vesperado = expected;
        this.fixo = fixed;
        if (fixed){
            vatual = expected;
        }
    }

    public int getLin() {
        return lin;
    }

    public int getCol() {
        return col;
    }
    
    public void setLin(final int l) {
          this.lin = l;
    }
    
    
     public void setCol(final int c) {
          this.col = c;
    }
    
    public int getAtual() {
        return vatual;
    }

    public void setAtual(final int actual) {
        if (fixo) return;
        this.vatual = actual;
    }
    public void setAtual2(final int actual) {
          this.vatual = actual;
    }

    public void clearSpace(){
        setAtual2(0);
    }

    public int getEsperado() {
        return vesperado;
    }

    public boolean isFixo() {
        return fixo;
    }
}//space
