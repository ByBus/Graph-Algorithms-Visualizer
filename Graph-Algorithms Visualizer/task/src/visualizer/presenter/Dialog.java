package visualizer.presenter;

import javax.swing.*;

public class Dialog {
    private final String message;
    private final String title;
    private final Checker checker;
    private Callback callback;

    public Dialog(String message, String title, Checker checker) {
        this.message = message;
        this.title = title;
        this.checker = checker;
    }
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

    public void addCallBack(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onSuccess(String index);
        void onFailed();
        void onCancel();
    }
}
