package Entidades;

public class Cena_Items {
    private int item_id;
    private Cenas cena_id;
    private String item_name;
    private String item_description;
    private boolean isCollectible;

    public Cena_Items (){

    }

    public Cena_Items(int item_id, Cenas cena_id, String item_name, String item_description, boolean isCollectible) {
        this.item_id = item_id;
        this.cena_id = cena_id;
        this.item_name = item_name;
        this.item_description = item_description;
        this.isCollectible = isCollectible;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public Cenas getCena_id() {
        return cena_id;
    }

    public void setCena_id(Cenas cena_id) {
        this.cena_id = cena_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public boolean isCollectible() {
        return isCollectible;
    }

    public void setCollectible(boolean collectible) {
        isCollectible = collectible;
    }

    @Override
    public String toString() {
        return "Cena_Items{" +
                "item_id=" + item_id +
                ", cena_id=" + cena_id +
                ", item_name='" + item_name + '\'' +
                ", item_description='" + item_description + '\'' +
                ", isCollectible=" + isCollectible +
                '}';
    }
}
