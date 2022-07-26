package visualizer.presenter;

import visualizer.data.checker.IndexChecker;
import visualizer.domain.usecases.Dialog;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputCircleGraphPresetDialog implements Dialog {
    private final String title;
    private final Checker checker;
    private final Checker indexChecker;
    private final JTextField numberOfVerticesInput = new JTextField("10",3);
    private final JTextField skipVerticesInput = new JTextField("3", 3);
    private final JTextField connectVerticesInput = new JTextField("3", 3);
    private final JTextField rotateFigureAngleInput = new JTextField("90", 3);
    private final JTextField startIndexInput = new JTextField("A", 3);
    private final JPanel myPanel = new JPanel();
    private Callback callback;

    public InputCircleGraphPresetDialog(String title, Checker numberChecker, Checker indexChecker) {
        this.title = title;
        this.checker = numberChecker;
        this.indexChecker = indexChecker;
        init();
    }

    private void init() {
        GridLayout layout = new GridLayout(5, 2);
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
        myPanel.add(new JLabel("Start index"));
        myPanel.add(startIndexInput);
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
            } else if (inputs.stream().map(JTextComponent::getText).allMatch(checker::check) &&
                    indexChecker.check(startIndexInput.getText())) {
                String startIndex = String.valueOf((int) startIndexInput.getText().charAt(0));
                callback.onSuccess(Stream.concat(inputs.stream().map(JTextComponent::getText), Stream.of(startIndex))
                        .collect(Collectors.joining(";")));
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
