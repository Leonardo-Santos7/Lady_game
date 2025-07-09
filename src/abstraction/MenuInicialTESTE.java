package abstraction;

import implementation.core.CheckerLogicImp;
import ui.BoardViewSwing;
import implementation.core.CheckerLogic;
import abstraction.CheckersController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicialTESTE extends JFrame {

    public MenuInicialTESTE() {
        setTitle("Menu Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 620);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Bem-vindo ao Jogo de Damas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.CENTER);

        JButton botaoRestart = new JButton("Restart");
        botaoRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha o menu

                // Cria a interface gráfica do jogo
                BoardViewSwing boardView = new BoardViewSwing();

                // Cria a lógica do jogo
                CheckerLogic logic = new CheckerLogicImp();

                // Cria o controlador que conecta lógica e interface
                CheckersController controller = new CheckersController(logic, boardView);

                // Liga o controlador à interface para receber eventos
                boardView.setController(controller);

                // Atualiza o tabuleiro para mostrar o estado inicial
                boardView.updateBoard();
            }
        });
        add(botaoRestart, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

