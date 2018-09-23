package com.tensquare.article.service;

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

import com.tensquare.article.dao.ColumnDao;
import com.tensquare.article.pojo.Column;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ColumnService {

	@Autowired
	private ColumnDao columnDao;
	
	@Autowired
	private IdWorker idWorker;

	public List<Column> findAll() {

		return columnDao.findAll();
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Column> findPage(int page, int size) {
		PageRequest pageRequest = new PageRequest(page-1, size);
		return columnDao.findAll(pageRequest);
	}

	private Specification<Column> where(Map searchMap) {
		
		return new Specification<Column>() {
          
			@Override
			public Predicate toPredicate(Root<Column> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 专栏名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 专栏简介
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
                
            }
        };		
		
	}

	public Page<Column> findSearch(Map whereMap, int page, int size) {
		Specification<Column> specification = where(whereMap);
		PageRequest pageRequest = new PageRequest(page-1, size);
		return columnDao.findAll(specification, pageRequest);
	}

	public Column findOne(String id) {
		return columnDao.findById(id).get();
	}

	public void add(Column column) {
		column.setId(idWorker.nextId()+""); //主键值
		columnDao.save(column);
	}
	
	public void update(Column column) {
		columnDao.save(column);
	}

	public void delete(String id) {
		columnDao.deleteById(id);
	}

	public void deleteList(String[] ids) {
		for (String id : ids) {
			columnDao.deleteById(id);
		}
	}

}
