import java.awt.event.*;//enventos
import java.awt.*;     //layout
import javax.swing.*;   //objetos
import javax.swing.table.*;//model

import java.util.ArrayList;
import java.util.List;


//trabalhando com arquivo
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.io.FileInputStream;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.border.LineBorder;

public class CriaJogo extends JFrame implements ActionListener
{
   JButton btSalvar, btSair;
			
   JPanel mainPanel;
   JPanel[][] sudokuSector = new JPanel[3][3];
   
   private String row,col,value,fixed,foto, flag;
   private static int linha,pro;///procura


   private static Space2 novoquadro[][]= new Space2[9][9];
   
  ArrayList pegaTudo;////carrega com lista

public CriaJogo()
{
	  super("Criando um novo quadro do jogo *Sudoku*"); 
	  setLayout(null);
	  setLocationRelativeTo(null);
	  flag="";//altera ou incliur salvar

       
  //JButton 
		btSalvar= new JButton("Salvar Quadro");btSalvar.setToolTipText("Salvar o quadro em um arquivo.txt, para iniciar novo quadro a ser jogado");
		btSair=new JButton("Sair"); btSair.setToolTipText("Fechar o Quadro");

		mainPanel=new JPanel();
		mainPanel.setSize(600,600);
		mainPanel.setBounds(20,50,600,600);
		mainPanel.setBorder(new LineBorder(Color.BLACK, 2, true));
		mainPanel.setLayout(new GridLayout(3,3));

		Number2Text [] textField = new Number2Text[9];
		JCheckBox [] trava = new JCheckBox[9];
		 
	  MontaQuadro();
 // Preenche a matriz
	 int r=0, c=0, endCol=2, endRow=2;
	 for (int i = 0; i < 3; i++)
	 {
		 for (int j = 0; j < 3; j++)
		  {
		        //System.out.println("setor linha="+i+" setor coluna="+j);
                sudokuSector[i][j] = new JPanel();
          // Pinta os painéis de forma alternada para criar um efeito de xadrez
                if ((i + j) % 2 == 0) {
                    sudokuSector[i][j].setBackground(Color.YELLOW);
                } else {
                    sudokuSector[i][j].setBackground(Color.LIGHT_GRAY);
                }
                sudokuSector[i][j].setBorder(new LineBorder(Color.BLACK, 2, true));
                //System.out.println("getlinha="+r+" endrow="+endRow+" coluna="+c+" endcol="+endCol);
              
                List<Space2> buracos = getSpacesFromSector(c, endCol, r, endRow);
									
				JPanel setor = new JPanel();
				
				setor.setBorder(new LineBorder(Color.BLACK, 3, true));
				setor.setSize(new Dimension(200,200));
                setor.setLayout(new GridLayout(3,3));	
                		
				for(int x=0;x<buracos.size();x++)
				{  
				     textField[x]=new Number2Text(buracos.get(x));
				     textField[x].setToolTipText("Numeros de 1 a 9 a ser digitados");
				     trava[x]=new JCheckBox("",false);
				     trava[x].setToolTipText("Fixo o numero nesta posicao do quadro");
					 setor.add(textField[x]);
					 setor.add(trava[x]);
				}
										
				sudokuSector[i][j].add(setor);
				c=c+3;
				if(c==9)c=0;
				endCol=c+2;
				mainPanel.add(sudokuSector[i][j]);
        }//for j
        r=r+3;
        endRow = r + 2;
        
   }//for  i           
                
		
        add(btSalvar);
		add(btSair);
		add(mainPanel);

		btSalvar.setBounds(20,15,150,20); btSalvar.addActionListener(this);
		
    	btSair.setBounds(180+160,15,100,20); btSair.addActionListener(this);

      setLocation(200,200); setSize(650,750); setVisible(true);
       
      setResizable(false);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      revalidate();
      repaint();
    
}////////////construtor
private void MontaQuadro()
{
       int linha=0;
       int coluna=0;
       int valorx=1;
       boolean fixo=true;
       
       for(int j=0; j<9; j++)
       {
			for(int k=0; k<9; k++)
            {
				linha=j;coluna=k;
				novoquadro[linha][coluna]=new Space2(0, valorx, fixo);
				//System.out.println("quadro linha="+j+" coluna="+k+" valor="+valorx);
			}//for k colunas	
            
        }//for j linhas
}
/////////////////////////////////////////////////////////////////////////////////
   /* public static boolean resolverSudoku() {
        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
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
    private static boolean movimentoValido(int[][] tabuleiro, int linha, int coluna, int numero)
    {
        // Verifica a linha
        for (int i = 0; i < 9; i++) {
            if (tabuleiro[linha][i] == numero) {
                return false;
            }
        }

        // Verifica a coluna
        for (int i = 0; i < 9; i++) {
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

*/

 


private List<Space2> getSpacesFromSector(final int initCol, final int endCol,final int initRow, final int endRow)
    {
        List<Space2> spaceSector = new ArrayList<>();
        
        for (int r = initRow; r <= endRow; r++)
        {
            for (int c = initCol; c <= endCol; c++)
            {   
				//System.out.println("   valor="+novoquadro[r][c].getEsperado()+"fixo="+novoquadro[r][c].getFixo());

                spaceSector.add(novoquadro[r][c]);
            }
        }
        //System.out.println("===============================================================");
        return spaceSector;
    }

private void salvarArquivo()
{
	int linha=0,coluna=0;
	JFileChooser chooser = new JFileChooser("c:\\javaDio\\sudokuwindows");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos com extensao TXT","txt");
    chooser.setFileFilter(filter);
    chooser.setDialogTitle("Salvar o Arquivo Texto com os valores do quadro");
    //chooser.setMultiSelectionEnabled(false);
    
     // 3. Abrir a caixa de diálogo "Salvar"
     int userSelection = chooser.showSaveDialog(null);

     if (userSelection == JFileChooser.APPROVE_OPTION)
     {
            File fileToSave = chooser.getSelectedFile();
            
            // Garantir que o arquivo termine com a extensão .txt
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".txt")) {
                fileToSave = new File(filePath + ".txt");
            }

            // 4. Gravar o conteúdo do texto no arquivo
            String textoParaSalvar = "";
            
            try (FileWriter writer = new FileWriter(fileToSave))
            {
                
				for(int j=0; j<9; j++)//linha
				{
							for(int k=0; k<9; k++)//coluna
							{
								
								linha=j;
								coluna=k;
								textoParaSalvar=""+coluna+","+linha+","+
								                   novoquadro[linha][coluna].getEsperado()+","+
								                   String.valueOf(novoquadro[linha][coluna].getFixo()+"\n");
								 writer.write(textoParaSalvar);            
							}//for k colunas	
							
				}//for j linhas
	            JOptionPane.showMessageDialog(null,"Arquivo salvo com sucesso em: " + fileToSave.getAbsolutePath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Erro ao salvar o arquivo: " + e.getMessage());
            }
        }
         
   else
	{
		JOptionPane.showMessageDialog(null,"Você não selecionou nenhum arquivo.");
		return;
	}
	
}

public byte[] imageToByte(String arqimg) //throws IOException
	{
		InputStream is = null;
		byte[] buffer = null;
		try
		{
			is = new FileInputStream(arqimg);
			buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
		}
		catch (Exception e) {
		JOptionPane.showMessageDialog(null,"erro bytes foto="+e);
			}
	return buffer;
	}/////////

///////////////////////////////////////////////////////////////	
public static ArrayList pegaTextoDoPainel(JPanel painel)
{
		ArrayList Texto=new ArrayList<>();
		 
		// Percorre todos os componentes dentro do JPanel
		for (Component comp1 : painel.getComponents()) {
			// Verifica se o componente é um JTextField
			if (comp1 instanceof JPanel)
			{
				//System.out.println("pegou um painel");
				//colunna,linha,valor,fixo?
				
				JPanel quadro = (JPanel) comp1;
				for (Component comp2 : quadro.getComponents())
				{
					if (comp2 instanceof Number2Text)
					{
						Number2Text campo = (Number2Text) comp2;
						Texto.add(campo.getText());
						//System.out.println("pega um valor="+campo.getText());
						
					}
					if (comp2 instanceof JCheckBox)
					{
						JCheckBox campo = (JCheckBox) comp2;
						if(campo.isSelected())
                           Texto.add("true");
						else
						   Texto.add("false");
						//System.out.println("pega check="+campo.isSelected());
					}	
						
				}//for comp2
			}
		}//for comp1
		painel.revalidate();
        painel.repaint();
		return Texto;
}
///////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////
///////METODO actionlistener
public void actionPerformed(ActionEvent ae)
{
	int lin=0,col=0,conta=0;
    if(ae.getSource()==btSair){ dispose();}
    
    
    
    
    if(ae.getSource()==btSalvar)
	{
		AtualizaNovoQuadro();
		  //verifica se quadro esta correta para gravacao
		for(int li=0;  li<9; li++)
		  for(int co=0; co<9; co++)  
		  {
			  	//System.out.println("linha="+li+" coluna="+co+" valor="+novoquadro[li][co].getEsperado());

			    if(movimentoValido(li,co,novoquadro[li][co].getEsperado()))
			    {
					//System.out.println("valor valido linha="+li+" colluna="+co+" valor="+novoquadro[li][co].getEsperado());
				}
				else
				{
					//System.out.println("->valor invalido linha="+li+" colluna="+co+" valor="+novoquadro[li][co].getEsperado());
					JOptionPane.showMessageDialog(null,
					   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
					   "<h2>Quadro tem posicoes com valores invalidos</h2><body>"+
					   "<br><h1>Nao pode ser Salvo!!!!</h1><br>"+
					   "</body></html>");
					return;
				}
		  }//for
		  int resposta = JOptionPane.showConfirmDialog(
                null, 
                "Deseja realmente salvar o jogo?", 
                "Salvar o jogo", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
                );

			if (resposta == JOptionPane.YES_OPTION) {
				System.out.println("Usuario clicou em Sim!");
				salvarArquivo();
			   // Lógica para confirmar a ação
			} else if (resposta == JOptionPane.NO_OPTION) {
				System.out.println("Usuario clicou em Nao!");
			} else {
				System.out.println("Usuario fechou a janela ou cancelou!");
			}
	 	
	}
/////////////////////////////////////////

}////acao dos botoes



private boolean movimentoValido(int linha, int coluna, int numero)
    {
         // System.out.println("testando linha="+linha+" coluna="+coluna+" valor="+numero);
        // Verifica a linha
        for (int i = 0; i < 9; i++) 
        {
            if(i==coluna)continue;
            //System.out.println(" coluna="+i);
            if (novoquadro[linha][i].getEsperado() == numero) {
                //System.out.println("verifica linha****invalido linha="+linha+" coluna="+i+" valor="+novoquadro[linha][i].getEsperado());
                return false;
            }
        }

        // Verifica a coluna
        for (int i = 0; i < 9; i++)
        {
            if(i==linha)continue;
            //System.out.println(" linha="+i);
            if (novoquadro[i][coluna].getEsperado() == numero) {
                //System.out.println("verifica coluna****invalido linha="+i+" coluna="+coluna+" valor="+novoquadro[i][coluna].getEsperado());
                return false;
            }
        }

        // Verifica a grade 3x3
        int linhaCaixa = linha - linha % 3;
        int colunaCaixa = coluna - coluna % 3;
        //System.out.println("("+linhaCaixa+","+colunaCaixa+") linhaCaixa,ColunaCaixa");
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++)
             {
                if( (i+linhaCaixa)==linha && (j+colunaCaixa)==coluna)continue;
                
                //System.out.println("("+i+","+j+") linhaCaixa="+(i+linhaCaixa)+" ColunaCaixa="+(j+colunaCaixa) );
                if (novoquadro[linhaCaixa + i][colunaCaixa + j].getEsperado() == numero) {
                    //System.out.println("verifica grade****invalido linha="+(linhaCaixa+i)+" coluna="+(colunaCaixa+j)+" valor="+novoquadro[linhaCaixa+i][colunaCaixa+j].getEsperado());
                    return false;
                }
            }
        }
        //System.out.println("movimento valido linha="+linha+" coluna="+coluna+" valor="+numero);
        return true;
    }
   	///////


public void AtualizaNovoQuadro()
{   int lin=0,col=0,conta=0;
	int qtdFixos=0;
	
  // Percorre a matriz de painel
	for (int i = 0; i < 3; i++)
	 {
			for (int j = 0; j < 3; j++)
				   {
						System.out.println("setor linha="+i+" setor coluna="+j);
						
						pegaTudo=pegaTextoDoPainel(sudokuSector[i][j]);
						qtdFixos=0;
						
						conta=0;
						for(lin=(i*3); lin<(i*3+3); lin++)
						{	  
							for(col=(j*3); col<(j*3+3); col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							     System.out.println("("+lin+","+col+")"+novoquadro[lin][col].getFixo());
							     if(novoquadro[lin][col].getFixo()) qtdFixos++;
							     System.out.println("quant. fixos="+qtdFixos);
							   
							  }  
							  
							  
					    }//lin
					    if(qtdFixos<3) 
						    {
								JOptionPane.showMessageDialog(null,
							   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
							   "<h2>Setor(0,0)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
							   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
							   "</body></html>");
							   return;
						    }
						
						
						
						
						
						
						/*
						if(i==0 && j==0)
						{   conta=0;
							for(lin=0; lin<3; lin++)
							  for(col=0; col<3; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							     System.out.println("("+lin+","+col+")"+novoquadro[lin][col].getFixo());
							     if(novoquadro[lin][col].getFixo()) qtdFixos++;
							     System.out.println("quant. fixos="+qtdFixos);
							  }  
							  
							if(qtdFixos<3) 
						    {
								JOptionPane.showMessageDialog(null,
							   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
							   "<h2>Setor(0,0)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
							   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
							   "</body></html>");
							   continue;
						    }  
					    }
						
						qtdFixos=0;
						
						if(i==0 && j==1)
						{
							conta=0;
							for(lin=0; lin<3; lin++)
							  for(col=3; col<6; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							     if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(0,1)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}

						}
						
						qtdFixos=0;
						
						if(i==0 && j==2)
						{
							conta=0;
							for(lin=0; lin<3; lin++)
							  for(col=6; col<9; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(0,2)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
									  
						}
				
						
						qtdFixos=0;
						///////////////////////////////////////////////////////////////
						if(i==1 && j==0)
						{
							conta=0;
							for(lin=3; lin<6; lin++)
							  for(col=0; col<3; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(1,0)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
						}
						
						qtdFixos=0;	
						if(i==1 && j==1)
						{
							conta=0;
							for(lin=3; lin<6; lin++)
							  for(col=3; col<6; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(1,1)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
						}
						
						qtdFixos=0;  
						if(i==1 && j==2)
						{
							conta=0;
							for(lin=3; lin<6; lin++)
							  for(col=6; col<9; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(1,2)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
						}
						
						qtdFixos=0;  
						/////////////////////////////////////////////////////////////////////////////
						if(i==2 && j==0)
						{
							conta=0;
							for(lin=6; lin<9; lin++)
							  for(col=0; col<3; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(2,0)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
						}
						
						qtdFixos=0;	
						if(i==2 && j==1)
						{
							conta=0;
							for(lin=6; lin<9; lin++)
							  for(col=3; col<6; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(2,1)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
						}
						
						qtdFixos=0;  
						if(i==2 && j==2)
						{
							conta=0;
							for(lin=6; lin<9; lin++)
							  for(col=6; col<9; col++)
							  {
								 novoquadro[lin][col].setEsperado(Integer.parseInt(""+pegaTudo.get(conta++)));
							     novoquadro[lin][col].setFixo(Boolean.parseBoolean(""+pegaTudo.get(conta++)));
							      if(novoquadro[lin][col].getFixo()) qtdFixos++;
							  } 
							  if(qtdFixos<3) 
								{
									JOptionPane.showMessageDialog(null,
								   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
								   "<h2>Setor(2,2)("+i+","+j+") tem "+qtdFixos+" casas com valores fixos</h2><body>"+
								   "<br><h1>Precisa de pelo menos 3 fixos por setor!!!!</h1><br>"+
								   "</body></html>");
								   continue;
								}
						}  
						
										
				
						novoquadro[3][0].setEsperado(Integer.parseInt(""+pegaTudo.get(0)));
						novoquadro[3][0].setFixo(Boolean.parseBoolean(""+pegaTudo.get(1)));
						
						novoquadro[3][1].setEsperado(Integer.parseInt(""+pegaTudo.get(2)));
						novoquadro[3][1].setFixo(Boolean.parseBoolean(""+pegaTudo.get(3)));
						
						novoquadro[3][2].setEsperado(Integer.parseInt(""+pegaTudo.get(4)));
						novoquadro[3][2].setFixo(Boolean.parseBoolean(""+pegaTudo.get(5)));
						//////////////////////////////////
						novoquadro[4][0].setEsperado(Integer.parseInt(""+pegaTudo.get(6)));
						novoquadro[4][0].setFixo(Boolean.parseBoolean(""+pegaTudo.get(7)));
						
						novoquadro[4][1].setEsperado(Integer.parseInt(""+pegaTudo.get(8)));
						novoquadro[4][1].setFixo(Boolean.parseBoolean(""+pegaTudo.get(9)));
						
						novoquadro[4][2].setEsperado(Integer.parseInt(""+pegaTudo.get(10));
						novoquadro[4][2].setFixo(Boolean.parseBoolean(""+pegaTudo.get(11)));
						
						//////////////////////////////////
						novoquadro[5][0].setEsperado(Integer.parseInt(""+pegaTudo.get(12)));
						novoquadro[5][0].setFixo(Boolean.parseBoolean(""+pegaTudo.get(13)));
						
						novoquadro[5][1].setEsperado(Integer.parseInt(""+pegaTudo.get(14)));
						novoquadro[5][1].setFixo(Boolean.parseBoolean(""+pegaTudo.get(15)));
						
						novoquadro[5][2].setEsperado(Integer.parseInt(""+pegaTudo.get(16)));
						novoquadro[5][2].setFixo(Boolean.parseBoolean(""+pegaTudo.get(17)));
					    
						
						
						System.out.println("valor="+pegaTudo.get(0));
						System.out.println("fixo="+pegaTudo.get(1));
						System.out.println("valor="+pegaTudo.get(2));
						System.out.println("fixo="+pegaTudo.get(3));
						System.out.println("valor="+pegaTudo.get(4));
						System.out.println("fixo="+pegaTudo.get(5));
                        
                        System.out.println("valor="+pegaTudo.get(6));
						System.out.println("fixo="+pegaTudo.get(7));
						System.out.println("valor="+pegaTudo.get(8));
						System.out.println("fixo="+pegaTudo.get(9));
						System.out.println("valor="+pegaTudo.get(10));
						System.out.println("fixo="+pegaTudo.get(11));
                        
                        System.out.println("valor="+pegaTudo.get(12));
						System.out.println("fixo="+pegaTudo.get(13));
						System.out.println("valor="+pegaTudo.get(14));
						System.out.println("fixo="+pegaTudo.get(15));
						System.out.println("valor="+pegaTudo.get(16));
						System.out.println("fixo="+pegaTudo.get(17));
      											
						
						System.out.println("===================================");
						*/	
					}//for j
	}//for  i 
            

}
 


public static void main(String t[])
   {
        new CriaJogo();
   }


}/////classss
