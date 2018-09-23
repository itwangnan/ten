package com.tensquare.recruit.controller;
import java.util.List;
import java.util.Map;

import com.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.recruit.pojo.Recruit;
import com.tensquare.recruit.service.RecruitService;

import com.entity.PageResult;
import com.entity.Result;
/**
 * 招聘模块
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Recruit> findAll(){
		return recruitService.findAll();
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Recruit findOne(@PathVariable String id){
		return recruitService.findOne(id);
	}
	
	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public PageResult<Recruit> findPage(@PathVariable int page,@PathVariable int size){		
		Page<Recruit> pageList = recruitService.findPage(page, size);		
		return new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 分页+多条件查询 
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public PageResult<Recruit> findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){		
		Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
		return new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 增加
	 * @param recruit
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Recruit recruit  ){

		recruitService.add(recruit);	
		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 修改
	 * @param recruit
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Recruit recruit,@PathVariable String id ){
		recruit.setId(id);
		recruitService.update(recruit);		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		recruitService.delete(id);
		return new Result(true,StatusCode.OK,"");
	}

	/**
	 * 推荐职位
	 * @return
	 */
	@RequestMapping(value = "/search/recommend",method = RequestMethod.GET )
	public Result recommend(){
		List<Recruit> recruits = recruitService.recommend();
		return new Result(true, StatusCode.OK,"操作成功",recruits);
	}

	/**
	 *最新职位
	 */
	@RequestMapping(value = "/search/newlist",method = RequestMethod.GET )
	public Result newlist(){
		List<Recruit> recruits = recruitService.newlist();
		return new Result(true, StatusCode.OK,"操作成功",recruits);
	}

}
