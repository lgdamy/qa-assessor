package com.damytec.qaassessor.view;

import com.damytec.qaassessor.pojo.Cpf;
import com.damytec.qaassessor.pojo.CreditCard;
import com.damytec.qaassessor.service.CPFAssessorService;
import com.damytec.qaassessor.service.CreditCardAssessorService;
import com.damytec.qaassessor.ui.BaseWindow;
import com.damytec.qaassessor.util.SimpleDocumentListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author lgdamy on 25/01/2021
 */
public class QaassessorPanel implements BaseWindow.ContentForm {
    private JTextField numberField;
    private JButton gerarButton;
    private JPanel root;
    private JTextField inputCpf;
    private JTextField dvCpfField;
    private JTextField formattedCpfField;
    private JTextField rawCpfField;
    private JLabel validCpfLabel;
    private JTabbedPane tabbedPane1;
    private JTextField inputCc;
    private JTextField dvCcField;
    private JTextField formattedCcField;
    private JTextField rawCcField;
    private JLabel validCcLabel;
    private JLabel ccErrorLabel;
    private JTextField bandeiraCcField;
    private JRadioButton imparRadio;
    private JRadioButton parRadio;
    private JRadioButton primeRadio;

    private ImageIcon ok;
    private ImageIcon nok;

    public QaassessorPanel() {
        this.buildImages();
        inputCpf.getDocument().addDocumentListener(inputCpfListener());
        inputCc.getDocument().addDocumentListener(inputCcListener());
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
