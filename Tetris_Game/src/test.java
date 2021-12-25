import org.junit.Assert;
import org.junit.Test;


public class test {

    @Test
    public void testCreateNewField(){
        char[][] data = Main.createNewField();
        Assert.assertEquals(data.length, Main.HIGH);
        Assert.assertEquals(data[0].length, Main.LENGTH);
    }

    @Test
    public void testUpdateField(){
        Main.saveField(new char[1][1]);
        Assert.assertEquals(Main.FIELD.length, 1);
        Assert.assertEquals(Main.FIELD[0].length, 1);
    }

}
