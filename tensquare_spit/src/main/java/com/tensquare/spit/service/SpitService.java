package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import com.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 吐槽Service
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 添加
     *
     * @param spit
     */
    public void insert(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0);
        spit.setThumbup(0);
        spit.setShare(0);
        spit.setComment(0);
        spit.setState("1");
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").in(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }

    /**
     * 查询全部
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据Id查找对象
     *
     * @param spitId
     * @return
     */
    public Spit findById(String spitId) {
        if (spitId!=null && !"".equals(spitId)){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spitId));
            Update update = new Update();
            update.inc("visits",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        return spitDao.findById(spitId).get();
    }

    /**
     * 修改
     */
    public void update(String spitId, Spit spit) {
        spit.set_id(spitId);
        spitDao.save(spit);
    }

    /**
     * 删除
     *
     * @param spitId
     */
    public void remove(String spitId) {
        spitDao.deleteById(spitId);
    }

    /**
     * 条件搜索
     *
     * @param spit
     * @return
     */
    public List<Spit> search(Spit spit) {

        Query query = new Query();
        Criteria criteria = new Criteria();
        if (spit.getState()!=null){
            criteria.and("state").is(spit.getState());
        }
        if (spit.getNickname()!=null){
            criteria.and("nickname").is(spit.getNickname());
        }

        String rex = "/你/";
        query.addCriteria(Criteria.where("").regex(rex));
        mongoTemplate.find(query,Spit.class);
        return null;
    }

    /**
     * 分页查询
     *
     * @param spit
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> searchPage(Spit spit, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);

        return null;
    }

    /**
     * 点赞
     *
     * @param spitId
     */
    public void thumbup(String spitId) {
        //第一种方法
//        Spit spit = spitDao.findById(spitId).get();
//        spit.setVisits(spit.getVisits()+1);
//        spitDao.save(spit);
        //第二种方法
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));

        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");

    }

    /**
     * 根据上级Id查询吐糟数据(分页)
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> comment(String parentid, Integer page, Integer size) {

        Pageable pageable = new PageRequest(page, size);
        Page<Spit> comment = spitDao.findByParentid(parentid, pageable);

        return comment;
    }
}
