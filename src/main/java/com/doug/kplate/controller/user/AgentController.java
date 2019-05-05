package com.doug.kplate.controller.user;

import com.doug.kplate.common.util.Query;
import com.doug.kplate.controller.sys.AbstractController;
import com.doug.kplate.dto.user.AgentDto;
import com.doug.kplate.entity.sys.SysRoleEntity;
import com.doug.kplate.entity.sys.SysUserEntity;
import com.doug.kplate.entity.user.Agent;
import com.doug.kplate.enums.EnumAgentType;
import com.doug.kplate.service.sys.SysRoleService;
import com.doug.kplate.service.sys.SysUserService;
import com.doug.kplate.service.user.AgentService;
import com.doug.kplate.utils.PageUtils;
import com.doug.kplate.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/user/agent")
public class AgentController extends AbstractController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/list")
    public R List(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);

        List<AgentDto> agentDtos = agentService.queryAll(query);
        Integer total = agentService.queryCount(query);

        PageUtils pageUtil = new PageUtils(agentDtos, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    @RequestMapping("/add")
    @Transactional
    public R add(@RequestBody AgentDto agentDto) {
        Agent agent = new Agent();
        SysUserEntity userEntity = new SysUserEntity();
        //新增用户
        userEntity.setCreateTime(new Date());
        if (StringUtils.isNotBlank(agentDto.getLoginName())) {
            userEntity.setUsername(agentDto.getLoginName());
        } else if (StringUtils.isNotBlank(agentDto.getAgentName())) {
            userEntity.setUsername(agentDto.getAgentName());
        } else {
            userEntity.setUsername(agentDto.getContactName() + "_" + agentDto.getContactMobile());
        }
        userEntity.setPassword(agentDto.getPassword());
        userEntity.setCreateUserId(getUserId());
        userEntity.setCreator(getUser().getUsername());
        userEntity.setOperator(getUser().getUsername());
        userEntity.setMobile(agentDto.getContactMobile());
        userEntity.setStatus(1);
        SysRoleEntity role = getRole(agentDto.getRoleName());
        sysUserService.save(userEntity);
        userEntity = sysUserService.queryByUserName(agentDto.getLoginName());
        //新增经纪人/代理商/客户
        BeanUtils.copyProperties(agentDto, agent);
        if (null != userEntity) {
            agent.setUserId(userEntity.getUserId().intValue());
        }
        agent.setRoleId(role.getRoleId().intValue());
        agent.setAgentType(EnumAgentType.getCodeByName(agentDto.getRoleName()));
        agent.setCreatetime(new Timestamp(System.currentTimeMillis()));
        agent.setAgentCode(UUID.randomUUID().toString());
        agentService.save(agent);
        return R.ok();
    }

    //通过名字获取角色对象
    private SysRoleEntity getRole(String roleName) {
        List<SysRoleEntity> sysRoleEntity = sysRoleService.queryList(null);
        for (SysRoleEntity role : sysRoleEntity) {
            if (role.getRoleName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }
}
