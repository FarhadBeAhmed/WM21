package https.outsourcingvilla.com.Annotations;

import java.lang.annotation.*;

/**
 * Using this annotation at parameters on less body - method
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String value();
}