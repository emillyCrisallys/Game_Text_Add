package Entidades;

import java.sql.Timestamp;

public class Player_Save {
    private int player_Id;
    private int current_Cena;
    private Timestamp save_Date;

    public Player_Save (){

    }
    public Player_Save(int player_Id, int current_Cena, Timestamp save_Date) {
        this.player_Id = player_Id;
        this.current_Cena = current_Cena;
        this.save_Date = save_Date;
    }

    public int getPlayer_Id() {
        return player_Id;
    }

    public void setPlayer_Id(int player_Id) {
        this.player_Id = player_Id;
    }

    public int getCurrent_Cena() {
        return current_Cena;
    }

    public void setCurrent_Cena(int current_Cena) {
        this.current_Cena = current_Cena;
    }

    public Timestamp getSave_Date() {
        return save_Date;
    }

    public void setSave_Date(Timestamp save_Date) {
        this.save_Date = save_Date;
    }

    @Override
    public String toString() {
        return "Player_Save{" +
                "player_Id=" + player_Id +
                ", current_Cena=" + current_Cena +
                ", save_Date=" + save_Date +
                '}';
    }
}

