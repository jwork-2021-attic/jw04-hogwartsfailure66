package com.anish.calabashbros;

import com.anish.maze.MazeGenerator;

public class World {

    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;

    private Tile<Thing>[][] tiles;

    private int[][] maze;

    public World() {

        MazeGenerator mazeGenerator = new MazeGenerator(30);    //0墙1路
        mazeGenerator.generateMaze();
        maze = mazeGenerator.getMaze();
//        System.out.println("RAW MAZE\n" + mazeGenerator.getRawMaze());
//        System.out.println("SYMBOLIC MAZE\n" + mazeGenerator.getSymbolicMaze());

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                tiles[i][j] = new Tile<>(i, j);
                if (maze[i][j] == 1) {
                    tiles[i][j].setThing(new Floor(this));
                } else {
                    tiles[i][j].setThing(new Wall(this));
                }
            }
        }
        tiles[29][29].setThing(new Destination(this));
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

    public int[][] getMaze() {
        return maze;
    }

    public Tile<Thing>[][] getTiles() {
        return tiles;
    }
}
