package entities;

import java.io.Serializable;
import java.util.Date;

public class Novel implements Serializable {
	private static final long serialVersionUID = 6805081031622185857L;
	/** 书名 */
	private String name;
	/** 作者 */
	private String author;
	/** 链接 */
	private String url;
	/** 类别 */
	private String type;
	/** 最新章节 */
	private String newestChapter;
	/** 最新章节链接 */
	private String newestChapterUrl;
	/** 更新时间 */
	private Date updateTime;
	/** 状态 1:连载 2:完结 3:烂尾 */
	private int status;
	/** 书名首字母 */
	private char fistLetter;
	
	/** 小说平台id */
	private int platformId;
	
	/** 改本小说存储到我们数据库的时间 */
	private Date addTime;
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public int getplatformId() {
		return platformId;
	}
	public void setplatformId(int platformId) {
		this.platformId = platformId;
	}
	public char getFistLetter() {
		return fistLetter;
	}
	public void setFistLetter(char fistLetter) {
		this.fistLetter = fistLetter;
	}
	public Novel(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNewestChapter() {
		return newestChapter;
	}
	public void setNewestChapter(String newestChapter) {
		this.newestChapter = newestChapter;
	}
	public String getNewestChapterUrl() {
		return newestChapterUrl;
	}
	public void setNewestChapterUrl(String newestChapterUrl) {
		this.newestChapterUrl = newestChapterUrl;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Novel [name=" + name + ", author=" + author + ", url=" + url + ", type=" + type + ", newestChapter="
				+ newestChapter + ", newestChapterUrl=" + newestChapterUrl + ", updateTime=" + updateTime + ", status="
				+ status + ", fistLetter=" + fistLetter + ", platformId=" + platformId + ", addTime=" + addTime + "]";
	}
	
}
