package cz.czechitas.webapp;

public class DetailForm {

    private String jmeno;
    private String telefonniCislo;
    private String email;

    public DetailForm() {
        this.jmeno = jmeno;
        this.telefonniCislo = telefonniCislo;
        this.email = email;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String newValue) {
        jmeno = newValue;
    }

    public String getTelefonniCislo() {
        return telefonniCislo;
    }

    public void setTelefonniCislo(String newValue) {
        telefonniCislo = newValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }
}
