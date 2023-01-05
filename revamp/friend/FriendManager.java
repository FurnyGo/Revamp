// 
// Decompiled by Procyon v0.5.36
// 

package revamp.friend;

import java.util.Iterator;
import java.util.ArrayList;

public class FriendManager
{
    public static ArrayList<Friend> friends;
    
    static {
        FriendManager.friends = new ArrayList<Friend>();
    }
    
    public static void addFriend(final String name) {
        FriendManager.friends.add(new Friend(name));
    }
    
    public static void removeFriend(final String name) {
        if (getFriend(name) != null) {
            FriendManager.friends.remove(getFriend(name));
        }
    }
    
    public static boolean isFriend(final String name) {
        return getFriend(name) != null;
    }
    
    public static Friend getFriend(final String name) {
        for (final Friend f : FriendManager.friends) {
            if (!f.name.equalsIgnoreCase(name)) {
                continue;
            }
            return f;
        }
        return null;
    }
}
