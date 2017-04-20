package configuration;

import java.io.Serializable;

public class Configuration implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private static final int TRY_TIMES = 3;
	/**
	 * 每个线程默认下载最大的章节数量
	 */
	public static final int DEFAULT_SIZE = 100;
	/**
	 * 下载后的文件保存在的基地址路径
	 */
	public String path;
	/**
	 * 一个线程下载最大章节数的量
	 */
	public int size;
	
	/**
	 * 请求是失败后的尝试次数
	 */
	private int tryTimes;
	public Configuration(){
		this.size = DEFAULT_SIZE;
		this.setTryTimes(TRY_TIMES);
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getTryTimes() {
		return tryTimes;
	}
	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}
	

}
