import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class TabuleiroVisual extends JFrame {
    private static final int TAMANHO = 8;
    private static final Color COR1 = new Color(4, 26, 110);
    private static final Color COR2 = new Color(220, 224, 228);
    private static final Icon PEAO_BRANCO = carregarIcone("/assets/leo.png");
    private static final Icon PEAO_VERMELHO = carregarIcone("/assets/luigi.png");

    public TabuleiroVisual() {
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
                add(casa);
            }
        }
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static Icon carregarIcone(String caminho) {
        URL url = TabuleiroVisual.class.getResource(caminho);
        if (url == null) {
            System.err.println("Imagem não encontrada: " + caminho);
            return null;
        }
        return new ImageIcon(url);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TabuleiroVisual::new);
    }
}