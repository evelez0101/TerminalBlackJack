import java.util.InputMismatchException;
import java.util.Scanner;

public class GameLogic 
{
    // Dealer
    private Player house;

    // Player
    private Player player;

    // Deck of Cards in use
    private Deck GameDeck;

    // Scanner for user input
    private Scanner scan;

    // Construction to begin gameflow
    public GameLogic() 
    {
        player = new Player();
        house = new Player(true);
        GameDeck = new Deck();
        scan = new Scanner(System.in);
        welcomeScreen();
        Round();
    }

    // Clears the Terminal screen to avoid clutter
    private void clearScreen()
    {
        // Clear Screan
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    // Displays Scores, current bet, and hand for player and dealer
    public void displayGame() 
    {
        // Clear Screan
        clearScreen(); 
      
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

    // Welcome Screen for the program is first run
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

        // Version Number 
       System.out.println("\n\t\tBy: Evelio Velez\tVersion: 0.9.0");   
       
       displayLine();
       
       buyInScreen();
    }

    // Players can "Buy in" so they can play at the table
    private void buyInScreen()
    {
        // Playe Choice
        System.out.println("\n\tHow Much Would you like to Buy-In for?");
        System.out.println("\t(Note: Table Minimum is $1)");

        // Error Handles user misinput
        int buyIn;

        // Do until player gives a valid answer
        do
        {
            buyIn = getUserReply("\n\tBuy-in Amount (Enter Whole $ amounts only): "); 
        }
        while(buyIn <= 0);

        // Despoite Buy in amount
        player.deposit(buyIn);

        clearScreen();
    }

     // Displays Line for styling
    private void displayLine() 
    {
        System.out.println("-------------------------------------------------------------------------");
    }

    // Handles Paying the player after a win
    private void payThePlayer()
    {
        // Player scores
        int[] houseHand = house.handValue();
        int[] playerHand = player.handValue();

        // Assigns the highest of the two values that is below 21
        int houseBest = ((houseHand[1] > houseHand[0]) && (houseHand[1] <= 21)) ? houseHand[1] : houseHand[0];
        int playerBest = ((playerHand[1] > playerHand[0]) && (playerHand[1] <= 21)) ? playerHand[1] : playerHand[0];


        // Insured Bet Payout
        if (house.hasBlackjack() && player.hasInsuarnce())
        {
            player.deposit(player.getInsuranceBet() * 2);
            System.out.println("\tYou Won: $" + player.getInsuranceBet() * 2);
        }
        // Push or get Money Back
        else if (playerBest == houseBest)
        {
            player.deposit(player.getBet());
            System.out.println("\tYou Won: $" + player.getBet());
        }
        // Black Jack Pays 3 to 2 + original amount
        else if (playerBest == 21 && player.hasBlackjack())
        {
            double threeToTwo = (player.getBet() * (1.5));
            player.deposit((int)threeToTwo + player.getBet());
            System.out.println("\tYou Won: $" + ((int)threeToTwo + player.getBet()));
        }
        // Regular 2 to 1 win
        else
        {
            player.deposit(player.getBet() * 2);
            System.out.println("\tYou Won: $" + player.getBet() * 2);
        }

    }

    // Method to hand user input handling 
    private int getUserReply(String message)
    {
        int reply; 
        
        // Error Handling
        do
        {
            System.out.print(message);

            try
            {
                reply = scan.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("\t\tPlease Enter a Numerical Value");
                reply = -1;
            }

            scan.nextLine(); // clears the buffer
        }
        while(reply <= 0);

        // Returns a valid reply 
        return reply;
    }

    // One round of Game Flow
    public void Round() 
    {
        // Screen Formatting
        clearScreen();

        displayLine();

        // Place Bet
        int reply;
        do
        {
            reply = getUserReply("\tPlace a bet (Whole $ amounts only): ");

        }
        while(!player.canBet(reply));

        // Handle Betting System
        if (player.canBet(reply))
        {
            player.withdrawl(reply);
            player.setBet(reply);
        }

        // Deal Cards (2 per player including the house)
        for (int i = 0; i < 2; i++) {
            // Player Card
            player.addCardtoHand(GameDeck.drawCard());

            // House
            house.addCardtoHand(GameDeck.drawCard());
        }

        // Debug (Dealer Black Jack)
        //player.addCardtoHand(new Card(1,1));
        //player.addCardtoHand(new Card(10,1));

        // Fresh Screen for Game to begin
        clearScreen();

        displayGame();

        // Insurance 
        int [] houseHandValue = house.handValue();
        
        // If the first card showing is an ace, the player can insure their bet 
        if (houseHandValue[1] == 11)
        {
            int reponse;
            
            // Error Handling
            do
            {
                reponse = getUserReply("\tWould You like Insurance?" + "\n\t\t 1.) Yes" + "\n\t\t 2.) No \n");
            } 
            while(reponse > 2);
           
            // Insure Bet
            if (reponse == 1 && player.canBet(player.getBet() / 2))
            {
                // Takes a rounded up 1/2 of initial bet
                player.setInsuranceBet((int)Math.ceil(player.getBet() / 2.0));
                player.withdrawl((int)Math.ceil(player.getBet() / 2.0));
                player.setInsureBet(true);
                System.out.println("\t\tYour Bet is Insured!");
            }
            else
            {
                player.setInsureBet(false);
            }

            // House has Black Jack
            if (house.hasBlackjack())
            {
                house.setHideCard(false);
                displayGame();
                System.out.println("\t\t\t== House has Black Jack ==");
                payThePlayer();
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

            playAgain();

            return;
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
        if (player.getBankroll() == 0)
        {
            displayLine();
            System.out.println("\t\tYou have run out of funds!");
            return;
        }

        displayLine();

        // Player Choice is diaplayed
        System.out.println("\n\tCurrent Bankroll: $" + player.getBankroll());
        System.out.println("\n\tWould You like to play again?");
        System.out.println("\n\tSelect One of the following Options");
        System.out.println("\t\t 1.) Another Round");
        System.out.println("\t\t 2.) Leave Table");

        // Reply from user
        int reply = getUserReply("");

        // Decide if player wants to play another round
        if (reply == 1) 
        {
            // Clear player, clears player insurance status,
            // house hands, hides dealer hand, and then starts another round
            house.setHideCard(true);
            player.clearHand();
            house.clearHand();
            player.setInsureBet(false);
            player.setBet(0);
            player.setInsuranceBet(0);
            player.setDoubleDownStatus(true);

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

        // Displays Choices to User
        System.out.println("\n\t\tSelect One of the following Options (1 or 2)");
        System.out.println("\t\t 1.) Hit");
        System.out.println("\t\t 2.) Stand");
        
        // Double Down only on first turn 
        if (player.canDoubleDown() && player.canBet(player.getBet()))
        {
            System.out.println("\t\t 3.) Double Down");
        }
        
        // Error handling
        int reply = getUserReply("");

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

            player.setDoubleDownStatus(false);

            // Repeats
            playerMove();
        }
        // Player Stand
        else if (reply == 2) 
        {
            // Ends Turn
            return;
        } 
        // Player Double Down 
        else if(reply == 3 && (player.canDoubleDown() && player.canBet(player.getBet())) )
        {
            // Withdraw the doubled bet 
            player.withdrawl(player.getBet());

            // Set the new bet 
            player.setBet(2 * player.getBet());
            
            // Add a card to hand
            player.addCardtoHand(GameDeck.drawCard());
            displayGame();
        }
        else 
        {
            System.out.println("Please enter a valid response");
            playerMove();
        }

        // Doesn't allow doubling down past first iteration or call
        player.setDoubleDownStatus(false);
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

        // If Dealer does not have aces
        if (value[0] == value[1]) 
        {
            // Dealer has to hit on anything less than 17
            if (value[0] <= 16) 
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
        // Hand Values of the Player and House
        int[] houseHand = house.handValue();
        int[] playerHand = player.handValue();

        // Assigns the highest of the two values that is below 21
        int houseBest = ((houseHand[1] > houseHand[0]) && (houseHand[1] <= 21)) ? houseHand[1] : houseHand[0];
        int playerBest = ((playerHand[1] > playerHand[0]) && (playerHand[1] <= 21)) ? playerHand[1] : playerHand[0];

        // Pickes the winner, who is closer to 21 without going over 
        if (((playerBest <= 21) && (playerBest > houseBest)) || (houseBest > 21)) 
        {
            System.out.println("\t\t\tPlayer Wins");
            payThePlayer();
        } 
        else if (((houseBest <= 21) && (playerBest < houseBest)) || (playerBest > 21)) 
        {
            System.out.println("\t\t\t\t\tHouse Wins");
        } 
        else 
        {
            System.out.println("\t\t\t\tDraw!");
            payThePlayer();
        }
    }
}
