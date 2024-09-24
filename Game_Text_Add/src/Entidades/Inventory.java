

package Entidades;
import java.util.ArrayList;
import java.util.List;


public class Inventory {
    private List<SceneItem> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(SceneItem item) {
        items.add(item);
    }

    public void displayItems() {
        if (items.isEmpty()) {
            System.out.println("Você não tem itens no inventário.");
        } else {
            System.out.println("Itens no inventário:");
            for (SceneItem item : items) {
                System.out.println("- " + item);
            }
        }
    }
}
