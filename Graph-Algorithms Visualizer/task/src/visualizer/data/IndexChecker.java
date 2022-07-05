package visualizer.data;

import visualizer.presenter.Checker;

public class IndexChecker implements Checker {
    @Override
    public Boolean check(String input) {
        return !input.isBlank() && input.length() == 1;
    }
}
