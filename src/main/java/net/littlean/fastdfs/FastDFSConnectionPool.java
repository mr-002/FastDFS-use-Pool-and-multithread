package net.littlean.fastdfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.concurrent.*;


import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;

import org.csource.fastdfs.TrackerServer;


/**
 * fastDFS连接池 默认连接数为6
 * @author an
 *
 * @email anyiwei123@sina.com
 */


public class FastDFSConnectionPool implements Staticwords{
	/**
	 * 这个竟然还得设置版本信息我也是醉了
	 */
	private static final long serialVersionUID = 1L;
	int size = 6;
	private ConcurrentHashMap<StorageClient, Object> 
	busyConnectionPool = null;
	private ArrayBlockingQueue<StorageClient> 
	idleConnectionPool = null;
	private Object obj = new Object();

	/**
	 * 构造函数
	 */
	private FastDFSConnectionPool() {

		busyConnectionPool = new 
				ConcurrentHashMap<StorageClient, Object>();
		idleConnectionPool = new 
				ArrayBlockingQueue<StorageClient>(size);
		init(size);

	}
	
	private static FastDFSConnectionPool conpool = new FastDFSConnectionPool();
	/**
	 * 返回一个pool的静态static实例
	 * @return
	 */
	public static FastDFSConnectionPool getfastdfsconnectionpool(){
		return conpool;
	}
	/**
	 * 返回tracker服务器地址。
	 * @return
	 * @throws IOException
	 */
	public static String getTrackerServerAddress() throws IOException{
		return new TrackerClient().getConnection().getInetSocketAddress().getHostName();
	}
	
	private void initClientGlobal() throws FileNotFoundException, IOException, Exception {
		String classPath = new File(FMUsePool.class.getResource("").getFile()).getCanonicalPath();
		String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
        System.out.println(fdfsClientConfigFilePath);
		ClientGlobal.init(fdfsClientConfigFilePath);
		}
	/**
	 * 初始化连接池
	 * @param size
	 */
	private void init(int size){
		TrackerServer ts = null;
		try {
			initClientGlobal();
			TrackerClient tc = new TrackerClient();
			ts = tc.getConnection();
			for (int i = 0; i < size; i++) {
				StorageServer ss = null;
				StorageClient sc1 = new StorageClient(ts, ss);
				idleConnectionPool.add(sc1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (ts!= null)
			{
				try {
					ts.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}	
		}
	}
	/**
	 * 尝试在waittimes秒中从idle中取出storageclient并同时放入busy若失败返回null
	 * @param waittimes
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized StorageClient checkout(int waittimes) throws InterruptedException{
		StorageClient sc1 = idleConnectionPool.poll(waittimes, TimeUnit.SECONDS);
		busyConnectionPool.put(sc1, obj);
		return sc1;
	}
	/**
	 * 将busy中的storageclient(如果存在的话)放回idle
	 * 
	 * @param sc1
	 */
	public synchronized void checkin(StorageClient sc1){
		if(busyConnectionPool.remove(sc1)!=null)
			idleConnectionPool.add(sc1);
		}
	/**
	 * 如果说在运行的过程之中storageclient出了问题像是初始化错误，连接中断等等
	 * 这时就需要drop掉该sc并重新初始化一个新的sc放在idle里面
	 * @param sc1
	 */
	public synchronized void drop(StorageClient sc1){
		if(busyConnectionPool.remove(sc1)!=null){
			TrackerServer ts = null;
			TrackerClient tc = new TrackerClient();
			try {
				ts = tc.getConnection();
				StorageServer ss = null;
				StorageClient sc2 = new StorageClient(ts, ss);
				idleConnectionPool.add(sc2);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				if (ts!= null)
				{
					try {
						ts.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}	
			}
		}
	}
	
	
	
}
