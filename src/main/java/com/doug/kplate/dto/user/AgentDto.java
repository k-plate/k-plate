package com.doug.kplate.dto.user;

import com.doug.kplate.enums.EnumAgentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class AgentDto {

    private Integer id;//主键 id
    private String agentCode;//客户编号
    private String agentName;//经纪人/代理商/客户名称
    private String roleName;
    private String loginName;//账号
    private String password;//密码
    private String contactMobile;//手机号
    private String contactName;//真实姓名
    private String noteUserName;//备注用户名
    private Timestamp createtime;//创建时间
    private Timestamp lastLoginTime;//最后一次登录时间
    private Integer orderCount;//订单数
    private BigDecimal balance;//账户余额
    private BigDecimal givingMoney;//赠送金额
    private BigDecimal margin;//保证金
    private String type;//身份
    private String dividends;//红利比例
    private String commissionRatio;//佣金比例
    private String superior;//上级
}
