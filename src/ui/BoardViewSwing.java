package ui;

import abstraction.BoardViewer;
import abstraction.CheckersController;
import abstraction.MenuInicialTESTE;  
import implementation.model.PositionCheckers;
import implementation.model.Peace;

import javax.swing.*;
import java.awt.*;
import java.net.URL;


public class BoardViewSwing extends JFrame implements BoardViewer {
    private final JButton[][] casas = new JButton[8][8];
    private final JLabel lblInfo;
    private CheckersController controller;

    private static final int TAMANHO = 8;
    private static final Color COR1 = new Color(234, 225, 225, 255);
    private static final Color COR2 = new Color(14, 44, 136);
    private static final Icon PEAO_BRANCO = carregarIcone("/assets/pedraBranca.png");
    private static final Icon PEAO_VERMELHO = carregarIcone("/assets/pedraVermelha.png");
    private static final Icon DAMA_VERMELHA = carregarIcone("/assets/dama_vermelha.png");
    private static final Icon DAMA_BRANCA = carregarIcone("/assets/dama_branca.png");

    // Construtor vazio
    public BoardViewSwing() {
        lblInfo = new JLabel(" ");
        initUI();
    }

    private void initUI() {
        setTitle("Tabuleiro de Damas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(TAMANHO, TAMANHO));

        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                JButton casa = new JButton();
                Color cor = (linha + coluna) % 2 == 0 ? COR1 : COR2;
                casa.setOpaque(true);
                casa.setBackground(cor);
                casa.setBorderPainted(true);
                casa.setFocusable(false);

                if (cor.equals(COR2)) {
                    if (linha < 3) {
                        casa.setIcon(PEAO_BRANCO);
                    } else if (linha > 4) {
                        casa.setIcon(PEAO_VERMELHO);
                    }
                }
                casas[linha][coluna] = casa;
                add(casa);
            }
        }
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setController(CheckersController controller) {
        this.controller = controller;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                final int row = r;
                final int col = c;
                casas[r][c].addActionListener(e -> {
                    if (this.controller != null) {
                        controller.clickProcess(new PositionCheckers(row, col));
                    }
                });
            }
        }
    }

    @Override
    public void updateBoard() {
        if (controller == null) return;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                JButton botao = casas[r][c];
                botao.setIcon(null);

                PositionCheckers pos = new PositionCheckers(r, c);
                Peace.Type tipoPeca = Peace.Type.fromValue(controller.getCheckerLogic().getTypePeace(pos));

                if (tipoPeca != null) {
                    switch (tipoPeca) {
                        case PEACE_PLAYER1 -> botao.setIcon(PEAO_VERMELHO);
                        case PEACE_PLAYER2 -> botao.setIcon(PEAO_BRANCO);
                        case CHECKERS_PLAYER1 -> botao.setIcon(DAMA_VERMELHA);
                        case CHECKERS_PLAYER2 -> botao.setIcon(DAMA_BRANCA);
                        default -> botao.setIcon(null);
                    }
                }
            }
        }
    }

    @Override
    public void highlightsHouse(int row, int col, boolean highlight) {
        if (row >= 0 && col >= 0 && row < 8 && col < 8) {
            casas[row][col].setBorderPainted(highlight);
            casas[row][col].setBorder(BorderFactory.createLineBorder(Color.YELLOW, highlight ? 3 : 0));
        }
    }

    @Override
    public void messages(String message) {
        lblInfo.setText(message);
    }

    private static Icon carregarIcone(String caminho) {
        URL url = BoardViewSwing.class.getResource(caminho);
        if (url == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }
        return new ImageIcon(url);
    }

    @Override
    public void showMenu() {
        JFrame menuFrame = new JFrame("Menu do Jogo");
        menuFrame.setSize(300, 200);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Menu do Jogo", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        menuFrame.add(label, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        JButton restartButton = new JButton("Reiniciar");
        JButton exitButton = new JButton("Sair");

        restartButton.addActionListener(e -> {
            menuFrame.dispose();
            this.dispose(); // fecha a janela principal
            new MenuInicialTESTE(); // reabre o menu inicial
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        panel.add(restartButton);
        panel.add(exitButton);

        menuFrame.add(panel, BorderLayout.CENTER);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }
}
