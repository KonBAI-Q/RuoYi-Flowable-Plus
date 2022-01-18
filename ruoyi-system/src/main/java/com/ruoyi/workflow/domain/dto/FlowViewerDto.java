package com.ruoyi.workflow.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Xuan xuan
 * @date 2021/4/21 20:55
 */
@Data
public class FlowViewerDto implements Serializable {

    private String key;
    private boolean completed;
}
