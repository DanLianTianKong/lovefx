package com.ldc.cfx.lovefx.mapper;

import com.ldc.cfx.lovefx.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Name:
 * Description:
 * Created by itdragon on 2019-12-12
 */

@Mapper
public interface UserMapper {

    @Insert("insert into user (name ,account_id,token,gmt_creat,gmt_moditied) values (#{name},#{accountId},#{token},#{gmtCreat},#{gmtModified})")
    void inster(User user);
}
