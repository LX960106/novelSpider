package interfaces;

import entities.ChapterDetail;

public interface IChapterDetailSpider {

	/**
	 * ����һ��url�Ҿͷ����½���ϸ����
	 * @param url
	 * @return
	 */
	public ChapterDetail getChapterDetail(String url);
}
