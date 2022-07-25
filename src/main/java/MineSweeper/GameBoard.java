package MineSweeper;

import MineSweeper.Position;
import MineSweeper.Tile;

import java.util.*;

public class GameBoard {

    private final Tile[][] tiles;
    private final int width;
    private final int height;
    private int bombCount;
    private int numRevealedTiles;


    //Sets up board with Tiles
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                this.tiles[x][y] = new Tile();
            }
        }
        this.bombCount = 0;
        this.numRevealedTiles = 0;
    }

    public void printBoard() {
        int x;
        //Print grid content
        for (int y = 0; y < this.height; ++y) {
            for (x = 0; x < this.width; ++x) {
                System.out.print(this.tiles[x][y] + "  ");
            }
            System.out.println(" |" + (y + 1));
        }

        //Print base of grid
        for (x = 0; x < this.width; ++x) {
            System.out.print("_  ");
        }
        System.out.println();

        //Print base numbers
        for (x = 0; x < this.width; ++x) {
            System.out.print(x + 1 + " ");
            if (x + 1 < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // Reveal tiles and neighbouring
    public void revealTile(Position position) {
        if (this.tiles[position.x][position.y].getNeighbours() != 0) {
            ++this.numRevealedTiles;
            this.tiles[position.x][position.y].showTile();
        } else {

            // Iterate through the grid,
            List<Position> shownTiles = this.floodFillShow(position);
            Iterator tileIterator = shownTiles.iterator();

            while (tileIterator.hasNext()) {
                Position p = (Position) tileIterator.next();
                this.revealAroundTile(p);
            }
        }
        ++this.numRevealedTiles;
        this.tiles[position.x][position.y].showTile();
    }

    //Define valid within grid dimensions
    public boolean validPosition(Position position) {
        return position.x >= 0 && position.y >= 0 && position.x < this.width && position.y < this.height;
    }

    public boolean isTileShown(Position position) {
        return this.tiles[position.x][position.y].getIsShown();
    }

    public boolean isTileBomb(Position position) {
        return this.tiles[position.x][position.y].getBomb();
    }

    public boolean isTileFlagged(Position position) {
        return this.tiles[position.x][position.y].getIsFlagged();
    }

    public void flagTile(Position position) {
        this.tiles[position.x][position.y].toggleIsFlagged();
    }

    // Checks if tile is already a bomb
    // Checks the coordinates, will set neighbour around any bombs, then adds the bomb & increases count.
    public boolean addBomb(Position position) {
        if (this.isTileBomb(position)) {
            return false;
        } else {
            int minX = Math.max(0, position.x - 1);
            int maxX = Math.min(this.width - 1, position.x + 1);
            int minY = Math.max(0, position.y - 1);
            int maxY = Math.min(this.height - 1, position.y + 1);

            for (int y = minY; y <= maxY; ++y) {
                for (int x = minX; x <= maxX; ++x) {
                    this.tiles[x][y].setNeighbours();
                }
            }
            this.tiles[position.x][position.y].setBomb();
            bombCount++;
            return true;
        }
    }

    //Place bombs at random coords within grid.
    public void spawnBombs(int maxBombs) {
        Random rand = new Random();

        for (int i = 0; i < maxBombs; ++i) {
            this.addBomb(new Position(rand.nextInt(this.width), rand.nextInt(this.height)));
        }
    }

    public void showAll() {
        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                this.tiles[x][y].showTile();
            }
        }
    }

    // Check around position selected
    // Keep X Y within grid 0 or higher, Max -1 prevent out of bounds
    private void revealAroundTile(Position position) {
        int minX = Math.max(0, position.x - 1);
        int maxX = Math.min(this.width - 1, position.x + 1);
        int minY = Math.max(0, position.y - 1);
        int maxY = Math.min(this.height - 1, position.y + 1);

        for (int y = minY; y <= maxY; ++y) {
            for (int x = minX; x <= maxX; ++x) {
                if (!this.tiles[x][y].getIsShown()) {
                    this.tiles[x][y].showTile();
                    ++this.numRevealedTiles;
                }
            }
        }
    }

    private void checkTileFloodFill(Position position, boolean[][] vis, Queue<Position> positionQueue) {
        if (this.validPosition(position)) {
            if (!vis[position.x][position.y] && !this.isTileShown(position) && this.tiles[position.x][position.y].getNeighbours() == 0) {
                positionQueue.add(position);
            }
            vis[position.x][position.y] = true;
        }
    }

    private List<Position> floodFillShow(Position position) {
        boolean[][] vis = new boolean[this.width][this.height];

        for (int x = 0; x < this.width; ++x) {
            for (int y = 0; y < this.height; ++y) {
                vis[x][y] = false;
            }
        }
        List<Position> changedPoints = new ArrayList();
        Queue<Position> positionQueue = new LinkedList();
        positionQueue.add(position);
        vis[position.x][position.y] = true;

        while (!positionQueue.isEmpty()) {
            Position positionToReveal = positionQueue.remove();
            this.tiles[positionToReveal.x][positionToReveal.y].showTile();
            ++this.numRevealedTiles;
            changedPoints.add(positionToReveal);
            this.checkTileFloodFill(new Position(positionToReveal.x + 1, positionToReveal.y), vis, positionQueue);
            this.checkTileFloodFill(new Position(positionToReveal.x - 1, positionToReveal.y), vis, positionQueue);
            this.checkTileFloodFill(new Position(positionToReveal.x, positionToReveal.y + 1), vis, positionQueue);
            this.checkTileFloodFill(new Position(positionToReveal.x, positionToReveal.y - 1), vis, positionQueue);
        }
        return changedPoints;
    }

    public boolean isWon() {
        return this.numRevealedTiles + this.bombCount == this.width * this.height;
    }

    public void printStatus() {
        System.out.println("You've managed to reveal " + this.numRevealedTiles + " out of " +
                (width * height) + " tiles with " + this.bombCount + " bombs!");
    }

}
