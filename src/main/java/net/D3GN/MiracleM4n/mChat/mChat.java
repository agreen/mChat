package net.D3GN.MiracleM4n.mChat;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class mChat extends JavaPlugin {
    PluginManager pm;

    //Listeners
    MPlayerListener pListener;
    MCommandSender cSender;
    MConfigListener cListener;
    MIConfigListener mIListener;
    MCConfigListener mCListener;

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

    // PermissionsEX
    public PermissionManager pexPermissions;
    Boolean PEXB = false;

    //PermissionsBukkit
    Boolean PermissionBuB = false;

    // Coloring & Configuration
    Logger console = null;
    Configuration config = null;
    Configuration mIConfig = null;
    Configuration mCConfig = null;

    // Information
    String infoResolve;

    //API Only Boolean
    Boolean mAPI_Only_Mode = false;

    // Formatting
    String chatFormat = "+p+dn+s&f: +m";
    String nameFormat = "+p+dn+s&e";
    String joinFormat = "+p+dn+s&e";
    String dateFormat = "HH:mm:ss";
    String joinMessage = "has joined the game.";
    String leaveMessage = "has left the game.";
    String kickMessage = "has been kicked from the game for +r.";

    //InfoHashMaps
    TreeMap<String, Object> infoMap = new TreeMap<String, Object>();
    TreeMap<String, Object> otherMap = new TreeMap<String, Object>();

    //Censor String List
    HashMap<String, Object> censorMap = new HashMap<String, Object>();

    public void onEnable() {
        // Default plugin data
        pm = getServer().getPluginManager();
        config = new Configuration(new File(getDataFolder(), "config.yml"));
        mIConfig = new Configuration(new File(getDataFolder(), "info.yml"));
        mCConfig = new Configuration(new File(getDataFolder(), "censor.yml"));
        console = getServer().getLogger();
        PluginDescriptionFile pdfFile = getDescription();

        // Initialize Listeners and Configurations
        if (!mAPI_Only_Mode)
            pListener = new MPlayerListener(this);

        cSender = new MCommandSender(this);
        cListener = new MConfigListener(this);
        mIListener = new MIConfigListener(this);
        mCListener = new MCConfigListener(this);

        setupSuperPerms();

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

        if (!(new File(getDataFolder(), "censor.yml")).exists()) {
            mCListener.defaultConfig();
            mCListener.loadConfig();
        } else {
            mCListener.loadConfig();
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

        console.log(Level.INFO, "[" + (pdfFile.getName()) + "] mChat version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();

        console.log(Level.INFO, "[" + (pdfFile.getName()) + "] mChat version " + pdfFile.getVersion() + " is disabled!");
    }

    private void setupSuperPerms() {
        Plugin bPermTest = this.getServer().getPluginManager().getPlugin("bPermissions");
        Plugin PermissionsBukkitTest = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");
        PluginDescriptionFile pdfFile = getDescription();

        if (PermissionsBukkitTest != null) {
            PermissionBuB = true;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " PermissionsBukkit " + (PermissionsBukkitTest.getDescription().getVersion()) + " found hooking in.");
        } else if (bPermTest != null) {
            PermissionBuB = true;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " bPermissions " + (PermissionsBukkitTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            PermissionBuB  = false;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " A superperms Permissions plugin was not found, Checking for PermissionsEX.");
            setupPEX();
        }
    }

    private void setupPEX() {
        Plugin pexTest = this.getServer().getPluginManager().getPlugin("PermissionsEx");
        PluginDescriptionFile pdfFile = getDescription();

        if (pexTest != null) {
            pexPermissions = PermissionsEx.getPermissionManager();
            PEXB = true;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " PermissionsEx " + (pexTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            PEXB = false;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " PermissionsEx was not found, Checking for Permissions.");
            setupPermissions();
        }
    }

    private void setupPermissions() {
        Plugin permTest = this.getServer().getPluginManager().getPlugin("Permissions");
        PluginDescriptionFile pdfFile = getDescription();

        if(permTest != null) {
            permissions = ((Permissions) permTest).getHandler();
            permissionsB = true;
            permissions3 = permTest.getDescription().getVersion().startsWith("3");
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " Permissions " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            permissionsB = false;
            permissions3 = false;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " Permissions not found, Checking for GroupManager.");
            setupGroupManager();
        }
    }

    private void setupGroupManager() {
        Plugin permTest = this.getServer().getPluginManager().getPlugin("GroupManager");
        PluginDescriptionFile pdfFile = getDescription();

        if (permTest != null) {
            gmPermissionsB = true;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " GroupManager " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            gmPermissionsB = false;
            console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " No Permissions plugins were found defaulting to permissions.yml");
        }
    }
}
