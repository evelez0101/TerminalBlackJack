import java.util.ArrayList;

public class Player 
{
    // Card Hand
    private int buyIn;
    private ArrayList<Card> hand;
    
    public Player()
    {
        hand = new ArrayList<Card>();
    }

    public void addCardtoHand(Card c)
    {
        hand.add(c);
    }

    public void displayHand()
    {
        for( Card c: hand)
        {
            System.out.print(c);
        }
    }

    public int [] handValue()
    {
        // Calculates two sums in case of an ace
        int val1 = 0; 
        int val2 = 0;

        // Sums up value of hand
        for (Card c: hand)
        {
            // Handles Ace 
            if (c.getValue() == 1)
            {
                val2 += 11;
                val1 += 1;
                continue;
            } 

            val1 += c.getValue();
            val2 += c.getValue();
        }

        // Return both possible hand values if there is an ace
        return new int [] {val1, val2};
    }
}
