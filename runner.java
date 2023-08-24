public class runner {
    public static void main(String [] args)
    {
        Deck d = new Deck();
        Player p = new Player();
        
        // Testing dealing to hands
        p.addCardtoHand(d.drawCard());
        p.addCardtoHand(d.drawCard());

        // Shows Hand 
        p.displayHand();

        System.out.println(p.handValue().toString());
    }
}
