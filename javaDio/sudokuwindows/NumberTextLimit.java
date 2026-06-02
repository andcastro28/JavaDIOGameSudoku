
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class NumberTextLimit extends PlainDocument
{

//JDK9    private final List<String> NUMBERS = List.of(" ","1", "2", "3", "4", "5", "6", "7", "8", "9");
// Criando um Array
       private static ArrayList numbers;
               
    @Override
    public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException
     {
       numbers=new ArrayList<>();
	   numbers.clear();//limpa tudo
	   numbers.add("0"); 
       numbers.add("1"); 
       numbers.add("2"); 
       numbers.add("3"); 
       numbers.add("4"); 
       numbers.add("5"); 
       numbers.add("6"); 
       numbers.add("7"); 
       numbers.add("8"); 
       numbers.add("9");  
        
      if(str == null || str.isEmpty() || (!numbers.contains(str))) return;

        if (getLength() + str.length() <= 1){
            super.insertString(offs, str, a);
        }
    }
    
}
