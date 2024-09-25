package Entidades;

public class Scene {
    private int id_scenes;
    private String scene_name;
    private String scene_description;



    public Scene(int id_scenes, String scene_name, String scene_description) {
        this.id_scenes = id_scenes;
        this.scene_name = scene_name;
        this.scene_description = scene_description;

    }

    public int getId_scenes() {
        return id_scenes;
    }

    public String getName() {
        return scene_name;
    }

    public String getScene_description() {
        return scene_description;
    }

    public String getScene_name() {
        return scene_name;
    }


    @Override
    public String toString() {
        return scene_name + "\n" + scene_description;
    }
}
