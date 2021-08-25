package com.yuantu.demo.web.dao;

import com.yuantu.plancenter.entity.NumSource;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author gongshizhong
 * @date 2021/03/03
 */
public interface NumSourceDao {

    /**
     * 新增（忽略逐渐存在的）
     *
     * @param numSource
     * @return
     */
    Long insertIgnore(NumSource numSource);

    /**
     * 批量新增（逐渐存在的则修改）
     *
     * @param numSourceList
     */
    void batchInsert(List<NumSource> numSourceList);

    /**
     * 补充 schOrderId 字段
     * @param numSource
     */
    void addSchOrderIdById(NumSource numSource);

    /**
     * 通过主键删除
     *
     * @param id
     * @param numSourceType
     * @return
     */
    int deleteNumSourceById(@Param("id") String id, @Param("numSourceType") Integer numSourceType);

    /**
     * 根据ID 批量删除
     * @param ids
     * @return
     */
    int deleteNumSourceByIds(@Param("list") List<String> ids);

    /**
     * 通过唯一性删除
     *
     * @param numSource
     * @return
     */
    int deleteByUnique(NumSource numSource);

    /**
     * 通过业务唯一属性查询
     *
     * @param schResultId
     * @param intervalFlag
     * @param numSourceSeq
     * @param numSourceType
     * @return
     */
    List<NumSource> selectByBizUnique(@Param("schResultId") String schResultId,
                                      @Param("intervalFlag") Integer intervalFlag,
                                      @Param("numSourceSeq") String numSourceSeq,
                                      @Param("numSourceType") Integer numSourceType);

    /**
     * 通过组合查询唯一值
     *
     * @param schResultId
     * @param intervalFlag
     * @param numSourceSeq
     * @param schOrderId
     * @return
     */
    List<NumSource> selectBySchOrderId(@Param("schResultId") String schResultId,
                                       @Param("intervalFlag") Integer intervalFlag,
                                       @Param("numSourceSeq") String numSourceSeq,
                                       @Param("schOrderId") String schOrderId);

    /**
     * 通过条件查询
     *
     * @param schResultId
     * @param intervalFlag
     * @param numSourceType
     * @return
     */
    List<NumSource> queryNumsource(@Param("schResultId") String schResultId,
                                   @Param("intervalFlag") Integer intervalFlag,
                                   @Param("numSourceType") Integer numSourceType);

    /**
     * 通过条件更新状态
     *
     * @param numSource
     * @return
     */
    int updateStatus(NumSource numSource);

    /**
     * 通过条件更新状态和时间
     *
     * @param numSource
     * @return
     */
    int updateStatusTime(NumSource numSource);

    /**
     * 删除小于指定排班日期的数据
     *
     * @param schDate
     * @return
     */
    int deleteLessDate(@Param("schDate") Date schDate);

}
