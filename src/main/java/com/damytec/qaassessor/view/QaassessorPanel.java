package com.damytec.qaassessor.view;

import com.damytec.qaassessor.pojo.Cpf;
import com.damytec.qaassessor.pojo.CreditCard;
import com.damytec.qaassessor.service.CPFAssessorService;
import com.damytec.qaassessor.service.CreditCardAssessorService;
import com.damytec.qaassessor.service.WeblocationService;
import com.damytec.qaassessor.ui.BaseWindow;
import com.damytec.qaassessor.ui.CustomButton;
import com.damytec.qaassessor.util.SimpleDocumentListener;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lgdamy on 25/01/2021
 */
public class QaassessorPanel implements BaseWindow.ContentForm {
    private JPanel root;
    private JTextField inputCpf;
    private JTextField dvCpfField;
    private JTextField formattedCpfField;
    private JTextField rawCpfField;
    private JLabel validCpfLabel;
    private JTextField inputCc;
    private JTextField dvCcField;
    private JTextField formattedCcField;
    private JTextField rawCcField;
    private JLabel validCcLabel;
    private JLabel ccErrorLabel;
    private JTextField bandeiraCcField;
    private CustomButton helpBandeiraCCButton;
    private JPanel ccCardPanel;
    private JTextPane ccHintText;
    private JTabbedPane tabbedPane;

    private ImageIcon ok;
    private ImageIcon nok;

    private static final URI LUHN_ALGORITHM = URI.create("https://en.wikipedia.org/wiki/Luhn_algorithm");

    public QaassessorPanel() {
        this.buildImages();
        inputCpf.getDocument().addDocumentListener(inputCpfListener());
        inputCc.getDocument().addDocumentListener(inputCcListener());
        ccHintText.setText(CreditCardAssessorService.BANDEIRA_HINT);
        ccHintText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ((CardLayout)ccCardPanel.getLayout()).show(ccCardPanel, "CCMain");
            }
        });
        ccHintText.addHyperlinkListener(e -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                WeblocationService.getInstance().openWebPage(LUHN_ALGORITHM);
            }
        });
    }

    private SimpleDocumentListener inputCpfListener() {
        return e -> {
            Cpf pojo = CPFAssessorService.getInstance().calcularDV(inputCpf.getText());
            if (!pojo.isValid()) {
                Toolkit.getDefaultToolkit().beep();
            }
            validCpfLabel.setIcon(pojo.isValid() ? ok : nok);
            validCpfLabel.setVisible(true);
            dvCpfField.setText(pojo.getDv());
            rawCpfField.setText(pojo.getRaw());
            formattedCpfField.setText(pojo.getFormatted());
        };
    }

    private SimpleDocumentListener inputCcListener() {
        return e -> {
            try {
                CreditCard pojo = CreditCardAssessorService.getInstance().calcularDV(inputCc.getText());
                validCcLabel.setIcon(ok);
                ccErrorLabel.setText(" ");
                dvCcField.setText(pojo.getDv());
                rawCcField.setText(pojo.getRaw());
                formattedCcField.setText(pojo.getFormatted());
                bandeiraCcField.setText(pojo.getFlag());
            } catch (Exception ex) {
                Toolkit.getDefaultToolkit().beep();
                validCcLabel.setIcon(nok);
                ccErrorLabel.setText(ex.getMessage());
                dvCcField.setText("");
                rawCcField.setText("");
                formattedCcField.setText("");
                bandeiraCcField.setText("");
            }
            validCcLabel.setVisible(true);
        };
    }

    private void buildImages() {
        this.ok = new ImageIcon(QaassessorPanel.class.getClassLoader().getResource("images/ok.gif"));
        this.nok = new ImageIcon(QaassessorPanel.class.getClassLoader().getResource("images/nok.png"));
    }

    public void createUIComponents() {
        helpBandeiraCCButton = new CustomButton("images/help.gif", new Dimension(20,20)) {
            @Override
            public void actionPerformed() {
                ((CardLayout)ccCardPanel.getLayout()).show(ccCardPanel, "CCHint");
            }
        };
    }

    @Override
    public JPanel root() {
        return this.root;
    }

//    Sobrescreva esse metodo apenas se sua janela vai mudar de titulo
//    @Override
//    public String title() {
//        return "Meu titulo especial";
//    }
}
