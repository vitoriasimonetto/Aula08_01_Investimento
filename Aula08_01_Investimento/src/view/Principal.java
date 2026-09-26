package view;

public class Principal {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new JFrameInvestimento().setVisible(true);
        });
    }
}
