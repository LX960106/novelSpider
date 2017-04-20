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
	 * 设置默认的请求参数
	 */
	private void setDefaultConfig(){
		
		this.setConfig(RequestConfig.custom()
				.setSocketTimeout(2000)
				.setConnectTimeout(5000)//设置连接服务器的超时时间
				.setConnectionRequestTimeout(5000)//设置重服务器读取数据的超时时间
				.build());
		this.setHeader("User-Agent","NovelSpider");
	}

}
