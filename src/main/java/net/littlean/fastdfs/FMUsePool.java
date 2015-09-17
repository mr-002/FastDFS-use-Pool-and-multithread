package net.littlean.fastdfs;


import java.io.IOException;

import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;

import org.csource.fastdfs.FileInfo;

import org.csource.fastdfs.StorageClient;
/**
 * 基于连接池的操作
 * @author An
 *
 */


public class FMUsePool implements Staticwords{

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FMUsePool.class);
	

	public static String upload(FastDFSFile file) throws IOException, InterruptedException {
		System.out.println("File Name: " + file.getName() + "		File Length: " + file.getContent().length);

		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("width", "120");
		meta_list[1] = new NameValuePair("heigth", "120");
		meta_list[2] = new NameValuePair("author", "nooe");

		long startTime = System.currentTimeMillis();
		String[] uploadResults = null;
		StorageClient storageClient  = FastDFSConnectionPool.getfastdfsconnectionpool().checkout(10);
		try {
			uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
		} catch (IOException e) {
			logger.error("IO Exception when uploadind the file: " + file.getName(), e);
		} catch (Exception e) {
			logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
		}
		logger.info("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

		if (uploadResults == null) {
			System.out.println("upload file fail, error code: " + storageClient.getErrorCode());
			FastDFSConnectionPool.getfastdfsconnectionpool().drop(storageClient);
		}

		String groupName = uploadResults[0];
		String remoteFileName = uploadResults[1];

		String fileAbsolutePath = PROTOCOL + FastDFSConnectionPool.getTrackerServerAddress() + SEPARATOR
				+ TRACKER_NGNIX_PORT + "/" + groupName + "/" + remoteFileName;

		System.out.println("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:" + " "
				+ remoteFileName);
		
		FastDFSConnectionPool.getfastdfsconnectionpool().checkin(storageClient);
		return fileAbsolutePath;

	}

	public static FileInfo getFile(String groupName, String remoteFileName) {
		try {
			return FastDFSConnectionPool.getfastdfsconnectionpool().checkout(10).get_file_info(groupName, remoteFileName);
		} catch (IOException e) {
			logger .error("IO Exception: Get File from Fast DFS failed", e);
		} catch (Exception e) {
			logger.error("Non IO Exception: Get File from Fast DFS failed", e);
		}
		return null;
	}

	public static int deleteFile(String groupName, String remoteFileName){
		int i = 0;
		try {
			StorageClient sc = FastDFSConnectionPool.getfastdfsconnectionpool().checkout(10);
			i = sc.delete_file(groupName, remoteFileName);
		    if(i!=0)
		    	FastDFSConnectionPool.getfastdfsconnectionpool().drop(sc);
		    else
		    	FastDFSConnectionPool.getfastdfsconnectionpool().checkin(sc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}


}
