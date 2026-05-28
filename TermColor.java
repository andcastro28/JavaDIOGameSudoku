public class TermColor
{
    // Códigos de Cores ANSI
   
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_VERMELHO = "\u001B[31m";
    public static final String ANSI_VERDE = "\u001B[32m";
    public static final String ANSI_AMARELO = "\u001B[33m";
    public static final String ANSI_AZUL = "\u001B[34m";
    
   public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
public static final String ANSI_WHITE = "\u001B[37m";
    
    
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void main(String[] args)
    {
		/*
        System.out.println(ANSI_VERMELHO + "Este texto é vermelho!" + ANSI_RESET);
        System.out.println(ANSI_VERDE + "Este texto é verde!" + ANSI_RESET);
        System.out.println(ANSI_AMARELO + "Este texto é amarelo!" + ANSI_RESET);
        System.out.println(ANSI_AZUL + "Este texto é azul!" + ANSI_RESET);
        * */
        if (System.console() == null) System.setProperty("jansi.passthrough", "true");

        
         // Declaring ANSI_RESET so that we can reset the color
     System.out.println(ANSI_YELLOW
                           + "This text is yellow"
                           + ANSI_RESET);
                           
                           
    System.out.println(ANSI_GREEN_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
	System.out.println(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
	System.out.println(ANSI_GREEN_BACKGROUND + ANSI_RED + "This text has a green background and red text!" + ANSI_RESET);

    // Declaring the color
    // Custom declaration
   

    // Main driver method
  
    }
}
