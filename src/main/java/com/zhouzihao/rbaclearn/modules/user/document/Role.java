package com.zhouzihao.rbaclearn.modules.user.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "role")
public class Role {
    @Id
    private String id;
    private String name; // 角色名称
    private String code; // 角色唯一标识
    private String description;
    private List<String> permissionIds; // 关联的权限ID列表
}
