package visualizer.presenter;

import visualizer.domain.ClickListenerAdder;
import visualizer.domain.Selectable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphCanvas extends JPanel {
    private final ClickListenerAdder clickAdder;

    public GraphCanvas(String name, ClickListenerAdder clickAdder) {
        this.clickAdder = clickAdder;
        setDoubleBuffered(true);
        setName(name);
        setLayout(null);
        clickAdder.add(this);
    }

    public Component getNamedComponent(String name) {
        return Arrays.stream(getComponents())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void syncComponents(List<Component> updatedComponents) {
        List<String> existingComponents = update(updatedComponents);
        removeExcessiveComponents(existingComponents);
        refresh();
    }

    public void updateComponents(List<Component> updatedComponents) {
        update(updatedComponents);
        refresh();
    }

    private List<String> update(List<Component> updatedComponents) {

        if (updatedComponents.isEmpty()) return Collections.emptyList();
        List<String> existingComponents = new ArrayList<>();

        updatedComponents.forEach(component -> {
            String name = component.getName();
            existingComponents.add(name);
            var oldComponent = getNamedComponent(name);
            if (oldComponent != null) {
                ((Selectable) oldComponent).select(((Selectable) component).isSelected());
                if (component instanceof EdgeLabel) {
                    var text = ((EdgeLabel) component).getText();
                    ((EdgeLabel) oldComponent).setText(text);
                }
            } else {
                add(component);
                //if (!(component instanceof EdgeLabel)) {
                    clickAdder.add(component);
                //}
            }
        });
        return existingComponents;
    }

    private void select(Selectable source, Selectable target) {
        target.select(source.isSelected());
    }

    private void removeExcessiveComponents(List<String> existingComponentNames) {
        Arrays.stream(getComponents())
                .filter(c -> !existingComponentNames.contains(c.getName()))
                .forEach(this::remove);
    }

    private void refresh() {
        revalidate();
        repaint();
    }
}
