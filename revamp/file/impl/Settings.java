// 
// Decompiled by Procyon v0.5.36
// 

package revamp.file.impl;

import java.util.Iterator;
import java.io.FileWriter;
import java.io.IOException;
import revamp.userinterfaces.clickgui.settings.Setting;
import revamp.modules.Module;
import revamp.Revamp;
import java.io.BufferedReader;
import revamp.interfaces.IFile;
import revamp.file.Files;

@IFile(name = "settings")
public class Settings extends Files
{
    @Override
    public void readFile(final BufferedReader bufferedReader) throws IOException {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String[] args = line.split("=");
            final Module module = Revamp.moduleManager.getModuleByName(args[0]);
            final Setting setting = Revamp.settingManager.getSetting(module, args[1]);
            if (setting != null) {
                switch (setting.getType()) {
                    default: {
                        continue;
                    }
                    case CHECKBOX: {
                        setting.setToggled(Boolean.parseBoolean(args[2]));
                        continue;
                    }
                    case COMBOBOX: {
                        setting.setCurrentMode(args[2]);
                        continue;
                    }
                    case SLIDER: {
                        setting.setCurrentValue(Float.parseFloat(args[2]));
                        continue;
                    }
                    case KEY: {
                        setting.setKey(Integer.parseInt(args[2]));
                        continue;
                    }
                    case TYPE: {
                        setting.setTyped(args[2]);
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    public void writeFile(final FileWriter fileWriter) throws IOException {
        for (final Module module : Revamp.moduleManager.getModules()) {
            if (module != null) {
                for (final Setting setting : Revamp.settingManager.getSetting()) {
                    if (setting.getModule().equals(module)) {
                        Object arg = "";
                        switch (setting.getType()) {
                            case CHECKBOX: {
                                arg = setting.isToggled();
                                break;
                            }
                            case COMBOBOX: {
                                arg = setting.getCurrentMode();
                                break;
                            }
                            case SLIDER: {
                                arg = setting.getCurrentValue();
                                break;
                            }
                            case KEY: {
                                arg = setting.getKey();
                                break;
                            }
                            case TYPE: {
                                arg = setting.getTyped();
                                break;
                            }
                        }
                        fileWriter.write(String.valueOf(module.getName()) + "=" + setting.getName() + "=" + arg + "\n");
                    }
                }
            }
        }
    }
}
