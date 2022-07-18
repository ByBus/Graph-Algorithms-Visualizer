package visualizer.data;

import java.util.Arrays;

public class AxisHolder {
    private final int[] values = new int[2];
    /**
     *  Holds two single coordinates of same coordinate axis
     *  (x0 and xn)
     *  <p>
     *  x0------------------xn----->X
     *  <p>
     *  Class needed to determine which of the points:
     *  is more left (in case of x-axis);
     *  is upper (in case of y-axis);     *
     * @param first coordinate value of left point
     * @param second coordinate value of right point
     */
    public AxisHolder(int first, int second) {
        values[0] = first;
        values[1] = second;
    }

    public int first() {
        return values[0];
    }

    public int second() {
        return values[1];
    }

    public void swap() {
        values[0] = values[0] + values[1];
        values[1] = values[0] - values[1];
        values[0] = values[0] - values[1];
    }

    public int delta() {
        return Math.abs(first() - second());
    }

    public AxisHolder sort() {
        Arrays.sort(values);
        return this;
    }

    public int direction() {
        return (first() < second()) ? 1 : -1;
    }
}
