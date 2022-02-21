
package me.pronil.API;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Reflection {
    private static final String version;

    static {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        version = name.substring(name.lastIndexOf(46) + 1);
    }

    public static void sendChatPacket(Player p, String message) {
        Class<?> packetClazz = Reflection.getNMSClass("PacketPlayOutChat");
        Class<?> ichatbasecomponentClazz = Reflection.getNMSClass("IChatBaseComponent");
        Class<?> serializerClazz = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
        try {
            Object obj = packetClazz.getConstructor(ichatbasecomponentClazz).newInstance(serializerClazz.getMethod("a", String.class).invoke(null, message));
            Object handle = Reflection.getHandle(p);
            assert handle != null;
            Object connection = Objects.requireNonNull(Reflection.getField(handle.getClass(), "playerConnection")).get(handle);
            Method sendToPlayer = connection.getClass().getMethod("sendPacket", NMS.Packet);
            sendToPlayer.invoke(connection, obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + Reflection.getVersion() + "." + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Class<?> getNMSClassException(String className) throws Exception {
        String fullName = "net.minecraft.server." + Reflection.getVersion() + "." + className;
        return Class.forName(fullName);
    }

    public static Object getHandle(Player obj) {
        try {
            return Objects.requireNonNull(Reflection.getMethod(obj.getClass(), "getHandle", new Class[0])).invoke(obj);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
            return field;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?>[] args) {
        Method[] methodArray = clazz.getMethods();
        int n = methodArray.length;
        int n2 = 0;
        while (n2 < n) {
            Method m = methodArray[n2];
            if (m.getName().equals(name) && (args.length == 0 || Reflection.ClassListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
            ++n2;
        }
        return null;
    }

    public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equals = true;
        if (l1.length != l2.length) {
            return false;
        }
        int i = 0;
        while (i < l1.length) {
            if (l1[i] != l2[i]) {
                return false;
            }
            ++i;
        }
        return equals;
    }

    public static String getVersion() {
        return version;
    }

    public static class JSONMessage {
        private final JSONObject chatObject = new JSONObject();

        public JSONMessage(String text) {
            this.chatObject.put("text", text);
        }

        public void addExtra(ChatExtra extraObject) {
            if (!this.chatObject.containsKey("extra")) {
                this.chatObject.put("extra", new JSONArray());
            }
            JSONArray extra = (JSONArray)this.chatObject.get("extra");
            extra.add(extraObject.toJSON());
            this.chatObject.put("extra", extra);
        }

        public String toString() {
            return this.chatObject.toJSONString();
        }

        public static class ChatExtra {
            private final JSONObject chatExtra = new JSONObject();

            public ChatExtra(String text) {
                this.chatExtra.put("text", text);
            }

            public void addClickEvent(ClickEventType action, String value) {
                JSONObject clickEvent = new JSONObject();
                clickEvent.put("action", action.getTypeString());
                clickEvent.put("value", value);
                this.chatExtra.put("clickEvent", clickEvent);
            }

            public void addHoverEvent(HoverEventType action, String value) {
                JSONObject hoverEvent = new JSONObject();
                hoverEvent.put("action", action.getTypeString());
                hoverEvent.put("value", value);
                this.chatExtra.put("hoverEvent", hoverEvent);
            }

            public JSONObject toJSON() {
                return this.chatExtra;
            }

        }

        public enum ClickEventType {
            RUN_COMMAND("run_command");

            private final String type;

            ClickEventType(String type) {
                this.type = type;
            }

            public String getTypeString() {
                return this.type;
            }
        }

        public enum HoverEventType {
            SHOW_TEXT("show_text");

            private final String type;

            HoverEventType(String type) {
                this.type = type;
            }

            public String getTypeString() {
                return this.type;
            }
        }
    }
}
 