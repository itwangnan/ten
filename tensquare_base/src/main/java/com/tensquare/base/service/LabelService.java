package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import com.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加一个label
     * @param label
     */
    public void add(Label label) {
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 根据labelId删除labelId
     * @param labelId
     */
    public void remove(String labelId) {
        labelDao.deleteById(labelId);
    }

    /**
     * 根据全部
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据labelId查询Label
     * @param labelId
     * @return
     */
    public Label findOne(String labelId) {
        return labelDao.findById(labelId).get();
    }

    /**
     * 修改
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 搜索
     * @param label
     * @return
     */
    public List<Label> createSpecification(Label label) {

        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Specification<Label> labelnames = new Specification<Label>() {
                        @Override
                        public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                            Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                            list.add(labelname);
                            return labelname;
                        }
                    };
                }
                if (label.getState()!=null && !"".equals(label.getState())){
                    Specification<Label> labelnames = new Specification<Label>() {
                        @Override
                        public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                            Predicate labelname = cb.equal(root.get("state").as(String.class), label.getState());
                            list.add(labelname);
                            return labelname;
                        }
                    };
                }
                Predicate[] predicates = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(predicates));
            }
        });
    }

    /**
     * 分页的条件查询
     */
    public Page<Label> findSearch(Label label, Integer page, Integer size) {


        Pageable pageable = new PageRequest(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Specification<Label> labelnames = new Specification<Label>() {
                        @Override
                        public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                            Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                            list.add(labelname);
                            return labelname;
                        }
                    };
                }
                if (label.getState()!=null && !"".equals(label.getState())){
                    Specification<Label> labelnames = new Specification<Label>() {
                        @Override
                        public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                            Predicate labelname = cb.equal(root.get("state").as(String.class), label.getState());
                            list.add(labelname);
                            return labelname;
                        }
                    };
                }
                Predicate[] predicates = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(predicates));
            }
        },pageable);
    }




}
