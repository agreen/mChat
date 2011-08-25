package net.D3GN.MiracleM4n.mChat;

import java.io.File;
import java.util.TreeMap;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.plugin.Plugin;

public class mChat extends JavaPlugin {
    PluginManager pm;

    //Listeners
    MPlayerListener pListener;
    MCommandSender cSender;
    MConfigListener cListener;
    MIConfigListener mIListener;

    //API
    public static mChatAPI API = null;
    mChatAPI mAPI = null;

    // Permissions
    public PermissionHandler permissions;
    Boolean permissions3;
    Boolean permissionsB = false;

    // GroupManager
    public AnjoPermissionsHandler gmPermissions;
    Boolean gmPermissionsB = false;

    //SuperPermsBridge
    Boolean superBridge;

    //PermissionsBukkit
    Boolean PermissionBuB = false;

    // Coloring & Configuration
    ColouredConsoleSender console = null;
    Configuration config = null;
    Configuration mIConfig = null;

    // Information
    String infoResolve;

    //API Only Boolean
    Boolean mAPI_Only_Mode = false;

    // Formatting
    String chatFormat = "+hb+p+dn+s&f: +message";
    String nameFormat = "+p+dn+s&e";
    String joinFormat = "+p+dn+s&e";
    String dateFormat = "HH:mm:ss";
    String joinMessage = "has joined the game.";
    String leaveMessage = "has left the game.";
    String kickMessage = "has been kicked from the game.";

    //InfoHasMaps
    TreeMap<String, Object> infoMap = new TreeMap<String, Object>();
    TreeMap<String, Object> otherMap = new TreeMap<String, Object>();

    public void onEnable() {
        // Default plugin data
        pm = getServer().getPluginManager();
        config = new Configuration(new File(getDataFolder(), "config.yml"));
        mIConfig = new Configuration(new File(getDataFolder(), "info.yml"));
        console = new ColouredConsoleSender((CraftServer) getServer());
        PluginDescriptionFile pdfFile = getDescription();

        // Initialize Listeners and Configurations
        if (!mAPI_Only_Mode)
            pListener = new MPlayerListener(this);

        cSender = new MCommandSender(this);
        cListener = new MConfigListener(this);
        mIListener = new MIConfigListener(this);

        setupPermissions();

        // Initialize the API
        API = new mChatAPI(this);
        mAPI = new mChatAPI(this);

        //Setup Configs
        if (!(new File(getDataFolder(), "config.yml")).exists()) {
            cListener.defaultConfig();
            cListener.checkConfig();
            cListener.loadConfig();
        } else {
            cListener.checkConfig();
            cListener.loadConfig();
        }

        if (!(new File(getDataFolder(), "info.yml")).exists()) {
            mIListener.defaultConfig();
            mIListener.checkConfig();
            mIListener.loadConfig();
            mAPI.refreshMaps();
        } else {
            mIListener.checkConfig();
            mIListener.loadConfig();
            mAPI.refreshMaps();
        }

        //Register Events
        if (!mAPI_Only_Mode) {
            pm.registerEvent(Event.Type.PLAYER_KICK, pListener, Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, Priority.Normal, this);
            pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Normal, this);
        }

        //Register Commands
        getCommand("mchat").setExecutor(cSender);

        console.sendMessage("[" + (pdfFile.getName()) + "] mChat version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();

        console.sendMessage("[" + (pdfFile.getName()) + "] mChat version " + pdfFile.getVersion() + " is disabled!");
    }

    private void setupPermissions() {
        Plugin bPermTest = this.getServer().getPluginManager().getPlugin("bPermissions");
        Plugin permTest = this.getServer().getPluginManager().getPlugin("Permissions");
        PluginDescriptionFile pdfFile = getDescription();

        if(permissions != null)
            return;

        if(permTest != null) {
            if (superBridge = permTest.getDescription().getVersion().startsWith("2.7.7")) {
                System.out.println("[" + pdfFile.getName() + "]" + " Permissions not found, Checking for GroupManager.");
                permissionsB = false;
            } else if (bPermTest != null) {
                System.out.println("[" + pdfFile.getName() + "]" + " Permissions not found, Checking for GroupManager.");
                permissionsB = false;
            } else {
                permissions = ((Permissions) permTest).getHandler();
                permissionsB = true;
                permissions3 = permTest.getDescription().getVersion().startsWith("3");

                System.out.println("[" + pdfFile.getName() + "]" + " Permissions " + (permTest.getDescription().getVersion()) + " found hooking in.");
            }
        } else {
            permissionsB = false;
            permissions3 = false;
            System.out.println("[" + pdfFile.getName() + "]" + " Permissions not found, Checking for GroupManager.");
            setupGroupManager();
        }
    }

    private void setupGroupManager() {
        Plugin permTest = this.getServer().getPluginManager().getPlugin("GroupManager");
        PluginDescriptionFile pdfFile = getDescription();

        if(gmPermissions != null)
            return;

        if (permTest != null) {
            gmPermissionsB = true;
            System.out.println("[" + pdfFile.getName() + "]" + " GroupManager " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            gmPermissionsB = false;
            System.out.println("[" + pdfFile.getName() + "]" + " GroupManager not found, Checking for PermissionsBukkit.");
            setupPermissionsBukkit();
        }
    }

    private void setupPermissionsBukkit() {
        Plugin PermissionsBukkitTest = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");
        PluginDescriptionFile pdfFile = getDescription();

        if (PermissionsBukkitTest != null) {
            PermissionBuB = true;
            System.out.println("[" + pdfFile.getName() + "]" + " PermissionsBukkit " + (PermissionsBukkitTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            PermissionBuB  = false;
            System.out.println("[" + pdfFile.getName() + "]" + " PermissionsBukkit not found, using superperms.");
        }
    }
}
