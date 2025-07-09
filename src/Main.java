import javax.swing.SwingUtilities;
import ui.BoardViewSwing;
import implementation.core.CheckerLogic;
import implementation.core.CheckerLogicImp;
import abstraction.CheckersController;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CheckerLogic checkerLogic = new CheckerLogicImp();
            BoardViewSwing boardViewerSwing = new BoardViewSwing();
            CheckersController controller = new CheckersController(checkerLogic, boardViewerSwing);

            boardViewerSwing.setController(controller);
            boardViewerSwing.setVisible(true);
        });
    }
}


