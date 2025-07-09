package ui;

import abstraction.BoardViewer;
import abstraction.CheckersController;
import implementation.core.CheckerLogic;
import implementation.core.CheckerLogicImp;
import implementation.model.PositionCheckers;
import implementation.model.Peace;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BoardViewSwing extends JFrame implements BoardViewer, BoardViewer.BoardView {
    private final JButton[][] casas = new JButton[8][8];
    private final JLabel lblInfo; // Mantido para mensagens gerais
    private CheckersController controller;

    private static final int TAMANHO = 8;
    private static final Color COR1 = new Color(234, 225, 225, 255);
    private static final Color COR2 = new Color(14, 44, 136);
    private static final Icon PEAO_BRANCO = carregarIcone("/assets/pedraBranca.png");
    private static final Icon PEAO_VERMELHO = carregarIcone("/assets/pedraVermelha.png");
    private static final Icon DAMA_VERMELHA = carregarIcone("/assets/dama_vermelha.png");
    private static final Icon DAMA_BRANCA = carregarIcone("/assets/dama_branca.png");

    private JPanel cardPanel; // Painel que usará CardLayout para alternar visualizações
    private JPanel menuPanel; // Painel para a visualização do menu
    private JPanel gameBoardPanel; // Painel para a visualização do tabuleiro do jogo

    // Novos componentes para o timer e contagem de peças
    private JLabel lblTimer;
    private Timer gameTimer;
    private int secondsPassed;
    private JLabel lblCapturedPlayer1;
    private JLabel lblCapturedPlayer2;

    public BoardViewSwing() {
        lblInfo = new JLabel(" "); // Inicializa lblInfo
        initUI();
    }

    private void initUI() {
        setTitle("Jogo de Damas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardPanel = new JPanel(new CardLayout());
        add(cardPanel);

        setupMenuPanel();
        cardPanel.add(menuPanel, "INITIAL_MENU");

        setupGameBoardPanel();
        cardPanel.add(gameBoardPanel, "GAME_BOARD");

        showInitialMenu();
        setVisible(true);
    }

    private void setupMenuPanel() {
        menuPanel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Bem-vindo ao Jogo de Damas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        menuPanel.add(titulo, BorderLayout.CENTER);

        // O botão "Iniciar Jogo" já existe e foi configurado.
        JButton botaoIniciar = new JButton("Iniciar Jogo");
        botaoIniciar.addActionListener(e -> {
            CheckerLogic logic = new CheckerLogicImp();
            controller = new CheckersController(logic, BoardViewSwing.this);
            setController(controller);
            showGame();
            updateBoard();
            startTimer(); // Inicia o timer quando o jogo começa
        });
        menuPanel.add(botaoIniciar, BorderLayout.SOUTH);
    }

    private void setupGameBoardPanel() {
        gameBoardPanel = new JPanel(new BorderLayout()); // Altera para BorderLayout para adicionar informações

        JPanel boardGridPanel = new JPanel(new GridLayout(TAMANHO, TAMANHO)); // Painel para o grid do tabuleiro
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
                boardGridPanel.add(casa);
            }
        }
        gameBoardPanel.add(boardGridPanel, BorderLayout.CENTER); // Adiciona o grid do tabuleiro ao centro

        // Painel para informações (timer e peças capturadas)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Layout vertical para as informações

        // Adiciona o timer
        lblTimer = new JLabel("Tempo: 00:00");
        lblTimer.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTimer.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o texto
        infoPanel.add(lblTimer);
        infoPanel.add(Box.createVerticalStrut(10)); // Espaçamento

        // Adiciona contagem de peças capturadas
        lblCapturedPlayer1 = new JLabel("Peças Capturadas (Jogador 1): 0");
        lblCapturedPlayer1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCapturedPlayer1.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(lblCapturedPlayer1);
        infoPanel.add(Box.createVerticalStrut(5)); // Espaçamento

        lblCapturedPlayer2 = new JLabel("Peças Capturadas (Jogador 2): 0");
        lblCapturedPlayer2.setFont(new Font("Arial", Font.PLAIN, 16));
        lblCapturedPlayer2.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(lblCapturedPlayer2);
        infoPanel.add(Box.createVerticalStrut(10)); // Espaçamento

        gameBoardPanel.add(infoPanel, BorderLayout.NORTH); // Adiciona o painel de informações ao topo
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

        lblCapturedPlayer1.setText("Peças Capturadas (Jogador 1): " + controller.getCheckerLogic().getCapturedPieces(1));
        lblCapturedPlayer2.setText("Peças Capturadas (Jogador 2): " + controller.getCheckerLogic().getCapturedPieces(2));
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

    // Métodos para o timer
    private void startTimer() {
        secondsPassed = 0;
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(1000, e -> {
            secondsPassed++;
            int minutes = secondsPassed / 60;
            int seconds = secondsPassed % 60;
            lblTimer.setText(String.format("Tempo: %02d:%02d", minutes, seconds));
        });
        gameTimer.start();
    }

    private void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    private void resetTimer() {
        stopTimer();
        secondsPassed = 0;
        lblTimer.setText("Tempo: 00:00");
    }

    @Override
    public void showInitialMenu() {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "INITIAL_MENU");
        resetTimer(); // Reinicia o timer ao voltar para o menu
    }

    public void showGame() {
        CardLayout cl = (CardLayout)(cardPanel.getLayout());
        cl.show(cardPanel, "GAME_BOARD");
    }

    public void showMenu() {
        showInitialMenu();
    }
}