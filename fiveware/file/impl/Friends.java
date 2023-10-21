package fiveware.file.impl;

import fiveware.Fiveware;
import fiveware.file.Files;
import fiveware.friend.FriendManager;
import fiveware.interfaces.IFile;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

@IFile(
   name = "friends"
)
public class Friends extends Files {
   public void readFile(BufferedReader bufferedReader) throws IOException {
      String line;
      while((line = bufferedReader.readLine()) != null) {
         if (!Fiveware.friendManager.isFriend(line)) {
            Fiveware.friendManager.addFriend(line);
         }
      }

   }

   public void writeFile(FileWriter fileWriter) throws IOException {
      Iterator var3 = Fiveware.friendManager.getFriends().iterator();

      while(var3.hasNext()) {
         FriendManager.Friend friend = (FriendManager.Friend)var3.next();
         fileWriter.write(friend.name + "\n");
      }

   }
}
