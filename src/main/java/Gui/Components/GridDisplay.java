package Gui.Components;

import javafx.scene.Group;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GridDisplay {

    private static final double ELEMENT_SIZE = 30;
    private static final double GAP = 0;

    private TilePane tilePane = new TilePane();
    private Group display = new Group(tilePane);
    private int nRows;
    private int nCols;

    public GridDisplay(int nRows, int nCols) {
        tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
        tilePane.setHgap(GAP);
        tilePane.setVgap(GAP);
        setColumns(nCols);
        setRows(nRows);
    }

    public void setColumns(int newColumns) {
        nCols = newColumns;
        tilePane.setPrefColumns(nCols);
        createElements();
    }

    public void setRows(int newRows) {
        nRows = newRows;
        tilePane.setPrefRows(nRows);
        createElements();
    }

    private void createElements() {
        tilePane.getChildren().clear();
        for (int i = 0; i < nCols; i++) {
            for (int j = 0; j < nRows; j++) {
                tilePane.getChildren().add(createElement());
            }
        }
    }

    private Rectangle createElement() {
        Rectangle rectangle = new Rectangle(ELEMENT_SIZE, ELEMENT_SIZE);
        rectangle.setStroke(Color.ORANGE);
        rectangle.setFill(Color.STEELBLUE);

        return rectangle;
    }

}
