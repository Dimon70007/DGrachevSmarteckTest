package ru.dgrachev.old;

import java.util.Arrays;

/**
 * Created by OTBA}|{HbIu` on 13.12.16.
 */
public class Icon {
    final int countXElements;
    final int countYElements;
    final int[][] picture;

    public Icon(int countXElements, int countYElements) {
        this.countXElements = countXElements;
        this.countYElements = countYElements;
        this.picture = new int[countXElements][countYElements];
        for (int i = 0; i < picture.length; i++) {
            Arrays.fill(picture[i],0);
        }
    }

    public Icon(Icon icon) {
        this.countXElements = icon.countXElements;
        this.countYElements = icon.countYElements;
        this.picture = Arrays.copyOf(icon.picture,icon.picture.length);
    }

    public int getElement(int x,int y) {
        return picture[x][y];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Icon icon = (Icon) o;

        return Arrays.deepEquals(picture, icon.picture);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(picture);
    }
}
