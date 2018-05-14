package org.codnect.firesnap.scan;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Created by Burak Koken on 14.5.2018.
 *
 * @author Burak Koken
 */
public class PackageUtilsTest {

    @Test
    public void testGetClasses() throws IOException, ClassNotFoundException {
        List<Class> classList = PackageUtils.getClasses("org.codnect.firesnap.model");
        assertNotEquals(classList.size(), 0);
    }

}
