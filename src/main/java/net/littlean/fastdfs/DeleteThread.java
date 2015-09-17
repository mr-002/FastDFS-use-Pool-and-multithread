package net.littlean.fastdfs;
/**
 * 多线程删除文件
 * @author An
 *
 */

public class DeleteThread implements Runnable{
    private String gn;
    private String fn; 
    private String url;
    public DeleteThread(String FastdfsUrl){
    	SplitPath sp = new SplitPath(FastdfsUrl);
    	sp.splitFastDFSpath();
    	this.gn = sp.getgroupName();
    	this.fn = sp.getremoteFileName();
    	this.url = FastdfsUrl;
    }
	public void run() {
		int i;
		try {
			i = FMUsePool.deleteFile(gn, fn);
			if (i == 0)
				System.out.println(this.url + " has been deleted!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not delete zhe file : " + this.url);
		}
		
		
	}
	

}
