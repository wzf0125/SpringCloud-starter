package org.quanta.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description: 批量删除对象
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDeleteDTO {
    @NotEmpty
    @ApiModelProperty("批量删除id列表")
    List<@NotNull Integer> idList;
}
