package visualizer.data;

import visualizer.presenter.VertexUI;

import java.util.Objects;

public class VertexDataModel {
    private int x;
    private int y;
    private boolean isHighlighted = false;
    private boolean showLabel = false;
    private final String index;

    public VertexDataModel(int x, int y, String index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }

    public String getIndex() {
        return index;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHighlighted(boolean value) {
        this.isHighlighted = value;
    }

    public VertexUI toVertexUI() {
        return new VertexUI(x, y, index);
    }

    public boolean isShowLabel() {
        return showLabel;
    }

    public VertexDataModel withLabel() {
        VertexDataModel vdm = new VertexDataModel(x, y, index);
        vdm.showLabel = true;
        return vdm;
    }

    public void setParameters(int x, int y, boolean isHighlighted) {
        this.x = x;
        this.y = y;
        this.isHighlighted = isHighlighted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexDataModel that = (VertexDataModel) o;
        return index.equals(that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return index + "" ;
    }

    public VertexDataModel copy() {
        var newVertex =  new VertexDataModel(x, y, index);
        newVertex.setHighlighted(isHighlighted);
        return newVertex;
    }
}
