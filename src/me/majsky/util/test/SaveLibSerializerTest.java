package me.majsky.util.test;

import me.majsky.util.save.ClassSerializer;
import me.majsky.util.save.ClassSerializer.SerializedName;
import me.majsky.util.save.ClassSerializer.Unserializable;
import me.majsky.util.save.SaveTagCompound;

public class SaveLibSerializerTest {
    @SerializedName("Double1")
    private double some;
    @Unserializable
    protected int data;
    
    public SaveLibSerializerTest(double some, int data) {
        this.some = some;
        this.data = data;
    }
    
    public SaveLibSerializerTest(){}

    public static void main(String[] args) throws Exception{
        
        SaveLibSerializerTest test = new SaveLibSerializerTest(15.125, 14);
        SaveTagCompound compound = ClassSerializer.toSaveTag(test, SaveLibSerializerTest.class);
        
        System.out.println(compound);
        
        SaveLibSerializerTest test1 = ClassSerializer.fromSaveTag(compound, SaveLibSerializerTest.class);
        System.out.println(test1.some);
        System.out.println(test1.data);
    }
}
