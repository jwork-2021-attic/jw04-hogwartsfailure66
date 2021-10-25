package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.BubbleSorter;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.RandomSort;
import com.anish.calabashbros.World;

import asciiPanel.AsciiPanel;

import java.util.Random;

public class WorldScreen implements Screen {

    private World world;
    private Calabash[][] monsters;
    String[] sortSteps;
    private final int num = 8;
    private final int total = 64;

    public WorldScreen() {
        world = new World();

        int randomPos[] = new int[total];
        for (int i = 0; i < total; i++) {
            randomPos[i] = i;
        }
        RandomSort randomSort = new RandomSort();
        randomSort.getRandom(randomPos, 100, total);

        monsters = new Calabash[num][num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Random random = new Random();
                int rank = i * num + j + 1;
                int r, g, b;
                if (rank < 22) {
                    r = 255 - rank * 2;
                    g = random.nextInt(50) + 100;
                    b = random.nextInt(50) + 100;
                } else if (rank < 44) {
                    r = random.nextInt(50) + 100;
                    g = 255 - (rank - 22) * 2;
                    b = random.nextInt(50) + 100;
                } else {
                    r = random.nextInt(50) + 100;
                    g = random.nextInt(50) + 100;
                    b = 255 - (rank - 43) * 2;
                }
                int posi=randomPos[rank - 1] / num;
                int posj=randomPos[rank - 1] % num;
                monsters[posi][posj] = new Calabash(
                        new Color(r, g, b), rank, world);
                world.put(monsters[posi][posj], 10 + posj * 2, 10 + posi * 2);
            }
        }

        BubbleSorter<Calabash> b = new BubbleSorter<>();
        b.load(monsters);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
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

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(monsters, sortSteps[i]);
            i++;
        }

        return this;
    }

}
