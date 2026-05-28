import java.util.Scanner;


public class SudokuSolver
{

    // Representação do tabuleiro. 0 representa uma célula vazia.
    private static final int TAMANHO_TABULEIRO = 9;
    private final static Scanner teclado = new Scanner(System.in);
    
    private static String BOARD="";
    private static StringBuilder builder=null;
    private static Space novoquadro[][]= new Space[9][9];
    private static int buracos=0, qtdErros=0;
/*
 cada espaço do jogo sudoku
 valor atual
 valor esperado
 fixo - true ou false posicao no quadro
*/
        //exemplo int[][] matriz = new int[3][4];
    
    private static int[][] tabuleiro = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };
    
    
    public static void monta_BoardZerado()
    {
            BOARD=      "+****0********1********2*********3********4********5*********6********7********8****+\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"0|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |0\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"1|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |1\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"2|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |2\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*************************************************************************************\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"3|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |3\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"4|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |4\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"5|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |5\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*************************************************************************************\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"6|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |6\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"7|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |7\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"*|-------||-------||-------|*|-------||-------||-------|*|-------||-------||-------|*\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"8|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |*|  %s   ||  %s   ||  %s   |8\n";
            BOARD=BOARD+"*|       ||       ||       |*|       ||       ||       |*|       ||       ||       |*\n";
            BOARD=BOARD+"+****0********1********2*********3********4********5*********6********7********8****+\n";
            
         
    }//monta
    

    public static void main(String[] args)
    {
        int option = -1;
        while (true)
        {
			if(qtdErros==3)
			{
				System.out.println("vc cometeu 3 erros, perdeu a partida!!!  :(");
				System.exit(0);
			}
            System.out.println("---------JOGO SUDOKO--(9x9)-------------");
            System.out.println("----------------------------------------");
            if(buracos!=0)
			System.out.println("vc precisa preencher "+buracos+" vazios");
            System.out.println("Qtde de erros:"+qtdErros+"(maximo 3 erros)");
             System.out.println("-------------------------------------");
            System.out.println("     Selecione uma das opcoes a seguir");
            System.out.println("     1 - Iniciar um novo Jogo");
            System.out.println("     2 - Colocar um novo numero");
            System.out.println("     3 - Remover um numero");
            System.out.println("     4 - Visualizar jogo atual");
            System.out.println("     5 - Resolver o jogo(preenche tudo)");
            System.out.println("     6 - Limpar jogo");
            System.out.println("     7 - Sair");
            System.out.print("     Qual opcao:");
            option = teclado.nextInt();

            switch (option){
                case 1 : startGame(); break;
                case 2 : inputNumber(); break;
                case 3 : removeNumber(); break;
                case 4 : imprimirTabuleiro(tabuleiro);
                         break;//showCurrentGame();
                case 5 : 
                    if (resolverSudoku())
                    {
						System.out.println("Sudoku resolvido com sucesso!");
						 atualizaQuadro();
                         System.out.println(builder); 
						imprimirTabuleiro(tabuleiro);
					} else
					{
						imprimirTabuleiro(tabuleiro);
						System.out.println("Nenhuma solucao encontrada para este tabuleiro.");
					}//showGameStatus();
					  break;
				case 6 : clearGame(); break;
               
                case 7 : System.exit(0);
                default : System.out.println("Opcao invalida, selecione uma das opcoes do menu\n");
            }
        }//while
    
    
            
        //imprimirTabuleiro(tabuleiro);
       
    }//main
    
    //metodo para remover um numero do quadro
      private static void removeNumber()
      {
        if (builder == null){
            System.out.println("O jogo ainda nao foi iniciado iniciado\n");
            return;
        }
        System.out.print("Informe a LINHA que em que o numero sera removido:");
        int row = runUntilGetValidNumber(0, 8);
        System.out.print("Informe a COLUNA que em que o numero sera removido:");
        int col = runUntilGetValidNumber(0, 8);

        if (novoquadro[row][col].isFixo())
        {
            System.out.printf("A posicao [%s,%s] tem um valor fixo (%s) nao pode ser removido <tecle enter>\n", row, col, novoquadro[row][col].getAtual() );
            qtdErros++;
            teclado.nextInt();
        }
        else
        {
            novoquadro[row][col].setAtual(0);
            tabuleiro[row][col]=0;
            atualizaQuadro();
            System.out.printf("A posicao [%s,%s] foi removido com sucesso!!!\n", row, col);
            buracos--;
 		}
 		System.out.println(builder);
      
    }//remover numero
    
    //metodo para inserir um numero no quadro
     private static void inputNumber()
     {
        if (builder == null)
        {
            System.out.println("O jogo ainda nao foi iniciado iniciado\n");
            return;
        }

        System.out.print("Informe a LINHA que em que o numero sera inserido:");
        int row = runUntilGetValidNumber(0, 8);
        
        System.out.print("Informe a COLUNA que em que o numero sera inserido:");
        int col = runUntilGetValidNumber(0, 8);
      
        System.out.printf("Informe o numero que vai entrar na posicao [%s,%s]:", row, col);
        int value = runUntilGetValidNumber(1, 9);
        if (novoquadro[row][col].isFixo())
        {
            System.out.printf("A posicao [%s,%s] tem um valor fixo %s <tecle enter>\n", row, col, novoquadro[row][col].getAtual() );
            qtdErros++;
            teclado.nextInt();
        }
        else
        {
           if(movimentoValido(row,col,value))
           { novoquadro[row][col].setAtual(value);
             System.out.printf("A posicao [%s,%s] tem valor %s\n", row, col, value);
             atualizaQuadro();
           }else
           {
			 System.out.printf("o valor %s nao pode ser inserido nessa posicao [%s,%s] <tecle enter>\n", value, col, row);
			 qtdErros++;
			 teclado.nextInt();
		   } 
		}
		
		System.out.println(builder);
   	}//inputNumber
   	//////////////////////////////////////////////////////////////////////
   	// Verifica se a inserção é válida nas linhas, colunas e blocos 3x3
    private static boolean movimentoValido(int linha, int coluna, int numero) {
        // Verifica a linha
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (novoquadro[linha][i].getAtual() == numero) {
                return false;
            }
        }

        // Verifica a coluna
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (novoquadro[i][coluna].getAtual() == numero) {
                return false;
            }
        }

        // Verifica a grade 3x3
        int linhaCaixa = linha - linha % 3;
        int colunaCaixa = coluna - coluna % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (novoquadro[linhaCaixa + i][colunaCaixa + j].getAtual() == numero) {
                    return false;
                }
            }
        }

        return true;
    }
   	//////////////////////////////////////////////////////////////////////
   	 private static int runUntilGetValidNumber(final int min, final int max)
   	 {
        int current = teclado.nextInt();
        while (current < min || current > max){
            System.out.printf("Informe um número entre %s e %s\n", min, max);
            current = teclado.nextInt();
        }
        return current;
     }//pega numero teclado
     ////////////////////////////////////////////////////////////
         //metodo para atualizar o quadro com novos valores
    public static void atualizaQuadro()
    {
        monta_BoardZerado();
        builder = new StringBuilder(BOARD);
        int posicao=0;
        String valor="";
        buracos=0;     
        for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++)
        {
            for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++)
            {
                if(builder.indexOf("%s")!=-1)
                {
                   posicao=builder.indexOf(" %s");
                   
                   if(novoquadro[linha][coluna].isFixo())
					     valor="<"+novoquadro[linha][coluna].getAtual()+">";
                   else
                        {
							 valor=" "+novoquadro[linha][coluna].getAtual()+" ";
                             if(novoquadro[linha][coluna].getAtual()==0)buracos++;
                         }
                   tabuleiro[linha][coluna]=novoquadro[linha][coluna].getAtual();
                   builder=new StringBuilder(builder).replace(posicao, posicao+3,valor);   
                   
                  
                 } 
               
            }//for coluna
        }//for linha
       System.out.printf("O Quadro foi atualizado, vc tem %s buracos vazios......\n",buracos);
        
	}//atualiza quadro

/////////////////////////////////////////
      public static void clearGame()
      {
		  
		 for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++)
           for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++)
              tabuleiro[linha][coluna]=0;
           
		  /*
		  tabuleiro = {
		     0  1  2  3  4  5  6  7  8
		    
            {0, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {5, 0, 0, 0, 8, 0, 0, 7, 9}
            */
            
            /*  0  1  2  3  4  5  6  7  8
		    
           0   {0, 3, 0, 0, 7, 0, 0, 0, 0},
            */
            tabuleiro[0][0]=5;
            tabuleiro[0][1]=3;
            tabuleiro[0][4]=7;
            
            /*  0  1  2  3  4  5  6  7  8
		    
             1 {6, 0, 0, 1, 9, 5, 0, 0, 0},
            */
            tabuleiro[1][0]=6;
            tabuleiro[1][3]=1;
            tabuleiro[1][4]=9;
            tabuleiro[1][5]=5;
            
            /*
             0  1  2  3  4  5  6  7  8
		    
          2 {0, 9, 8, 0, 0, 0, 0, 6, 0},
            */
            tabuleiro[2][1]=9;
            tabuleiro[2][2]=8;
            tabuleiro[2][7]=6;
            
            
            /*
               0  1  2  3  4  5  6  7  8
		    
           3  {8, 0, 0, 0, 6, 0, 0, 0, 3},
            */
            
             tabuleiro[3][0]=8;
            tabuleiro[3][4]=6;
            tabuleiro[3][8]=3;
            
            
            /*  0  1  2  3  4  5  6  7  8
		    
           4   {4, 0, 0, 8, 0, 3, 0, 0, 1},
            */
            tabuleiro[4][0]=4;
            tabuleiro[4][3]=8;
            tabuleiro[4][5]=3;
            tabuleiro[4][8]=1;
            
            /*  0  1  2  3  4  5  6  7  8
           5   {7, 0, 0, 0, 2, 0, 0, 0, 6},
             */
            tabuleiro[5][0]=7;
            tabuleiro[5][4]=2;
            tabuleiro[5][8]=6;
            
            /*   0  1  2  3  4  5  6  7  8
             6  {0, 6, 0, 0, 0, 0, 2, 8, 0},
            */
             tabuleiro[6][1]=6;
            tabuleiro[6][6]=2;
            tabuleiro[6][7]=8;
            
            
            /*  0  1  2  3  4  5  6  7  8
             7 {0, 0, 0, 4, 1, 9, 0, 0, 5} */
             tabuleiro[7][3]=4;
            tabuleiro[7][4]=1;
            tabuleiro[7][5]=9;
            tabuleiro[7][8]=5;
            
            /*   0  1  2  3  4  5  6  7  8
             8  {5, 0, 0, 0, 8, 0, 0, 7, 9}*/
              tabuleiro[8][0]=0;
            tabuleiro[8][4]=8;
            tabuleiro[8][7]=7;
            tabuleiro[8][8]=9;
             
            startGame();
           
	  }
    ////////////////////////////////////////////////////////////
    //metodo para iniciar o jogo
    public static void startGame()
    {
		  
        monta_BoardZerado();
        builder = new StringBuilder(BOARD);
        int posicao=0;
        String valor="";
        boolean travado=false;
        
        /*
        String text = "apple and banana and orange";
        String target = "and";
        String replacement = "or";

         int index = text.indexOf(target);
        if (index != -1) {
        text = new StringBuilder(text)
        .replace(index, index + target.length(), replacement)
        .toString();
         }
        */
        buracos=0;
        for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++)
        {
            for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++)
            {
                if(builder.indexOf("%s")!=-1)
                {
                   posicao=builder.indexOf(" %s");
                   if(tabuleiro[linha][coluna]!=0)
                     valor="<"+tabuleiro[linha][coluna]+">";
                   else
                   {
                     valor=" "+tabuleiro[linha][coluna]+" ";
                     buracos++;
                   }
                   if(tabuleiro[linha][coluna]!=0)travado=true;
                   else travado=false;
                   
                   novoquadro[linha][coluna]=new Space(tabuleiro[linha][coluna],travado);
                   builder=new StringBuilder(builder).replace(posicao, posicao+3,valor);
                 } 
               
            }//for coluna
        }//for linha
        //System.out.println(BOARD);
        System.out.println(builder);
        System.out.printf("O jogo esta pronto para comecar, vc tem %s buracos vazios......\n",buracos);
        
	}//startGame

    // Método principal de backtracking para resolver o jogo
    public static boolean resolverSudoku() {
        for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++) {
            for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++) {
                // Encontra uma célula vazia
                if (tabuleiro[linha][coluna] == 0) {
                    for (int numero = 1; numero <= 9; numero++) 
                    {   //System.out.println("-----> numero="+numero+"\n");
                        if (movimentoValido(tabuleiro, linha, coluna, numero)) 
                        {
							//System.out.println("  valido linha="+linha+" coluna="+coluna+" numero="+numero+"\n");
                            tabuleiro[linha][coluna] = numero;
                            novoquadro[linha][coluna].setAtual(numero);
                            
                            // Chamada recursiva para tentar resolver o resto
                            if (resolverSudoku()) {
                                return true;
                            }

                            // Se não for possível resolver, desfaz a escolha (backtracking)
                            tabuleiro[linha][coluna] = 0;
                            novoquadro[linha][coluna].setAtual(0);
                            //System.out.println(" invalido linha="+linha+" coluna="+coluna+" numero=0 \n");
                       
                        }//movimento valido
                    }//for numero
                    return false; // Retorna falso se nenhum número de 1 a 9 couber aqui
                }
            }
        }
        return true; // Tabuleiro completamente preenchido
    }

    // Verifica se a inserção é válida nas linhas, colunas e blocos 3x3
    private static boolean movimentoValido(int[][] tabuleiro, int linha, int coluna, int numero) {
        // Verifica a linha
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (tabuleiro[linha][i] == numero) {
                return false;
            }
        }

        // Verifica a coluna
        for (int i = 0; i < TAMANHO_TABULEIRO; i++) {
            if (tabuleiro[i][coluna] == numero) {
                return false;
            }
        }

        // Verifica a grade 3x3
        int linhaCaixa = linha - linha % 3;
        int colunaCaixa = coluna - coluna % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[linhaCaixa + i][colunaCaixa + j] == numero) {
                    return false;
                }
            }
        }

        return true;
    }

    // Exibe o tabuleiro formatado no console
    private static void imprimirTabuleiro(int[][] tabuleiro) {
        for (int linha = 0; linha < TAMANHO_TABULEIRO; linha++) {
            for (int coluna = 0; coluna < TAMANHO_TABULEIRO; coluna++) {
                System.out.print(tabuleiro[linha][coluna] + " ");
            }
            System.out.println();
        }
    }
    
    
    
    
    
}
