package util;

import java.net.URI;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpGet;

public class NovelSpiderHttpGet extends HttpGet {

	
	public NovelSpiderHttpGet() {
		// TODO Auto-generated constructor stub
	}

	public NovelSpiderHttpGet(URI uri) {
		super(uri);
		// TODO Auto-generated constructor stub
	}

	public NovelSpiderHttpGet(String uri) {
		super(uri);
		setDefaultConfig();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ����Ĭ�ϵ��������
	 */
	private void setDefaultConfig(){
		
		this.setConfig(RequestConfig.custom()
				.setSocketTimeout(2000)
				.setConnectTimeout(5000)//�������ӷ������ĳ�ʱʱ��
				.setConnectionRequestTimeout(5000)//�����ط�������ȡ���ݵĳ�ʱʱ��
				.build());
		this.setHeader("User-Agent","NovelSpider");
	}

}
