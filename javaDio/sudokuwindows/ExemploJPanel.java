import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ExemploJPanel
{
    public static void main(String[] args) {
        // 1. Cria a janela principal
        JFrame frame = new JFrame("Minha Janela com JPanel");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // 2. Cria o JPanel
        JPanel painel = new JPanel();
        painel.setSize(170,170);
        painel.setBorder(new LineBorder(Color.BLACK, 2, true));
        
        // 3. Cria um componente e o adiciona ao JPanel
        JButton botao = new JButton("Clique Aqui");
        painel.add(botao);
        frame.add(painel);
        
        
        
        // 4. Adiciona o JPanel à janela principal
        frame.add(painel);
        
       
        // 4. Adiciona o JPanel à janela principal
        frame.add(painel);
        
        
        

        // 5. Torna a janela visível
        frame.setVisible(true);
    }
}
