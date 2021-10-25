package com.anish.calabashbros;

import com.anish.maze.Node;

import java.util.ArrayList;


public class AutoFind {
    private int[][] flag;
    private ArrayList<Node> path = new ArrayList<Node>();
    private ArrayList<Node> tmp = new ArrayList<Node>();

    public void init(Tile<Thing>[][] tiles) {
        flag = new int[30][30];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (tiles[i][j].getThing() instanceof Destination) flag[i][j] = 1;
                else if (tiles[i][j].getThing() instanceof Wall)
                    flag[i][j] = 0;
                else flag[i][j] = 2;
//                System.out.print(flag[i][j] + " ");
            }
//            System.out.println();
        }
    }

    public boolean check(int x, int y) {
        if (x < 0 || y < 0 || x > 29 || y > 29) return false;
        return flag[x][y] == 1 || flag[x][y] == 2;
    }

    //0wall 1des 2road 3visited
    public void find(int x, int y) {
        if (flag[x][y] == 1) {
            path.addAll(tmp);
        } else {
            flag[x][y] = 3;
            if (check(x - 1, y)) {
                tmp.add(new Node(x - 1, y));
                find(x - 1, y);
                tmp.remove(tmp.size() - 1);
            }
            if (check(x + 1, y)) {
                tmp.add(new Node(x + 1, y));
                find(x + 1, y);
                tmp.remove(tmp.size() - 1);
            }
            if (check(x, y - 1)) {
                tmp.add(new Node(x, y - 1));
                find(x, y - 1);
                tmp.remove(tmp.size() - 1);
            }
            if (check(x, y + 1)) {
                tmp.add(new Node(x, y + 1));
                find(x, y + 1);
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    public ArrayList<Node> getPath() {
        return path;
    }
}
