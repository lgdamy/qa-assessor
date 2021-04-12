package com.damytec.qaassessor.pojo;

/**
 * @author lgdamy@raiadrogasil.com on 11/04/2021
 */
public class CreditCard {

    private String dv;
    private String raw;
    private String formatted;
    private String flag;

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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
