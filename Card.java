public class Card
{
    private int Value;
    private char Suite; 

    public Card(int val, int suite)
    {
        this.Value = val;
        this.Suite = suiteAssigner(suite);
    }

    private char suiteAssigner(int s)
    {
        switch(s)
        {
            case 0: return '♠';
            case 1: return '♥';
            case 2: return '♣';
            case 3: return '♦';
            default: return ' ';        
        }
    }

    public int getValue()
    {
        return this.Value;
    }

    public char getSuite()
    {
        return this.Suite;
    }

    public String toString()
    {
        String [] pieces ={ "┌─────────┐", 
                            "│" + Value + "........│" , 
                            "│.........│" , 
                            "│...."+ Suite + "....│", 
                            "│.........│", 
                            "│.........│", 
                            "│.........│", 
                            "│........" +  Value + "│", 
                            "└─────────┘" }; 
        
        for (String x: pieces)
        {
            System.out.println(x);
        }

        return "";
    }

    // Potentially Draw out the Cards in the terminal 
    // TODO
}
