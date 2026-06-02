
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.PLAIN;

public class Number2Text extends JTextField //implements EventListener
{

    private final Space2 space;
        
    public void setNumber(String txt)
    {
		if (this.space.getFixo())
            this.setText(this.space.getEsperado()+"");
        else
            this.setText("0");
	}

    public boolean getIsfixo()
    {
		return this.space.getFixo();
	}
	
	public int getNumber()
	{ 
		return this.space.getEsperado();
	}
	
    public Number2Text(final Space2 space)
    {
        this.space = space;
        Dimension dimen = new Dimension(150, 150);
        this.setSize(dimen);
        this.setVisible(true);
        this.setFont(new Font("Arial", PLAIN, 40));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new NumberTextLimit());
        this.setEnabled(space.getFixo());
        
        
        if (space.getFixo())
            this.setText(space.getEsperado()+"");
        
        
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

    
}
