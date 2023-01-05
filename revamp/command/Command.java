// 
// Decompiled by Procyon v0.5.36
// 

package revamp.command;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public abstract class Command
{
    public String name;
    public String description;
    public List<String> alias;
    
    public Command(final String name, final String description, final String... alias) {
        this.alias = new ArrayList<String>();
        this.name = name;
        this.description = description;
        this.alias = Arrays.asList(alias);
    }
    
    public abstract void onCommand(final String[] p0, final String p1);
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public List<String> getAlias() {
        return this.alias;
    }
    
    public void setAlias(final List<String> alias) {
        this.alias = alias;
    }
}
