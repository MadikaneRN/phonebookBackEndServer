package za.ac.cput.entity;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EntryTest {
    private Entry entry;

    @Before
    public void setUp() throws Exception {

        entry = new  Entry();
        entry.setId(1);
        entry.setName("Amanda");
        entry.setPhoneNumber("0839133030");

    }

    @Test
    public void testEntry() throws Exception {
        Assert.assertNotNull(entry);
        Assert.assertEquals(entry.getName(),"Amanda");
        Assert.assertEquals(entry.getPhoneNumber(),"0839023030");

    }
}
