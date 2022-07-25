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




}