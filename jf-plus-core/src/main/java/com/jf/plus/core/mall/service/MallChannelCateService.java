package com.jf.plus.core.mall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jf.plus.common.vo.TreeVo;
import com.jf.plus.core.mall.dao.MallChannelCateDao;
import com.jf.plus.core.mall.entity.MallChannelCate;

import cn.iutils.common.Page;
import cn.iutils.common.service.CrudService;

/**
 * 渠道商品分类表 Service层
 *
 * @author Tng
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class MallChannelCateService extends CrudService<MallChannelCateDao, MallChannelCate> {

	public List<TreeVo> findTreePage(Page<MallChannelCate> page) {
		MallChannelCate mallChannelCate = page.getEntity();
		List<MallChannelCate> list = dao.findList(mallChannelCate);
		List<TreeVo> treeList = new ArrayList<TreeVo>();

		TreeVo tv = null;

		int level = 0;

		for (MallChannelCate pc : list) {
			tv = new TreeVo();
			tv.setId(Integer.valueOf(pc.getId()));
			tv.setCatId(pc.getCatId());
			tv.setName(pc.getCatName());
			tv.setPid(pc.getCatPid().intValue());
			tv.setSeq(pc.getSort());
			tv.setCatName(pc.getProductCateName());
			// tv.setCreateBy(Integer.valueOf(pc.getCreateBy()));
			// tv.setCreateDate(pc.getCreateDate());
			// tv.setUpdateBy(Integer.valueOf(pc.getUpdateBy()));
			// tv.setUpdateDate(pc.getUpdateDate());
			tv.setSource(pc.getChannelType());

			if (pc.getCatPid() == 0 || pc.getCatPid() == null) {
				tv.setLevel(level);
				treeList.add(tv);
			} else {
				for (int i = 0; i < treeList.size(); i++) {
					TreeVo treeVo = treeList.get(i);
					if (pc.getCatPid().intValue() == treeVo.getId().intValue()) {
						tv.setLevel(treeVo.getLevel() + 1);
						treeList.add(i + 1, tv);
						break;
					}
				}
			}

			int node = Integer.valueOf(pc.getId());
			for (MallChannelCate pcb : list) {
				if (node == pcb.getCatPid()) {
					tv.setLeaf(false);// 有子节点
					break;
				} else {
					tv.setLeaf(true);// 无子节点
				}
			}

			tv.setLoaded(false);
			tv.setExpanded(false);
		}

		return treeList;
	}

	@Transactional(readOnly = false)
	public void batchAdd(List<MallChannelCate> list){
		dao.batchInsert(list);
	}

}
