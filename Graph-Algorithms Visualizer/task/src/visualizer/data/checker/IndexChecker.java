package visualizer.data.checker;

import visualizer.presenter.Checker;

public class IndexChecker implements Checker {
    @Override
    public Boolean check(String input) {
        return !input.isBlank() && input.length() == 1;
    }
}
