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
import java.util.logging.Logger; // <-- FIX: Korrekter Import für den Logger

public class ZeChatColors extends JavaPlugin implements Listener {

    // --- ANPASSUNG (FIX) ---
    // Wir holen uns den Standard-Logger, da getLogger() nicht existiert
    private static final Logger log = Logger.getLogger("Minecraft");
    // --- ENDE ANPASSUNG ---

    // Diese "Notizliste" speichert die Farbwahl der Spieler
    // Key: Spielername (String), Value: Farbcode (String, z.B. "&9")
    private final Map<String, String> playerColors = new HashMap<>();

    @Override
    public void onEnable() {
        // Registriert dieses Plugin als "Zuhörer" für Events (wie das Chat-Event)
        getServer().getPluginManager().registerEvents(this, this);

        // --- ANPASSUNG (FIX) ---
        // Wir benutzen jetzt unsere 'log' Variable
        log.info("[ZeChatColors] ZeChatColors wurde aktiviert!");
        // --- ENDE ANPASSUNG ---
    }

    @Override
    public void onDisable() {
        // --- ANPASSUNG (FIX) ---
        // Wir benutzen jetzt unsere 'log' Variable
        log.info("[ZeChatColors] ZeChatColors wurde deaktiviert.");
        // --- ENDE ANPASSUNG ---
    }

    // Diese Methode wird für BEIDE Befehle aufgerufen
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Wir prüfen, ob der Absender ein Spieler ist (nicht die Konsole)
        if (!(sender instanceof Player)) {
            sender.sendMessage("Diese Befehle können nur von Spielern verwendet werden.");
            return true;
        }

        Player player = (Player) sender;

        // --- Logik für /farbcodes ---
        if (cmd.getName().equalsIgnoreCase("farbcodes")) {
            sendColorList(player);
            return true;
        }

        // --- Logik für /setcolor ---
        if (cmd.getName().equalsIgnoreCase("setcolor")) {
            handleSetColor(player, args);
            return true;
        }

        return false;
    }

    /**
     * Sendet dem Spieler die Liste der Farbcodes.
     */
    private void sendColorList(Player player) {
        player.sendMessage(ChatColor.GOLD + "--- Minecraft Farbcodes ---");
        player.sendMessage(c("&0 &0 (Schwarz)"));
        player.sendMessage(c("&1 &1 (Dunkelblau)"));
        player.sendMessage(c("&2 &2 (Dunkelgrün)"));
        player.sendMessage(c("&3 &3 (Dunkel-Aqua)"));
        player.sendMessage(c("&4 &4 (Dunkelrot)"));
        player.sendMessage(c("&5 &5 (Dunkellila)"));
        player.sendMessage(c("&6 &6 (Gold)"));
        player.sendMessage(c("&7 &7 (Grau)"));
        player.sendMessage(c("&8 &8 (Dunkelgrau)"));
        player.sendMessage(c("&9 &9 (Blau)"));
        player.sendMessage(c("&a &a (Grün)"));
        player.sendMessage(c("&b &b (Aqua)"));
        player.sendMessage(c("&c &c (Rot)"));
        player.sendMessage(c("&d &d (Helllila)"));
        player.sendMessage(c("&e &e (Gelb)"));
        player.sendMessage(c("&f &f (Weiß)"));
        player.sendMessage(ChatColor.GOLD + "--- Formatierung ---");
        player.sendMessage(c("&l &l (Fett)"));
        player.sendMessage(c("&m &m (Durchgestrichen)"));
        player.sendMessage(c("&n &n (Unterstrichen)"));
        player.sendMessage(c("&o &o (Kursiv)"));
        player.sendMessage(c("&k &k (Magisch)"));
        player.sendMessage(c("&r &r (Zurücksetzen)"));
    }

    /**
     * Verarbeitet den /setcolor Befehl.
     */
    private void handleSetColor(Player player, String[] args) {
        if (args.length == 0) {
            // Spieler hat keinen Code angegeben. Wir entfernen seine Farbe.
            if (playerColors.remove(player.getName()) != null) {
                player.sendMessage(c("&aDeine Standardfarbe wurde entfernt."));
            } else {
                player.sendMessage(c("&cDu hast keine Farbe festgelegt."));
                player.sendMessage(c("&fBenutzung: /setcolor <Farbcode> (z.B. &a)"));
            }
            return;
        }

        String colorCode = args[0];

        // Einfache Prüfung: Ist es ein gültiger Farbcode?
        // (Wir erlauben nur 2 Zeichen, z.B. "&a", und es muss mit '&' beginnen)
        if (colorCode.length() != 2 || colorCode.charAt(0) != '&') {
            player.sendMessage(c("&cDas ist kein gültiger Farbcode!"));
            player.sendMessage(c("&fBenutze z.B. '&a' oder '&9'."));
            player.sendMessage(c("&fBenutze /farbcodes für eine Liste."));
            return;
        }

        // Speichere die Wahl des Spielers in unserer "Notizliste"
        playerColors.put(player.getName(), colorCode);

        // Sende eine Bestätigung, die bereits die gewählte Farbe verwendet
        player.sendMessage(c("&aDeine Chatfarbe ist jetzt: " + colorCode + "Dieser Text hat deine Farbe."));
    }

    /**
     * Diese Methode wird JEDES Mal aufgerufen, wenn jemand chattet.
     */
    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        // Prüfen, ob der Spieler eine Farbe in unserer Liste hat
        if (playerColors.containsKey(playerName)) {
            // Ja, hat er. Hole die Farbe.
            String color = playerColors.get(playerName);

            // Hole die Original-Nachricht
            String message = event.getMessage();

            // Setze die neue Nachricht (Farbcode + Original-Nachricht)
            event.setMessage(c(color + message));
        }
        // Wenn der Spieler keine Farbe hat, tun wir einfach nichts.
    }

    /**
     * Eine kleine Helfer-Methode, um das ständige Tippen von
     * ChatColor.translateAlternateColorCodes zu ersparen.
     */
    private String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}