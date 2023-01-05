// 
// Decompiled by Procyon v0.5.36
// 

package revamp.interfaces;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SettingInfo {
    String name();
    
    float min() default 0.0f;
    
    float max() default 0.0f;
    
    boolean onlyInteger() default false;
    
    String[] modes() default { "" };
}
