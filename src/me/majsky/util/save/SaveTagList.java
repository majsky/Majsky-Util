package me.majsky.util.save;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import me.majsky.util.save.TagBuilder.AutoRegister;
import me.majsky.util.save.exception.CantParentItselfException;
@AutoRegister(7)
public class SaveTagList extends SaveTagBase implements List<SaveTagBase>{

    private List<SaveTagBase> list;
    
    public SaveTagList(){
        list = new ArrayList<>();
    }
    
    @Override
    public boolean add(SaveTagBase arg0) {
        if(arg0 != null && arg0.equals(this))
            throw new CantParentItselfException();
        return list.add(arg0);
    }

    @Override
    public void add(int arg0, SaveTagBase arg1) {
        if(arg1.equals(this))
            throw new CantParentItselfException();
        list.add(arg0, arg1);
    }

    @Override
    public boolean addAll(Collection<? extends SaveTagBase> arg0) {
        if(arg0.contains(this))
            throw new CantParentItselfException();
        return list.addAll(arg0);
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends SaveTagBase> arg1) {
        if(arg1.contains(this))
            throw new CantParentItselfException();
        return list.addAll(arg0, arg1);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(Object arg0) {
        return list.contains(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return list.containsAll(arg0);
    }

    @Override
    public SaveTagBase get(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int indexOf(Object arg0) {
        return list.indexOf(arg0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<SaveTagBase> iterator() {
        return list.iterator();
    }

    @Override
    public int lastIndexOf(Object arg0) {
        return list.lastIndexOf(arg0);
    }

    @Override
    public ListIterator<SaveTagBase> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<SaveTagBase> listIterator(int arg0) {
        return list.listIterator(arg0);
    }

    @Override
    public boolean remove(Object arg0) {
        return list.remove(arg0);
    }

    @Override
    public SaveTagBase remove(int arg0) {
        return list.remove(arg0);
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        return list.removeAll(arg0);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        return list.retainAll(arg0);
    }

    @Override
    public SaveTagBase set(int arg0, SaveTagBase arg1) {
        return list.set(arg0, arg1);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<SaveTagBase> subList(int arg0, int arg1) {
        return list.subList(arg0, arg1);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arg0) {
        return list.toArray(arg0);
    }

    @Override
    public byte[] write() {
        SaveTagCompound compound = new SaveTagCompound();
        
        for(int i=0;i<size();i++)
            compound.put(i, get(i));
        
        return compound.write();
    }

    @Override
    public void read(byte[] data) {
        SaveTagCompound compound = new SaveTagCompound();
        compound.read(data);
        
        for(int i=0;i<compound.size();i++)
            add(null);
        
        for(Entry<String, SaveTagBase> e:compound.entrySet())
            this.set(Integer.parseInt(e.getKey()), e.getValue());
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("[");
        Iterator<SaveTagBase> iterator = iterator();
        while(iterator.hasNext()){
            SaveTagBase tag = iterator.next();
            b.append("(");
            b.append(tag.getClass().getSimpleName());
            b.append(")");
            b.append(tag);
            if(iterator.hasNext())
                b.append(", ");
        }
        b.append("]");
        return b.toString();
    }

}
