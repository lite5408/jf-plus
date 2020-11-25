package cn.iutils.common.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.iutils.common.Page;

/**
 * 
 * @ClassName: MPageInfo
 * @Description: 手机端分页
 * @author Tng
 * @date 2017年1月16日 下午3:37:16
 *
 */
@SuppressWarnings("rawtypes")
public class MPageInfo implements Serializable{

	private static final long serialVersionUID = -5577794119663009242L;
	
	private List rows; //显示的记录  
	
	private Map<String, Object> page = new HashMap<>();
	
	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public Map<String, Object> getPage() {
		return page;
	}

	public void setPage(Map<String, Object> page) {
		this.page = page;
	}

	public static MPageInfo transform(Page page){
		MPageInfo mPageInfo = new MPageInfo();
		mPageInfo.setRows(page.getList());
		mPageInfo.getPage().put("nowpage", page.getPageNo());//页码
		mPageInfo.getPage().put("records", page.getPageSize());//每页数量
		mPageInfo.getPage().put("total", page.getTotal());//总条数
		mPageInfo.getPage().put("totalpage", page.getPageNumber());//总页数
		return mPageInfo;
	}

}
