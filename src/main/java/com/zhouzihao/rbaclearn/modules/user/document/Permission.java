package com.zhouzihao.rbaclearn.modules.user.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "permission")
public class Permission {
    @Id
    private String id;
    private String name; // 权限名称
    private String code; // 权限唯一标识
    private String apiPath; // 绑定的API路径
    private String description;
    private String controllerName; // 控制器名
    private Integer status; // 0=有效, 1=逻辑删除
}
