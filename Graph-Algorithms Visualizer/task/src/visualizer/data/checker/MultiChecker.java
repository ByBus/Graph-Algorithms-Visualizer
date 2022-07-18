package visualizer.data.checker;

import visualizer.presenter.Checker;

import java.util.Arrays;

public class MultiChecker implements Checker {
    private final Checker[] checkers;

    public MultiChecker(Checker... checkers) {
        this.checkers = checkers;
    }

    @Override
    public Boolean check(String input) {
        return Arrays.stream(checkers)
                .allMatch(checker -> checker.check(input));
    }
}
