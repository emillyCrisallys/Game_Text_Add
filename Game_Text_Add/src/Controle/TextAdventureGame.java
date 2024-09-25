package Controle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import Entidades.*;
import java.util.Scanner;

public class TextAdventureGame {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private Scanner scanner;
    private PlayerSave playerSave;


    public TextAdventureGame() {
        dbConnection = new DatabaseConnection();
        connection = dbConnection.connect();
        scanner = new Scanner(System.in);

        initializeGame();
    }

    public void initializeGame() {
        System.out.println("Bem-vindo ao Mundo Secreto de Coraline!");
        System.out.println("Você é Coraline, uma jovem curiosa e aventureira que acabou de se mudar para uma nova casa com sua família. " +
                "Você estava explorando a velha mansão e tropeçou em uma porta misteriosa escondida atrás de um papel de parede. " +
                "A porta está trancada, mas você sente que há algo estranho e maravilhoso do outro lado.");
        // Aqui você pode adicionar lógica para carregar o jogador ou criar um novo.
        playerSave = loadPlayerSave(1); // Carrega o jogador (ou cria novo)
        if (playerSave != null) {
            System.out.println("Carregando jogo salvo...");
            loadScene(playerSave.getCurrentSceneId());
        } else {
            System.out.println("Nenhum jogo salvo encontrado.");
            // Começar nova aventura
        }
        gameLoop();
    }

    private void gameLoop() {
        String command;
        while (true) {
            System.out.print("> ");
            command = scanner.nextLine().trim().toUpperCase();

            if (command.startsWith("CHECK ")) {
                // Extrai o nome do item do comando
                String itemName = command.substring(6).trim();
                if (!itemName.isEmpty()) {
                    checkSceneItem(itemName);
                } else {
                    System.out.println("Especifique o nome de um item para verificar.");
                }
            } else {
                switch (command) {

                    case "HELP":
                        showHelp();
                        break;
                    case "GET":
                        getItem();
                        break;
                    case "USE":
                        useItem();
                        break;
                    case "SAVE":
                        saveGame();
                        break;
                    case "LOAD":
                        loadGame();
                        break;
                    case "RESTART":
                        restartGame();
                        break;
                    case "CHECK INVENTORY": // Novo comando para checar o inventário
                        checkInventory();
                        break;
                    case "EXIT":
                        closeConnection();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Comando não reconhecido. Digite HELP para ajuda.");
                        break;
                }
            }
        }
    }

    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("HELP - Mostra esta ajuda.");
        System.out.println("GET [ITEM] - Coleta um item da cena.");
        System.out.println("USE [ITEM] - Usa um item do inventário.");
        System.out.println("CHECK INVENTORY - Checar a descrição dos itens do inventário");
        System.out.println("CHECK ITEM - Checar a descrição dos itens da cena");
        System.out.println("SAVE - Salva o jogo.");
        System.out.println("LOAD - Carrega o jogo salvo.");
        System.out.println("RESTART - Reinicia o jogo.");
        System.out.println("EXIT - Sair do jogo.");
    }




    // Implementação do comando GET
    private void getItem() {
        System.out.print("Digite o nome do item que deseja pegar: ");
        String itemName = scanner.nextLine().trim().toUpperCase();
        try {
            String query = "SELECT * FROM Scene_Items WHERE item_scene_id = ? AND item_name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, playerSave.getCurrentSceneId());
            stmt.setString(2, itemName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int itemId = rs.getInt("item_id");
                addItemToInventory(itemId, itemName);
                System.out.println("Você pegou o item: " + itemName);
            } else {
                System.out.println("Esse item não está disponível na cena.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Função para adicionar um item ao inventário
    private void addItemToInventory(int itemId, String itemName) {
        try {
            String query = "INSERT INTO inventory (player_id, item_id, item_name) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, playerSave.getPlayerId());
            stmt.setInt(2, itemId);
            stmt.setString(3, itemName);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Implementação do comando USE
    private void useItem() {
        System.out.print("Digite o nome do item que deseja usar: ");
        String itemName = scanner.nextLine().trim().toUpperCase();
        try {
            // Verificar se o item está no inventário
            String query = "SELECT * FROM inventory WHERE player_id = ? AND item_name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, playerSave.getPlayerId());
            stmt.setString(2, itemName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Verificar o efeito e a mensagem de uso do item na cena atual
                query = "SELECT efeito_uso, mensagem_uso FROM item_scene_id WHERE id_scenes = ? AND item_name = ?";
                PreparedStatement stmtEfeito = connection.prepareStatement(query);
                stmtEfeito.setInt(1, playerSave.getCurrentSceneId()); // Cena atual
                stmtEfeito.setString(2, itemName); // Nome do item
                ResultSet rsEfeito = stmtEfeito.executeQuery();

                if (rsEfeito.next()) {
                    String efeito = rsEfeito.getString("efeito_uso");
                    String mensagem = rsEfeito.getString("mensagem_uso");

                    // Exibir a mensagem dinamicamente
                    System.out.println(mensagem);

                    // Aplicar o efeito do item
                    aplicarEfeito(efeito); // Função que trata o efeito do item
                } else {
                    System.out.println("Este item não tem efeito nesta cena.");
                }

                rsEfeito.close();
                stmtEfeito.close();
            } else {
                System.out.println("Esse item não está no seu inventário.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Função para aplicar o efeito do item na cena
    private void aplicarEfeito(String efeito) {
        switch (efeito) {
            case "ABRIR_PORTA":
                System.out.println("A porta foi aberta!");
                advanceToNextScene();
                break;
            case "DESATIVAR_ARMADILHA":
                System.out.println("Você desativou uma armadilha!");

                break;

            default:
                System.out.println("Nada acontece...");
                break;
        }
    }

    // Função auxiliar para avançar para a próxima cena
    private void advanceToNextScene() {
        try {
            int nextCenaId = playerSave.getCurrentSceneId() + 1; // Avançar para a próxima cena (exemplo simples)
            playerSave.setCurrentSceneId(nextCenaId); // Atualizar a cena atual do jogador

            // Carregar a nova cena
            loadScene(nextCenaId);
            System.out.println("Você avançou para a próxima cena.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkInventory() {
        System.out.println("Seu inventário contém os seguintes itens:");

        try {
            String query = "SELECT item_name, item_description FROM inventory WHERE player_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, playerSave.getPlayerId());
            ResultSet rs = stmt.executeQuery();

            boolean hasItems = false;
            while (rs.next()) {
                hasItems = true;
                String itemName = rs.getString("item_name");
                String itemDescription = rs.getString("item_description");
                System.out.println(itemName + ": " + itemDescription);
            }

            if (!hasItems) {
                System.out.println("Seu inventário está vazio.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkSceneItem(String itemName) {
        try {
            // Consulta para verificar a descrição do item na cena atual
            String query = "SELECT item_description FROM Scene_Items WHERE item_scene_id = ? AND item_name = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, playerSave.getCurrentSceneId());  // Obtém o ID da cena atual
            stmt.setString(2, itemName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String itemDescription = rs.getString("item_description");
                System.out.println(itemName + ": " + itemDescription);
            } else {
                System.out.println("Este item não está presente na cena.");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Scene loadScene(int sceneId) {
        Scene scene = null;
        try {
            String query = "SELECT * FROM Scenes WHERE id_scenes = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, sceneId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                scene = new Scene(rs.getInt("id_scenes"), rs.getString("scene_name"), rs.getString("scene_description"));
                System.out.println(scene);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scene;
    }

    public PlayerSave loadPlayerSave(int playerId) {
        PlayerSave playerSave = null;
        try {
            String query = "SELECT * FROM Player_Save WHERE player_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                playerSave = new PlayerSave(rs.getInt("player_id"), rs.getInt("current_Cena"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerSave;
    }

    private void saveGame() {
        try {
            String query;
            if (playerSave != null) {
                // Atualiza o salvamento existente
                query = "UPDATE Player_Save SET current_Cena = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, playerSave.getCurrentSceneId());
                stmt.setInt(2, playerSave.getPlayerId());
                stmt.executeUpdate();
                System.out.println("Jogo salvo com sucesso!");
            } else {
                // Insere um novo salvamento
                query = "INSERT INTO Player_Save (player_id, current_Cena) VALUES (?, ?)";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, 1); // Defina o ID do jogador
                stmt.setInt(2, 1); // Defina a cena inicial (ou atual)
                stmt.executeUpdate();
                System.out.println("Novo salvamento criado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() {
        playerSave = loadPlayerSave(1); // Carregar o salvamento do jogador
        if (playerSave != null) {
            System.out.println("Jogo carregado com sucesso!");
            loadScene(playerSave.getCurrentSceneId()); // Carrega a cena após carregar o jogo
        } else {
            System.out.println("Nenhum salvamento encontrado.");
        }
    }

    private void restartGame() {
        playerSave = new PlayerSave(1, 1); // Reinicia com o ID do jogador e a cena inicial
        System.out.println("Jogo reiniciado.");
        loadScene(playerSave.getCurrentSceneId());
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TextAdventureGame();
    }
    }

