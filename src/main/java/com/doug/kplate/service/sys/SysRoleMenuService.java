package com.doug.kplate.service.sys;

import java.util.List;


/**
 * 角色与菜单对应关系
 * 
 * @author honghu cloud
 * @technology +QQ： 2170983087
 * @date 2016年9月18日 上午9:42:30
 */
public interface SysRoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
	
}
