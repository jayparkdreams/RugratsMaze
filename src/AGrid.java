/*
   Assignment: Final Project
   Name: Thang Nguyen
   ID: 5965
   Name: Henry Duong
   ID: 2891  
   Name: Feng-Yun Lin
   ID: 2265
 */

import world.Posn;

/** A class designed to present the scene as a grid
 * and a point on the grid and the variables that are involved with 
 * drawing a cell 
 */
public abstract class AGrid extends Posn{
    /** Constant to represent the size of each cell*/
    public int CELL_SIZE = 50;
    /** Constant to represent the width of each cell*/
    public int WIDTH_CELLS = 20;
    /** Constant to represent the height of each cell*/
    public int HEIGHT_CELLS = 9;
    
    /** Constant to represent the width of the scene*/
    public int WIDTH = this.WIDTH_CELLS * this.CELL_SIZE;
    /** Constant to represent the height of the scene*/
    public int HEIGHT = this.HEIGHT_CELLS * this.CELL_SIZE;
    
    /** Constructor that only takes in the x and y co-ordinate
     * which represents positions on this grid. 
     */
    public AGrid(int x, int y){
        super(x,y);
    }
    
    /* Template
     *   Fields:
     *     ... this.x ...                               -- int
     *     ... this.y ...                               -- int
     *     ... this.CELL_SIZE ...                       -- int
     *     ... this.WIDTH_CELLS ...                     -- int
     *     ... this.HEIGHT_CELLS ...                    -- int
     *     ... this.WIDTH ...                           -- int
     *     ... this.HEIGHT ...                          -- int
     *     
     *   Methods:
     *     ... this.cellToPixel() ...                   -- Posn
     *     ... this.sameLocation(Posn) ...              -- boolean
     *     ... this.move() ...                          -- void
     *     
     */    
    
    /** Calculate the pixel-coordinates of this cell*/
    public Posn cellToPixel(){
        return new Posn(this.CELL_SIZE * this.x + this.CELL_SIZE/2,
                        this.CELL_SIZE * this.y + this.CELL_SIZE/2);
    }
    
    /** Determines whether this GridPoint is the same as that GridPoint.
     * @param Posn A given cell location on the grid 
     * @return True if this location is the same as the given location.
     */
    public boolean sameLocation(Posn that){
        return (this.x == that.x) && (this.y == that.y);
    }
    
    /** Moves the Posn depending on the String dir.
     * If dir equals "up" y decreases by 1.
     * If dir equals "down" y increases by 1.
     * If dir equals "left" x decreases by 1.
     * If dir equals "right" x increases by 1.
     * @param dir Direction the posn will be moved by.
     */
    public void move(String dir){
        if(dir.equals("up"))
            this.y--;
        else if(dir.equals("down"))
            this.y++;
        else if(dir.equals("left")){
            this.x--;
        }    
        else if(dir.equals("right")){
            this.x++;
        }    
    }
    
}