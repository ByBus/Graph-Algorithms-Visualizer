package visualizer.data.checker;

import visualizer.presenter.Checker;

public class PositiveNumberChecker implements Checker {
    @Override
    public Boolean check(String input) {
        return input.matches("\\d+");
    }
}
