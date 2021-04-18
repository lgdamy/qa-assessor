package com.damytec.qaassessor.service;

import java.awt.*;
import java.net.URI;

/**
 * @author lgdamy on 22/01/2021
 */
public class WeblocationService {

    private static WeblocationService INSTANCE;

    private WeblocationService() {}

    public static WeblocationService getInstance() {
        return INSTANCE = INSTANCE == null ? new WeblocationService() : INSTANCE;
    }

    public void openWebPage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
