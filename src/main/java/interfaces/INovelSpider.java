package interfaces;

import java.util.Iterator;
import java.util.List;

import entities.Novel;

/**
 * ��ȥĳ��վ���С˵�б�
 * @author Li Xian
 *
 */
public interface INovelSpider {

	public static final int MAX_TRY_TIMES = 3;
	/**
	 * ����һ��URL,�Ҿ͸���һ�ѵ�С˵ʵ��
	 * @param url
	 * @param tryTimes ����ʧ�����ԵĴ���
	 * @return
	 */
	public List<Novel> getNovelList(String url,int tryTimes);
	
	public boolean hasNext();
	public String next();
	/**
	 * �����������������鼮�б�
	 * @param firstPage
	 * @param tryTimes ����ʧ�ܵĴ���
	 * @return
	 */
	public Iterator<List<Novel>> iterator(String firstPage,int tryTimes);
}
