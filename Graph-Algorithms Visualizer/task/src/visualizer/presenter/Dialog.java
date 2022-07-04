package visualizer.presenter;

import javax.swing.*;

public class Dialog {
    private Callback callback;

    public void show() {
        String result = JOptionPane.showInputDialog(null,
                "Enter the Vertex ID (Should be 1 char):",
                "Vertex",
                JOptionPane.QUESTION_MESSAGE);
        try {
            if (!result.isBlank() && result.length() == 1) {
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
    }
}
