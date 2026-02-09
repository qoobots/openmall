package com.qoobot.openmall.portal.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 销售属性VO
 *
 * @author openmall
 */
@Data
public class SaleAttributeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性值列表
     */
    private List<AttrValueVO> attrValues;

    @Data
    public static class AttrValueVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * SKU ID
         */
        private Long id;

        /**
         * 属性值
         */
        private String attrValue;
    }
}
