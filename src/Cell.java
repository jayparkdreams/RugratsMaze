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

/** Represents a Cell in the {@link Maze Maze} */
public class Cell extends AGrid{
    /** Represents the walls that are intact. 
     *  If the element is true then the Wall is intact.
     *  If the element is false then the Wall is destroyed.
     *  0 = North, 1 = East, 2 = South, 3 = West*/
    public ArrayList<Boolean> walls = new ArrayList<Boolean>();
    
    /** Represents the North direction*/
    public static final int NORTH = 0;
    /** Represents the East direction*/
    public static final int EAST = 1;
    /** Represents the South direction*/
    public static final int SOUTH = 2;
    /** Represents the West direction*/
    public static final int WEST = 3;
    
    /**
     * The default constructor, all walls are initially intact.
     * @param x The x coordinate of the cell.
     * @param y The y coordinate of the cell.
     */
    public Cell(int x, int y){
        super(x,y);
        
        for(int i = 0; i < 4; i++){
            walls.add(true);
        }
    }
    
    /* Template:
     *  
     *  Fields  
     *    ... this.x ...                               -- int
     *    ... this.y ...                               -- int
     *    ... this.NORTH ...                           -- int
     *    ... this.EAST ...                            -- int
     *    ... this.SOUTH ...                           -- int
     *    ... this.WEST ...                            -- int
     *    ... this.walls ...                           -- ArrayList<Boolean> 
     *    
     *  Methods
     *    ... this.wallIntact(int) ...                 -- boolean
     *    ... this.knockDownWall(int) ...
     *    ... this.drawCell(Scene, String) ...         -- Scene
     */
    
    /**
     * Determines if the wall for the given position is intact
     * @param i The direction of the wall
     * @return Returns true if the wall is intact
     */
    public boolean wallIntact(int i){
        return walls.get(i);
    }
    
    /** Knocks down the wall.
     * @param i The direction of the wall. */
    public void knockDownWall(int i){
        walls.set(i, false);
    }
    
    /** Draws the Cell onto the given Scene with the given color.
     * @param scn The given scene.
     * @param color The given color.*/
    public Scene drawCell(Scene scn, String color){
        Posn cell = this.cellToPixel();
        
        for(int i = 0; i < walls.size(); i++){
            if(walls.get(i) && i == NORTH){
                scn = scn.addLine(new Posn(cell.x - this.CELL_SIZE/2, cell.y - this.CELL_SIZE/2), 
                            new Posn(cell.x + this.CELL_SIZE/2, cell.y - this.CELL_SIZE/2), color);
            }
            else if(walls.get(i) && i == EAST){
                scn = scn.addLine(new Posn(cell.x + this.CELL_SIZE/2, cell.y - this.CELL_SIZE/2), 
                            new Posn(cell.x + this.CELL_SIZE/2, cell.y + this.CELL_SIZE/2), color);
            }
            else if(walls.get(i) && i == SOUTH){
                scn = scn.addLine(new Posn(cell.x - this.CELL_SIZE/2, cell.y + this.CELL_SIZE/2), 
                            new Posn(cell.x + this.CELL_SIZE/2, cell.y + this.CELL_SIZE/2), color);
            }
            else if(walls.get(i) && i == WEST){
                scn = scn.addLine(new Posn(cell.x - this.CELL_SIZE/2, cell.y - this.CELL_SIZE/2), 
                            new Posn(cell.x - this.CELL_SIZE/2, cell.y + this.CELL_SIZE/2), color);
            }
        }
        
        return scn;   
    }
}
