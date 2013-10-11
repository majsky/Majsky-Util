package me.majsky.util.debug;

import java.io.IOException;
import java.io.InputStream;

import me.majsky.util.reflection.ReflectionHelper;

public class DebugInputStream extends InputStream {

    private InputStream stream;
    
    public DebugInputStream(InputStream wrapped){
        stream = wrapped;
    }
    
    @Override
    public int read() throws IOException {
        int read = stream.read();
        StackTraceElement e = ReflectionHelper.getCaller();
        System.out.println((e!=null?e.getClassName() + " is ":"") + "reading " + read);
        return read;
    }

    @Override
    public int available() throws IOException {
        int i = stream.available();
        StackTraceElement e = ReflectionHelper.getCaller();
        System.out.println((e!=null?e.getClassName() + " calls ":"") + "avaiable " + i);
        return i;
    }

    @Override
    public void close() throws IOException {
        StackTraceElement e = ReflectionHelper.getCaller();
        System.out.println((e!=null?e.getClassName() + " is ":"") + "Closing stream");
        stream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        StackTraceElement e = ReflectionHelper.getCaller();
        System.out.println((e!=null?e.getClassName() + " is ":"") + "marking " + readlimit);
        stream.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return stream.markSupported();
    }

    @Override
    public synchronized void reset() throws IOException {
        StackTraceElement e = ReflectionHelper.getCaller();
        System.out.println((e!=null?e.getClassName() + " is ":"") + "reseting mark");
        stream.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        long l = stream.skip(n);
        StackTraceElement e = ReflectionHelper.getCaller();
        System.out.println((e!=null?e.getClassName() + " is ":"") + "Skipping " + n + "(returnval " + l + ")");
        return l;
    }

}
