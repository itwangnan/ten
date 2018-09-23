package com.tensquare.article.controller;
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

import com.tensquare.article.pojo.Channel;
import com.tensquare.article.service.ChannelService;

import com.entity.PageResult;
import com.entity.Result;
/**
 * 频道模块
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(value="/",method=RequestMethod.GET)
	public List<Channel> findAll(){
		return channelService.findAll();
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Channel findOne(@PathVariable String id){
		return channelService.findOne(id);
	}
	
	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public PageResult<Channel> findPage(@PathVariable int page,@PathVariable int size){		
		Page<Channel> pageList = channelService.findPage(page, size);		
		return new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 分页+多条件查询 
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public PageResult<Channel> findSearch(@RequestBody Map searchMap ,@PathVariable int page,@PathVariable int size){		
		Page<Channel> pageList = channelService.findSearch(searchMap, page, size);
		return new PageResult<Channel>(pageList.getTotalElements(), pageList.getContent());		
	}
	
	/**
	 * 增加
	 * @param channel
	 */
	@RequestMapping(value="/",method=RequestMethod.POST)
	public Result add(@RequestBody Channel channel  ){

		channelService.add(channel);	
		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 修改
	 * @param channel
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Result update(@RequestBody Channel channel,@PathVariable String id ){
		channel.setId(id);
		channelService.update(channel);		
		return new Result(true,StatusCode.OK,"");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		channelService.delete(id);
		return new Result(true,StatusCode.OK,"");
	}
	
}
