package impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import entities.Chapter;

public class SortChapterSpider extends AbstractChapterSpider {

	public List<Chapter> getChapter(String url){
		List<Chapter>chapters = super.getChapter(url);
		Collections.sort(chapters, new Comparator<Chapter>(){

			@Override
			public int compare(Chapter o1, Chapter o2) {
				// TODO Auto-generated method stub
				String o1Url = o1.getUrl();
				String o2Url = o2.getUrl();
				String num1 = o1Url.substring(o1Url.lastIndexOf("/")+1,o1Url.lastIndexOf("."));
				String num2 = o2Url.substring(o2Url.lastIndexOf("/")+1,o2Url.lastIndexOf("."));
				return num1.compareTo(num2);
			}
			
		});
		return chapters;
	}
}
