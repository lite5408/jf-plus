package com.github.sd4324530.fastweixin.message;

import com.github.sd4324530.fastweixin.message.util.MessageBuilder;

import java.io.Serializable;

import org.springframework.web.util.HtmlUtils;

public class Article {

    private String title;
    private String description;
    private String picUrl;
    private String url;
    private String thumb_url;
    private String digest;
    
//    "title": "图文测试",
//    "author": "贺",
//    "digest": "哈哈哈哈哈",
//    "content": "<p>哈哈哈</p><p><img data-w=\"132\" data-ratio=\"1\" data-s=\"300,640\" data-type=\"jpeg\" data-src=\"http://mmbiz.qpic.cn/mmbiz/icgM1DfEv2U1EficjbDtbb8XSNqSIKwrQF4kZq0701E5jIx8wu1LBSUF2uia6IDG8XsOUGzo0SBUHqODV9SRxDLzw/0?wx_fmt=jpeg\"  /></p><p>哈哈<br  /></p>",
//    "content_source_url": "",
//    "thumb_media_id": "YRQm81_h423IRRtgDd46OQz2mPxaAF9y_0XgFQcb6QM",
//    "show_cover_pic": 0,
//    "url": "http://mp.weixin.qq.com/s?__biz=MzIwMTIwOTU2OQ==&mid=100000002&idx=1&sn=0d5e1e55d3aebf8d62e30e42791ce7d7#rd",
//    "thumb_url": "http://mmbiz.qpic.cn/mmbiz/icgM1DfEv2U1EficjbDtbb8XSNqSIKwrQF0Ms5VLL0VFZJYYMwZfOcGHyCo9nbdsmb5Lllb6X6w0rn4iaog8Jjpvg/0?wx_fmt=jpeg"

    public Article() {

    }

    public Article(String title) {
        this.title = title;
    }

    public Article(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Article(String title, String description, String picUrl, String url) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
        this.thumb_url = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    

    public String getThumb_url() {
		return thumb_url;
	}

	public void setThumb_url(String thumb_url) {
		this.thumb_url = thumb_url;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String toXml() {
        MessageBuilder mb = new MessageBuilder();
        mb.addData("Title", title);
        //mb.addData("Description", description);
        //mb.addData("PicUrl", picUrl);
        mb.addData("Description", digest);
        mb.addData("PicUrl", thumb_url);
        mb.addData("Url", url);
        mb.surroundWith("item");
        return mb.toString();
    }

}
