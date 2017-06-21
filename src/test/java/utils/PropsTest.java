package utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Adam on 21/06/2017.
 */
public class PropsTest {
    @Test
    public void getUserProperty() throws Exception {
        String unitTestKey = "unitTestKey";
        String unitTestValue = "unitTestValue";
        Props.setUserProperty(unitTestKey, unitTestValue);

        String userProperty = Props.getUserProperty(unitTestKey);
        assertEquals(unitTestValue, userProperty);

        Props.deleteProperty(unitTestKey, true);
        userProperty = Props.getUserProperty(unitTestKey);
        assertNull(userProperty);
    }


}