package com.ruoyi.flowable.utils;

import cn.hutool.core.convert.Convert;
import com.ruoyi.flowable.core.FormConf;

import java.util.Map;

/**
 * 流程表单工具类
 *
 * @author KonBAI
 * @createTime 2022/8/7 17:09
 */
public class ProcessFormUtils {

    /**
     * 填充表单项内容
     *
     * @param formConf 表单配置信息
     * @param data 表单内容
     */
    public static void fillFormData(FormConf formConf, Map<String, Object> data) {
        for (Map<String, Object> field : formConf.getFields()) {
            String modelKey = Convert.toStr(field.get("__vModel__"));
            Object value = data.get(modelKey);
            if (value != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> configMap = (Map<String, Object>) field.get("__config__");
                configMap.put("defaultValue", value);
            }
        }
    }
}
