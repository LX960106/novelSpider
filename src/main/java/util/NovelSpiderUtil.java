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
			//ʲô��˼  NovelSpiderUtil.class.getClassLoader().getResourceAsStream("conf/Spider-rule.xml")
			Document doc = reader.read(new File("conf/Spider-rule.xml"));//���ļ���Ҫ�����쳣
			Element root = doc.getRootElement();
			List<Element> sites = root.elements("site");
			
			for(Element site:sites){
				Map<String,String> node = new HashMap<>();
				List<Element> subs = site.elements();
				for(Element sub:subs){
					//System.out.println(sub.getName()+"����");
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
	 * �õ�������վ�Ľ�������
	 * @param novelsiteenum ö������
	 * @return Map<String,String>
	 */
	public static Map<String,String> getContext(NovelSiteEnum novelsiteenum){
		return Context_Map.get(novelsiteenum);
	}
	/**
	 * ��pathĿ¼�µ��ļ��ϲ�Ϊһ���ļ����ļ�����ΪmergeToFile
	 * @param path �ļ�����Ŀ¼
	 * @param mergeToFile �ϲ�����ļ��� Ĭ����Ϊmerge.txt
	 * @param isDelete �ж��ļ��ϲ����Ƿ�Ҫɾ��С�ļ�
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
				 * �ж��Ƿ�Ҫɾ��С�ļ�
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
			System.out.println("�ϲ��ɹ���");
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
		if(status.contains("����")||status.contains("������")){
			return 1;
		}else if(status.contains("���")||status.contains("���")){
			return 2;
		}else{
			System.out.println(status);
			throw new RuntimeException("��֧�ָ��鼮��״̬");
		}
	}
	/**
	 * ��ʽ�������ַ���Ϊ���ڶ���
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
	 * ��ȡ���������ַ���
	 * @param field
	 * @return
	 */
	public static String getDateField(int field){
		Calendar cal = new GregorianCalendar();
		return cal.get(field)+"";
	}
}
