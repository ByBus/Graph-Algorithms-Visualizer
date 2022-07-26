package visualizer.presenter;

import visualizer.domain.usecases.Dialog;

import javax.swing.*;

public class InputCheckBoxDialog implements Dialog {
    private final JCheckBox isDirectedCheckbox;
    private final String message;
    private final String title;
    private final Checker checker;
    private Callback callback;
    private final JPanel myPanel = new JPanel();
    private final JTextField input = new JTextField(5);

    public InputCheckBoxDialog(String message, String checkboxMessage, String title, Checker checker) {
        this.message = message;
        this.title = title;
        this.checker = checker;
        this.isDirectedCheckbox = new JCheckBox(checkboxMessage);
        init();
    }

    private void init() {
        myPanel.add(new JLabel(message));
        myPanel.add(input);
        myPanel.add(Box.createHorizontalStrut(5));
        myPanel.add(isDirectedCheckbox);
    }

    @Override
    public void show() {
        JOptionPane pane = new JOptionPane(myPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION) {
            @Override
            public void selectInitialValue() {
                input.requestFocusInWindow();
            }
        };
        pane.createDialog(null, title).setVisible(true);
        try {
            int result = (int) pane.getValue();
            if (result == JOptionPane.OK_CANCEL_OPTION) {
                callback.onCancel();
            } else if (checker.check(input.getText())) {
                callback.onSuccess(input.getText() + ";" + isDirectedCheckbox.isSelected());
            } else {
                callback.onFailed();
            }
        } catch (Exception e) {
            System.out.println("Dialog was closed");
        } finally {
            input.setText("");
        }
    }

    @Override
    public void addCallBack(Callback callback) {
        this.callback = callback;
    }
}
