package Entidades;

public class SceneItem {

    private int item_Id;
    private String item_Name;
    private String item_description;
    private boolean is_collectible;
    private int item_scene_id;

    public SceneItem(int itemId, String itemName, String itemDescription, boolean isCollectible, int itemSceneId) {
        this.item_Id = itemId;
        this.item_Name = itemName;
        this.item_description = itemDescription;
        this.is_collectible = isCollectible;
        this.item_scene_id = itemSceneId;
    }

    public int getItem_Id() {
        return item_Id;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public String getItem_description() {
        return item_description;
    }

    public boolean Is_collectible() {
        return is_collectible;
    }

    public int getItem_scene_id() {
        return item_scene_id;
    }

    @Override
    public String toString() {
        return item_Name + ": " + item_description + (is_collectible ? " (Coletável)" : "") + " [Cena ID: " + item_scene_id + "]";
    }

}
