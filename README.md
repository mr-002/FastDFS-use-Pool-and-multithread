# FastDFS-use-Pool-and-multithread
Enter the project you will see pom.xml contains:
<pre>
 <xmp>
  <dependency>
		<groupId>org.csource</groupId>
		<artifactId>fastdfs-client-java</artifactId>
		<version>1.2.0</version>
	</dependency>
	</xmp>
	</pre>

<p>which is not included in maven public repository.</p>
You should download fastdfs-client-java.jar or upload the jar in your own repository.

<p>And in the meanwhile you will find the sentence in SaveinBase.java:</p>

<pre>
        static String url = "jdbc:mysql://localhost:3306/fastdfs?characterEncoding=utf-8";
	static String username = "root";
	static String password = "root";
</pre>
please change information in your own condition
