import java.awt.event.*;//enventos
import java.awt.*;     //layout
import javax.swing.*;   //objetos
import javax.swing.table.*;//model

import java.util.ArrayList;
import java.util.List;

//escolher foto
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.io.FileInputStream;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.LineBorder;

public class Jogo extends JFrame implements ActionListener
{
   JButton btReiniciar, btVerifica, btCompleta, btSair, btArquivo;
   JLabel  lbQtdBuracos, lbQtdErros;
   public static JTextField txtQtdBuracos, txtQtdErros;
			
   JPanel mainPanel;
   public static JPanel[][] sudokuSector = new JPanel[3][3];
   
   private String row,col,value,fixed, flag;
   byte[] bytefoto;
   public static int linha,pro, qtdBuracos, qtdErros, qtdMaxima;///procura

   public static boolean fim;
   public static Space novoquadro[][]= new Space[9][9];
   
  ArrayList pegaTudo;////carrega com lista

public Jogo()
{
  super("Sudoku"); 
  setLayout(null);
  setLocationRelativeTo(null);
  flag="vazio";//vazio ou criado o quadro
  fim=false;
  qtdMaxima=Menu.maximo;

    
  //JButton btReiniciar, btVerifica, btCompleta, btSair;
btArquivo= new JButton("Ler Arquivo");btArquivo.setToolTipText("Selecione o Arquivo Texto, com dados do quadro a ser jogado");
btReiniciar= new JButton("Reiniciar");btReiniciar.setToolTipText("Reiniciar jogo , limpar tudo");
btVerifica = new JButton("Verifica"); btVerifica.setToolTipText("Verifica valores digitados no jogo");
btCompleta =new JButton("Resolve"); btCompleta.setToolTipText("Resolve o jogo, preenche o quadro");
btSair=new JButton("Sair"); btSair.setToolTipText("Sair do programa");

mainPanel=new JPanel();
mainPanel.setSize(600,600);
mainPanel.setBounds(20,50,600,600);
mainPanel.setBorder(new LineBorder(Color.BLACK, 2, true));
mainPanel.setLayout(new GridLayout(3,3));

 carregaArquivo();
 if(flag.equals("vazio")) return;
 
 NumberText[] textField = new NumberText[9];
 
 // Preenche a matriz
 int r=0, c=0, endCol=2, endRow=2;
 qtdBuracos=0;
 for (int i = 0; i < 3; i++)
 {
     for (int j = 0; j < 3; j++)
      {
		        //System.out.println("setor linha="+i+" setor coluna="+j);
                sudokuSector[i][j] = new JPanel();
          // Pinta os painéis de forma alternada para criar um efeito de xadrez
                if ((i + j) % 2 == 0) {
                    sudokuSector[i][j].setBackground(Color.DARK_GRAY);
                } else {
                    sudokuSector[i][j].setBackground(Color.LIGHT_GRAY);
                }
                sudokuSector[i][j].setBorder(new LineBorder(Color.BLACK, 2, true));
                //System.out.println("getlinha="+r+" endrow="+endRow+" coluna="+c+" endcol="+endCol);
              
                List<Space> buracos = getSpacesFromSector(c, endCol, r, endRow);
									
				JPanel setor = new JPanel();
				setor.setBorder(new LineBorder(Color.BLACK, 3, true));
				setor.setSize(new Dimension(200,200));
                setor.setLayout(new GridLayout(3,3));	
                		
				for(int x=0;x<buracos.size();x++)
				{  
					//System.out.println("buracos="+buracos.get(x).getAtual());
				    if(buracos.get(x).getAtual()==0)qtdBuracos++;
				    
				    textField[x]=new NumberText(buracos.get(x));
							   
					   //textField[x].setSize(new Dimension(150,150));
					 // System.out.println("buracos="+buracos.get(x).getAtual());
					 setor.add(textField[x]);
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
                
              
 // Adiciona uma borda para separar visualmente os painéis
  //sudokuSector[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));

		add(btArquivo);
		add(btReiniciar);
		add(btVerifica);
		add(btCompleta);
		add(btSair);
		add(mainPanel);
		
		lbQtdBuracos=new JLabel("Espacos Vazios:");
		lbQtdBuracos.setBounds(20,660,100,20);
		add(lbQtdBuracos);
		
		txtQtdBuracos=new JTextField(""+qtdBuracos);
		txtQtdBuracos.setBounds(20+110,660,30,20);
		txtQtdBuracos.setEnabled(false);
		add(txtQtdBuracos);
		
		
		lbQtdErros=new JLabel("Qtd.Vidas:");
		lbQtdErros.setToolTipText("Quantidade maxima de "+qtdMaxima+" Erros");
		lbQtdErros.setBounds(130+110,660,100,20);
		add(lbQtdErros);
		
		txtQtdErros=new JTextField(""+(qtdMaxima-qtdErros));
		txtQtdErros.setToolTipText("Quantidade maxima de "+qtdMaxima+" Erros");
		txtQtdErros.setBounds(130+220,660,30,20);
		txtQtdErros.setEnabled(false);
		add(txtQtdErros);
		

		btArquivo.setBounds(20,15,100,20); btArquivo.addActionListener(this);
		btReiniciar.setBounds(20+110,15,100,20); btReiniciar.addActionListener(this);
		btVerifica.setBounds(130+110,15,100,20); btVerifica.addActionListener(this);
		btCompleta.setBounds(130+220,15,100,20); btCompleta.addActionListener(this);
		btSair.setBounds(130+330,15,100,20); btSair.addActionListener(this);

      setLocation(200,200); setSize(650,750); setVisible(true);
       
      setResizable(false);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      revalidate();
      repaint();
    
}////////////construtor

public static List<Space> getSpacesFromSector(final int initCol, final int endCol,final int initRow, final int endRow)
    {
        List<Space> spaceSector = new ArrayList<>();
        
        for (int r = initRow; r <= endRow; r++)
        {
            for (int c = initCol; c <= endCol; c++)
            {   
				//System.out.println("   valor="+novoquadro[r][c].getAtual()+"fixo="+novoquadro[r][c].isFixo());

                spaceSector.add(novoquadro[r][c]);
            }
        }
        return spaceSector;
    }

private void carregaArquivo()
{
	JFileChooser chooser = new JFileChooser("c:\\javaDio\\sudokuwindows");
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos com extensao TXT","txt");
    chooser.setFileFilter(filter);
    chooser.setDialogTitle("ESCOLHA o Arquivo Texto com os valores do quadro");
    chooser.setMultiSelectionEnabled(false);
    
    int returnVal = chooser.showOpenDialog(null);
    
    if(returnVal == JFileChooser.APPROVE_OPTION)
    {
      File arquivo = chooser.getSelectedFile();
      //JOptionPane.showMessageDialog(null,"o arquivo: "+ arquivo.getName());
      String caminho = arquivo.getPath();
      String nomefoto= arquivo.getName();
      
       arqTxt arquivoTxt=new arqTxt();
       arquivoTxt.lerTexto(caminho);
       pegaTudo=arquivoTxt.pegaLista();
       int linha=0;
       int coluna=0;
       int valorx=0;
       boolean fixo=false;
       
       if(pegaTudo!=null)
       for(int j=0; j<pegaTudo.size(); j++)
       {
			col =""+pegaTudo.get(j); j++;
			row =""+pegaTudo.get(j); j++;
			value=""+pegaTudo.get(j); j++;
			fixed=""+pegaTudo.get(j);
			
		    linha=Integer.parseInt(row);
		    coluna=Integer.parseInt(col);
		    valorx=Integer.parseInt(value);
		    fixo=Boolean.parseBoolean(fixed);	
			
			novoquadro[linha][coluna]=new Space(valorx,fixo, linha, coluna);
			
            
        }//for
        flag="Criado"; 
   }else
	{   
		JOptionPane.showMessageDialog(null,"Você não selecionou nenhum arquivo.");
		dispose();
		return;
	}
	
}/////carrega arquivo na matriz 9x9

///////////////////////////////////////////////////////////////	
	
public static void alteraTextoDoPainel(JPanel painel)
{
		// Percorre todos os componentes dentro do JPanel
		for (Component comp1 : painel.getComponents()) {
			// Verifica se o componente é um JTextField
			if (comp1 instanceof JPanel)
			{
				//System.out.println("pegou um painel");
				JPanel quadro = (JPanel) comp1;
				for (Component comp2 : quadro.getComponents())
				{
					if (comp2 instanceof NumberText)
					{
						NumberText campo = (NumberText) comp2;
						
						if(campo.getText().trim().isEmpty()) qtdBuracos++;
						
						if(campo.getIsfixo())
						{
							//System.out.println("-------numero fixo="+campo.getText());
						}
						else
						{
							//System.out.println("pega um valor="+campo.getText());
							String sv="0"+campo.getText();
							//System.out.println("string valor="+sv);
							int v=Integer.parseInt(sv);
							//System.out.println("numero valor="+v);
							if(v!=0)
							{
							   //System.out.println(".....valor zero");
							   campo.setText(" ");
							   campo.setBackground(Color.WHITE);
							   qtdBuracos++;
							}
						}	
						//System.out.println("pega um valor="+campo.getText());
						
					}
				}//for comp2
			}
		}//for comp1
		painel.revalidate();
        painel.repaint();
		return;
}
///////////////////////////////////////////////////////////////////////////////////
public static void verificaTextoDoPainel(JPanel painel, List<Space> vcerto)
{
	   int xp=0, px=0;
	  // Percorre todos os componentes dentro do JPanel
		for (Component comp1 : painel.getComponents())
		{
		  if (comp1 instanceof JPanel)
		  {
			JPanel quadro = (JPanel) comp1;
			for (Component comp2 : quadro.getComponents())
			{
			   // Verifica se o componente é um JTextField
		       if (comp2 instanceof NumberText)
			   {
				NumberText campo = (NumberText) comp2;
				String sv="0"+campo.getText();
						//System.out.println("string valor="+sv);
				int v=Integer.parseInt(sv);
						//System.out.println("numero valor="+v);
				px=vcerto.get(xp).getEsperado(); xp=xp+1;
				
				if(!campo.getIsfixo())
				if(!campo.getText().trim().isEmpty())
				{		
				  //System.out.println("valor digitado="+campo.getText());
				  //System.out.println("valor certo="+v);
				  if(v!=px)
				   {
						campo.setForeground(Color.RED);
						qtdErros=qtdErros+1;
					}
				  else
				  {
					 campo.setForeground(Color.GREEN);
				  }
				}// if empty  	
			   }//if comp2 = NumberText	
						
		     }//for comp2
		  }//if comp1 = panel   
		}//for comp1     
		
		return;
}

///////////////////////////////////////////////////////////////////////////////////
public static void completaTextoDoPainel(JPanel painel, List<Space> vcerto)
{
	   int xp=0, px=0;
		// Percorre todos os componentes dentro do JPanel
		for (Component comp1 : painel.getComponents())
		{
		  if (comp1 instanceof JPanel)
		  {
			JPanel quadro = (JPanel) comp1;
			for (Component comp2 : quadro.getComponents())
			{
			   // Verifica se o componente é um JTextField
		       if (comp2 instanceof NumberText)
			   {
				NumberText campo = (NumberText) comp2;
						
				//System.out.println("pega um valor="+campo.getText());
				String sv="0"+campo.getText();
						//System.out.println("string valor="+sv);
				int v=Integer.parseInt(sv);
						//System.out.println("numero valor="+v);
				px=vcerto.get(xp++).getEsperado();
				if(v!=px)
						    {
							   campo.setText(px+"");
							}
			   }//if comp2 = NumberText	
						
		     }//for comp2
		  }//if comp1 = panel   
		}//for comp1     
		
		return;
}

///////METODO actionlistener
public void actionPerformed(ActionEvent ae)
{
    if(ae.getSource()==btSair){ dispose();}
    
    if(ae.getSource()==btCompleta)
    { 
		//System.out.println("Usuario clicou em Verifica");
		int r=0, c=0, endCol=2, endRow=2;
		for (int i = 0; i < 3; i++)
		 {
				for (int j = 0; j < 3; j++)
				{
				  List<Space> buracos = getSpacesFromSector(c, endCol, r, endRow);
				  //System.out.println("setor linha="+i+" setor coluna="+j);
				  completaTextoDoPainel(sudokuSector[i][j],buracos);
				  c=c+3;
				  if(c==9)c=0;
				  endCol=c+2;		
				}//for j
			    r=r+3;
                endRow = r + 2;	
		  }//for  i
	}//if verifica 
    
 
    if(ae.getSource()==btVerifica)
    { 
		qtdErros=0;
		int r=0, c=0, endCol=2, endRow=2;
		for (int i = 0; i < 3; i++)
		 {
				for (int j = 0; j < 3; j++)
				{
				  List<Space> buracos = getSpacesFromSector(c, endCol, r, endRow);
				  //System.out.println("setor linha="+i+" setor coluna="+j);
				  verificaTextoDoPainel(sudokuSector[i][j],buracos);
				  c=c+3;
				  if(c==9)c=0;
				  endCol=c+2;		
				}//for j
			    r=r+3;
                endRow = r + 2;	
		  }//for  i
		  
		  JOptionPane.showMessageDialog(null,"Qtde de erros no quadro="+qtdErros);
	}//if verifica 

	if(ae.getSource()==btReiniciar)
	{
	     int resposta = JOptionPane.showConfirmDialog(
                null, 
                "Deseja realmente reiniciar o jogo?", 
                "Limpar o jogo", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION)
        {
           reIniciar();
			   			   
            // Lógica para confirmar a ação
        } else if (resposta == JOptionPane.NO_OPTION) {
            System.out.println("Usuario clicou em Nao!");
            return;
        } else {
            System.out.println("Usuario fechou a janela ou cancelou!");
            return;
        }
	 	qtdErros=0;
		txtQtdErros.setText(""+qtdErros);
		txtQtdBuracos.setText(""+qtdBuracos);
		fim=false;
	}//if reiniciar

	if(ae.getSource()==btArquivo)
	{
		JFileChooser chooser = new JFileChooser("c:\\javaDio\\sudokuwindows");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos com extensao TXT","txt");
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("ESCOLHA o Arquivo Texto com os valores do quadro");
		chooser.setMultiSelectionEnabled(false);
		
		int returnVal = chooser.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
		   carregaArquivo();
	  
		}	//if 
	}
/////////////////////////////////////////

}////acao dos botoes
public static void reIniciar()
{
     System.out.println("Usuario clicou em Sim!");
  			  // Percorre a matriz de painel
  			 qtdBuracos=0;
  			 txtQtdBuracos.setText(""+qtdBuracos);
  			 System.out.println("antes qtd Buracos="+qtdBuracos);
			 for (int i = 0; i < 3; i++)
			 {
				 for (int j = 0; j < 3; j++)
				  {
						//System.out.println("setor linha="+i+" setor coluna="+j);
						alteraTextoDoPainel(sudokuSector[i][j]);
							
				  }//for j
			  }//for  i   
		System.out.println("depois qtd Buracos="+qtdBuracos);
		qtdErros=0;
		txtQtdErros.setText(""+qtdErros);
		txtQtdBuracos.setText(""+qtdBuracos);
		fim=false;	
}

public static void main(String t[])
   {
        new Jogo();
   }


}/////classss
