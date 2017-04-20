package impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import util.NovelSpiderHttpGet;

public abstract class AbstractSpider {

	/**
	 * 抓取网页内容
	 * @param url
	 * @param charset 字符集
	 * @return 返回文档字符串
	 * @throws Exception
	 */
	protected String crawl(String url,String charset) throws Exception{
		try(CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			//CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(url))
			CloseableHttpResponse httpResponse = httpClient.execute(new NovelSpiderHttpGet(url))){
			String result = EntityUtils.toString(httpResponse.getEntity(),charset);
			return result;
		
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
}
