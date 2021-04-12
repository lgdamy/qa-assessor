package com.damytec.qaassessor.pojo;

/**
 * @author lgdamy@raiadrogasil.com on 22/01/2021
 */
public class Cpf {

    private String dv;
    private String raw;
    private String formatted;
    private boolean valid;

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
