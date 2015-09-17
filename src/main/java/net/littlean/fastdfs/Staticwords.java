package net.littlean.fastdfs;
import java.io.Serializable;

/**
 * @author an
 *
 * @email anyiwei123@sina.com
 */
public interface Staticwords extends Serializable {

	public static final String FILE_DEFAULT_WIDTH = "120";
	public static final String FILE_DEFAULT_HEIGHT = "120";
	public static final String FILE_DEFAULT_AUTHOR = "Diandi";

	public static final String PROTOCOL = "http://";
	public static final String SEPARATOR = ":";

	public static final String TRACKER_NGNIX_PORT = "8080";

	public static final String CLIENT_CONFIG_FILE = "fdfs_client.conf";

}
