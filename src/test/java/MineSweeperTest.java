import MineSweeper.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MineSweeperTest {

    @Test
    public void test_getBombs(){
        Tile testBomb = new Tile();
        testBomb.setBomb();
        assertEquals(true, testBomb.getBomb(), "The bomb returned false");
    }

    @Test
    public void test_getNeighbours(){
        Tile testNeighbours = new Tile();
        testNeighbours.setNeighbours();
        assertEquals(1, testNeighbours.getNeighbours(), "You don't live next to anyone");
    }

    @Test
    public void test_getFlagged(){
        Tile testFlag = new Tile();
        testFlag.toggleIsFlagged();
        assertEquals(true, testFlag.getIsFlagged(), "The tile isn't flagged");
    }


}