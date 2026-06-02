import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.Dimension;

import java.awt.Component;

public class MatrizPainelExemplo
{
    public static void main(String[] args)
    {
        // Configurações da matriz
        int linhas = 3;
        int colunas = 3;

        JFrame frame = new JFrame("Matriz de JPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Define o layout do JFrame como GridLayout para a matriz
        frame.setLayout(new GridLayout(linhas, colunas));

        // Cria a matriz de JPanel
        JPanel[][] matrizPaineis = new JPanel[linhas][colunas];
     
        
        NumberText[] textField = new NumberText[9];
        
        Space espaco;
        // Preenche a matriz
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matrizPaineis[i][j] = new JPanel();
                
                // Pinta os painéis de forma alternada para criar um efeito de xadrez
                if ((i + j) % 2 == 0) {
                    matrizPaineis[i][j].setBackground(Color.DARK_GRAY);
                } else {
                    matrizPaineis[i][j].setBackground(Color.LIGHT_GRAY);
                }
                
                // Adiciona uma borda para separar visualmente os painéis
                matrizPaineis[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                JPanel setor = new JPanel();
                   setor.revalidate();
                   setor.repaint();
                setor.setLayout(new GridLayout(3,3));
                
                for(int x=1;x<10;x++)
                {  
				   espaco=new Space(x,true);	
				   textField[x-1]=new NumberText(espaco);
				   textField[x-1].setColumns(4);
                   setor.add(textField[x-1]);
                }
                matrizPaineis[i][j].add(setor);
                
                // Adiciona o JPanel diretamente no JFrame
                frame.add(matrizPaineis[i][j]);
            }
        }

        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);
         
        
        JOptionPane.showMessageDialog(null,"Alterando valores");
          for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                altera2TextoDoPainel(matrizPaineis[i][j]);
             }
           }
           
        frame.revalidate();
        frame.repaint();   
       
    }//main
    
public static void altera2TextoDoPainel(JPanel painel)
{
		// Percorre todos os componentes dentro do JPanel
		for (Component comp1 : painel.getComponents()) {
			// Verifica se o componente é um JTextField
			if (comp1 instanceof JPanel)
			{
				System.out.println(".............pegou um painel");
				JPanel quadro = (JPanel) comp1;
				for (Component comp2 : quadro.getComponents())
				{
					if (comp2 instanceof NumberText)
					{
						NumberText campo = (NumberText) comp2;
						System.out.println("pega um valor="+campo.getText());
						String sv="0"+campo.getText();
						System.out.println("string valor="+sv);
						int v=Integer.parseInt(sv);
						System.out.println("numero valor="+v);
						if(v==1)
						{
					       System.out.println(".....valor um trocado");
						   campo.setText("9");
						}
						//System.out.println("pega um valor="+campo.getText());
						//break; // Para no primeiro JTextField encontrado
					}
				}//for comp2
			}
		}//for comp1
		
		return;
}


	public static void altera1TextoDoPainel(JPanel painel) {
				
		// Percorre todos os componentes dentro do JPanel
		for (Component comp : painel.getComponents()) {
			// Verifica se o componente é um JTextField
			if (comp instanceof JTextField)
			{
				JTextField campo = (JTextField) comp;
				campo.setText("0");
				//System.out.println("pega um valor="+valor);
				//break; // Para no primeiro JTextField encontrado
			}
		}
		return;
	}

}
