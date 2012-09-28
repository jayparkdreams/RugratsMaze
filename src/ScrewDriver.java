import image.FromFile;
import image.Image;
import image.Scene;
/** A class that represents a ScrewDriver, the item in the game
 * that {@link Tommy Tommy} has to pick up during the course of 
 * the game.  
 */
public class ScrewDriver extends AGrid{
    
    /** An <code>Image</code> to represent the image of ScrewDriver*/
    public Image screwDriver = new FromFile("screwdriver.gif");
    
    /** The constructor that only takes in the x and y coordinates
     * of the ScrewDriver.*/
    public ScrewDriver(int x, int y){
        super(x, y);
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
     *    ... this.screwDriver ...                     -- Image
     *    
     *  Methods
     *    ... this.cellToPixel() ...                   -- Posn
     *    ... this.sameLocation(Posn) ...              -- boolean
     *    ... this.move(String) ...                    -- void
     *    ... this.drawScrewDriver(Scene) ...          -- Scene
     */
    
    /** Draws the image of ScrewDriver onto the given scene.
     * @param scn The base scene{@link Scene Scene}
     * @return Scene Scene with screwDriver {@link Scene Scene}
     */
    public Scene drawScrewDriver(Scene scn){
        return scn.placeImage(screwDriver, cellToPixel());
    }
    
}
