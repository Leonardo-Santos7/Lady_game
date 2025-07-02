package abstraction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardView extends JFrame {
    private static final int TAMANHO = 8;
    private static final Color COR1 = new Color(181, 136, 99);   // castanho médio
    private static final Color COR2 = new Color(240, 217, 181);  // bege claro
    private static final Icon PEAO_BRANCO = carregarIcone("/assets/leo.png");
    private static final Icon PEAO_VERMELHO = carregarIcone("/assets/luigi.png");

    private JLabel timerLabel;
    private JLabel scoreBrancoLabel;
    private JLabel scoreVermelhoLabel;
    private int segundos = 0;

    // Scores separados
    private int scoreBranco = 0;
    private int scoreVermelho = 0;

    public BoardView() {
        setTitle("Tabuleiro de Damas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel do tabuleiro
        JPanel painelTabuleiro = new JPanel(new GridLayout(TAMANHO, TAMANHO));
        painelTabuleiro.setPreferredSize(new Dimension(600, 600));

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
                painelTabuleiro.add(casa);
            }
        }

        // Painel lateral para timer e scores
        JPanel painelLateral = new JPanel();
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.setPreferredSize(new Dimension(150, 600));

        timerLabel = new JLabel("Tempo: 0s");
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreBrancoLabel = new JLabel("Branco: 0");
        scoreBrancoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreVermelhoLabel = new JLabel("Vermelho: 0");
        scoreVermelhoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelLateral.add(Box.createVerticalGlue());
        painelLateral.add(timerLabel);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelLateral.add(scoreBrancoLabel);
        painelLateral.add(Box.createRigidArea(new Dimension(0, 5)));
        painelLateral.add(scoreVermelhoLabel);
        painelLateral.add(Box.createVerticalGlue());

        // Botão de Restart
        JButton botaoRestart = new JButton("Restart");
        botaoRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
        botaoRestart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela atual

                // Volta ao menu inicial
                new MenuInicialTESTE(); // <-- Altere se o nome da sua classe de menu for diferente
            }
        });
        painelLateral.add(Box.createRigidArea(new Dimension(0, 10)));
        painelLateral.add(botaoRestart);

        add(painelTabuleiro, BorderLayout.CENTER);
        add(painelLateral, BorderLayout.EAST);

        iniciarTimer();

        setSize(750, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                segundos++;
                timerLabel.setText("Tempo: " + segundos + "s");
            }
        });
        timer.start();
    }

    // Métodos para atualizar scores separados
    public void adicionarPontoBranco(int pontos) {
        scoreBranco += pontos;
        atualizarScoreLabel();
    }

    public void adicionarPontoVermelho(int pontos) {
        scoreVermelho += pontos;
        atualizarScoreLabel();
    }

    private void atualizarScoreLabel() {
        scoreBrancoLabel.setText("Branco: " + scoreBranco);
        scoreVermelhoLabel.setText("Vermelho: " + scoreVermelho);
    }

    public int getScoreBranco() {
        return scoreBranco;
    }

    public int getScoreVermelho() {
        return scoreVermelho;
    }

    private static Icon carregarIcone(String caminho) {
        java.net.URL url = BoardView.class.getResource(caminho);
        if (url == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }
        return new ImageIcon(url);
    }
}


