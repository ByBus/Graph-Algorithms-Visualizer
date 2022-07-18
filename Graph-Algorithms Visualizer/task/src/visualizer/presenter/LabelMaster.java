package visualizer.presenter;

import visualizer.domain.Path;

import javax.swing.*;

public class LabelMaster {
    private static final String INIT_MESSAGE = "Please choose a starting vertex";
    private static final String WAIT_MESSAGE = "Please wait...";
    private String prefix;
    private final JLabel label;

    public LabelMaster(JLabel label) {
        this.label = label;
        setEnable(false);
    }

    public void waitMessage() {
        setEnable(true);
        label.setText(WAIT_MESSAGE);
    }

    public void initMessage() {
        label.setText(INIT_MESSAGE);
        setEnable(true);
    }

    public void hide() {
        setEnable(false);
    }

    public void showPath(Path path) {
        setEnable(true);
        label.setText(prefix + " : " + path);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    private void setEnable(boolean value) {
        label.setVisible(value);
    }
}
