import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Deck 
{
    // Internal Deck
    private ArrayList<Card> Deck;
    // Deck Used in Game
    private Stack<Card> GameDeck;
    
    public Deck()
    {
        Deck = new ArrayList<Card>();
        GameDeck = new Stack<Card>();
        createCards();
        shuffle();
        putintoStack();
        //displayDeck();
    }

    private void displayDeck()
    {
        // For debugging
        for(Card c: Deck)
        {
            System.out.println(c);
        }
    }

    private void putintoStack()
    {
        for(Card c: Deck)
        {
            GameDeck.add(c);
        }
    }    
    

    private void shuffle()
    {   
        Random rand = new Random();
        
        // Fisher Yates Shuffle Algorithm 

        for(int i = Deck.size() - 1; i >= 0;i--)
        {
            swap(i, rand.nextInt(i + 1));
        }
    }

    private void swap(int current, int target)
    {
        Card temp = Deck.get(target);
        Deck.set(target, Deck.get(current));
        Deck.set(current, temp);
    }

    private void createCards()
    {
        // Suite
        for (int suite = 1; suite <= 4; suite++)
        {
            // Card Values
            for (int value = 1; value <= 13; value++)
            {
                Deck.add(new Card(value, suite));
            }
        }
    }

    private void refillgameDeck()
    {
        shuffle();
        putintoStack();
    }
    
    public Card drawCard()
    {
        Card c;

        // Handles Stack underflow
        try 
        {
            c = GameDeck.pop();
        }
        catch (Exception e)
        {
            // Have to comeback and error handle
            System.out.println("Deck is empty");
            refillgameDeck();
            c = GameDeck.pop();
        }

        return c;
    }
}
