package net.littlean.fastdfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * 多线程上传文件
 * @author An
 *
 */

public class UploadThread implements Runnable {
	private String url;
	private String patha;
	public UploadThread(String p){
		this.patha=p;
	}

	public void run() {
		try{
		String path = this.patha;
		SplitPath spl = new SplitPath(path);
		spl.splitRealPath();
		File content = new File(path);
		FileInputStream fis = new FileInputStream(content);
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}
		FastDFSFile file = new FastDFSFile(spl.getfilename(), file_buff, spl.getfiletype());
		String fileAbsolutePath = FMUsePool.upload(file);
		System.out.println(fileAbsolutePath);
		fis.close();
		this.url = fileAbsolutePath;
		
	   }catch(IOException e)
		{} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public String geturl(){
		return this.url;
	}
	
	
	

}
