// 
// Decompiled by Procyon v0.5.36
// 

package revamp.interfaces;

import com.google.gson.JsonObject;

public interface IConfig
{
    void loadConfig(final JsonObject p0);
    
    JsonObject saveConfig();
}
