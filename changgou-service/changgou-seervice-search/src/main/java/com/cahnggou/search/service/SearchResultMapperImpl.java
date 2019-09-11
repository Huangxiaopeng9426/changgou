package com.cahnggou.search.service;

import com.alibaba.fastjson.JSON;
import com.changgou.search.pojo.SkuInfo;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 自定义结果映射
 *目的:获取高亮的数据
 *
 */
public class SearchResultMapperImpl implements SearchResultMapper {
    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
        List<T> count = new ArrayList<>();
        if (searchResponse.getHits() == null || searchResponse.getTotalShards() <=0) {
            return new AggregatedPageImpl<T>(count);
        }
        for (SearchHit hit : searchResponse.getHits()) {
            String sourceAsString = hit.getSourceAsString();//每一个行的数据 json的 数据
            SkuInfo skuInfo = JSON.parseObject(sourceAsString, SkuInfo.class);//将json数据转成Javabean
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("name");

            //判断是否含有高亮的数据,有设置
            if (highlightField != null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (Text text : highlightField.getFragments()) {
                    stringBuffer.append(text.string());
                }
                skuInfo.setName(stringBuffer.toString());
            }
            //将设置好的高亮数据,添加到count集合中,进行展示
            count.add((T)skuInfo);
        }
        return new AggregatedPageImpl<T>(count,pageable,searchResponse.getHits().getTotalHits(),searchResponse.getAggregations(),searchResponse.getScrollId());
    }
}
