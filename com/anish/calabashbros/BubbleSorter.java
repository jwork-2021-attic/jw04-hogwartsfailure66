package com.anish.calabashbros;

public class BubbleSorter<T extends Comparable<T>> implements Sorter<T> {

    private T[][] a;

    private final int num = 8;
    private final int total = 64;

    @Override
    public void load(T[][] a) {
        this.a = a;
    }

    private void swap(int i, int j) {
        T temp;
        int i1 = i / num, i2 = i % num, j1 = j / num, j2 = j % num;
        temp = a[i1][i2];
        a[i1][i2] = a[j1][j2];
        a[j1][j2] = temp;
        plan += "" + a[i1][i2] + "<->" + a[j1][j2] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
//            for (int i = 0; i < a.length - 1; i++) {
//                if (a[i].compareTo(a[i + 1]) > 0) {
//                    swap(i, i + 1);
//                    sorted = false;
//                }
//            }
            for (int i = 0; i < total - 1; i++) {
                if (a[i / num][i % num].compareTo(a[(i + 1) / num][(i + 1) % num]) > 0) {
                    swap(i, i + 1);
                    sorted = false;
                }
            }
        }
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}