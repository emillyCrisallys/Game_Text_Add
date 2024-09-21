package Entidades;

public class Inventory {
    private Player_Save player_Id;
    private Cena_Items item_ID;

    public Inventory (){

    }

    public Inventory(Player_Save player_Id, Cena_Items item_ID) {
        this.player_Id = player_Id;
        this.item_ID = item_ID;
    }

    public Player_Save getPlayer_Id() {
        return player_Id;
    }

    public void setPlayer_Id(Player_Save player_Id) {
        this.player_Id = player_Id;
    }

    public Cena_Items getItem_ID() {
        return item_ID;
    }

    public void setItem_ID(Cena_Items item_ID) {
        this.item_ID = item_ID;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "player_Id=" + player_Id +
                ", item_ID=" + item_ID +
                '}';
    }
}
