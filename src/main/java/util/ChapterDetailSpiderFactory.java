package util;

import impl.DefaultChapterDetailSpider;
import impl.SortChapterSpider;
import interfaces.IChapterDetailSpider;
import interfaces.IChapterSpider;
/**
 * ͨ��������URL,����IChapterDetailSpider�ľ���ʵ����
 * @author Li Xian
 *
 */
public final class ChapterDetailSpiderFactory {
	private ChapterDetailSpiderFactory(){};
	public static IChapterDetailSpider getChapterDetailSpider(String url){
		IChapterDetailSpider chapterDetailSpider = null;
		NovelSiteEnum novelsiteenum = NovelSiteEnum.getEnumByUrl(url);
		switch(novelsiteenum){
		case DingDianXiaoShuo:
		case BiQuGe:
		case BiXiaWenXue:
		case KanShuZhong:
			chapterDetailSpider = new DefaultChapterDetailSpider();
		}
		return chapterDetailSpider;
	}
}
