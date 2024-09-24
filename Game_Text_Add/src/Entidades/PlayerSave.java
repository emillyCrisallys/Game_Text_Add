package Entidades;

public class PlayerSave {
    private int playerId;
    private int currentSceneId;

    public PlayerSave(int playerId, int currentSceneId) {
        this.playerId = playerId;
        this.currentSceneId = currentSceneId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getCurrentSceneId() {
        return currentSceneId;
    }

    public void setCurrentSceneId(int currentSceneId) {
        this.currentSceneId = currentSceneId;
    }

    @Override
    public String toString() {
        return "Jogador ID: " + playerId + ", Cena Atual: " + currentSceneId;
    }
}
