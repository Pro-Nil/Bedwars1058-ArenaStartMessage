
package me.pronil.API;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import me.pronil.API.NMS;
import me.pronil.API.Reflection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

class TitleUtils {
    private boolean ticks = true;
    private int fadeInTime;
    private int fadeOutTime;
    private int stayTime;
    private ChatColor color;
    private ChatColor subcolor;
    private final HashMap<String, String> json_data = new HashMap();

    public TitleUtils() {
        this.color = ChatColor.WHITE;
        this.subcolor = ChatColor.WHITE;
        this.setTimes(5, 5, 60);
    }

    public void useTicks(boolean ticks) {
        this.ticks = ticks;
    }

    public void setTimes(Integer in, Integer out, Integer stay) {
        this.fadeInTime = in == null ? this.fadeInTime : in;
        this.fadeOutTime = out == null ? this.fadeOutTime : out;
        this.stayTime = stay == null ? this.stayTime : stay;
    }

    public void setTitleColor(char color) {
        this.color = ChatColor.getByChar(color);
    }

    public void setSubtitleColor(char color) {
        this.subcolor = ChatColor.getByChar(color);
    }

    public void clearTitle(Player player) {
        try {
            Object handle = Reflection.getHandle(player);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object[] actions = NMS.PacketPlayOutTitle$EnumTitleAction.getEnumConstants();
            Constructor<?> constructor = NMS.PacketPlayOutTitle.getConstructor(NMS.PacketPlayOutTitle$EnumTitleAction, NMS.IChatBaseComponent);
            Object[] objectArray = new Object[2];
            objectArray[0] = actions[3];
            Object packet = constructor.newInstance(objectArray);
            Method sendToPlayer = connection.getClass().getMethod("sendPacket", NMS.Packet);
            sendToPlayer.invoke(connection, packet);
        }
        catch (Exception e) {
            System.out.println("An problem occurred during sending clear title packet ");
        }
    }

    public void resetTitle(Player player) {
        try {
            Object handle = Reflection.getHandle(player);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object[] actions = NMS.PacketPlayOutTitle$EnumTitleAction.getEnumConstants();
            Constructor<?> constructor = NMS.PacketPlayOutTitle.getConstructor(NMS.PacketPlayOutTitle$EnumTitleAction, NMS.IChatBaseComponent);
            Object[] objectArray = new Object[2];
            objectArray[0] = actions[4];
            Object packet = constructor.newInstance(objectArray);
            Method sendToPlayer = connection.getClass().getMethod("sendPacket", NMS.Packet);
            sendToPlayer.invoke(connection, packet);
        }
        catch (Exception e) {
            System.out.println("An problem occurred during sending reset title packet ");
        }
    }

    public void send(Player player, String title, String subtitle) {
        this.resetTitle(player);
        try {
            Object handle = Reflection.getHandle(player);
            Object connection = Reflection.getField(handle.getClass(), "playerConnection").get(handle);
            Object[] actions = NMS.PacketPlayOutTitle$EnumTitleAction.getEnumConstants();
            Constructor<?> constructor = NMS.PacketPlayOutTitle.getConstructor(NMS.PacketPlayOutTitle$EnumTitleAction, NMS.IChatBaseComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE);
            Object[] objectArray = new Object[5];
            objectArray[0] = actions[2];
            objectArray[2] = this.fadeInTime * (this.ticks ? 1 : 20);
            objectArray[3] = this.stayTime * (this.ticks ? 1 : 20);
            objectArray[4] = this.fadeOutTime * (this.ticks ? 1 : 20);
            Object packet = constructor.newInstance(objectArray);
            if (this.fadeInTime != -1 && this.fadeOutTime != -1 && this.stayTime != -1) {
                Method sendToPlayer = connection.getClass().getMethod("sendPacket", NMS.Packet);
                sendToPlayer.invoke(connection, packet);
            }
            this.addJSON("text", ChatColor.translateAlternateColorCodes('&', title));
            this.addJSON("color", this.color.name().toLowerCase());
            Object serialized = NMS.IChatBaseComponent$ChatSerializer.getMethod("a", String.class).invoke(null, this.toJSON());
            constructor = NMS.PacketPlayOutTitle.getConstructor(NMS.PacketPlayOutTitle$EnumTitleAction, NMS.IChatBaseComponent);
            packet = constructor.newInstance(actions[0], serialized);
            Method sendToPlayer = connection.getClass().getMethod("sendPacket", NMS.Packet);
            sendToPlayer.invoke(connection, packet);
            if (subtitle != null) {
                this.addJSON("text", ChatColor.translateAlternateColorCodes('&', subtitle));
                this.addJSON("color", this.subcolor.name().toLowerCase());
                serialized = NMS.IChatBaseComponent$ChatSerializer.getMethod("a", String.class).invoke(null, this.toJSON());
                constructor = NMS.PacketPlayOutTitle.getConstructor(NMS.PacketPlayOutTitle$EnumTitleAction, NMS.IChatBaseComponent);
                packet = constructor.newInstance(actions[1], serialized);
                sendToPlayer = connection.getClass().getMethod("sendPacket", NMS.Packet);
                sendToPlayer.invoke(connection, packet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("An problem occurred during sending title packet");
        }
    }

    private String toJSON() {
        JSONObject json = new JSONObject(this.json_data);
        this.json_data.clear();
        return json.toJSONString();
    }

    private void addJSON(String key, String value) {
        this.json_data.put(key, value);
    }
}
 
