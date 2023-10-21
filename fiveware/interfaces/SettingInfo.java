package fiveware.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingInfo {
   String name();

   float min() default 0.0F;

   float max() default 0.0F;

   boolean onlyInteger() default false;

   String[] modes() default {""};
}
