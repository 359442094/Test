package com.test.model.persistence;

import com.test.model.domain.MtcAfterOrder;
import com.test.model.domain.MtcAfterOrderExample;
import java.util.List;

import com.test.model.dto.MtcAfterOrderDTO;
import com.test.model.dto.QueryShopOrderDTO;
import org.apache.ibatis.annotations.Param;

public interface MtcAfterOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int countByExample(MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int deleteByExample(MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int deleteByPrimaryKey(Long afterOrderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int insert(MtcAfterOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int insertSelective(MtcAfterOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    List<MtcAfterOrder> selectByExampleWithBLOBs(MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    List<MtcAfterOrder> selectByExample(MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    MtcAfterOrder selectByPrimaryKey(Long afterOrderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int updateByExampleSelective(@Param("record") MtcAfterOrder record, @Param("example") MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int updateByExampleWithBLOBs(@Param("record") MtcAfterOrder record, @Param("example") MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int updateByExample(@Param("record") MtcAfterOrder record, @Param("example") MtcAfterOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int updateByPrimaryKeySelective(MtcAfterOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int updateByPrimaryKeyWithBLOBs(MtcAfterOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mtc_after_order
     *
     * @mbggenerated Mon Jul 27 20:19:41 GMT+08:00 2020
     */
    int updateByPrimaryKey(MtcAfterOrder record);

    List<MtcAfterOrder> productList();

    MtcAfterOrderDTO getOrderDtoById(int afterOrderId);

}