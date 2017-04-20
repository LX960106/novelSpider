package util;

import java.util.Scanner;

import impl.BXWXNovelSpider;
import impl.KSZNovelSpider;
import interfaces.INovelSpider;
/**
 * ����ץȡ�鼮�б��ʵ����
 * @author Administrator
 *
 */
public final class NovelSpiderFactory {

	/**
	 * ����ץȡ��url,����һ��ץȡ�鼮�б��ʵ�����
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
		default:throw new RuntimeException("��ʱ��֧�ָ���ַ");
		}
		return novelSpider;
	}
}
