package com.qoobot.openmall.portal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 购物车DTO
 */
@Data
public class CartItemDTO {

    @NotNull(message = "SKU ID不能为空")
    private Long skuId;

    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须大于0")
    private Integer quantity;
}
