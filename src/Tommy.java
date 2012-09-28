/*
   Assignment: Final Project
   Name: Thang Nguyen
   ID: 5965
   Name: Henry Duong
   ID: 2891  
   Name: Feng-Yun Lin
   ID: 2265
 */

import image.FromFile;
import image.Image;
import image.Scene;

/** A class that represents Tommy, the main character 
 * of the game.
 */
public class Tommy extends AGrid{
    
    /** An <code>Image</code> to represent the image of Tommy
     * when he is moving left. */
    public Image left = new FromFile("l.gif");
    /** An <code>Image</code> to represent the image of Tommy
     * when he is moving right. */
    public Image right = new FromFile("r.gif");
    /** Boolean that represents if Tommy going left.*/
    public boolean goingLeftHuh;
    
    /** The constructor that only takes in the x and y coordinates
     * of Tommy.*/ 
    public Tommy(int x, int y){
        super(x,y);
        goingLeftHuh = true;
    }
    
    /* Template:
     *  
     *  Fields  
     *    ... this.x ...                               -- int
     *    ... this.y ...                               -- int
     *    ... this.CELL_SIZE ...                       -- int
     *    ... this.WIDTH_CELLS ...                     -- int
     *    ... this.HEIGHT_CELLS ...                    -- int
     *    ... this.WIDTH ...                           -- int
     *    ... this.HEIGHT ...                          -- int
     *    ... this.left ...                            -- Image
     *    ... this.right ...                           -- Image
     *    ... this.goingLeftHuh ...                    -- boolean
     *    
     *  Methods
     *    ... this.cellToPixel() ...                   -- Posn
     *    ... this.sameLocation(Posn) ...              -- boolean
     *    ... this.move(String) ...                    -- void
     *    ... this.drawTommy(Scene) ...                -- Scene
     */
   
    /** Moves Tommy with the given direction. Image changes
     * if direction equals left or right.
     * @param dir The direction where Tommy will move.
     */
    public void move(String dir){
        if(dir.equals("up"))
            this.y--;
        else if(dir.equals("down"))
            this.y++;
        else if(dir.equals("left")){
            this.goingLeftHuh = true;
            this.x--;
        }    
        else if(dir.equals("right")){
            this.goingLeftHuh = false;
            this.x++;
        }    
    }
    
    /** Draws the image of Tommy onto the given scene.
     * @param scn The base scene{@link Scene Scene}
     * @return scn Scene with the image left or right, 
     * depending on the current direction of Tommy. {@link Scene Scene}
     */
    public Scene drawTommy(Scene scn){
       if(this.goingLeftHuh)
           return scn.placeImage(left, cellToPixel());
       else
           return scn.placeImage(right, cellToPixel());
    }
}
