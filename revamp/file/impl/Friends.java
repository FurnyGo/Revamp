// 
// Decompiled by Procyon v0.5.36
// 

package revamp.file.impl;

import java.util.Iterator;
import revamp.friend.Friend;
import java.io.FileWriter;
import java.io.IOException;
import revamp.friend.FriendManager;
import revamp.Revamp;
import java.io.BufferedReader;
import revamp.interfaces.IFile;
import revamp.file.Files;

@IFile(name = "friends")
public class Friends extends Files
{
    @Override
    public void readFile(final BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final FriendManager friendManager = Revamp.friendManager;
            FriendManager.addFriend(line);
        }
    }
    
    @Override
    public void writeFile(final FileWriter fileWriter) throws IOException {
        for (final Friend friend : FriendManager.friends) {
            fileWriter.write(String.valueOf(friend.name) + "\n");
        }
    }
}
