package com.anish.calabashbros;

import java.util.Random;

public class RandomSort {
    public void swap(int a[], int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public void getRandom(int a[], int time, int size) {
        for (int i = 0; i < time; i++) {
            Random random = new Random();
            int k = random.nextInt(1000) % size;
            int j = random.nextInt(1000) % size;
            swap(a, k, j);
        }
    }
}
