import java.util.ArrayList;

public class Player 
{
    // Card Hand
    private ArrayList<Card> hand;
    
    public Player()
    {
        hand = new ArrayList<Card>();
    }

    // Adds Card to hand
    public void addCardtoHand(Card c)
    {
        hand.add(c);
    }

    // Calculates two potential Sums in case of Ace
    public void displayScore()
    {
        int [] scores = handValue();
        
        // Ony two values will ever be returned 
        if (scores[0] == scores[1])
        {
            // If the values are equal
            System.out.println("\t\tCurrent Value: " + scores[0]);
        }
        else
        {
            // If the values are different
            System.out.println("\t\tCurrent Values: " + scores[0] + " or " + scores[1]);
        }
    }

    public boolean hasBust()
    {
        // Check values
        int [] scores = handValue();

        // Need come back and fix this to consider edge cases
        if (scores[0] > 21 || scores[1] > 21 )
        {
            return true;
        }

        return false;   
    }


    public void displayHand()
    {
        // Array of Arrays
        String [] [] cards = new String[hand.size()] [];

        // Populate Array
        for(int i = 0; i < hand.size(); i++)
        {
            cards[i] = hand.get(i).cardCreate();
        }

        StringBuilder sb = new StringBuilder();

        // Displays the Cards line by line
        for(int cardRow = 0; cardRow < hand.get(0).cardCreate().length; cardRow++)
        {
            for (int card = 0; card < hand.size(); card++)
            {
                sb.append(" " + cards[card][cardRow]);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    public boolean hasBlackjack()
    {
        // Checks that the first two cards are an ace and a "ten" card
        return (hand.get(0).getValue() >= 10 || hand.get(1).getValue() >= 10) 
                && (hand.get(0).getValue() == 1 || hand.get(1).getValue() == 1);
    }

    private int [] handValue()
    {
        // Calculates two sums in case of an ace
        int val1 = 0; 
        int val2 = 0;

        // Sums up value of hand
        for (Card c: hand)
        {
            // Handles Ace 
            switch(c.getValue())
            {
                // Ace
                case 1:  val2 += 11; 
                         val1 += 1;
                         break;
                // Jack
                case 11: val2 += 10; 
                         val1 += 10;
                         break;
                // Queen
                case 12: val2 += 10; 
                         val1 += 10;
                         break;
                // King
                case 13: val2 += 10; 
                         val1 += 10;
                         break;
                // All other Cards
                default: val1 += c.getValue();
                         val2 += c.getValue();
                         break;
            }
        }

        // Return both possible hand values if there is an ace
        return new int [] {val1, val2};
    }
}
