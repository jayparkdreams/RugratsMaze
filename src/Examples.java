/*
   Assignment: Final Project
   Name: Thang Nguyen
   ID: 5965
   Name: Henry Duong
   ID: 2891  
   Name: Feng-Yun Lin
   ID: 2265
 */

import tester.*;
import world.*;
import java.util.*;
import world.sound.tunes.Note;

/** Represents Examples */
public class Examples {
    Maze maze;
    Maze maze1;
    Maze maze2;
    
    Cell cell;
    Cell cell1;

    Posn p;
    Posn p1;
    Posn p2;
    Posn p3;
    Posn p4;
    
    ArrayList<Posn> beenTo;
    ArrayList<Posn> beenTo1;
    ArrayList<Posn> beenTo2;
    ArrayList<Posn> beenTo3;
    
    Tommy tommy;
    Tommy tommy1;
    
    Reptar reptar;
    Reptar reptar1;
    
    ScrewDriver screwDriver;
    ScrewDriver find;
    
    MazeWorld mw;
    MazeWorld mw1;
    MazeWorld mw2;
    
    public void reset(){
        maze = new Maze(5,5);
        maze1 = new Maze(10,10);
        maze2 = new Maze(1,1);
        
        cell = maze.labyrinth[0][0];
        cell1 = maze1.labyrinth[5][5];
        
        p = new Posn(0,0);
        p1 = new Posn(0,1);
        p2 = new Posn(6,5);
        p3 = new Posn(4,5);
        p4 = new Posn(5,4);
        
        beenTo = new ArrayList<Posn>();
        beenTo1 = new ArrayList<Posn>(Arrays.asList(p,p1));
        beenTo2 = new ArrayList<Posn>(Arrays.asList(p2,p3,p4));
        beenTo3 = new ArrayList<Posn>(Arrays.asList(p4,p2,new Posn(5,6),p3));
        
        tommy = new Tommy(0,0);
        tommy1 = new Tommy(5,5);
        
        reptar = new Reptar(10,10);
        reptar1 = new Reptar(0,0);
        screwDriver = new ScrewDriver(1,7);
        find = new ScrewDriver(5,5);
        
        mw = new MazeWorld();
        mw1 = new MazeWorld(10,10,200,tommy1, find, reptar);
        mw2 = new MazeWorld(5,5, 200, tommy, screwDriver, reptar1);
    }
    
    /**
     * Tests for:
     * the method knockDown in the Maze class
     * the method wallDownHuh in the Maze class
     * the method wallIntact in the Cell class
     */
    public void testknockDownANDwallDownHuhANDwallIntact(Tester t){
        reset();
        
        maze.knockDown(cell, p1);
        t.checkExpect(cell.wallIntact(2), false);
        Cell cell3 = maze.labyrinth[p1.x][p1.y];
        t.checkExpect(cell3.wallIntact(0), false);
        t.checkExpect(maze.wallDownHuh(2, 0, 0), true);
        t.checkExpect(maze.wallDownHuh(3, 0, 0), false);
        
        maze1.knockDown(cell1, p2);
        t.checkExpect(cell1.wallIntact(1), false);
        Cell cell4 = maze1.labyrinth[p2.x][p2.y];
        t.checkExpect(cell4.wallIntact(3), false);
        t.checkExpect(maze1.wallDownHuh(3, 5, 5), false);
        t.checkExpect(maze1.wallDownHuh(1, 6, 5), false);
        
        maze1.knockDown(cell1, p3);
        t.checkExpect(cell1.wallIntact(3), false);
        Cell cell5 = maze1.labyrinth[p3.x][p3.y];
        t.checkExpect(cell5.wallIntact(1), false);
        t.checkExpect(maze1.wallDownHuh(1, 4, 5), true);
        t.checkExpect(maze1.wallDownHuh(3, 5, 5), true);
        
        maze1.knockDown(cell1, p4);
        t.checkExpect(cell1.wallIntact(0), false);
        Cell cell6 = maze1.labyrinth[p4.x][p4.y];
        t.checkExpect(cell6.wallIntact(2), false);
        t.checkExpect(maze1.wallDownHuh(0, 5, 4), false);
        t.checkExpect(maze1.wallDownHuh(2, 5, 5), false);
        t.checkExpect(maze1.wallDownHuh(2, 5, 4), true);
        t.checkExpect(maze1.wallDownHuh(0, 5, 5), true);
    }
    
    /**
     * Tests for:
     * the method neighborsWithWalls in the Maze class
     */
    public void testneighborsWithWalls(Tester t){
        reset();
        
        t.checkExpect(maze.neighborsWithWalls(cell, beenTo),
                new ArrayList<Posn>(Arrays.asList(new Posn(1,0), new Posn(0,1))));
        t.checkExpect(maze.neighborsWithWalls(cell, beenTo1),
                new ArrayList<Posn>(Arrays.asList(new Posn(1,0))));
        t.checkExpect(maze1.neighborsWithWalls(cell1, beenTo3), beenTo);
        t.checkExpect(maze1.neighborsWithWalls(cell1, beenTo2), 
                new ArrayList<Posn>(Arrays.asList(new Posn(5,6))));
        t.checkExpect(maze1.neighborsWithWalls(cell1, beenTo), beenTo3);
    }
    
    /** 
     * Checks if at least one wall is knocked down in each cell
     * If the R and C is = 1 then it returns true because a maze
     * with only one cell would not have any walls knocked down.
     */
    public boolean createMazetestHelper(Cell[][] m){
        if(m.length == 1 & m[0].length == 1)
            return true;
        
        for(int i = 0; i < m.length; i++)
            for(int j = 0; j < m[0].length; j++){
                for(int k = 0; k < 4; k++){
                    if(!m[i][j].wallIntact(k))
                        break;
                    else if(k == 3)
                        return false;
                }
            }
        return true;
    }
     
    /**
     * Tests for:
     * the method createMaze in the Maze class
     * 
     * NOTE: Because createMaze generates a random 'perfect' maze
     * by starting at a random position in the labyrinth we had to create
     * an unorthodox way to test whether createMaze works by creating
     * a helper function in the examples class.
     * 
     * At least one wall must be knocked down in each cell in order
     * for the maze to be 'perfect' if one wall is not knocked down
     * then the maze is not perfect. But there are exceptions like 
     * R and C is equal to 1 (one cell maze). No walls would be
     * needed to knock down.
     */
    public void testcreateMaze(Tester t){
        reset();
        
        maze.createMaze();
        t.checkExpect(createMazetestHelper(maze.labyrinth), true);
        t.checkExpect(createMazetestHelper(maze1.labyrinth), false);
        maze1.createMaze();
        t.checkExpect(createMazetestHelper(maze1.labyrinth), true);
        t.checkExpect(createMazetestHelper(maze2.labyrinth), true);
    }
    
    /**
     * Tests for:
     * the method nextCell in the Maze class
     */
    public void testnextCell(Tester t){
        reset();
        
        t.checkExpect(maze1.nextCell(0, 5, 5), new Posn(5,4));
        t.checkExpect(maze.nextCell(1, 5, 5), new Posn(6,5));
        t.checkExpect(maze.nextCell(2, 5, 5), new Posn(5,6));
        t.checkExpect(maze.nextCell(3, 5, 5), new Posn(4,5));
    }
    
    /**
     * Tests for:
     * the method nextCellGoodHuh in the Maze class
     */
    public void testnextCellGoodHuh(Tester t){
        reset();
        
        t.checkExpect(maze1.nextCellGoodHuh(0, 5, 5), true);
        t.checkExpect(maze1.nextCellGoodHuh(1, 5, 5), true);
        t.checkExpect(maze1.nextCellGoodHuh(2, 5, 5), true);
        t.checkExpect(maze1.nextCellGoodHuh(3, 5, 5), true);
        t.checkExpect(maze.nextCellGoodHuh(0, 0, 0), false);
        t.checkExpect(maze.nextCellGoodHuh(2, 5, 5), false);
        t.checkExpect(maze.nextCellGoodHuh(3, 7, 7), false);
    }
    
    /**
     * Tests for:
     * the method hasPosn in the Maze class
     */
    public void testhasPosn(Tester t){
        reset();
        
        t.checkExpect(maze1.hasPosn(beenTo, p), false);
        t.checkExpect(maze1.hasPosn(beenTo3, new Posn(0,0)), false);
        t.checkExpect(maze.hasPosn(beenTo1, p1), true);
        t.checkExpect(maze.hasPosn(beenTo3, p4), true);
    }
    
    /**
     * Tests for:
     * the method notoutOfBounds in the Maze class
     */
    public void testnotoutOfBounds(Tester t){
        reset();
        
        t.checkExpect(maze1.notoutOfBounds(0, 0), true);
        t.checkExpect(maze1.notoutOfBounds(7, 7), true);
        t.checkExpect(maze1.notoutOfBounds(11, 7), false);
        t.checkExpect(maze1.notoutOfBounds(7, 11), false);
        t.checkExpect(maze.notoutOfBounds(7, 7), false);
        t.checkExpect(maze.notoutOfBounds(5, 6), false);
        t.checkExpect(maze.notoutOfBounds(6, 5), false);
    }
    
    /**
     * Tests for:
     * the method knockDownWall in the Cell class
     */
    public void testknockDownWall(Tester t){
        reset();
        
        cell.knockDownWall(0);
        t.checkExpect(cell.wallIntact(0), false);
        cell.knockDownWall(1);
        t.checkExpect(cell.wallIntact(1), false);
        cell.knockDownWall(2);
        t.checkExpect(cell.wallIntact(2), false);
        cell.knockDownWall(3);
        t.checkExpect(cell.wallIntact(3), false);
    }
    
    /**
     * Tests for:
     * the method move in the AGrid class
     * the method move in the Tommy class
     * the method move in the Reptar class
     * the method move in the ScrewDriver class
     */
    public void testMove(Tester t){
        reset();
        
        tommy.move("up");
        t.checkExpect(tommy.y, -1);
        t.checkExpect(tommy.x, 0);
        tommy.move("down");
        t.checkExpect(tommy.y, 0);
        t.checkExpect(tommy.x, 0);
        tommy.move("left");
        t.checkExpect(tommy.y, 0);
        t.checkExpect(tommy.x, -1);
        t.checkExpect(tommy.goingLeftHuh, true);
        tommy.move("right");
        t.checkExpect(tommy.y, 0);
        t.checkExpect(tommy.x, 0);
        t.checkExpect(tommy.goingLeftHuh, false);
        
        tommy1.move("up");
        t.checkExpect(tommy1.y, 4);
        t.checkExpect(tommy1.x, 5);
        tommy1.move("down");
        t.checkExpect(tommy1.y, 5);
        t.checkExpect(tommy1.x, 5);
        tommy1.move("left");
        t.checkExpect(tommy1.y, 5);
        t.checkExpect(tommy1.x, 4);
        t.checkExpect(tommy1.goingLeftHuh, true);
        tommy1.move("right");
        t.checkExpect(tommy1.y, 5);
        t.checkExpect(tommy1.x, 5);
        t.checkExpect(tommy1.goingLeftHuh, false);
        
        reptar.move("up");
        t.checkExpect(reptar.y, 9);
        t.checkExpect(reptar.x, 10);
        reptar.move("down");
        t.checkExpect(reptar.y, 10);
        t.checkExpect(reptar.x, 10);
        reptar.move("left");
        t.checkExpect(reptar.y, 10);
        t.checkExpect(reptar.x, 9);
        reptar.move("right");
        t.checkExpect(reptar.y, 10);
        t.checkExpect(reptar.x, 10);
        
        screwDriver.move("up");
        t.checkExpect(screwDriver.y, 6);
        t.checkExpect(screwDriver.x, 1);
        screwDriver.move("down");
        t.checkExpect(screwDriver.y, 7);
        t.checkExpect(screwDriver.x, 1);
        screwDriver.move("left");
        t.checkExpect(screwDriver.y, 7);
        t.checkExpect(screwDriver.x, 0);
        screwDriver.move("right");
        t.checkExpect(screwDriver.y, 7);
        t.checkExpect(screwDriver.x, 1);
    }
    
    /**
     * Tests for:
     * the method cellToPixel in the AGrid class
     * the method cellToPixel in the Tommy class
     * the method cellToPixel in the Reptar class
     * the method cellToPixel in the ScrewDriver class
     */
    public void testcellToPixel(Tester t){
        reset();
        
        t.checkExpect(tommy.cellToPixel(), new Posn(25,25));
        t.checkExpect(tommy1.cellToPixel(), new Posn(275,275));
        t.checkExpect(reptar.cellToPixel(), new Posn(525,525));
        t.checkExpect(screwDriver.cellToPixel(), new Posn(75,375));
    }
    
    /**
     * Tests for:
     * the method sameLocation in the AGrid class
     * the method sameLocation in the Tommy class
     * the method sameLocation in the Reptar class
     * the method sameLocation in the ScrewDriver class
     */
    public void testsameLocation(Tester t){
        reset();
        
        t.checkExpect(tommy.sameLocation(new Posn(25,25)), false);
        t.checkExpect(tommy.sameLocation(new Posn(0,0)), true);
        t.checkExpect(tommy1.sameLocation(new Posn(25,25)), false);
        t.checkExpect(tommy1.sameLocation(new Posn(5,5)), true);
        t.checkExpect(reptar.sameLocation(new Posn(10,10)), true);
        t.checkExpect(reptar.sameLocation(new Posn(0,10)), false);
        t.checkExpect(screwDriver.sameLocation(new Posn(1,7)), true);
        t.checkExpect(screwDriver.sameLocation(new Posn(11,7)), false);
    }
    
    /**
     * Tests for:
     * the method onKey in the MazeWorld class
     */
    public void testonKey(Tester t){
        reset();
        
        mw.onKey("up");
        t.checkExpect(mw.tommy.x, 0);
        t.checkExpect(mw.tommy.y, 0);
        mw.onKey("j");
        t.checkExpect(mw.tommy.x, 0);
        t.checkExpect(mw.tommy.y, 0);
        mw.onKey("left");
        t.checkExpect(mw.tommy.x, 0);
        t.checkExpect(mw.tommy.y, 0);
        mw.onKey("down");
        t.checkExpect(mw.tommy.x, 0);
        t.checkOneOf("testing tommy movement", mw.tommy.y, 0, 1);
        reset();
        mw.onKey("right");
        t.checkOneOf("testing tommy movement", mw.tommy.x, 0, 1);
        t.checkExpect(mw.tommy.y, 0);
        
        mw1.onKey("j");
        t.checkExpect(mw1.tommy.x, 5);
        t.checkExpect(mw1.tommy.y, 5);
        mw1.onKey("up");
        t.checkOneOf("testing tommy movement", mw1.tommy.x, 5, 5);
        t.checkOneOf("testing tommy movement", mw1.tommy.y, 5, 4);
        reset();
        mw1.onKey("left");
        t.checkOneOf("testing tommy movement", mw1.tommy.x, 5, 4);
        t.checkOneOf("testing tommy movement", mw1.tommy.y, 5, 5);
        reset();
        mw1.onKey("down");
        t.checkOneOf("testing tommy movement", mw1.tommy.x, 5, 5);
        t.checkOneOf("testing tommy movement", mw1.tommy.y, 5, 6);
        reset();
        mw1.onKey("right");
        t.checkOneOf("testing tommy movement", mw1.tommy.x, 6, 5);
        t.checkOneOf("testing tommy movement", mw1.tommy.y, 5, 5);
    }
    
    /**
     * Tests for:
     * the method onTick in the MazeWorld class
     */
    public void testonTick(Tester t){
        reset();
        
        mw.onTick();
        mw.start = true;
        t.checkExpect(mw.foundKey, false);
        t.checkExpect(mw.time, 400); 
        mw1.onTick();
        mw1.start = true;
        t.checkExpect(mw1.foundKey, true); 
        t.checkExpect(mw1.time, 200); 
        mw2.onTick();
        mw2.start = true;
        t.checkExpect(mw2.foundKey, false); 
        t.checkExpect(mw2.time, 200); 
    }
    
    /**
     * Tests for:
     * the method stopWhen in the MazeWorld class
     */
    public void teststopWhen(Tester t){
        reset();
        
        t.checkExpect(mw.stopWhen(), false);
        mw2.foundKey = true;
        t.checkExpect(mw2.stopWhen(), true);
        t.checkExpect(mw1.stopWhen(), false);
        mw1.time = 0;
        t.checkExpect(mw1.stopWhen(), true);
    }
    
    /**
     * Tests for:
     * the method reset in the MazeWorld class
     */
    public void testReset(Tester t){
        reset();
        
        Iterator<Note> n = mw.theme.iterator();
        mw.reset();
        t.checkExpect(mw.notes, n);
        mw1.reset();
        t.checkExpect(mw1.notes, n);
        mw2.playNextNote();
        mw2.reset();
        t.checkExpect(mw2.notes, n);
    }
    
    /**
     * Tests for:
     * the method playNextNote in the MazeWorld class
     */
    public void testplayNextNote(Tester t){
        reset();
        
        Iterator<Note> n = mw.theme.iterator();
        mw.playNextNote();
        n.next();
        t.checkExpect(mw.notes, n);
        mw.playNextNote();
        n.next();
        t.checkExpect(mw.notes, n);
    }
}
