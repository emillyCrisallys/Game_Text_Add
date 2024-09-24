package Controle;
import Entidades.*;
import java.sql.*;

import java.util.Scanner;

public class TextAdventureGame {
        private Scene currentScene;
        private Inventory inventory;
        private PlayerSave playerSave;
        private Connection connection;

        public TextAdventureGame() {
            inventory = new Inventory();
            connectToDatabase();
            initializeGame();
        }

        private void connectToDatabase() {
            try {
                // Substitua com suas credenciais
                String url = "jdbc:mysql://localhost:3306/text_add";
                String user = "root";
                String password = "";
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void initializeGame() {
            // Cria um novo jogador e inicia a cena
            playerSave = new PlayerSave(1, 1); // Exemplo: jogador com ID 1 na cena 1
            loadScene(playerSave.getCurrentSceneId());
        }

        private void loadScene(int sceneId) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Cenas WHERE cena_id = " + sceneId);

                if (rs.next()) {
                    currentScene = new Scene(rs.getInt("cena_id"), rs.getString("cena_name"), rs.getString("description"));
                    System.out.println(currentScene); // Usando toString

                    // Carrega itens da cena
                    loadSceneItems(sceneId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void loadSceneItems(int sceneId) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Cena_Items WHERE item_cena_id = " + sceneId);

                while (rs.next()) {
                    SceneItem item = new SceneItem(
                            rs.getInt("item_id"),
                            rs.getString("item_name"),
                            rs.getString("item_description"),
                            rs.getBoolean("is_collectible"),
                            rs.getInt("item_cena_id") // Atributo novo
                    );
                    System.out.println("Item na cena: " + item); // Usando toString
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void displayHelp() {
            System.out.println("Comandos disponíveis:");
            System.out.println("HELP - Mostra este menu de ajuda.");
            System.out.println("GET [ITEM] - Coleta um item e adiciona ao inventário.");
            System.out.println("INVENTORY - Exibe os itens que você possui.");
            System.out.println("SAVE - Salva o progresso atual do jogo.");
            System.out.println("RESTART - Reinicia o jogo para a cena inicial.");
        }

        private void executeCommand(String command) {
            String[] parts = command.split(" ", 2);
            String action = parts[0].toUpperCase();

            switch (action) {
                case "HELP":
                    displayHelp();
                    break;
                case "GET":
                    if (parts.length > 1) {
                        String itemName = parts[1];
                        getItem(itemName);
                    } else {
                        System.out.println("Você precisa especificar um item para coletar.");
                    }
                    break;
                case "INVENTORY":
                    inventory.displayItems();
                    break;
                case "SAVE":
                    saveProgress();
                    break;
                case "RESTART":
                    restartGame();
                    break;
                default:
                    System.out.println("Comando desconhecido. Digite 'HELP' para ver os comandos disponíveis.");
                    break;
            }
        }

        private void getItem(String itemName) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Cena_Items WHERE item_name = '" + itemName + "'");

                if (rs.next()) {
                    SceneItem item = new SceneItem(
                            rs.getInt("item_id"),
                            rs.getString("item_name"),
                            rs.getString("item_description"),
                            rs.getBoolean("is_collectible"),
                            rs.getInt("item_cena_id") // Atributo novo
                    );

                    if (item.Is_collectible()) {
                        inventory.addItem(item);
                        System.out.println("Você coletou: " + item);
                    } else {
                        System.out.println("Este item não é coletável.");
                    }
                } else {
                    System.out.println("Item não encontrado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void saveProgress() {
            try {
                PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Player_Save (current_Cena) VALUES (?)");
                pstmt.setInt(1, playerSave.getCurrentSceneId());
                pstmt.executeUpdate();
                System.out.println("Progresso salvo!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void restartGame() {
            playerSave.setCurrentSceneId(1); // Reinicia para a cena 1
            loadScene(playerSave.getCurrentSceneId());
            System.out.println("O jogo foi reiniciado.");
        }

        public static void main(String[] args) {
            TextAdventureGame game = new TextAdventureGame();
            Scanner scanner = new Scanner(System.in);
            String command;

            while (true) {
                System.out.print("> ");
                command = scanner.nextLine();
                game.executeCommand(command);
            }
        }
    }
