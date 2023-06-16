package com.davidout.customenchants.commands;

import com.davidout.api.custom.command.CustomCommand;
import com.davidout.customenchants.gui.RecipeGui;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class KeyRecipeCommand implements CustomCommand {
    @Override
    public String getCommandName() {
        return "keyrecipe";
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("kr");
    }

    @Override
    public List<CustomCommand> getSubCommands() {
        return Arrays.asList();
    }

    @Override
    public boolean executeCommand(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof Player)) return true;
        Player player = (Player) commandSender;
        RecipeGui recipeGui = new RecipeGui();
        recipeGui.openInventory(player);
        return false;
    }

    @Override
    public List<String> autoCompleteCommand(CommandSender commandSender, String[] strings) {
        return null;
    }
}
