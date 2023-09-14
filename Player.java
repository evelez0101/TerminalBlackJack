import java.util.ArrayList;

public class Player 
{
    // Card Hand
    private ArrayList<Card> hand;

    // Insurance
    private boolean hasInsuarnce;

    // Bankroll
    private int Bankroll;

    // Current Bet for a round
    private int bet;

    // Hides Cards for dealers
    private boolean hiddenCard;

    // Constructor for player 
    public Player() 
    {
        hand = new ArrayList<Card>();
        Bankroll = 0;
        bet = 0;
        hasInsuarnce = false;
    }

    // Consturction for Dealer
    public Player(boolean hide) {
        hand = new ArrayList<Card>();
        hiddenCard = hide;
    }

    // Adds Card to hand
    public void addCardtoHand(Card c) {
        hand.add(c);
    }

    // Returns whether or not player has insurance during current round 
    public boolean hasInsuarnce()
    {
        return this.hasInsuarnce;
    }

    // Allows change of insurance status accordingly
    public void setInsureBet(boolean b)
    {
        this.hasInsuarnce = b; 
    }

    // Returns current value of bankroll
    public int getBankroll()
    {
        return this.Bankroll;
    }

    // Returns current value of bet 
    public int getBet()
    {
        return this.bet;
    }

    // Allows change of the current bet accordinly
    public void setBet(int amount)
    {
        this.bet = amount;
    }

    // Withdrawls an amount from the current bankroll
    public void withdrawl(int amount)
    {
        this.Bankroll -= amount;
    }

    // Adds to the current bankroll
    public void deposit(int amount)
    {
        this.Bankroll += amount;
    }

    // Ensures the bet is valid meaning the player will not go below $0
    public boolean canBet(int amount)
    {
        return ( (this.Bankroll - amount) >= 0);
    }

    // Clears player hand for a new round
    public void clearHand() 
    {
        hand.clear();
    }

    // Displays to the screen the current value of the hand
    public void displayScore() 
    {
        // Handles the event of an ace in a hand
        int[] scores = handValue();

        // Ony two values will ever be returned in scores
        // The smaller of the two hand values will always be index 0 
        // and the larger will always be index 1

        // Two equal and scores below 22
        if ((scores[0] == scores[1]) && (scores[0] <= 21 && scores[1] <= 21)) 
        {
            System.out.println("\t\tCurrent Value: " + scores[0]);
        } 
        // Two diffent scores below 22
        else if (scores[0] <= 21 && scores[1] <= 21) 
        {
            System.out.println("\t\tCurrent Values: " + scores[0] + " or " + scores[1]);
        }
        // Two different scores and 1 is greater than 21 and the other isn't
        else if (scores[0] > 21 && scores[1] <= 21) 
        {
            System.out.println("\t\tCurrent Value: " + scores[1]);
        } 
        else if (scores[1] > 21 && scores[0] <= 21) 
        {
            System.out.println("\t\tCurrent Value: " + scores[0]);
        }
        // If both scores are over 21 then show the player has gone bust    
        else 
        {
            System.out.println("\t\tBust");
        }
    }

    // Returns if the player has gone bust or not (Hand value over 21)
    public boolean hasBust() 
    {
        // Check values
        int[] scores = handValue();

        // If both potntial hand values are over 21 then the player
        // has gone bust, if not the player must player the highest hand
        // that is below 22
        return (scores[0] > 21 && scores[1] > 21);
    }

    // Displays the hand of cards to the terminal 
    public void displayHand() 
    {
        // Creaye the Array of Card Pieces Arrays
        String[][] cards = new String[hand.size()][];

        // Populate Cards array with card pieces arrays
        for (int i = 0; i < hand.size(); i++) 
        {
            // Hides Card the second card in the hand 
            // for dealer when when needed
            if (i == 1 && hiddenCard) 
            {
                // Populated the array with a hidden card 
                // when appropriate
                cards[i] = hand.get(i).hiddenCard();
                continue;
            }

            // index i gets the pieces of the appropriate card 
            cards[i] = hand.get(i).cardCreate();
        }

        // String Builder to create hand line by line 
        StringBuilder sb = new StringBuilder();

        // Displays the Cards line by line
        
        // First we traverse across the top row of card pieces
        for (int cardRow = 0; cardRow < hand.get(0).cardCreate().length; cardRow++) 
        {
            // Append the appropriate card piece next to the other 
            for (int card = 0; card < hand.size(); card++) 
            {
                sb.append(" " + cards[card][cardRow]);
            }

            // New line for the next row for cards
            sb.append("\n");
        }

        // Display the hand where each card is next to the other
        System.out.println(sb.toString());
    }

    // Dealers have to unhide 2nd card in their hand after initally dealing
    public void setHideCard(boolean hidden) 
    {
        this.hiddenCard = hidden;
    }

    // Returns whether a player has black jack or not
    // We define blackjack as a player having 21 with the first two cards dealt to them
    public boolean hasBlackjack() 
    {
        // Checks that the first two cards are an ace and a "ten" or face card
 
        return (hand.get(0).getValue() >= 10 || hand.get(1).getValue() >= 10)
                && (hand.get(0).getValue() == 1 || hand.get(1).getValue() == 1);
    }


    // Returns an array of 2 potential hand values, because an ace could be 
    // worth 1 or 11, the smaller of the two hand values will be index 0
    // and the larger of the two vallues will always be index 1
    public int[] handValue() 
    {
        // Calculates two sums in case of an ace
        int val1 = 0;
        int val2 = 0;

        int index = 0;

        // Sums up value of hand
        for (Card c : hand) 
        {
            // Handles Hiding 2nd card in the hand 
            // for the Dealer when needed 
            if (index == 1 && hiddenCard) 
            {
                continue;
            }

            // Assigns each card their value and handles face cards and aces
            switch (c.getValue()) 
            {
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
