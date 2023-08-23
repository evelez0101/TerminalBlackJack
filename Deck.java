import java.util.ArrayList;
public class Deck 
{
    private ArrayList<Card> Deck;
    
    public Deck()
    {
        Deck = new ArrayList<Card>();
        createCards();
        
        for(Card c: Deck)
        {
            System.out.println(c);
        }
    }

    private void createCards()
    {
        // Suite
        for (int suite = 0; suite < 4; suite++)
        {
            // Card Values
            for (int value = 0; value < 13; value++)
            {
                Deck.add(new Card(value, suite));
            }
        }
    }
}
