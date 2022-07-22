package visualizer.presenter;

import visualizer.domain.usecases.Dialog;

import javax.swing.*;

public class InputDialog implements Dialog {
    private final String message;
    private final String title;
    private final Checker checker;
    private Callback callback;

    public InputDialog(String message, String title, Checker checker) {
        this.message = message;
        this.title = title;
        this.checker = checker;
    }
    @Override
    public void show() {
        String result = JOptionPane.showInputDialog(null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE);
        try {
            if (result == null) {
                callback.onCancel();
            } else if (checker.check(result)) {
                callback.onSuccess(result);
            } else {
                callback.onFailed();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCallBack(Callback callback) {
        this.callback = callback;
    }
}
