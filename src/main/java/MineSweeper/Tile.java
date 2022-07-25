package MineSweeper;

public class Tile {

    private boolean isShown;
    private boolean isBomb;
    private int neighbours;
    private boolean isFlagged;

    public Tile(){
        this.resetTile();
    }

    //Reset the tile to blank
    public void resetTile(){
        this.isShown = false;
        this.isBomb = false;
        this.isFlagged = false;
        this.neighbours = 0;
    }

    public void showTile(){
        this.isShown = true;
    }
    public boolean getIsShown(){
        return this.isShown;
    }

    public void setBomb(){
        this.isBomb = true;
    }
    public boolean getBomb(){
        return this.isBomb;
    }

    public void setNeighbours() {
        ++this.neighbours;
    }
    public int getNeighbours() {
        return this.neighbours;
    }

    public boolean getIsFlagged(){
        return this.isFlagged;
    }
    public void toggleIsFlagged(){
        this.isFlagged = !this.isFlagged;
    }

    //Return B for Bomb, F for Flag or * / Neighbour number
    public String toString(){
        if(isShown) {
            if(isBomb){
                return  "B";
            } else {
                return ""+neighbours;
            }
        } else if(isFlagged){
            return "F";
        } else {
            return "*";
        }
    }


}
