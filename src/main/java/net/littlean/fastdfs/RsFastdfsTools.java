package net.littlean.fastdfs;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * 	fastDFS有生工具类，具有将文件上传至fastDFS分布式文件系统及删除等操作的功能。
 * 如果抛出空指针异常请检查risesoft\common\fastdfs\fdfs_client.conf是否配置正确
 * 或fastDFS服务器的联网情况。
 * @author an
 *
 * 
 */
public class RsFastdfsTools {
	   /**
    
     *将本地的文件传输至fastDFS服务器，path为本地文件路径。
     *返回值为完整的http路径，可直接访问文件。
     *@author an
     */
	public String upload(String path) throws Exception {
		System.out.println(path);
		String url="";
		UploadThread up = new UploadThread(path);
		Thread upload = new Thread(up);
		upload.start();
		upload.join();
		url = up.geturl();
		return url;

	}
	/**
	 * 提供一个地址，该方法会向FastDFS上传该地址目录下的所有文件并将本地地址与FastDFS服务器地址
	 * 存于相应的数据库中
	 * @param path
	 * @throws InterruptedException
	 */
	
	public void uploadAllinDir(String path) throws InterruptedException{
		List<String> urls = new ArrayList<String>();
		TraversalDir td = new TraversalDir();
		td.bianli(path);
		List<String> alldir = td.getDirList();
		for(int i=0;i<alldir.size();i++)
		{
			UploadThread up = new UploadThread(alldir.get(i));
			Thread upload = new Thread(up);
			upload.start();
			upload.join();
			urls.add(i,up.geturl());	
			}
		Saveinbase save = new Saveinbase();
	
		for(int i=0;i<alldir.size();i++)
		{
			
			try {
				save.add(alldir.get(i),urls.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("数据库录入不成功");
			};
		}
		}
   /**
    
     *将fastDFS服务器上的某个文件删除掉，FastdfsUrl为该文件在服务器上的http路径，
     *在传递的String路径参数中有无“http://”字样或者有无ip地址及端口号均可，只需要有端口号之后的所有信息即可。
     *@author an
     */
	public void deletefile(String FastdfsUrl){
		DeleteThread del = new DeleteThread(FastdfsUrl);
		Thread delete = new Thread(del);
		delete.start();
	}
	

}