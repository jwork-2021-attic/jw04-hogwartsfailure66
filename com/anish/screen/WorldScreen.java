package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.*;

import asciiPanel.AsciiPanel;
import com.anish.maze.Node;

import java.util.ArrayList;
import java.util.Random;

public class WorldScreen implements Screen {

    private World world;
    private int[][] maze;
    private Calabash character;

    public WorldScreen() {
        world = new World();
        maze = world.getMaze();
        character = new Calabash(new Color(255, 255, 255), 0, world);
        world.put(character, 0, 0);
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[][] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Calabash getBroByRank(Calabash[][] bros, int rank) {
        for (Calabash[] broline : bros) {
            for (Calabash bro : broline) {
                if (bro.getRank() == rank) {
                    return bro;
                }
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        int x = character.getX();
        int y = character.getY();
        int[][] maze = world.getMaze();
        ArrayList<Node> path = new ArrayList<Node>();
        if (key.getKeyCode() == KeyEvent.VK_UP) {
            if (y > 0 && maze[x][y - 1] == 1) {
                world.put(new Calabash(new Color(100, 100, 100), 0, world), x, y);
                character.moveTo(x, y - 1);
            }
        } else if (key.getKeyCode() == KeyEvent.VK_DOWN) {
            if (y < 29 && maze[x][y + 1] == 1) {
                world.put(new Calabash(new Color(100, 100, 100), 0, world), x, y);
                character.moveTo(x, y + 1);
            }
        } else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (x < 29 && maze[x + 1][y] == 1) {
                world.put(new Calabash(new Color(100, 100, 100), 0, world), x, y);
                character.moveTo(x + 1, y);
            }
        } else if (key.getKeyCode() == KeyEvent.VK_LEFT) {
            if (x > 0 && maze[x - 1][y] == 1) {
                world.put(new Calabash(new Color(100, 100, 100), 0, world), x, y);
                character.moveTo(x - 1, y);
            }
        } else if (key.getKeyCode() == KeyEvent.VK_ENTER) {
            AutoFind autoFind = new AutoFind();
            autoFind.init(world.getTiles());
            autoFind.find(x, y);
            path = autoFind.getPath();
            world.put(new Calabash(new Color(100, 100, 100), 0, world), x, y);
            for (int i = 0; i < path.size(); i++) {
                character.moveTo(path.get(i).x, path.get(i).y);
                if (i < path.size() - 1) {
                    world.put(new Calabash(new Color(100, 100, 100), 0, world), path.get(i).x,
                            path.get(i).y);
                }
            }
        }
        return this;
    }

}
