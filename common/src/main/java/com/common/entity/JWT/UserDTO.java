package com.common.entity.JWT;

import lombok.Data;

@Data
public class UserDTO {
    /// <summary>
    /// 所属门店id
    /// </summary>
    public String ShopId;

    /// <summary>
    /// 企业微信id
    /// </summary>
//    public String CorpWechatId;

    /// <summary>
    /// 所属门店名称
    /// </summary>
//    public String ShopName;

    /// <summary>
    /// 所属品牌id
    /// </summary>
    public String CompanyId ;

    ///// <summary>
    ///// 所属品牌id
    ///// </summary>
    public String CompanyName;

    /// <summary>
    /// 品牌logo
    /// </summary>
    public String CompanyLogo;

    /// <summary>
    /// 企业微信二维码（不需验证）
    /// </summary>
//    public String CorpWechatQrcode1;

    /// <summary>
    /// 企业微信二维码（需验证）
    /// </summary>
//    public String CorpWechatQrcode2;

    /// <summary>
    /// 用户id。员工id
    /// </summary>
    public String StaffId ;

    /// <summary>
    /// 用户编号=员工编号。不同公司可能相同
    /// </summary>
    public String StaffCode ;

    /// <summary>
    /// 用户名。员工名
    /// </summary>
    public String StaffName ;

    /// <summary>
    /// 员工手机号
    /// </summary>
//    public String Mobile;

    /// <summary>
    /// 角色
    /// </summary>
//    public String[] RoleList ;

    /// <summary>
    /// 权限
    /// </summary>
//    public String[] PermissionList ;

    /// <summary>
    /// 创建时间
    /// </summary>
    public String CreateTime;

    /// <summary>
    /// 最近登录时间
    /// </summary>
    public String LastVisitTime ;

    /// <summary>
    /// 授权过期时间
    /// </summary>
    public String ExpireTime ;

    /// <summary>
    /// 是否管理员
    /// </summary>
    public Boolean IsAdmin ;

    /// <summary>
    /// 管理门店id集合
    /// </summary>
    public String[] ManageShopIdList ;
}
