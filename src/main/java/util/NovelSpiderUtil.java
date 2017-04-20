package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public final class NovelSpiderUtil {

	private static final Map<NovelSiteEnum,Map<String,String>> Context_Map = new HashMap<>();
	private NovelSpiderUtil(){
		init();
	}
	static {
		init();
	}
	public static void init(){
		SAXReader reader = new SAXReader();
		try {
			//什么意思  NovelSpiderUtil.class.getClassLoader().getResourceAsStream("conf/Spider-rule.xml")
			Document doc = reader.read(new File("conf/Spider-rule.xml"));//度文件需要捕获异常
			Element root = doc.getRootElement();
			List<Element> sites = root.elements("site");
			
			for(Element site:sites){
				Map<String,String> node = new HashMap<>();
				List<Element> subs = site.elements();
				for(Element sub:subs){
					//System.out.println(sub.getName()+"李贤");
					node.put(sub.getName(), sub.getTextTrim());
					
				}
				Context_Map.put(NovelSiteEnum.getEnumByUrl(node.get("url")), node);
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 拿到对于网站的解析规则
	 * @param novelsiteenum 枚举类型
	 * @return Map<String,String>
	 */
	public static Map<String,String> getContext(NovelSiteEnum novelsiteenum){
		return Context_Map.get(novelsiteenum);
	}
	/**
	 * 将path目录下的文件合并为一个文件，文件名字为mergeToFile
	 * @param path 文件所在目录
	 * @param mergeToFile 合并后的文件名 默认名为merge.txt
	 * @param isDelete 判断文件合并后是否要删除小文件
	 */
	public static void mutilFileMerge(String path,String mergeToFile,boolean isDelete){
		mergeToFile = (mergeToFile==null||mergeToFile=="")?(path+"/merge.txt"):(path+"/"+mergeToFile);
		System.out.println(mergeToFile);
		File[] files = new File(path).listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".txt");
			}
			
		});
		Arrays.sort(files, new Comparator<File>(){

			@Override
			public int compare(File o1, File o2) {
				// TODO Auto-generated method stub
				int o1name = Integer.parseInt(o1.getName().split("-")[0]);
				int o2name = Integer.parseInt(o2.getName().split("-")[0]);
				return o1name-o2name;
			}
			
		});
		PrintWriter printout = null;
		try {
			printout = new PrintWriter(new File(mergeToFile),"GBK");
			for(File file:files){
				
				BufferedReader bufr = 
				new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
				
				String line;
				while((line=bufr.readLine())!=null){
					printout.println(line);
				}
				bufr.close();
				/**
				 * 判断是否要删除小文件
				 */
				if(isDelete){
					//System.out.println("aaaa");
					file.delete();
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e1);
		}finally{
			System.out.println("合并成功！");
			printout.close();
		}
		/*
		for(File file:files){
			try(
				BufferedReader bufr = 
				new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"))
				){
				String line;
				while((line=bufr.readLine())!=null){
					printout.println(line);
				}
				
			}catch(IOException e){
				
			}
		}*/
	}
	
	
	public static int getNovelStatus(String status){
		if(status.contains("连载")||status.contains("连载中")){
			return 1;
		}else if(status.contains("完结")||status.contains("完成")){
			return 2;
		}else{
			System.out.println(status);
			throw new RuntimeException("不支持该书籍的状态");
		}
	}
	/**
	 * 格式化日期字符串为日期对象
	 * @param date
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String date,String pattern) throws ParseException{
		if("MM-dd".equals(pattern)){
			pattern = "yyyy-MM-dd";
			date = getDateField(Calendar.YEAR)+"-"+date;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date d = sdf.parse(date);
		return d;
		
	}
	
	/**
	 * 获取本日历的字符量
	 * @param field
	 * @return
	 */
	public static String getDateField(int field){
		Calendar cal = new GregorianCalendar();
		return cal.get(field)+"";
	}
}
