package auto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AutoWireExample.class)
public class AutoWireTest {
    @Autowired
    private AutoWireExample autoWireExample;

    @Test
    public void test() {
        autoWireExample.info();
    }


}
