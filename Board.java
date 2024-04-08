package test;


public class Board {//
    private static Board singl;
    private Tile[][] newBoard;

private Board(){
    newBoard= new Tile[15][15];

}

public Board getBoard {
        if (single == null) {
            singl = new Board();
        }
        return singl;
    }
}
