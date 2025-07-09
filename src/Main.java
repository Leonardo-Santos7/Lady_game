import abstraction.MenuInicialTESTE;
import implementation.core.CheckerLogic;
import implementation.core.CheckerLogicImp;
import abstraction.CheckersController;
import ui.BoardViewSwing;

import javax.swing.SwingUtilities;

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
