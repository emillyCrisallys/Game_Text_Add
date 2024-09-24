package Entidades;

public class Scene {
    private int id_scenes;
    private String scene_name;
    private String scene_description;

    public Scene(int id_scenes, String name, String description) {
        this.id_scenes = id_scenes;
        this.scene_name = name;
        this.scene_description = description;
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

    @Override
    public String toString() {
        return "Cena: " + scene_name + "\n" + scene_description;
    }
}
