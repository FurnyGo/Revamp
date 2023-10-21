package fiveware.friend;

import java.util.ArrayList;
import java.util.Iterator;

public class FriendManager {
   private ArrayList<FriendManager.Friend> friends = new ArrayList();

   public void addFriend(String name) {
      this.friends.add(new FriendManager.Friend(name));
   }

   public void removeFriend(String name) {
      if (this.getFriendByName(name) != null) {
         this.friends.remove(this.getFriendByName(name));
      }

   }

   public void clearFriends() {
      this.friends.clear();
   }

   public boolean isFriend(String name) {
      return this.getFriendByName(name) != null;
   }

   public ArrayList<FriendManager.Friend> getFriends() {
      return this.friends;
   }

   public FriendManager.Friend getFriendByName(String name) {
      Iterator var3 = this.friends.iterator();

      while(var3.hasNext()) {
         FriendManager.Friend f = (FriendManager.Friend)var3.next();
         if (f.name.equalsIgnoreCase(name)) {
            return f;
         }
      }

      return null;
   }

   public class Friend {
      public String name;

      public Friend(String name) {
         this.name = name;
      }
   }
}
