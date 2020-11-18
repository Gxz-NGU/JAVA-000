package auto;

import org.springframework.stereotype.Component;

@Component
public class AutoWireExample {
    public AutoWireExample(){
        System.out.println("Construct AutoWireExample");
    }

    public void info() {
        System.out.println("AutoWireExample.info() is invoked.");
    }
}
