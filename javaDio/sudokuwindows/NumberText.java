
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.*;
import static java.awt.Font.PLAIN;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.ArrayList;
import java.util.List;

public class NumberText extends JTextField implements FocusListener
{

    private final Space space;
    
    public void setNumber(String txt)
    {
		if (this.space.isFixo())
            this.setText(this.space.getAtual()+"");
        else
            this.setText("0");
	}

    public boolean getIsfixo()
    {
		return this.space.isFixo();
	}
	
	public int getNumber()
	{ 
		return this.space.getAtual();
	}
	
	public int getLine()
	{   return this.space.getLin();
	}
	
	public int getColumn()
	{   return this.space.getCol();
	}
	
    public NumberText(final Space space)
    {
        this.space = space;
        Dimension dimen = new Dimension(150, 150);
        this.setSize(dimen);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 40));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(!space.isFixo());
        
        this.addFocusListener(this);
         
        if (space.isFixo())
            this.setText(space.getAtual()+"");
        
        
        this.getDocument().addDocumentListener(new DocumentListener()
        {

            @Override
            public void insertUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                changeSpace();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                changeSpace();
            }

            private void changeSpace(){
                if(Jogo.fim==true)return;
                if (getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setAtual(Integer.parseInt(getText()));
            }

        });//documentlistener
    }
    
    @Override
    public void focusGained(FocusEvent e)
     {
                if(Jogo.fim==true) 
                {
					// JOptionPane.showMessageDialog(null,"Reinicie o jogo");
					Jogo.reIniciar();
					return;
                }
                
                if(this.getText().trim().isEmpty())
                {
                // Highlight text automatically for easier overwriting
                  this.selectAll(); 
                  //this.setBackground(Color.LIGHT_GRAY);
                }
                 else
                {
					  Jogo.qtdBuracos=Jogo.qtdBuracos+1;
				} 
     }

     @Override
     public void focusLost(FocusEvent e)
      {
		        this.setForeground(Color.BLACK);
                  if(Jogo.fim==true) return; 
                  
                // Reset background and trim text on exit
                //this.setBackground(Color.WHITE);
                  if (this.getText().trim().isEmpty())
                {
					//this.setBackground(Color.RED);
					return;
                }
                else
                {
					Jogo.qtdBuracos=Jogo.qtdBuracos-1;
					
				}
                 
                int ll=this.getLine();
                int cc=this.getColumn();
                int valorCerto=Jogo.novoquadro[ll][cc].getEsperado();
                int valorDig=this.getNumber();
                
                if(valorCerto==valorDig)
                	this.setForeground(Color.GREEN);
                else
                {
                    Jogo.qtdErros++;
                    Jogo.txtQtdErros.setText(""+(Jogo.qtdMaxima-Jogo.qtdErros));
                    this.setForeground(Color.RED);
                    if(Jogo.qtdErros==Jogo.qtdMaxima && Jogo.fim==false)
                    {
                     JOptionPane.showMessageDialog(null,"Infelizmente vc Errou "+Jogo.qtdMaxima+" vezes!! vc Deve reiniciar o jogo :(");
                     Jogo.fim=true;
                     return;
				    }
                }
                
              
				Jogo.txtQtdBuracos.setText(""+Jogo.qtdBuracos);
				if(Jogo.qtdBuracos==0)
				   {
					   	Jogo.qtdErros=0;
						int r=0, c=0, endCol=2, endRow=2;
						for (int i = 0; i < 3; i++)
						 {
								for (int j = 0; j < 3; j++)
								{
								  List<Space> buracos = Jogo.getSpacesFromSector(c, endCol, r, endRow);
								  //System.out.println("setor linha="+i+" setor coluna="+j);
								  Jogo.verificaTextoDoPainel(Jogo.sudokuSector[i][j],buracos);
								  c=c+3;
								  if(c==9)c=0;
								  endCol=c+2;		
								}//for j
								r=r+3;
								endRow = r + 2;	
						  }//for  i
						  if(Jogo.qtdErros!=0)
						       JOptionPane.showMessageDialog(null,"Qtde de erros no quadro="+Jogo.qtdErros);
						  else
						     {
						       JOptionPane.showMessageDialog(null,"Parabens vc finalizou o jogo :)");
						       Jogo.fim=true;
						       return;
					         }
									   
				   }
				   
            }

 }
