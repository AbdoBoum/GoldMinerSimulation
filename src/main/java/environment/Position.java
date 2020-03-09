package environment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Position represent a position in the field
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    /**
     * index of row
     */
    private int row = 0;
    /**
     * index of column
     */
    private int col = 0;

    /**
     * To describe the position
     * @return the string of position
     */
    @Override
    public String toString() {
        return this.row + " " + this.col;
    }
}
