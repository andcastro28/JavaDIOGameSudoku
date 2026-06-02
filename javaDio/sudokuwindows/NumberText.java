
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.*;
import static java.awt.Font.PLAIN;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
                if (getText().isEmpty()){
                    space.clearSpace();
                    return;
                }
                space.setAtual(Integer.parseInt(getText()));
            }

        });//documentlistener
    }
    
    @Override
    public void focusGained(FocusEvent e) {
                // Highlight text automatically for easier overwriting
                this.selectAll(); 
                this.setBackground(Color.LIGHT_GRAY);
     }

     @Override
     public void focusLost(FocusEvent e) {
                // Reset background and trim text on exit
                //this.setBackground(Color.WHITE);
                if (this.getText().trim().isEmpty())
                {
                    this.setBackground(Color.RED);;
                }
                else
                {
					Jogo.qtdBuracos=Jogo.qtdBuracos-1;
					Jogo.txtQtdBuracos.setText(""+Jogo.qtdBuracos);
				}
            }

    //@Override
    //public void update(final EventEnum eventType) {
    //    if (eventType.equals(CLEAR_SPACE) && (this.isEnabled())){
    //        this.setText("");
    //    }
    //}
}
