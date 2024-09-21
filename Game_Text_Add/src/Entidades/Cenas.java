package Entidades;

public class Cenas {
    private int cena_id;
    private String cena_name;
    private String description;

    public Cenas (){

    }

    public Cenas(int cena_id, String cena_name, String description) {
        this.cena_id = cena_id;
        this.cena_name = cena_name;
        this.description = description;
    }

    public int getCena_id() {
        return cena_id;
    }

    public void setCena_id(int cena_id) {
        this.cena_id = cena_id;
    }

    public String getCena_name() {
        return cena_name;
    }

    public void setCena_name(String cena_name) {
        this.cena_name = cena_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Cenas{" +
                "cena_id=" + cena_id +
                ", cena_name='" + cena_name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
