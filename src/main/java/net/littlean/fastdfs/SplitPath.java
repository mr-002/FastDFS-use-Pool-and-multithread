package net.littlean.fastdfs;



/**
 * @author an
 *
 *         切割路径找出文件名以及文件类型
 */

public class SplitPath {

	private String path = "";
	private String filename = "";
	private String filetype = "";
	private String groupName = "";
	private String remoteFileName = "";

	public SplitPath() {
	}

	public SplitPath(String path) {
		super();
		this.path = path;
	}
	
	public void clean(String path){
		this.path = path;
		this.filename = "";
		this.filetype = "";
		this.groupName = "";
		this.remoteFileName = "";
	}

	public void splitRealPath() {

		String s = /* "C:\\Users\\An\\Pictures\\name.png" */ this.path;

		String t = "";
		String n = "";
		int i = 0;
		flag: for (i = s.length() - 1; i > 0; i--) {

			char a = s.charAt(i);
			if (a != '.')
				t = a + t;
			else

				break flag;

		}
		if (i == 0) {
			System.out.println("This is not a path");
			return;
		}
		for (i -= 1; i > 0; i--) {
			char a = s.charAt(i);
			if (a != '/' && a != '\\')
				n = a + n;
			else
				break;

		}
		System.out.println("name:" + n + "   type:" + t);
		this.filetype = t;
		this.filename = n;

	}

	public void splitFastDFSpath() {
		String s = this.path;
		String g = "";
		String m = "";
		int i = s.indexOf("group");
		if (i == -1) {
			System.out.println("This is not a FastdfsPath!!");
			return;
		}
		char a = s.charAt(i);
		do {
			g += a;
			i++;
			a = s.charAt(i);
		} while (a != 92 && a != 47);
		for (i += 1; i < s.length(); i++)
			m += s.charAt(i);
		this.groupName = g;
		this.remoteFileName = m;
		System.out.println("groupname:" + g + "		remoteFileName:" + m);

	}

	public String getgroupName() {
		return this.groupName;
	}

	public String getremoteFileName() {
		return this.remoteFileName;
	}

	public String getfilename() {
		return this.filename;
	}

	public String getfiletype() {
		return this.filetype;
	}
}
