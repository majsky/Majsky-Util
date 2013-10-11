package me.majsky.util.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import me.majsky.util.save.SaveTagCompound;
import me.majsky.util.save.SaveTagList;
import me.majsky.util.save.SaveTagString;
import me.majsky.util.save.TagBuilder;
import me.majsky.util.save.internal.SaveTagHeader;
import me.majsky.util.save.internal.SaveTagInputStream;

public class SaveLibraryTest{
    
    public static void main(String[] strings) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        
        TagBuilder.autoRegister(SaveLibraryTest.class.getPackage());
        SaveTagCompound compound = new SaveTagCompound();
        SaveTagList list = new SaveTagList();
        list.add(new SaveTagString().setString("me.majsky.test.Test"));
        list.add(new SaveTagString().setString("me.majsky.util.save.TagCompound"));
        compound.put("list", list);
        
        SaveTagCompound compound2 = new SaveTagCompound();
        compound2.put("string", "SubCompuund");
        compound2.put("bool", false);
        compound.put("comp", compound2);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        compound.write(baos);
        
        System.out.println(Arrays.toString(baos.toByteArray()));
        
        SaveTagInputStream stream = new SaveTagInputStream(new ByteArrayInputStream(baos.toByteArray()));
        SaveTagHeader header = stream.readHeader();
        stream.nextHeader();
        header = stream.readHeader();
        
        System.out.printf("%s, %s, %s", header.tagLength, header.tagName, header.tagType);
        
        stream.close();
    
    }
}
