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
        house = new Player(true);
        GameDeck = new Deck();
        scan = new Scanner(System.in);
        welcomeScreen();
        Round();
    }

    public void displayGame() 
    {
        
        // Dealer Hand
        displayLine();
        System.out.println("\n\t\t\t\t== Dealer Hand == ");
        house.displayHand();
        house.displayScore();

        // Player Hand
        System.out.println("\n\t\t\t\t== Your Hand ==\t\tCurrent Bet: $" + player.getBet());
        player.displayHand();
        player.displayScore();
        displayLine();
    }

    private void welcomeScreen()
    {
        displayLine();

        // Banner put into an array to print line by line 
        String [] banner = new String[4];
        banner[0] = (" _____              _         _    _____ _         _      __         _  ");
        banner[1] = ("|_   _|__ ___ _____|_|___ ___| |  | __  | |___ ___| |_ __|  |___ ___| |_");
        banner[2] = ("  | || -_|  _|     | |   | .'| |  | __ -| | .'|  _| '_|  |  | .'|  _| '_|");
        banner[3] = ("  |_||___|_| |_|_|_|_|_|_|__,|_|  |_____|_|__,|___|_,_|_____|__,|___|_,_|");

        // Prints Banner Line by Line
        for(String x: banner)
        {
            System.out.println(x);
        }

       System.out.println("\n\t\tBy: Evelio Velez\tVersion: 0.8.1");   
       displayLine();
       
       buyInScreen();
    }


    private void buyInScreen()
    {

        System.out.println("\n\tHow Much Would you like to Buy-In for?");
        System.out.println("\t(Note: Table Minimum is $10)");

        System.out.print("\n\tBuy-in Amount (Enter Whole $ amounts only): ");
        
        int buyIn = scan.nextInt();
        
        player.deposit(buyIn);

        System.out.println("\n\tYou have bought in for $" + buyIn + "\n\n\t\t\tGood Luck!");
    }

    private void displayLine() 
    {
        System.out.println("-------------------------------------------------------------------------");
    }

    private void payThePlayer()
    {

        int[] houseHand = house.handValue();
        int[] playerHand = player.handValue();

        // Assigns the highest of the two values that is below 21
        int houseBest = ((houseHand[1] > houseHand[0]) && (houseHand[1] <= 21)) ? houseHand[1] : houseHand[0];
        int playerBest = ((playerHand[1] > playerHand[0]) && (playerHand[1] <= 21)) ? playerHand[1] : playerHand[0];


        // Push or get Money Back
        if (playerBest == houseBest)
        {
            player.deposit(player.getBet());
            System.out.println("\tYou Won: $" + player.getBet());
        }
        // Black Jack Pays 3 to 2
        else if (playerBest == 21 && player.hasBlackjack())
        {
            double threeToTwo = player.getBet() * (1.5);
            player.deposit((int)threeToTwo);
            System.out.println("\tYou Won: $" + (int)threeToTwo);
        }
        // Regular 2 to 1 win
        else
        {
            player.deposit(player.getBet() * 2);
            System.out.println("\tYou Won: $" + player.getBet() * 2);
        }
    }

    public void Round() {
        
        // Place Bets
        displayLine();

        System.out.print("\tPlace a bet (Whole $ amounts only): ");

        // Error checkt this later
        int reply = scan.nextInt();

        // Handle Betting System
        if (player.canBet(reply))
        {
            player.withdrawl(reply);
            player.setBet(reply);
        }

        // Card Deal

        // Dealer Deals 2 cards to each player including the house
        for (int i = 0; i < 2; i++) {
            // Player Card
            // Could potentially add a player list of somekind to hand multiple people at
            // table
            player.addCardtoHand(GameDeck.drawCard());

            // House
            house.addCardtoHand(GameDeck.drawCard());
        }

        // Debug 
        // house.addCardtoHand(new Card(1,1));
        // house.addCardtoHand(new Card(10,1));

        displayGame();

        // Insurance
        int [] houseHandValue = house.handValue();
        
        if (houseHandValue[1] == 11)
        {
            System.out.println("\tWould You like Insurance?");
            System.out.println("\t\t 1.) Yes");
            System.out.println("\t\t 2.) No");

            int r = scan.nextInt();

            // Error handle later
            switch(r)
            {
                case 1: System.out.println("\t\t\tBet Insured"); break;
                case 2: System.out.println("\t\t\tNo bet Insured"); break;
            }

            // House has Black Jack
            if (house.hasBlackjack())
            {
                house.setHideCard(false);
                displayGame();
                System.out.println("\t\t\t== House has Black Jack ==");
                playAgain();
            }
        }

        // Player Black Jack
        if (!player.hasBlackjack()) 
        {
            playerMove();
        } 
        else 
        {
            // Workout what to do, currently it exits the game
            System.out.println("\t\t\t== Black Jack ==");

            calculateWinner();

            // Pay the Player
            payThePlayer();

            playAgain();
        }

        // Dealer Moves
        house.setHideCard(false);
        
        // Player Bust is an instant loss
        if (!player.hasBust()) 
        {
            dealerMove();
        }

        // Update Screen
        displayGame();

        // Calculate winner of round
        calculateWinner();

        // Ask to Play Again
        playAgain();
    }

    private void playAgain() 
    {
        // Displays to user a choice to make
        displayLine();
        System.out.println("\n\tCurrent Bankroll: $" + player.getBankroll());
        System.out.println("\n\tWould You like to play again?");

        System.out.println("\n\tSelect One of the following Options");
        System.out.println("\t\t 1.) Another Round");
        System.out.println("\t\t 2.) Leave Table");

        // Reply from user
        int reply;

        reply = scan.nextInt();

        // Decide if player wants to play another round
        if (reply == 1) 
        {
            // Clear player, house hands, hides dealer hand, and then starts another round
            house.setHideCard(true);
            player.clearHand();
            house.clearHand();

            // Reshuffle deck when there are only 20 cards left
            if (GameDeck.getGameDeckSize() <= 40) 
            {
                GameDeck.refillgameDeck();
                displayLine();
                System.out.println("\t\tThe Deck has been reshuffled");
                displayLine();
            }
            
            // New Round
            Round();
        } 
        else if (reply == 2) 
        {
            displayLine();
            System.out.println("\t\t\tThanks for playing!");
            displayLine();
        } 
        else 
        {
            System.out.println("\t\tInvalid input");
            playAgain();
        }

    }

    private void playerMove() 
    {
        // Player Hit
        int reply;

        // Resets the reply with each iteration
        reply = -1;

        // Displays Choices to User
        System.out.println("\n\t\tSelect One of the following Options (1 or 2)");
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

        // Player Hit
        if (reply == 1) 
        {
            // Add a card to hand
            player.addCardtoHand(GameDeck.drawCard());
            displayGame();

            // Check to see if the additional card send the player over 21
            if (player.hasBust()) 
            {
                // Instant Loss
                System.out.println("\t\t\tYou have gone Bust!");
                return;
            }

            // Repeats
            playerMove();
        }
        // Player Stand
        else if (reply == 2) 
        {
            // Ends Turn
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
        // Dealer Hand values
        int[] value = house.handValue();

        // Dealer Black Jack
        if (house.hasBlackjack()) 
        {
            // Instant GameOver
            System.out.println("\t\t== House Black Jack ==");
            return;
        }

        // If Dealer does have aces
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
        // Handles a hand with one or more aces 
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
        int[] houseHand = house.handValue();
        int[] playerHand = player.handValue();

        // Assigns the highest of the two values that is below 21
        int houseBest = ((houseHand[1] > houseHand[0]) && (houseHand[1] <= 21)) ? houseHand[1] : houseHand[0];
        int playerBest = ((playerHand[1] > playerHand[0]) && (playerHand[1] <= 21)) ? playerHand[1] : playerHand[0];

        // Pickes the winner, who is closer to 21 without going over 
        if (((playerBest <= 21) && (playerBest > houseBest)) || (houseBest > 21)) {
            System.out.println("\t\t\tPlayer Wins");
            payThePlayer();
        } 
        else if (((houseBest <= 21) && (playerBest < houseBest)) || (playerBest > 21)) {
            System.out.println("\t\t\tHouse Wins");
        } 
        else 
        {
            System.out.println("\t\t\tDraw!");
            payThePlayer();
        }
    }
}
