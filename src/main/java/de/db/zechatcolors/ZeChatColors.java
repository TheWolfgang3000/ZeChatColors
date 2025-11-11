package de.db.zechatcolors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ZeChatColors extends JavaPlugin implements Listener {

    private static final Logger log = Logger.getLogger("Minecraft");

    private final String VALID_COLOR_CHARS = "0123456789abcdef";

    private final Map<String, String> playerColors = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        log.info("[ZeChatColors] ZeChatColors activated!");
    }

    @Override
    public void onDisable() {
        log.info("[ZeChatColors] ZeChatColors deactivated.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("These Commands can only be used by Players.");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("cc")) {
            sendColorList(player);
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setcolor")) {
            handleSetColor(player, args);
            return true;
        }

        return false;
    }

    private void sendColorList(Player player) {
        player.sendMessage(ChatColor.GOLD + "--- Minecraft Color Codes ---");
        player.sendMessage(c("& 0 &0 (Black)"));
        player.sendMessage(c("& 1 &1 (Dark Blue)"));
        player.sendMessage(c("& 2 &2 (Dark Green)"));
        player.sendMessage(c("& 3 &3 (Dark Aqua)"));
        player.sendMessage(c("& 4 &4 (Dark Red)"));
        player.sendMessage(c("& 5 &5 (Dark Purple)"));
        player.sendMessage(c("& 6 &6 (Gold)"));
        player.sendMessage(c("& 7 &7 (Gray)"));
        player.sendMessage(c("& 8 &8 (Dark Gray)"));
        player.sendMessage(c("& 9 &9 (Blue)"));
        player.sendMessage(c("& a &a (Green)"));
        player.sendMessage(c("& b &b (Aqua)"));
        player.sendMessage(c("& c &c (Red)"));
        player.sendMessage(c("& d &d (Light Purple)"));
        player.sendMessage(c("& e &e (Yellow)"));
        player.sendMessage(c("& f &f (White)"));
        player.sendMessage(ChatColor.GOLD + "-------------------------");
    }

    private void handleSetColor(Player player, String[] args) {

        if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("reset"))) {
            if (playerColors.remove(player.getName()) != null) {
                player.sendMessage(c("&aYour standard Color has been removed."));
            } else {
                player.sendMessage(c("&cYou haven't set a Color."));
            }
            return;
        }

        String colorCode = args[0];

        if (colorCode.length() != 2 ||
                colorCode.charAt(0) != '&' ||
                VALID_COLOR_CHARS.indexOf(Character.toLowerCase(colorCode.charAt(1))) == -1) {

            player.sendMessage(c("&cThis isn't a valid Color code!"));
            player.sendMessage(c("&fUse for example '&a' oder '&9'."));
            player.sendMessage(c("&fFormat-Codes (&l, &k etc.) are not allowed."));
            player.sendMessage(c("&fUse /cc for a list of Codes."));
            return;
        }

        playerColors.put(player.getName(), colorCode);

        player.sendMessage(c("&aYour Chat color is now: " + colorCode + "This Text has YOUR Color."));
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (playerColors.containsKey(playerName)) {
            String color = playerColors.get(playerName);
            String message = event.getMessage();
            event.setMessage(c(color + message));
        }
    }

    private String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}