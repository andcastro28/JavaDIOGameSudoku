import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Formatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class arqTxt
{
	
	private static ArrayList Lista;
	
    public static void gravaTexto(String nomeArquivo){
	try {
		Formatter arquivo = new Formatter(nomeArquivo);
		for(int i=1; i<=5;i++)
		{
			  arquivo.format("%s,%s,%s,%s\n", i, "texto", 20, "73a");
		}//for
		arquivo.close();
		} catch (FileNotFoundException e) {
		System.out.println("Erro gravacao="+e);
		}
    }
	public static void lerTexto(String nomeArquivo)
	{
		Scanner tecla = new Scanner(System.in);
		try {
		File arquivo = new File(nomeArquivo);
		if(!arquivo.exists())
			{
			JOptionPane.showMessageDialog(null,"Arquivo "+nomeArquivo+" nao existe!!!!!");
			return;
			}
		Scanner sc = new Scanner(arquivo);
		//sc.useDelimiter("\\s*,\\s*");
		sc.useDelimiter("\\s*,\\s*|\\R");
		
		Lista=new ArrayList<>();
		Lista.clear();//limpa tudo
		while(sc.hasNext())
			{
				Lista.add(sc.next());
			}///while
			 sc.close();
		} catch (FileNotFoundException e) {
		       JOptionPane.showMessageDialog(null,"Erro arquivo texto leitura="+e);
		}
	}


   public ArrayList pegaLista()
   {
	   return Lista;
   }
   


}
