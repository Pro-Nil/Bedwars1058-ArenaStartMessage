
package me.pronil.API;


class NMS {
    final static boolean init;
    public static Class<?> Packet;
    public static Class<?> PacketPlayOutTitle;
    public static Class<?> IChatBaseComponent;
    public static Class<?> IChatBaseComponent$ChatSerializer;
    public static Class<?> PacketPlayOutTitle$EnumTitleAction;

    static {
        init = true;
        try {
            Packet = Reflection.getNMSClassException("Packet");
            PacketPlayOutTitle = Reflection.getNMSClassException("PacketPlayOutTitle");
            IChatBaseComponent = Reflection.getNMSClassException("IChatBaseComponent");
            IChatBaseComponent$ChatSerializer = Reflection.getNMSClassException("IChatBaseComponent$ChatSerializer");
            PacketPlayOutTitle$EnumTitleAction = Reflection.getNMSClassException("PacketPlayOutTitle$EnumTitleAction");
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    NMS() {
    }
}
 