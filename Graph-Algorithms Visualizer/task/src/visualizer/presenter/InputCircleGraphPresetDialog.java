package visualizer.presenter;

import visualizer.domain.usecases.Dialog;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class InputCircleGraphPresetDialog implements Dialog {
    private final String title;
    private final Checker checker;
    private final JTextField numberOfVerticesInput = new JTextField("10",3);
    private final JTextField skipVerticesInput = new JTextField("3", 3);
    private final JTextField connectVerticesInput = new JTextField("3", 3);
    private final JTextField rotateFigureAngleInput = new JTextField("90", 3);
    private final JPanel myPanel = new JPanel();
    private Callback callback;

    public InputCircleGraphPresetDialog(String title, Checker checker) {
        this.title = title;
        this.checker = checker;
        init();
    }

    private void init() {
        GridLayout layout = new GridLayout(4, 2);
        layout.setHgap(5);
        myPanel.setLayout(layout);
        myPanel.add(new JLabel("Number of vertices"));
        myPanel.add(numberOfVerticesInput);
        myPanel.add(new JLabel("Skip vertices"));
        myPanel.add(skipVerticesInput);
        myPanel.add(new JLabel("Connect vertices"));
        myPanel.add(connectVerticesInput);
        myPanel.add(new JLabel("Initial figure rotation"));
        myPanel.add(rotateFigureAngleInput);
    }

    @Override
    public void show() {
        JOptionPane pane = new JOptionPane(myPanel, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        pane.createDialog(null, title).setVisible(true);
        try {
            int result = (int) pane.getValue();
            var inputs = List.of(numberOfVerticesInput, skipVerticesInput, connectVerticesInput, rotateFigureAngleInput);
            if (result == JOptionPane.CANCEL_OPTION) {
                callback.onCancel();
            } else if (inputs.stream().map(JTextComponent::getText).allMatch(checker::check)) {
                callback.onSuccess(inputs.stream().map(JTextComponent::getText).collect(Collectors.joining(";")));
            } else {
                callback.onFailed();
            }
        } catch (Exception e) {
            System.out.println("Dialog was closed");
        }
    }

    @Override
    public void addCallBack(Callback callback) {
        this.callback = callback;
    }
}
