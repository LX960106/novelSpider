package interfaces;


import java.util.List;

import entities.Chapter;
public interface IChapterSpider {

	/**
	 * ������һ��������url,���Ǿ͸��㷵��һ���½��б�
	 * @param url
	 * @return
	 */
	public List<Chapter> getChapter(String url);
	
}
