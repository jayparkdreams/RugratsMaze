/*
   Assignment: Final Project
   Name: Thang Nguyen
   ID: 5965
   Name: Henry Duong
   ID: 2891  
   Name: Feng-Yun Lin
   ID: 2265
 */

import java.util.*;
import world.*;
import image.*;

/** Represents the Maze. */
public class Maze {
    /** 2D Array that represents the maze. Each element represents a "room"
     * which is represented by a {@link Cell Cell}. */
    public Cell [][] labyrinth; 
    
    /** Represents the North direction*/
    public static final int NORTH = 0;
    /** Represents the East direction*/
    public static final int EAST = 1;
    /** Represents the South direction*/
    public static final int SOUTH = 2;
    /** Represents the West direction*/
    public static final int WEST = 3;
    
    /**
     * The constructor of the maze.
     * @param x The width of the maze.
     * @param y The height of the maze.
     */
    public Maze(int x, int y){
        labyrinth = new Cell[x][y];
        
        for (int i=0; i < labyrinth.length ; i++)
            for (int j=0; j < labyrinth[i].length ; j++)
              labyrinth[i][j] = new Cell(i,j);
        
    }
    
    /* Template:
     *  
     *  Fields  
     *    ... this.labyrinth ...                                            -- Cell[][]
     *    ... this.NORTH ...                                                -- int
     *    ... this.EAST ...                                                 -- int
     *    ... this.SOUTH ...                                                -- int
     *    ... this.WEST ...                                                 -- int
     *    
     *  Methods
     *    ... this.createMaze() ...                                         -- void
     *    ... this.knockDown(Cell, Posn) ...                                -- void
     *    ... this.neighborsWithWallsHuh(Cell, ArrayList<Posn>) ...         -- ArrayList<Posn>
     *    ... this.hasPosn(ArrayList<Posn>, Posn) ...                       -- boolean
     *    ... this.nextCell(int, int, int) ...                              -- Posn
     *    ... this.nextCellGoodHuh(int, int, int) ...                       -- boolean
     *    ... this.notoutOfBounds(int, int) ...                             -- boolean
     *    ... this.drawMaze(Scene) ...                                      -- Scene
     *    ... this.wallDownHuh(int, int, int) ...                           -- boolean
     *    
     */
    
    /** Creates the Maze by randomly selecting a position on the 2D Array and
     * selectively knocking down walls between cells until all Cells have been
     * visited once.
     * Algorithm:
     * 1) Start at a random cell in the grid.  
     * 2) Look for a random neighbor cell you haven't been to yet.  
     * 3) If you find one, move there, knocking down the wall between the cells. 
     *    If you don't find one, back up to the previous cell.  
     * 4) Repeat steps 2 and 3 until you've been to every cell in the grid.*/
    public void createMaze(){
        Stack<Cell> toVisit = new Stack<Cell>();
        ArrayList<Posn> beenTo = new ArrayList<Posn>();
        
        int totalCell = labyrinth.length * labyrinth[0].length;
        
        // Random location for currentCell
        int x = (int) (Math.random() * labyrinth.length);
        int y = (int) (Math.random() * labyrinth[0].length);
        Cell currentCell = labyrinth[x][y];
        
        beenTo.add(new Posn(currentCell.x, currentCell.y));
        
        while(beenTo.size() < totalCell){
            
            ArrayList<Posn> neighbors = neighborsWithWalls(currentCell, beenTo);
            
            if(neighbors.size() > 0){
                
                int random = (int) (Math.random() * neighbors.size());
                Posn next = neighbors.get(random);
                this.knockDown(currentCell, next);
                
                toVisit.push(currentCell);
                
                currentCell = labyrinth[next.x][next.y]; 
                beenTo.add(new Posn(currentCell.x, currentCell.y));
            }
            else{
                currentCell = toVisit.pop();  
            }
        }
    }
    
    /**
     * Knocks down the wall between the current {@link Cell Cell} and next Cell. 
     * The next cell is found by a Posn.
     * @param currentCell The current cell.
     * @param next A Posn that represents the next Cell.
     */
    public void knockDown(Cell currentCell, Posn next){
        Cell nextCell = labyrinth[next.x][next.y];
        
        if(next.x > currentCell.x){
            currentCell.knockDownWall(EAST);
            nextCell.knockDownWall(WEST);
        }
        else if(next.x < currentCell.x){
            currentCell.knockDownWall(WEST);
            nextCell.knockDownWall(EAST);
        }
        else if(next.y > currentCell.y){
            currentCell.knockDownWall(SOUTH);
            nextCell.knockDownWall(NORTH);
        }
        else if(next.y < currentCell.y){
            currentCell.knockDownWall(NORTH);
            nextCell.knockDownWall(SOUTH);
        }
    }
    
    /**
     * Creates an ArrayList<Posn> of the neighbors that have yet to be visited.
     * @param current The current cell.
     * @param beenTo ArrayList<Posn> that represents the cells we have been to.
     * @return Returns a ArrayList<Posn> of the neighbors that have yet to be visited.
     */
    public ArrayList<Posn> neighborsWithWalls(Cell current, ArrayList<Posn> beenTo){
        ArrayList<Posn> neighbors = new ArrayList<Posn>();
        
        for(int i = 0; i < 4; i++){
            if(current.wallIntact(i) && this.nextCellGoodHuh(i, current.x, current.y)){
                Posn nextCello = this.nextCell(i, current.x, current.y);
                
                if(!this.hasPosn(beenTo, nextCello))
                    neighbors.add(nextCello); 
            }
        }
        
        return neighbors;
    }
    
    /**
     * Does the given ArrayList<Posn> have the given Posn?
     * @param beenTo ArrayList<Posn> that represent Cell we have already been to.
     * @param p Posn of the cell we may visit.
     * @return True if the given Posn is not in the given ArrayList<Posn>.
     */
    public boolean hasPosn(ArrayList<Posn> beenTo, Posn p){
        for(Posn b : beenTo){
            if(b.x == p.x && b.y == p.y)
                return true;
        }
        return false;
    }
    
    /**
     * Produces the posn of the next {@link Cell Cell}.
     * @param dir The direction of the Cell.
     * @param x The x coordinate of the current Cell.
     * @param y The y coordinate of the current Cell.
     * @return A Posn that of the next Cell.
     */
    public Posn nextCell(int dir, int x, int y){
        if(dir == NORTH) 
            return new Posn(x, y - 1);
        else if(dir == EAST){
            return new Posn(x + 1, y);
        }
        else if(dir == SOUTH){
            return new Posn(x, y + 1);
        }
        else
            return new Posn(x - 1, y);
        
    }
    
    /**
     * Determines if the next {@link Cell Cell} is a that is not out of bounds.
     * @param dir The direction of the next Cell.
     * @param x The x coordinate of the Cell.
     * @param y The y coordinate of the Cell.
     * @return True if the next Cell is in the Maze.
     */
    public boolean nextCellGoodHuh(int dir, int x, int y){
        if(dir == NORTH){
            return this.notoutOfBounds(x, y - 1);
        }
        else if(dir == EAST){
            return this.notoutOfBounds(x + 1, y);
        }
        else if(dir == SOUTH){
            return this.notoutOfBounds(x, y + 1);
        }
        else
            return this.notoutOfBounds(x - 1, y);
    }
    
    /** Determines if the given x and y is not out of bounds in the Maze.
     *  @param x The x coordinate of the Cell.
     *  @param y The y coordinate of the Cell.
     *  @return Returns true if the position based on x and y is not out of bounds.*/
    public boolean notoutOfBounds(int x, int y){
        return (x >= 0 && x < labyrinth.length && y >= 0 && y < labyrinth[0].length );  
    }
    
    /** Draws the maze onto the given Scene.
     * @param scn The scene maze will be draw on.
     * @return Returns the scene with maze draw on it.
     * */
    public Scene drawMaze(Scene scn){
        for (int i=0; i < labyrinth.length ; i++){
            for (int j=0; j < labyrinth[i].length ; j++){
              if(j % 2 == 0)
                scn = labyrinth[i][j].drawCell(scn, "steelblue");
              else
                scn = labyrinth[i][j].drawCell(scn, "royalblue");
            }  
        }
        
        return scn;
    }
    
    /** Determines if the wall is down for that direction in the {@link Cell Cell}
     * based on the given x and y.
     * @param dir The direction of the wall.
     * @param x The x coordinate of the Cell.
     * @param y The y coordinate of the Cell.
     * @return Returns true if the given wall is intact.
     */
    public boolean wallDownHuh(int dir, int x, int y){
        Cell current = labyrinth[x][y];
        
        return !current.wallIntact(dir);
    }
}
