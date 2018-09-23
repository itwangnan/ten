package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import com.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class EnterpriseService {

	@Autowired
	private EnterpriseDao enterpriseDao;
	
	@Autowired
	private IdWorker idWorker;

	public List<Enterprise> findAll() {

		return enterpriseDao.findAll();
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Enterprise> findPage(int page, int size) {
		PageRequest pageRequest = new PageRequest(page-1, size);
		return enterpriseDao.findAll(pageRequest);
	}

	private Specification<Enterprise> where(Map searchMap) {
		
		return new Specification<Enterprise>() {
          
			@Override
			public Predicate toPredicate(Root<Enterprise> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 企业名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 企业简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 企业地址
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                	predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 标签列表
                if (searchMap.get("labels")!=null && !"".equals(searchMap.get("labels"))) {
                	predicateList.add(cb.like(root.get("labels").as(String.class), "%"+(String)searchMap.get("labels")+"%"));
                }
                // 坐标
                if (searchMap.get("coordinate")!=null && !"".equals(searchMap.get("coordinate"))) {
                	predicateList.add(cb.like(root.get("coordinate").as(String.class), "%"+(String)searchMap.get("coordinate")+"%"));
                }
                // 是否热门
                if (searchMap.get("ishot")!=null && !"".equals(searchMap.get("ishot"))) {
                	predicateList.add(cb.like(root.get("ishot").as(String.class), "%"+(String)searchMap.get("ishot")+"%"));
                }
                // LOGO
                if (searchMap.get("logo")!=null && !"".equals(searchMap.get("logo"))) {
                	predicateList.add(cb.like(root.get("logo").as(String.class), "%"+(String)searchMap.get("logo")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
                
            }
        };		
		
	}

	public Page<Enterprise> findSearch(Map whereMap, int page, int size) {
		Specification<Enterprise> specification = where(whereMap);
		PageRequest pageRequest = new PageRequest(page-1, size);
		return enterpriseDao.findAll(specification, pageRequest);
	}

	public Enterprise findOne(String id) {
		return enterpriseDao.findById(id).get();
	}

	public void add(Enterprise enterprise) {
		enterprise.setId(idWorker.nextId()+""); //主键值
		enterpriseDao.save(enterprise);
	}
	
	public void update(Enterprise enterprise) {
		enterpriseDao.save(enterprise);
	}

	public void delete(String id) {
		enterpriseDao.deleteById(id);
	}

	public void deleteList(String[] ids) {
		for (String id : ids) {
			enterpriseDao.deleteById(id);
		}
	}

	/**
	 * 热门企业
	 * @return
	 */
	public List<Enterprise> hotlist() {
		return enterpriseDao.findByIshot("1");
	}
}
