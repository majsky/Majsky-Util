package me.majsky.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class PatchableZipFile extends ZipFile {
	
	public static final IZipPatch PATCH_DELETE_META_INF = new DeleteMetaInfZipPatch();
	
	private IZipPatch patch;
	private File tempFile;
	private boolean isPatched;
	private CloseableHelper ch;
	
	public PatchableZipFile(File zip, IZipPatch patch) throws ZipException, IOException {
		super(zip);
		this.patch = patch;
		tempFile = File.createTempFile(zip.getName().substring(0, zip.getName().lastIndexOf('.')), ".zip");
		tempFile.deleteOnExit();
		ch = new CloseableHelper();
	}
	
	public void setPatch(IZipPatch patch){
		this.patch = patch;
	}
	
	
	@Override
	public InputStream getInputStream(ZipEntry entry) throws IOException {
		if(patch != null)
			return patch.getInputStream(entry, this, super.getInputStream(entry));
		return super.getInputStream(entry);
	}
	
	public void patch() throws ZipException, IOException{
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(tempFile));
		ch.open(out);
		Enumeration<? extends ZipEntry> enu = this.entries();
		while(enu.hasMoreElements()){
			ZipEntry e = enu.nextElement();
			if(!patch.contains(e, this))
				continue;
			out.putNextEntry(new ZipEntry(e.getName()));
			if(e.isDirectory())
				continue;
			byte[] buff = new byte[1024];
			int len;
			InputStream is = getInputStream(e);
			ch.open(is);
			while((len = is.read(buff, 0, buff.length)) != -1)
				out.write(buff, 0, len);
			out.closeEntry();
			ch.close(is);
		}
		
		AdditonialZipEntry[] toAdd = patch.getNewEnteries();
		
		if(toAdd != null){
			for(AdditonialZipEntry aze:toAdd){
				ZipEntry e = new ZipEntry(aze.name);
				out.putNextEntry(e);
				out.write(aze.data);
				out.closeEntry();
			}
		}
		
		out.finish();
		ch.close(out);
		isPatched = true;
	}
	
	public URL getURL() throws ZipException, IOException{
		if(!isPatched)
			patch();
		return tempFile.toURI().toURL();
	}
	
	public File getPatchedFile(){
		if(!isPatched)
			return null;
		return tempFile;
	}
	
	public void patchAndCopyTo(File destination) throws ZipException, IOException{
		if(!isPatched)
			patch();
		
		FileOutputStream out = new FileOutputStream(destination);
		ch.open(out);
		FileInputStream in = new FileInputStream(tempFile);
		ch.open(in);
		
		byte[] buff = new byte[1024];
		int len;
		while((len = in.read(buff, 0, buff.length)) != -1)
			out.write(buff, 0, len);
		
		ch.close(out);
		ch.close(in);
	}
	
	public static interface IZipPatch{
		public InputStream getInputStream(ZipEntry entry, PatchableZipFile file, InputStream original) throws IOException;
		public boolean contains(ZipEntry e, PatchableZipFile file);
		public AdditonialZipEntry[] getNewEnteries();
	}
	
	public static final class AdditonialZipEntry{
		
		private final byte[] data;
		private final String name;
		
		public AdditonialZipEntry(String name, byte[] data){
			this.name = name;
			this.data= data;
		}
	}
	
	private static final class DeleteMetaInfZipPatch implements IZipPatch{

		@Override
		public InputStream getInputStream(ZipEntry entry,
				PatchableZipFile file, InputStream original) throws IOException {
			return original;
		}

		@Override
		public boolean contains(ZipEntry e, PatchableZipFile file) {
			return !e.getName().contains("META-INF");
		}

		@Override
		public AdditonialZipEntry[] getNewEnteries() {
			return null;
		}
		
	}
}
