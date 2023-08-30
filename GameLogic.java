import java.util.Scanner;

public class GameLogic 
{
    private Player house;
    private Player player;
    private Deck GameDeck;
    private Scanner scan;

    public GameLogic()
    {
        player = new Player();
        house = new Player();  
        GameDeck = new Deck();    
        scan = new Scanner(System.in);
        Round();
    }   
    

    public void displayGame()
    {  
        displayLine();
        System.out.println("\n\t\t\t== Dealer Hand == ");
        house.displayHand();
        house.displayScore();

        // One of the dealers cards has to be hidden until it flipped 
        //TODO

        System.out.println("\n\t\t\t== Your Hand ==");
        player.displayHand();
        player.displayScore();
        displayLine();
    }

    private void displayLine()
    {
        System.out.println("-----------------------------------------------------------");
    }


    public void Round()
    {
        //Place Bets 
        // TODO

        //Dealer Deals 2 cards to each player including the house
        for (int i = 0; i < 2; i++)
        {
            // Player Card 
            // Could potentially add a player list of somekind to hand multiple people at table
            player.addCardtoHand(GameDeck.drawCard());

            // House 
            house.addCardtoHand(GameDeck.drawCard());
        }

        displayGame();

        // Insurance
        // TODO

        // Black Jack
        if (!player.hasBlackjack())
        {
            playerMove();
        }
        else
        {
            System.out.println("\t\t\t== Black Jack ==");
            return;
        }
        
        // Dealer Moves
        if(!player.hasBust())
        {
            dealerMove();
        }

        displayGame();

        calculateWinner();
    }

    private void playerMove()
    {
        //Player Hit
        int reply;
       
        
        // Resets the reply with each iteration 
        reply = -1;

        System.out.println("\n\t\tSelect One of the following Options");
        System.out.println("\t\t 1.) Hit");
        System.out.println("\t\t 2.) Stand");
            
        // Error handling 
        try
        {
            reply = scan.nextInt();
        } 
        catch (Exception e)
        {
            System.out.println("Please enter a numeric value");
        }
            
        // Consider Case // If you stand or hit on an ace (19 or 29) 

        // Player Hit
        if (reply == 1)
        {
            player.addCardtoHand(GameDeck.drawCard());
            displayGame();

            if (player.hasBust())
            {
                System.out.println("\t\t\tYou have gone Bust!");
                return;
            }

            playerMove();
        }
         // Player Stand 
        else if (reply == 2)
        {
        return;
        }
        else
        {
            System.out.println("Please enter a valid response");
            playerMove();
        }
    }

    private void dealerMove()
    {
        // Dealer Hit
        int [] value = house.handValue();

        // If there are no aces 
        if (value[0] == value[1])
        {
            // Dealer has to hit on anything less than 17
            if (value[0] < 16)
            {
                // Dealer Move
                house.addCardtoHand(GameDeck.drawCard());
                
                // Has to decide again whether to hit or stand
                dealerMove();
            }
            else
            {
                // Stand 
                return;
            }
        }
        else 
        {
            // If either hand is a above 16 dealer must stand
            if (value[0] > 16 && value[1] > 16)
            {
                // Stand
                return;
            }
            else 
            {   
                // Dealer Move
                house.addCardtoHand(GameDeck.drawCard());
                
                // Has to decide again whether to hit or stand
                dealerMove();
            }
        }
    }

    public void calculateWinner()
    {
        int [] houseHand = house.handValue();
        int [] playerHand =  player.handValue();

        // Assigns the highest of the two values that is 
        int houseBest = ( (houseHand[1] > houseHand[0]) && (houseHand[1] <= 21) ) ? houseHand[1] : houseHand[0];
        int playerBest = ( (playerHand[1] > playerHand[0]) && (playerHand[1] <= 21) ) ? playerHand[1] : playerHand[0];

    
        if ( (playerBest <= 21) && (playerBest > houseBest))
        {
            System.out.println("\t\t\tPlayer Wins");
        }
        else if ( (houseBest <= 21) && (playerBest < houseBest))
        {
            System.out.println("\t\t\tHouse Wins");
        }
        else
        {
            System.out.println("\t\t\tDraw!");
        }
    }
}
