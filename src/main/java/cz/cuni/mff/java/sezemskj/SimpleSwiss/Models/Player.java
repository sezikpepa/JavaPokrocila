package cz.cuni.mff.java.sezemskj.SimpleSwiss.Models;

/**
 * Person which plays in the tournament
 */
public class Player {

    /**
     * Creates new player without any information
     */
    public Player(){

    }

    /**
     * Creates new player without its ID - used in form
     * @param firstName First name of the player
     * @param lastName Surname of the player
     * @param chessClub Chess club of the player
     * @param elo Elo of the player
     */
    public Player(String firstName, String lastName, String chessClub, int elo){
        FirstName = firstName;
        LastName = lastName;
        ChessClub = chessClub;
        Elo = elo;
    }

    /**
     * Creates new player with its ID - used in retrieval of saved data
     * @param id Unique identifier of the player
     * @param firstName First name of the player
     * @param lastName Surname of the player
     * @param chessClub Chess club of the player
     * @param elo Elo of the player
     */
    public Player(String id, String firstName, String lastName, String chessClub, int elo){
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        ChessClub = chessClub;
        Elo = elo;
    }

    /**
     * Unique identifier of the player
     */
    public String Id;

    /**
     * First name of the player
     */
    public String FirstName;

    /**
     * Surname of the player
     */
    public String LastName;

    /**
     * Chess club of the player
     */
    public String ChessClub;

    /**
     * Elo of the player
     */
    public int Elo;

    @Override
    public String toString(){
        return FirstName + " " + LastName;
    }

    /**
     * Swiss generator need even number of players. If the number of players is odd, one player is added, but is not shown in the results
     * @return If this player is mule
     */
    public boolean isMule(){
        return Id.equals("123456789");
    }

}
