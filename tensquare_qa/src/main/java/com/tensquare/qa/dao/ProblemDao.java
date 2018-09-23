package com.tensquare.qa.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = " select * from tb_problem pm,tb_pl pl where pm.id=pl.problemid and pl.labelid=1 order by pm.updatetime desc",nativeQuery = true)
    List<Problem> newlist(String label, Pageable pages);

    @Query(value = " SELECT * FROM tb_problem pm,tb_pl pl WHERE pm.id=pl.problemid AND pl.labelid=1 ORDER BY pm.reply DESC",nativeQuery = true)
    List<Problem> hotlist(String label, Pageable pages);

    @Query(value = " select * from tb_problem pm,tb_pl pl where pm.id=pl.problemid and pl.labelid=1 and pm.reply=0 order by pm.createtime desc",nativeQuery = true)
    List<Problem> waitlist(String label, Pageable pages);
}
