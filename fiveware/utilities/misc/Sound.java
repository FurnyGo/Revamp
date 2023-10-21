package fiveware.utilities.misc;

import fiveware.utilities.Utilities;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl.Type;

public class Sound extends Utilities {
   public static void playSound(URL file) {
      try {
         Clip clip = AudioSystem.getClip();
         clip.open(AudioSystem.getAudioInputStream(file));
         ((FloatControl)clip.getControl(Type.MASTER_GAIN)).setValue(1.0E-6F);
         clip.start();
      } catch (UnsupportedAudioFileException | IOException | LineUnavailableException var2) {
         var2.printStackTrace();
      }

   }
}
