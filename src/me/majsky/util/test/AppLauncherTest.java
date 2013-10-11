package me.majsky.util.test;

import java.net.URL;
import java.net.URLClassLoader;

import me.majsky.util.AppLauncher;
import me.majsky.util.ArrayBuilder;
import me.majsky.util.ArrayUtils;

public class AppLauncherTest {
    
    public static void main(String[] _args) throws Exception{
        ArrayBuilder<URL> builder = new ArrayBuilder<>();
        builder.addAll(AppLauncher.fromFolder("C:\\Users\\Mário\\Desktop\\mod test\\.minecraft\\bin"));
        builder.add(AppLauncher.getContainer());
        URL[] urls = builder.toArray(new URL[0]);
        ArrayUtils.print(urls);
        URLClassLoader loader = new URLClassLoader(urls, null);
        
        String[] args = "majsky".split(" ");
        
        AppLauncher.injectNative("C:\\Users\\Mário\\Desktop\\mod test\\.minecraft\\bin\\natives");
        AppLauncher.launch("net.minecraft.client.Minecraft", args, loader);
    }
}
