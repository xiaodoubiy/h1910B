package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Types;
import com.xiaoshu.entity.TypesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TypesMapper extends BaseMapper<Types> {

	Object findAll();

}