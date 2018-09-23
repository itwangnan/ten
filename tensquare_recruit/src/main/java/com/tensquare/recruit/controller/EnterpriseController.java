package com.tensquare.recruit.controller;

import com.entity.PageResult;
import com.entity.Result;
import com.entity.StatusCode;
import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 企业模块
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {

	@Autowired
	private EnterpriseService enterpriseService;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Enterprise> findAll(){
		return enterpriseService.findAll();
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Enterprise findOne(@PathVariable String id){
		return enterpriseService.findOne(id);
	}
	
	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public PageResult<Enterprise> findPage(@PathVariable int page, @PathVariable int size){
		Page<Enterprise> pageList = enterpriseService.findPage(page, size);		
		return new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 分页+多条件查询 
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public PageResult<Enterprise> findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){		
		Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
		return new PageResult<Enterprise>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 增加
	 * @param enterprise
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Enterprise enterprise  ){

		enterpriseService.add(enterprise);	
		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 修改
	 * @param enterprise
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Enterprise enterprise,@PathVariable String id ){
		enterprise.setId(id);
		enterpriseService.update(enterprise);		
		return new Result(true, StatusCode.OK,"");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		enterpriseService.delete(id);
		return new Result(true,StatusCode.OK,"");
	}


	@RequestMapping(value="/search/hotlist",method=RequestMethod.GET)
	public Result hotlist(){
		List<Enterprise> list = enterpriseService.hotlist();
		return new Result(true,StatusCode.OK,"操作正确",list);
	}
}
