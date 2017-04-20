package util;

/**
 * 已经被支持的小说网站枚举
 * @author Li Xian
 * 
 */
public enum NovelSiteEnum {
	DingDianXiaoShuo(1,"www.23us.com"),//相当于在调用构造方法
	BiQuGe(2,"www.biquge.com"),
	BiXiaWenXue(3,"www.bxwx9.org"),
	KanShuZhong(4,"www.kanshuzhong.com");
	private int id;
	private String url;
	private NovelSiteEnum(int id,String url){
		this.id = id;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public static NovelSiteEnum getEnumById(int id){
		switch(id){
		case 1:return DingDianXiaoShuo;
		case 2:return BiQuGe;
		default:throw new RuntimeException("id="+id+"是不支持的小说网站");
		}
	}
	public static NovelSiteEnum getEnumByUrl(String url){
		for(NovelSiteEnum novelSiteEnum:values()){
			if(url.contains(novelSiteEnum.url)){
				return novelSiteEnum;
			}
		}
		throw new RuntimeException("url="+url+"是不被支持的小说链接");
		
	}

}
