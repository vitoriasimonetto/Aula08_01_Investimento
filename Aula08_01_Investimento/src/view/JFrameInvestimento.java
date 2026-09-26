package view;

import business.Aplicacao;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JFrameInvestimento extends JFrame {

    private final JTextField txtValor = new JTextField();
    private final JTextField txtPrazo = new JTextField();
    private final JComboBox<String> cbIndexador =
            new JComboBox<>(new String[]{
                "Poupança",
                "CDI",
                "Tesouro Direto"
            });
    private final JButton btnCalcular =
            new JButton("Calcular Rendimento");
    private final JLabel lblResultado =
            new JLabel("Rendimento: ");

    public JFrameInvestimento() {
        setTitle("Aula08_01_Investimento");
        setSize(450, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        montarTela();
        configurarEventos();
    }

    private void montarTela() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Valor a ser aplicado:"));
        painel.add(txtValor);

        painel.add(new JLabel("Prazo da aplicação (meses):"));
        painel.add(txtPrazo);

        painel.add(new JLabel("Indexador financeiro:"));
        painel.add(cbIndexador);

        painel.add(new JLabel("Rendimento:"));
        painel.add(lblResultado);

        JPanel painelPrincipal = new JPanel(new FlowLayout());
        painelPrincipal.add(painel);
        painelPrincipal.add(btnCalcular);

        add(painelPrincipal);
    }

    private void configurarEventos() {
        txtValor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }

                if (c == '.' && txtValor.getText().contains(".")) {
                    e.consume();
                }
            }
        });

        txtPrazo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });

        btnCalcular.addActionListener(e -> calcular());
    }

    private void calcular() {
        try {
            if (txtValor.getText().isBlank()
                    || txtPrazo.getText().isBlank()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Preencha o valor e o prazo.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            float valor = Float.parseFloat(txtValor.getText());
            int prazo = Integer.parseInt(txtPrazo.getText());

            if (valor <= 0 || prazo <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Digite valores maiores que zero.",
                        "Atenção",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            float taxa;

            switch (cbIndexador.getSelectedItem().toString()) {
                case "Poupança":
                    taxa = 0.38f;
                    break;
                case "CDI":
                    taxa = 0.53f;
                    break;
                default:
                    taxa = 0.65f;
                    break;
            }

            Aplicacao aplicacao = new Aplicacao();
            aplicacao.calcularRendimento(valor, prazo, taxa);

            float resultado = aplicacao.getRendimento();

            NumberFormat formato =
                    NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            lblResultado.setText(formato.format(resultado));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Digite apenas números válidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
