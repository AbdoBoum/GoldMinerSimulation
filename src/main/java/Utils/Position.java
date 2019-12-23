package Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private int row = 0;
    private int col = 0;

    @Override
    public String toString() {
        return this.row + " " + this.col;
    }
}
