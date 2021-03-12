package co.edu.escuelaing.arep.sparkserver.Robin.entity;

public class Dato {
    private String data;
    private String date;

    public Dato(String data, String fecha) {
        this.data=data;
        this.date=fecha;
    }

    public String getData() {
        return data;
    }

    public String getDate() {
        return date;
    }

}
