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

import com.tensquare.article.dao.ChannelDao;
import com.tensquare.article.pojo.Channel;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ChannelService {

	@Autowired
	private ChannelDao channelDao;
	
	@Autowired
	private IdWorker idWorker;

	public List<Channel> findAll() {

		return channelDao.findAll();
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Channel> findPage(int page, int size) {
		PageRequest pageRequest = new PageRequest(page-1, size);
		return channelDao.findAll(pageRequest);
	}

	private Specification<Channel> where(Map searchMap) {
		
		return new Specification<Channel>() {
          
			@Override
			public Predicate toPredicate(Root<Channel> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 频道名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                	predicateList.add(cb.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
                
            }
        };		
		
	}

	public Page<Channel> findSearch(Map whereMap, int page, int size) {
		Specification<Channel> specification = where(whereMap);
		PageRequest pageRequest = new PageRequest(page-1, size);
		return channelDao.findAll(specification, pageRequest);
	}

	public Channel findOne(String id) {
		return channelDao.findById(id).get();
	}

	public void add(Channel channel) {
		channel.setId(idWorker.nextId()+""); //主键值
		channelDao.save(channel);
	}
	
	public void update(Channel channel) {
		channelDao.save(channel);
	}

	public void delete(String id) {
		channelDao.deleteById(id);
	}

	public void deleteList(String[] ids) {
		for (String id : ids) {
			channelDao.deleteById(id);
		}
	}

}
