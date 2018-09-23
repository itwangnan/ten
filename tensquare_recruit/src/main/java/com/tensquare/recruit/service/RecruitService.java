package com.tensquare.recruit.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.util.IdWorker;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class RecruitService {

	@Autowired
	private RecruitDao recruitDao;
	
	@Autowired
	private IdWorker idWorker;

	public List<Recruit> findAll() {

		return recruitDao.findAll();
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Recruit> findPage(int page, int size) {
		PageRequest pageRequest = new PageRequest(page-1, size);
		return recruitDao.findAll(pageRequest);
	}

	private Specification<Recruit> where(Map searchMap) {
		
		return new Specification<Recruit>() {
          
			@Override
			public Predicate toPredicate(Root<Recruit> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 职位名称
                if (searchMap.get("jobname")!=null && !"".equals(searchMap.get("jobname"))) {
                	predicateList.add(cb.like(root.get("jobname").as(String.class), "%"+(String)searchMap.get("jobname")+"%"));
                }
                // 薪资范围
                if (searchMap.get("salary")!=null && !"".equals(searchMap.get("salary"))) {
                	predicateList.add(cb.like(root.get("salary").as(String.class), "%"+(String)searchMap.get("salary")+"%"));
                }
                // 经验要求
                if (searchMap.get("condition")!=null && !"".equals(searchMap.get("condition"))) {
                	predicateList.add(cb.like(root.get("condition").as(String.class), "%"+(String)searchMap.get("condition")+"%"));
                }
                // 学历要求
                if (searchMap.get("education")!=null && !"".equals(searchMap.get("education"))) {
                	predicateList.add(cb.like(root.get("education").as(String.class), "%"+(String)searchMap.get("education")+"%"));
                }
                // 任职方式
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                	predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
                }
                // 办公地址
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                	predicateList.add(cb.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 企业ID
                if (searchMap.get("eid")!=null && !"".equals(searchMap.get("eid"))) {
                	predicateList.add(cb.like(root.get("eid").as(String.class), "%"+(String)searchMap.get("eid")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 网址
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
                // 标签
                if (searchMap.get("label")!=null && !"".equals(searchMap.get("label"))) {
                	predicateList.add(cb.like(root.get("label").as(String.class), "%"+(String)searchMap.get("label")+"%"));
                }
                // 职位描述
                if (searchMap.get("content1")!=null && !"".equals(searchMap.get("content1"))) {
                	predicateList.add(cb.like(root.get("content1").as(String.class), "%"+(String)searchMap.get("content1")+"%"));
                }
                // 职位要求
                if (searchMap.get("content2")!=null && !"".equals(searchMap.get("content2"))) {
                	predicateList.add(cb.like(root.get("content2").as(String.class), "%"+(String)searchMap.get("content2")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
                
            }
        };		
		
	}

	public Page<Recruit> findSearch(Map whereMap, int page, int size) {
		Specification<Recruit> specification = where(whereMap);
		PageRequest pageRequest = new PageRequest(page-1, size);
		return recruitDao.findAll(specification, pageRequest);
	}

	public Recruit findOne(String id) {
		return recruitDao.findById(id).get();
	}

	public void add(Recruit recruit) {
		recruit.setId(idWorker.nextId()+""); //主键值
		recruitDao.save(recruit);
	}
	
	public void update(Recruit recruit) {
		recruitDao.save(recruit);
	}

	public void delete(String id) {
		recruitDao.deleteById(id);
	}

	public void deleteList(String[] ids) {
		for (String id : ids) {
			recruitDao.deleteById(id);
		}
	}

	/**
	 * 推荐职位
	 * @return
	 */
	public List<Recruit> recommend() {
		return recruitDao.findByStateOrderByCreatetimeDesc("2");
	}

	public List<Recruit> newlist() {
		return recruitDao.findByOrderByCreatetimeDesc();
	}
}
