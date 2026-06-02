import java.awt.event.*;//action
import java.awt.*;//layout, cor
import javax.swing.*;//objetos
import javax.swing.event.*;//acao menu

class Menu extends JFrame
implements ActionListener, MenuListener, MouseListener
{
     private JMenuBar menuBar;
     private JMenu jogoMenu, sobreMenu, sairMenu;
     private JMenuItem jogoIniciar,jogoCriar, jogoSair;
     private JMenuItem sobreAJUDA;
     private JLabel lbFoto;
     
     private JMenuItem menuSair;
     
 public void menuCanceled(MenuEvent e){
  }
//Invoked when the menu is canceled.
  public void menuDeselected(MenuEvent e){
  }
//Invoked when the menu is deselected.
   public void menuSelected(MenuEvent menuevent)
   {
   	
   	if(menuevent.getSource()==sobreMenu)
    {
   	ImageIcon foto=new ImageIcon("logocurso.jpg");
   	Image nova=foto.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT);
   	JOptionPane.showMessageDialog(this,
   	   "<html><style>h1{color:red} h2{color:blue} h3{color:green}</style>"+
   	   "<h2>Jogo Sudoku</h2><body>"+
   	   "<br><h1>- Curso Dio EAD - 2026 -</h1><br>"+
       "<br><h1>Globant - Java & Spring Boot AI Developer</h1><br><h3> Aluno: Andre Luiz Ferraz Castro <br>"+
       "<br> Professor Jose Luiz Abreu Cardoso Junior - Desenvolvedor Backend Kotlin Senior, Caju Beneficios</h3> <br>"+
       "<br> Tudo EAD <br></body></html>",
   	   "Sobre direitos Autorais",-1,new ImageIcon(nova));
   }
   	
  } //menuselected    
     
     
////////acao do mouse//
public void mouseClicked(MouseEvent me)
{
if(me.getSource()==sairMenu)
{
   int re=JOptionPane.showConfirmDialog(null,
                "Confirma?", "Saindo do programa", 
                JOptionPane.YES_NO_OPTION);
   if(re==0)System.exit(0);
 }
 
 
}//mouse

public void mouseEntered(MouseEvent e){}
public void mouseExited(MouseEvent e){}
public void mousePressed(MouseEvent e){}
public void mouseReleased(MouseEvent e){}
           
///////////////////acao dos item de menu
     public void actionPerformed(ActionEvent ae)
     {  
		if(ae.getSource()==jogoSair)
        {   JOptionPane.showMessageDialog(this,"Saindo do jogo sudoku");
            System.exit(0);
        }
        
        if(ae.getSource()==jogoIniciar)
        {   JOptionPane.showMessageDialog(this,"Comecando o jogo sudoku, vc precisa escolher um quadro para jogar");
			new Jogo();
            return;
        }
        
        if(ae.getSource()==jogoCriar)
        {   JOptionPane.showMessageDialog(this,"Criando um novo quadro para o jogo sudoku");
			new CriaJogo();
            return;
        }
     }//////////////////

     public Menu()
     {
         super("Menu Jogo Sudoku");//construtor JFrame
         setLayout(null);
         setSize(450, 300);//larg, alt
         setLocation(200,200);
         
        
        // Cria uma barra de menu para o JFrame
        menuBar=new JMenuBar();
// Adiciona a barra de menu ao frame
        setJMenuBar(menuBar);
         
// Define e adiciona dois menus drop down na barra de menus
jogoMenu = new JMenu("Jogo");
jogoMenu.setToolTipText("Iniciar novo jogo");
jogoMenu.setMnemonic('J');

sobreMenu = new JMenu("Sobre");
sobreMenu.setMnemonic('S');
sobreMenu.setToolTipText("Sobre o Desenvolvimento do jogo");
sobreMenu.addMenuListener(this);//acao

sairMenu = new JMenu("Sair");
sairMenu.setMnemonic('r');
sairMenu.setToolTipText("Sair do programa");
sairMenu.addMouseListener(this);//acao


menuBar.add(jogoMenu);
menuBar.add(sobreMenu);
menuBar.add(sairMenu);

// Cria e adiciona um item simples para o menu
jogoIniciar=new JMenuItem("Iniciar novo Jogo");
jogoCriar=new JMenuItem("Criar Quadro no Jogo");
jogoSair=new JMenuItem("Sair");

jogoMenu.add(jogoIniciar);
jogoMenu.add(jogoCriar);
jogoMenu.addSeparator();//linha de separacao
jogoMenu.add(jogoSair);

jogoCriar.addActionListener(this);//funfa
jogoIniciar.addActionListener(this);//funfa
jogoSair.addActionListener(this);//funfa
////////////////////////////////////////////////////////

lbFoto=new JLabel(new ImageIcon("vazio.png"));
lbFoto.setBounds(100,10,200,200);

	ImageIcon fotoMenu=new ImageIcon("logosudokujava.jpg");
   	Image nova=fotoMenu.getImage().getScaledInstance(200,200,Image.SCALE_DEFAULT);
    lbFoto.setIcon(new ImageIcon(nova));
    add(lbFoto);

setDefaultCloseOperation(EXIT_ON_CLOSE);
setLocationRelativeTo(null);
//setVisible(true);
show();
}//construtor
   
   public static void main(String t[])
   {
        new Menu();
   }
   
}///class menu  
