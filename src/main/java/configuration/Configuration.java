package configuration;

import java.io.Serializable;

public class Configuration implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private static final int TRY_TIMES = 3;
	/**
	 * ÿ���߳�Ĭ�����������½�����
	 */
	public static final int DEFAULT_SIZE = 100;
	/**
	 * ���غ���ļ������ڵĻ���ַ·��
	 */
	public String path;
	/**
	 * һ���߳���������½�������
	 */
	public int size;
	
	/**
	 * ������ʧ�ܺ�ĳ��Դ���
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
