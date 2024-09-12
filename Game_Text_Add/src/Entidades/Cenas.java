package Entidades;

public class Cenas {
    private String Cena_id;
    private String Cena_name;
    private String description;

    public Cenas (){

    }
    public  Cenas (String Cena_id, String Cena_name, String description ){
        this.Cena_id = Cena_id;
        this.Cena_name = Cena_name;
        this.description = description;
    }

    public String getCena_id() {
        return Cena_id;
    }

    public String getCena_name() {
        return Cena_name;
    }

    public String getDescription() {
        return description;
    }

    public void setCena_id(String cena_id) {
        Cena_id = cena_id;
    }

    public void setCena_name(String cena_name) {
        Cena_name = cena_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return Cena_id + ";" + Cena_name + ";" + description ;
    }
}
