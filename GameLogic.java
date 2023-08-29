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
        System.out.println("\n\t\t== Dealer Hand == ");
        house.displayHand();
        house.displayScore();


        System.out.println("\n\t\t== Your Hand ==");
        player.displayHand();
        player.displayScore();

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
            System.out.println("\t\t== Black Jack ==");
        }
        
        // Dealer Moves
        dealerMove();
    }

    private void playerMove()
    {
        //Player Hit
        int reply = -1;
       
        do
        {
            System.out.println("\nSelect One of the following Options");
            System.out.println("\t 1.) Hit");
            System.out.println("\t 2.) Stand");

            try
            {
                reply = scan.nextInt();
            } 
            catch (Exception e)
            {
                System.out.println("Please enter a numeric value");
            }
            

            if (reply == 1)
            {
                player.addCardtoHand(GameDeck.drawCard());
                displayGame();

                if (player.hasBust())
                {
                    System.out.println("You have gone Bust!");
                    return;
                }
            }
            else if(reply == 2)
            {
                return;
            }
            else
            {
                System.out.println("Please enter a valid response");
            }

        } while (true);

        //Player Stand
    }

    private void dealerMove()
    {
        // Dealer Hit

        // Dealer Stand (Hard 17 Rules)

    }


}

