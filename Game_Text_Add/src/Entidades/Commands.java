package Entidades;

public class Commands {
    private int command_id;
    private Cenas cena_id;
    private String command_name;
    private String command_description;

    public Commands (){

    }

    public Commands(String command_description, String command_name, int command_id) {
        this.command_description = command_description;
        this.command_name = command_name;
        this.command_id = command_id;
    }

    public int getCommand_id() {
        return command_id;
    }

    public void setCommand_id(int command_id) {
        this.command_id = command_id;
    }

    public String getCommand_name() {
        return command_name;
    }

    public void setCommand_name(String command_name) {
        this.command_name = command_name;
    }

    public String getCommand_description() {
        return command_description;
    }

    public void setCommand_description(String command_description) {
        this.command_description = command_description;
    }

    @Override
    public String toString() {
        return command_id + ";" + command_name + ";" + command_description;
    }
}
