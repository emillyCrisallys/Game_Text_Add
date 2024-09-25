package Entidades;

public class SceneItem {

    private int item_Id;
    private String item_Name;
    private String item_description;
    private boolean is_collectible;
    private int item_scene_id;
    private String efeito_uso;
    private String mensagem_uso;

    public SceneItem(int item_Id, String item_Name, String item_description, boolean is_collectible, int item_scene_id, String efeito_uso, String mensagem_uso) {
        this.item_Id = item_Id;
        this.item_Name = item_Name;
        this.item_description = item_description;
        this.is_collectible = is_collectible;
        this.item_scene_id = item_scene_id;
        this.efeito_uso = efeito_uso;
        this.mensagem_uso = mensagem_uso;
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

    public String getEfeito_uso() {
        return efeito_uso;
    }

    public String getMensagem_uso() {
        return mensagem_uso;
    }


}
