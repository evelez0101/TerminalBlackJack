import java.util.ArrayList;

public class Player {
    // Card Hand
    private ArrayList<Card> hand;

    // Insurance
    private boolean hasInsuarnce;

    // Bankroll
    private int Bankroll;

    private int bet;

    // Hides Cards for dealers
    private boolean hiddenCard;

    public Player() 
    {
        hand = new ArrayList<Card>();
        Bankroll = 0;
        bet = 0;
        hasInsuarnce = false;
    }

    public Player(boolean hide) {
        hand = new ArrayList<Card>();
        hiddenCard = hide;
    }

    // Adds Card to hand
    public void addCardtoHand(Card c) {
        hand.add(c);
    }

    public boolean hasInsuarnce()
    {
        return this.hasInsuarnce;
    }

    public void setInsureBet(boolean b)
    {
        this.hasInsuarnce = b; 
    }
    
    public int getBankroll()
    {
        return this.Bankroll;
    }

    public int getBet()
    {
        return this.bet;
    }

    public void setBet(int amount)
    {
        this.bet = amount;
    }

    public int withdrawl(int amount)
    {
        this.Bankroll -= amount;
        return amount;
    }

    public void deposit(int amount)
    {
        this.Bankroll += amount;
    }

    public boolean canBet(int amount)
    {
        return ( (this.Bankroll - amount) >= 0);
    }

    public void clearHand() {
        // Clears player hand
        hand.clear();
    }

    // Calculates two potential Sums in case of Ace
    public void displayScore() {
        int[] scores = handValue();

        // Ony two values will ever be returned
        if ((scores[0] == scores[1]) && (scores[0] <= 21 && scores[1] <= 21)) 
        {
            // If the values are equal
            System.out.println("\t\tCurrent Value: " + scores[0]);
        } 
        else if (scores[0] <= 21 && scores[1] <= 21) 
        {
            // If the values are different
            System.out.println("\t\tCurrent Values: " + scores[0] + " or " + scores[1]);
        }
        // Showes lower of two scores
        else if (scores[0] > 21 && scores[1] <= 21) 
        {
            System.out.println("\t\tCurrent Value: " + scores[1]);
        } 
        else if (scores[1] > 21 && scores[0] <= 21) 
        {
            System.out.println("\t\tCurrent Values " + scores[0]);
        }
        // If both scores are over 21 then show the player has gone bust    
        else {
            System.out.println("\t\tBust");
        }
    }

    public boolean hasBust() {
        // Check values
        int[] scores = handValue();

        // Need come back and fix this to consider edge cases
        return (scores[0] > 21 && scores[1] > 21);
    }

    public void displayHand() {
        // Array of Arrays
        String[][] cards = new String[hand.size()][];

        // Populate Array
        for (int i = 0; i < hand.size(); i++) {
            // Hides Card for dealer when needed
            if (i == 1 && hiddenCard) {
                cards[i] = hand.get(i).hiddenCard();
                continue;
            }

            cards[i] = hand.get(i).cardCreate();
        }

        StringBuilder sb = new StringBuilder();

        // Displays the Cards line by line
        for (int cardRow = 0; cardRow < hand.get(0).cardCreate().length; cardRow++) {
            for (int card = 0; card < hand.size(); card++) {
                sb.append(" " + cards[card][cardRow]);
            }
            sb.append("\n");
        }

        System.out.println(sb.toString());
    }

    public void setHideCard(boolean hidden) {
        this.hiddenCard = hidden;
    }

    public boolean hasBlackjack() {
        // Checks that the first two cards are an ace and a "ten" card
        return (hand.get(0).getValue() >= 10 || hand.get(1).getValue() >= 10)
                && (hand.get(0).getValue() == 1 || hand.get(1).getValue() == 1);
    }

    public int[] handValue() {
        // Calculates two sums in case of an ace
        int val1 = 0;
        int val2 = 0;

        int index = 0;

        // Sums up value of hand
        for (Card c : hand) {
            // Handles Hiding Score for Dealer
            if (index == 1 && hiddenCard) {
                continue;
            }

            // Handles Ace
            switch (c.getValue()) {
                // Ace
                case 1:
                    val2 += 11;
                    val1 += 1;
                    break;
                // Jack
                case 11:
                    val2 += 10;
                    val1 += 10;
                    break;
                // Queen
                case 12:
                    val2 += 10;
                    val1 += 10;
                    break;
                // King
                case 13:
                    val2 += 10;
                    val1 += 10;
                    break;
                // All other Cards
                default:
                    val1 += c.getValue();
                    val2 += c.getValue();
                    break;
            }

            index++;
        }

        // Return both possible hand values if there is an ace
        return new int[] { val1, val2 };
    }
}
