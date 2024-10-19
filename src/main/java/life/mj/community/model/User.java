package life.mj.community.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {

    private Integer id;
    private String account_id;
    private String name;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;


}
