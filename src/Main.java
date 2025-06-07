import abstraction.BoardView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoardView::new);
    }
}
