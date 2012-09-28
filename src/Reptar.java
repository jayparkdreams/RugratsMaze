import image.FromFile;
import image.Image;
import image.Scene;
/** A class that represents Reptar, the dinosaur 
 * that {@link Tommy Tommy} has to reach in time in order to win the 
 * game. 
 */
public class Reptar extends AGrid{
    
    /** An <code>Image</code> to represent the image of Reptar*/
    public Image reptar = new FromFile("reptar.gif");
    
    /** The constructor that only takes in the x and y coordinates
     * of Reptar.*/
    public Reptar(int x, int y){
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
     *    ... this.reptar ...                          -- Image
     *    
     *  Methods
     *    ... this.cellToPixel() ...                   -- Posn
     *    ... this.sameLocation(Posn) ...              -- boolean
     *    ... this.move(String) ...                    -- void
     *    ... this.drawReptar(Scene) ...               -- Scene
     */
    
    /** Draws the image of Reptar onto the given scene.
     * @param scn The base scene{@link Scene Scene}
     * @return Scene Scene with reptar {@link Scene Scene}
     */
    public Scene drawReptar(Scene scn){
        return scn.placeImage(reptar, cellToPixel());
    }

}
