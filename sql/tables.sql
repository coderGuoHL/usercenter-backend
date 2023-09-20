create table if not exists user
(
    id           bigint auto_increment comment '用户id'
    primary key,
    user_account varchar(256)                        null comment '用户账户',
    username     varchar(256)                        null comment '用户名',
    avatar_url   varchar(1024)                       null comment '用户头像地址',
    gender       tinyint   default 0                 not null comment '性别',
    password     varchar(256)                        null comment '密码',
    email        varchar(512)                        null comment '邮箱',
    user_role    int       default 0                 not null comment '用户角色 0-用户 1-管理员',
    user_status  int       default 0                 not null comment '用户状态可拓展 0-有效 1表示无效',
    create_time  timestamp default CURRENT_TIMESTAMP not null,
    update_time  timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    is_delete    tinyint   default 0                 not null comment '是否删除 0-没有删除 1-表示删除'
    )
    comment '用户信息表';

