public class Card
{
    private int Value;
    private char Suite; 

    public Card(int val, int suite)
    {
        this.Value = val;
        this.Suite = suiteAssigner(suite);
    }

    // Assigns Suite ASCII character
    private char suiteAssigner(int s)
    {
        switch(s)
        {
            case 1: return '♠';
            case 2: return '♥';
            case 3: return '♣';
            case 4: return '♦';
            default: return ' ';        
        }
    }

    // Assigns Special characters to card
    private char faceCardAssigner()
    {
        switch(Value)
        {
            case 1: return 'A';
            case 11: return 'J';
            case 12: return 'Q';
            case 13: return 'K';
            default: return 'X';        
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

    // For all other cards
    private String cardBuilder()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("┌─────────┐");
        sb.append("\n│" + Value + "        │");
        sb.append("\n│         │");
        sb.append("\n│         │");
        sb.append("\n│    "+ Suite + "    │");
        sb.append("\n│         │");
        sb.append("\n│         │");
        sb.append("\n│        " +  Value + "│"); 
        sb.append("\n└─────────┘"); 

        return sb.toString();
    }


    // Face Cards and Special Cards
    private String cardBuilder(char face)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("┌─────────┐");
        sb.append("\n│" + face + "        │");
        sb.append("\n│         │");
        sb.append("\n│         │");
        sb.append("\n│    "+ Suite + "    │");
        sb.append("\n│         │");
        sb.append("\n│         │");
        sb.append("\n│        " +  face + "│"); 
        sb.append("\n└─────────┘"); 

        return sb.toString();
    }

    // For 10 card
    private String cardBuilder(int num)
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("┌─────────┐");
        sb.append("\n│" + num + "       │");
        sb.append("\n│         │");
        sb.append("\n│         │");
        sb.append("\n│    "+ Suite + "    │");
        sb.append("\n│         │");
        sb.append("\n│         │");
        sb.append("\n│       " +  num + "│"); 
        sb.append("\n└─────────┘"); 

        return sb.toString();
    }

    public String [] cardCreate()
    {
        // Pieces of the Card used for line by line display
        String [] card = new String [9];

        // Top Portion
        card[0] = "┌─────────┐";
        
        // Special Formatting for 10 
        if (Value == 10) 
        {
            card[1] = "│" + 10 + "       │";
        }
        // Special Formatting for Face Cards and Ace
        else if (Value > 10 || Value == 1)
        {
            card[1] = "│" + faceCardAssigner() + "        │";
        }
        // All other Cards
        else
        {
            card[1] = "│" + Value + "        │";
        }

        // Middle Portion of the Card
        card[2] = "│         │";
        card[3] = "│         │";
        card[4] = "│    "+ Suite + "    │";
        card[5] = "│         │";
        card[6] = "│         │";
    
        // Bottom Portion

        // Special Formatting for 10 
        if (Value == 10) 
        {
            card[7] = "│       " +  10 + "│";
        }
        // Special Formatting for Face Cards and Ace
        else if (Value > 10 || Value == 1)
        {
            card[7] = "│        " +  faceCardAssigner() + "│";
        }
        // All other Cards
        else
        {
            card[7] = "│        " +  Value + "│";
        }

        card[8] = "└─────────┘";

        return card;
    }


    public String toString()
    {
        // Face Cards or Special Cards
        if (Value == 1 || Value == 11 || Value == 12 || Value == 13)
        {
            return cardBuilder(faceCardAssigner());
        }
        // Formatting Issues with 10
        else if (Value == 10)
        {
            return cardBuilder(10);
        }
        
        // All Other Cards
        return cardBuilder();
    }

}
