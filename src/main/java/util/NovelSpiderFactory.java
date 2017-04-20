package util;

import java.util.Scanner;

import impl.BXWXNovelSpider;
import impl.KSZNovelSpider;
import interfaces.INovelSpider;
/**
 * 生成抓取书籍列表的实现类
 * @author Administrator
 *
 */
public final class NovelSpiderFactory {

	/**
	 * 传入抓取的url,返回一个抓取书籍列表的实体对象
	 * @param url
	 * @return
	 */
	public static INovelSpider getNovelSpider(String url){
		NovelSiteEnum novelsiteenum = NovelSiteEnum.getEnumByUrl(url);
		//System.out.println(novelsiteenum);
		//Scanner input = new Scanner(System.in);
		//int a = input.nextInt();
		INovelSpider novelSpider = null;
		switch(novelsiteenum){
		case BiXiaWenXue:
			novelSpider = new BXWXNovelSpider();break;
		case KanShuZhong:
			novelSpider = new KSZNovelSpider();break;
		default:throw new RuntimeException("暂时不支持该网址");
		}
		return novelSpider;
	}
}
