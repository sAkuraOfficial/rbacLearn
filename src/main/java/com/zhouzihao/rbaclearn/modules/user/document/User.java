package com.zhouzihao.rbaclearn.modules.user.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private List<String> roleIds; // 关联的角色ID列表
}
