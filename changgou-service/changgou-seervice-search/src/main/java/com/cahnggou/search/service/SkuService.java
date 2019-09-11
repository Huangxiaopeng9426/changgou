package com.cahnggou.search.service;

import java.util.Map;

public interface SkuService {
    /**
     * 将数据导入到索引库中
     */
    void importSku();

    /**
     * 根据
     * @param searchMap
     * @return
     */
    Map search(Map<String,String> searchMap);
}
