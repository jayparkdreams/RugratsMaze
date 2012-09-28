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
import image.*;
import world.sound.SoundWorld;
import world.sound.tunes.Chord;
import world.sound.tunes.Note;

/** A class that represents the world of the maze.*/
public class MazeWorld extends SoundWorld{
    /** Represents the maze in this game*/
    public Maze maze;
    /** Represents {@link Tommy Tommy}.*/
    public Tommy tommy;
    /** Represents {@link Reptar Reptar}.*/
    public Reptar reptar;
    /** Represents {@link ScrewDriver ScrewDriver}.*/
    public ScrewDriver screwDriver;
    /** Represents the amount of time left in the game. */
    public int time;
    /** Determines whether or not the player has reached
     * the screwDriver. It is initialized to false. 
     */
    public boolean foundKey = false;
    
    /** Determines whether the game has started yet or not*/
    public boolean start = false;
    /** Image of the start scene */
    public Image startImg = new FromFile("start.jpg");
    /** Image of the winner scene */
    public Image winnerImg = new FromFile("winner.jpg");
    /** Image of the loser scene */
    public Image loserImg = new FromFile("loser.jpg");
    
    
    /** Represents the North direction*/
    public static final int NORTH = 0;
    /** Represents the East direction*/
    public static final int EAST = 1;
    /** Represents the South direction*/
    public static final int SOUTH = 2;
    /** Represents the West direction*/
    public static final int WEST = 3;
    
    public Iterator<Note> notes;
    /**  Uses the Chord constructor to generate a
     *    list of notes from a list of integers.
     *    Uses {@link world.sound.tunes.SoundConstants SoundConstants}
     *    interface. */
    public ArrayList<Note> theme = new Chord(
            0,0,
            noteE,0,0,
            noteF,0,0,
            noteG,0,0,
            noteA,0,0,0,0, 
            noteB,0,0,0,0,
            
            noteUpC, 0,0,0,0,
            noteB, 0,0,0,
            noteG, 0,0,0,
            noteA,0,0,0,0,
            noteG, 0,0,
            noteF,0,0,
            
            0,0,0,0,
            noteE, 0,0,
            noteF,0,0,
            noteG,0,0,
            noteA,0,0,0,0,
            noteB,0,0,0,0,
            
            noteUpC,0,0,0,0,
            noteUpD,0,0,0,
            noteB,0,0,
            noteUpC,0,0,0,0,
            noteB,0,0,
            noteA,0,0,
            
            0,0,0,0

            // Grab the notes ArrayList from the constructed Chord
   
            ).notes;
    
    /** The constructor of MazeWorld. */
    public MazeWorld(){
        this.maze = new Maze(20,9);
        this.maze.createMaze();
        
        this.tommy = new Tommy(0,0);
        
        this.reset();
        this.time = 400;
        
        reptar = new Reptar(maze.labyrinth.length-1, maze.labyrinth[0].length-1);
        
        // Random location for key
        int x = (int) (Math.random() * maze.labyrinth.length - 1);
        int y = (int) (Math.random() * maze.labyrinth[0].length - 1);
        screwDriver = new ScrewDriver(x, y);
    }
    
    public MazeWorld(int x, int y, int time, Tommy t, ScrewDriver sd, Reptar r){
        this.maze = new Maze(x,y);
        this.maze.createMaze();
        
        this.reset();
        this.time = time;
        
        this.tommy = t;
        this.reptar = r;
        screwDriver = sd;
    }
    
    /* Template:
     *  
     *  Fields                                                             
     *    ... this.maze ...                                                 -- Maze
     *    ... this.tommy ...                                                -- Tommy
     *    ... this.reptar ...                                               -- Reptar
     *    ... this.screwDriver ...                                          -- ScrewDriver
     *    ... this.foundKey ...                                             -- boolean
     *    ... this.start ...                                                -- boolean
     *    ... this.startImg ...                                             -- Image
     *    ... this.winImg ...                                             -- Image
     *    ... this.loseImg ...                                             -- Image
     *    ... this.NORTH ...                                                -- int
     *    ... this.EAST ...                                                 -- int
     *    ... this.SOUTH ...                                                -- int
     *    ... this.WEST ...                                                 -- int
     *    ... this.notes ...                                                -- Iterator<Note>
     *    ... this.theme ...                                                -- ArrayList<Note>
     *  Methods
     *    ... this.onKey(String) ...                                        -- void
     *    ... this.tickRate() ...                                           -- double
     *    ... this.playNextNote() ...                                       -- void
     *    ... this.reset() ...                                              -- void
     *    ... this.drawTime(Scene) ...                                      -- Scene
     *    ... this.drawBackground(Scene) ...                                -- Scene
     *    ... this.drawReptar(Scene) ...                                    -- Scene
     *    ... this.onDraw() ...                                             -- Scene
     *    ... this.stopWhen() ...                                           -- boolean
     *    ... this.lastScene() ...                                          -- Scene
     *    ... this.loseScene() ...                                          -- Scene
     *    ... this.winScene() ...                                           -- Scene
     *  
     *  Methods of Fields  
     *    ... this.createMaze() ...                                         -- void
     *    ... this.knockDown(Cell, Posn) ...                                -- void
     *    ... this.neighborsWithWallsHuh(Cell, ArrayList<Posn>) ...         -- ArrayList<Posn>
     *    ... this.hasPosn(ArrayList<Posn>, Posn) ...                       -- boolean
     *    ... this.nextCell(int, int, int) ...                              -- Posn
     *    ... this.nextCellGoodHuh(int, int, int) ...                       -- boolean
     *    ... this.notoutOfBounds(int, int) ...                             -- boolean
     *    ... this.drawMaze(Scene) ...                                      -- Scene
     *    ... this.wallDownHuh(int, int, int) ...                           -- boolean
     *    ... this.tommy.cellToPixel() ...                                  -- Posn
     *    ... this.tommy.sameLocation(Posn) ...                             -- boolean
     *    ... this.tommy.move(String) ...                                   -- void
     *    ... this.tommy.drawTommy(Scene) ...                               -- Scene
     *    ... this.reptar.cellToPixel() ...                                 -- Posn
     *    ... this.reptar.sameLocation(Posn) ...                            -- boolean
     *    ... this.reptar.move(String) ...                                  -- void
     *    ... this.reptar.drawReptar(Scene) ...                             -- Scene
     *    ... this.screwDriver.cellToPixel() ...                            -- Posn
     *    ... this.screwDriver.sameLocation(Posn) ...                       -- boolean
     *    ... this.screwDriver.move(String) ...                             -- void
     *    ... this.screwDriver.drawScrewDriver(Scene) ...                   -- Scene
     */
    
    /**Moves the player by the given key
     * @see world.sound.SoundWorld#onKey(java.lang.String)
     */
    public void onKey(String ke){
        // Default -1 for no given direction
        int dir = -1;
        if(ke.equals(" "))
            start = true;
        else if(ke.equals("up"))
            dir = NORTH;  
        else if(ke.equals("right"))
            dir = EAST;
        else if(ke.equals("down")){
            dir = SOUTH;
        }
        else if(ke.equals("left")){
            dir = WEST;
        }
        
        // If dir = -1 means key event was not Up, Down, Left or Right!
        if(dir != -1 && maze.wallDownHuh(dir, tommy.x, tommy.y)){
            tommy.move(ke);
            this.keyTunes.addNote(WOOD_BLOCK, new Note(noteDownC, 1));
        }  
    }          
    
    /*
     * @see world.sound.SoundWorld#tickRate()
     */
    public double tickRate(){ return .09; }
    
    /*
     * @see world.sound.SoundWorld#onTick()
     * Loop the notes when it is completed else play the next note. 
     * Check if the player has reached the screwDriver
     * Decrement the time by one. 
     */
    
    /**
     * Make a next tune method that generates the next note.
     * Checks if the key has been found.
     */
    public void onTick(){
        
        playNextNote();
        
        if(this.tommy.sameLocation(screwDriver)){
            foundKey = true;
            this.tickTunes.addNote(BIRD_TWEET, this.notes.next());
        }
        
        if(start)
            time--;
    }
    
    /** Plays the next note on the theme */
    public void playNextNote(){
        if(!this.notes.hasNext())
            this.reset();
        this.tickTunes.addNote(PIANO, this.notes.next());
    }
    
    /** Reset the pointer in the iterator to the beginning of the Notes */
    public void reset(){
        this.notes = this.theme.iterator();
    }
   
    
    /** Draws the time on the given Scene */
    public Scene drawTime(Scene scn){
        return scn.placeImage(new Text("Time left: " + time, 20, "darkblue"), tommy.WIDTH/2, tommy.HEIGHT + 30);
    }
    
    
    /** Draw the background of scene*/
    public Scene drawBackground(){
        return new EmptyScene(tommy.WIDTH, tommy.HEIGHT + 50);
    }
    
    /** Draw Reptar (ie. the screwDriver) only when the key (the screwDriver)
     * has been found. 
     * @param scn The base scene
     * @return Draws the scene with the reptar only if the key has been collected. 
     */
    public Scene drawReptar(Scene scn){
        if(foundKey)
            return reptar.drawReptar(scn);
        else
            return screwDriver.drawScrewDriver(scn); 
    }
    
    
    /** Draw the images for the game
     * @see world.sound.SoundWorld#onDraw()
     */
    public Scene onDraw(){
        if(!start)
            return drawBackground().placeImage(startImg, tommy.WIDTH /2, (tommy.HEIGHT + 50) / 2 );
        else
            return drawReptar(tommy.drawTommy(drawTime(maze.drawMaze(drawBackground()))));
    }
    
    /**
     * @see world.sound.SoundWorld#stopWhen()
     */
    public boolean stopWhen(){
        return (time == 0) || (tommy.sameLocation(reptar) && foundKey);
    }
    
    /**
     * @see world.sound.SoundWorld#lastScene()
     */
    public Scene lastScene(){
        if(time == 0)
            return this.loseScene();
        else
            return this.winScene();
    }
    
    /** Draws the end scene of the game */
    public Scene loseScene(){
        return new EmptyScene(tommy.WIDTH,tommy.HEIGHT + 50).placeImage
        (loserImg, tommy.WIDTH/2,(tommy.HEIGHT + 50) / 2 );
    }
    
    /** Draws the winning scene of the game */
    public Scene winScene(){
        return new EmptyScene(tommy.WIDTH,tommy.HEIGHT + 50).placeImage
        (winnerImg.overlay(new Text(""+(750 - time), 65, "black")), tommy.WIDTH/2, (tommy.HEIGHT + 50) / 2 );
    }
    
    /** Start the world */
    public static void main(String[] args){
        MazeWorld start = new MazeWorld();
        start.bigBang();
    }

}
