
/*
 cada espaço do jogo sudoku
 valor atual
 valor esperado
 fixo - true ou false posicao no quadro
*/
public class Space
 {

    private int vatual;
    private final int vesperado;
    private final boolean fixo;


    public Space(final int expected, final boolean fixed) {
        this.vesperado = expected;
        this.fixo = fixed;
        if (fixed){
            vatual = expected;
        }
    }

    public int getAtual() {
        return vatual;
    }

    public void setAtual(final int actual) {
        if (fixo) return;
        this.vatual = actual;
    }

    public void clearSpace(){
        setAtual(0);
    }

    public int getEsperado() {
        return vesperado;
    }

    public boolean isFixo() {
        return fixo;
    }
}//space
