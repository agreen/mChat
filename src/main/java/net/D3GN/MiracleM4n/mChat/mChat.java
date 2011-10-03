package net.D3GN.MiracleM4n.mChat;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

import org.bukkit.entity.Player;
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

import de.bananaco.permissions.info.InfoReader;
import de.bananaco.permissions.worlds.WorldPermissionsManager;

import org.blockface.bukkitstats.CallHome;

public class mChat extends JavaPlugin {
    PluginManager pm;

    // Listeners
    MPlayerListener pListener;
    MCommandSender cSender;
    MConfigListener cListener;
    MIConfigListener mIListener;
    MCConfigListener mCListener;

    // API
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

    // PermissionsBukkit
    Boolean PermissionBuB = false;

    // bPermissions
    public WorldPermissionsManager bPermS;
    public InfoReader bInfoR;
    Boolean bPermB;

    // mChannel
    Boolean mChannelB = false;

    // Configuration
    Configuration mConfig = null;
    Configuration mIConfig = null;
    Configuration mCConfig = null;

    // Optional mChat only Info Support
    Boolean mChat_Info_Only = false;

    // API Only Boolean
    Boolean mAPI_Only_Mode = false;

    // Fomatting Event Messages Boolean
    Boolean mFormat_Events = true;

    // Formatting
    String chatFormat = "+p+dn+s&f: +m";
    String nameFormat = "+p+dn+s&e";
    String joinFormat = "+p+dn+s&e";
    String dateFormat = "HH:mm:ss";
    String joinMessage = "has joined the game.";
    String leaveMessage = "has left the game.";
    String kickMessage = "has been kicked from the game +r.";

    // Other Config Stuff
    Double chatDistance = -1.0;

    // InfoHashMaps
    TreeMap<String, Object> usersMap = new TreeMap<String, Object>();
    TreeMap<String, Object> groupsMap = new TreeMap<String, Object>();

    // Censor String List
    HashMap<String, Object> censorMap = new HashMap<String, Object>();

    public void onEnable() {
        // Default plugin data
        pm = getServer().getPluginManager();
        mConfig = new Configuration(new File(getDataFolder(), "config.yml"));
        mIConfig = new Configuration(new File(getDataFolder(), "info.yml"));
        mCConfig = new Configuration(new File(getDataFolder(), "censor.yml"));

        PluginDescriptionFile pdfFile = getDescription();

        // Initialize Listeners
        if (!mAPI_Only_Mode)
            pListener = new MPlayerListener(this);

        cSender = new MCommandSender(this);
        cListener = new MConfigListener(this);
        mIListener = new MIConfigListener(this);
        mCListener = new MCConfigListener(this);

        // Initialize the API
        API = new mChatAPI(this);
        mAPI = new mChatAPI(this);

        // Setup Permissions
        setupSuperPerms();

        // Setup mChannel
        setupmChannel();

        // Setup Configs
        setupConfigs();

        // Register Events
        if (!mAPI_Only_Mode) {
            pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Priority.Normal, this);
            if (mFormat_Events) {
                pm.registerEvent(Event.Type.PLAYER_KICK, pListener, Priority.Normal, this);
                pm.registerEvent(Event.Type.PLAYER_JOIN, pListener, Priority.Normal, this);
                pm.registerEvent(Event.Type.PLAYER_QUIT, pListener, Priority.Normal, this);
            }
        }

        // Register Commands
        getCommand("mchat").setExecutor(cSender);

        // Call Home                                        `
        CallHome.load(this);

        mAPI.log("[" + (pdfFile.getName()) + "] mChat version " + pdfFile.getVersion() + " is enabled!");

        for (Player players : getServer().getOnlinePlayers())
            if (usersMap.get("users." + players.getName()) == null)
                mIListener.addDefaultPlayer(players);
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();

        mAPI.log("[" + (pdfFile.getName()) + "] mChat version " + pdfFile.getVersion() + " is disabled!");
    }

    private void setupSuperPerms() {
        Plugin PermissionsBukkitTest = getServer().getPluginManager().getPlugin("PermissionsBukkit");
        PluginDescriptionFile pdfFile = getDescription();

        if (PermissionsBukkitTest != null) {
            PermissionBuB = true;
            mAPI.log("[" + pdfFile.getName() + "] PermissionsBukkit " + (PermissionsBukkitTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            PermissionBuB  = false;
            setupbPerms();
        }
    }

    protected void setupbPerms() {
        Plugin bPermTest = getServer().getPluginManager().getPlugin("bPermissions");
        PluginDescriptionFile pdfFile = getDescription();

        if (bPermTest != null) {
            bPermB = true;
            bInfoR = de.bananaco.permissions.Permissions.getInfoReader();
            bPermS = de.bananaco.permissions.Permissions.getWorldPermissionsManager();
            mAPI.log("[" + pdfFile.getName() + "] bPermissions " + (bPermTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            bPermB  = false;
            setupPEX();
        }
    }

    protected void setupPEX() {
        Plugin pexTest = getServer().getPluginManager().getPlugin("PermissionsEx");
        PluginDescriptionFile pdfFile = getDescription();

        if (pexTest != null) {
            pexPermissions = PermissionsEx.getPermissionManager();
            PEXB = true;
            mAPI.log("[" + pdfFile.getName() + "] PermissionsEx " + (pexTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            PEXB = false;
            setupPermissions();
        }
    }

    protected void setupPermissions() {
        Plugin permTest = getServer().getPluginManager().getPlugin("Permissions");
        PluginDescriptionFile pdfFile = getDescription();

        if(permTest != null) {
            permissions = ((Permissions) permTest).getHandler();
            permissionsB = true;
            permissions3 = permTest.getDescription().getVersion().startsWith("3");
            mAPI.log("[" + pdfFile.getName() + "] Permissions " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            permissionsB = false;
            permissions3 = false;
            setupGroupManager();
        }
    }

    protected void setupGroupManager() {
        Plugin permTest = getServer().getPluginManager().getPlugin("GroupManager");
        PluginDescriptionFile pdfFile = getDescription();

        if (permTest != null) {
            gmPermissionsB = true;
            mAPI.log("[" + pdfFile.getName() + "] GroupManager " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            gmPermissionsB = false;
            mAPI.log("[" + pdfFile.getName() + "] No Permissions plugins were found defaulting to permissions.yml/info.yml");
        }
    }

    protected void setupmChannel() {
        Plugin mChannelTest = getServer().getPluginManager().getPlugin("mChannel");
        PluginDescriptionFile pdfFile = getDescription();

        if (mChannelTest != null) {
            mChannelB = true;
            mAPI.log("[" + pdfFile.getName() + "] " +  mChannelTest.getDescription().getName() + " " + (mChannelTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            mChannelB = false;
            mAPI.log("[" + pdfFile.getName() + "] mChannel not found not using.");
        }
    }

    protected void setupConfigs() {
        cListener.checkConfig();
        cListener.loadConfig();

        mIListener.checkConfig();
        mIListener.loadConfig();

        mCListener.loadConfig();
    }
}
