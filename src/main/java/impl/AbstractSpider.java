package impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import util.NovelSpiderHttpGet;

public abstract class AbstractSpider {

	/**
	 * ץȡ��ҳ����
	 * @param url
	 * @param charset �ַ���
	 * @return �����ĵ��ַ���
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
