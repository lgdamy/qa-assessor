package com.damytec.qaassessor;

import com.damytec.qaassessor.ui.BaseWindow;
import com.damytec.qaassessor.view.QaassessorPanel;
import com.formdev.flatlaf.FlatLightLaf;

import java.util.concurrent.Callable;

/**
 * @author lgdamy@ on 22/01/2021
 */
public class App {
    public static void main(String[] args) throws Exception {
        FlatLightLaf.install();
        new BaseWindow(new QaassessorPanel(), 400,300);
    }
}
