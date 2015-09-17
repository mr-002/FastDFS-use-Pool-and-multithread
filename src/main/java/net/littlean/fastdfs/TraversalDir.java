package net.littlean.fastdfs;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
/**
 * 遍历地址，并返回一个存有该路径下所有文件地址的list
 * @author An
 *
 */

public class TraversalDir {
	
	private List<String> dirlist = new ArrayList<String>();
	
	public void bianli(String path){
		File D = new File(path);
		File f[] = D.listFiles();
		for(int i=0;i<f.length;i++)
		{
			if(!f[i].isDirectory())
				this.dirlist.add(f[i].getAbsolutePath());
			else
				bianli(f[i].getAbsolutePath());
			}}
	
	public List<String> getDirList(){
		return this.dirlist;
	}
	

}
