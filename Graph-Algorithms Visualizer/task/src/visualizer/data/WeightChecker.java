package visualizer.data;

import visualizer.presenter.Checker;

public class WeightChecker implements Checker {
    @Override
    public Boolean check(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Input String is not Integer.");
        }
        return false;
    }
}
