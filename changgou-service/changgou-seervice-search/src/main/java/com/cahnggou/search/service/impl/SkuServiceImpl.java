package com.cahnggou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.cahnggou.search.dao.SkuEsMapper;
import com.cahnggou.search.service.SearchResultMapperImpl;
import com.cahnggou.search.service.SkuService;
import com.changgou.entity.Result;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Sku;
import com.changgou.search.pojo.SkuInfo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired(required = false)
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Override
    public void importSku() {
        //调用changgou-service-goods微服务
        Result<List<Sku>> skuListResult = skuFeign.findByStatus("1");
        //将数据转成search.Sku
        List<SkuInfo> skuInfos = JSON.parseArray(JSON.toJSONString(skuListResult.getData()), SkuInfo.class);
        for (SkuInfo skuInfo : skuInfos) {
            Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec());
            skuInfo.setSpecMap(specMap);
        }
        skuEsMapper.saveAll(skuInfos);
    }

    @Override
    public Map search(Map<String, String> searchMap) {
        //1.获取关键字
        String keywords = searchMap.get("keywords");
        //1.1判断前端传来的数据是否为空"
        if (StringUtils.isEmpty(keywords)) {
            //如果为空,给定默认值
            keywords = "华为";
        }
        //3.创建查询对象
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();


        //4.设置查询条件
        // 4.1 商品分类的列表展示: 按照商品分类的名称来分组
        //terms  指定分组的一个别名
        //field 指定要分组的字段名
        // size 指定查询结果的数量 默认是10个
        queryBuilder.addAggregation(AggregationBuilders.terms("skuCategoryGroup").field("categoryName").size(50));

        queryBuilder.addAggregation(AggregationBuilders.terms("skuBrandGroup").field("brandName").size(100));

        queryBuilder.addAggregation(AggregationBuilders.terms("skuSpecGroup").field("spec.keyword").size(500));


        //构建高亮条件的属性名称
        queryBuilder.withHighlightFields(new HighlightBuilder.Field("name"));
        //给指定的属性添加样式的标签条件
        queryBuilder.withHighlightBuilder(new HighlightBuilder().preTags("<form style=\"color:red\">").postTags("</form>"));

        //匹配查询  先分词 再查询  主条件查询
        //参数1 指定要搜索的字段
        //参数2 要搜索的值(先分词 再搜索)
        queryBuilder.withQuery(QueryBuilders.multiMatchQuery (keywords ,"name","brandName","categoryName"));
        //=============================== 过滤查询的开始 ===================================
        //设置过滤查询的条件
        //构建过滤查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //获取前端传来的参数brand  品牌过滤查询
        String brand = searchMap.get("brand");
        if (!StringUtils.isEmpty(brand)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("brandName",brand));
        }

        String category = searchMap.get("category");
        if (!StringUtils.isEmpty(category)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("categoryName",category));
        }

        //获取前端传来的参数spec_ 规格过滤查询

        if (searchMap != null) {
            for (String key : searchMap.keySet()) {
                if (key.startsWith("spec_")) {
                    boolQueryBuilder.filter(QueryBuilders.termQuery("specMap."+key.substring(5)+".keyword",searchMap.get(key)));
                }
            }
        }

        //设置价格区间查询
        String price = searchMap.get("price");
        //判断传来的数据是否为空
        if (!StringUtils.isEmpty(price)) {
            //不为空按照 - 进行切割
            String[] split = price.split("-");
            //过滤范围查询
            //判断是否含有*
            if (!split[0].equals("*")) {
                //0-500
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(split[0],true).to(split[1],true));
            }else {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(split[0]));
            }
        }


        queryBuilder.withFilter(boolQueryBuilder);

        //============================ 过滤查询的结束 ==============================

        //构建分页查询条件   PageNum:当前页码  PageSize:每页显示的条数
        //获取前端传来的数据
        String pageNum1 = searchMap.get("pageNum");
        //将字符串强转成integer类型
        Integer pageNum = Integer.valueOf(pageNum1);
        Integer pageSize = 30;
        queryBuilder.withPageable(PageRequest.of(pageNum-1,pageSize));

        //构建price的排序的条件   获取前端传来的数据 sortRule: 要排序的类型  sortField:要排序的字段
        String sortField = searchMap.get("sortField");
        String sortRule = searchMap.get("sortRule");
        if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
            queryBuilder.withSort(SortBuilders.fieldSort(sortField).order(sortRule.equalsIgnoreCase("DESC")? SortOrder.DESC : SortOrder.ASC));
        }


        //5.构建查询对象
        NativeSearchQuery query = queryBuilder.build();
        //6.执行查询
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(query, SkuInfo.class,new SearchResultMapperImpl());

        //6.2获取聚合结果,获取商品分组的列表数据
        StringTerms stringTerms = (StringTerms) skuInfos.getAggregation("skuCategoryGroup");
        List<String> categoryList = getBrandList(stringTerms);

        //6.3 获取 品牌分组结果 列表数据
        StringTerms stringTermsBrand = (StringTerms) skuInfos.getAggregation("skuBrandGroup");
        List<String> brandList = getBrandList(stringTermsBrand);


        //6.4 获取 规格的分组结果 列表数据
    StringTerms specMap = (StringTerms) skuInfos.getAggregation("skuSpecGroup");
        Map<String,Set<String>> specMapSet = new HashMap<>();
        Set<String> specSet = new HashSet<>();
        if (specMap != null) {
            for (StringTerms.Bucket bucket : specMap.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();

                //将数据转成json对象 map key:规格的名称 value:规格名对应的值
                Map<String,String> map = JSON.parseObject(keyAsString, Map.class);
                for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
                    String key = stringStringEntry.getKey();//规格的名称
                    String value = stringStringEntry.getValue();//规格名所对应的值
                    specSet = specMapSet.get(key);
                    if (specSet == null) {
                        specSet = new HashSet<>();
                    }
                    specSet.add(value);//将value添加到set集合中
                    specMapSet.put(key,specSet);//将set获取的数据设置到specMap集合中
                }
            }
        }

        //7.返回结果
        //将获取的数据封装到map集合中
        Map resultMap = new HashMap<>();
        resultMap.put("total", skuInfos.getTotalElements());//总记录数
        resultMap.put("totalPages", skuInfos.getTotalPages());//总页数
        resultMap.put("rows", skuInfos.getContent());//当前页的集合
        resultMap.put("categoryList", categoryList);
        resultMap.put("brandList", brandList);
        resultMap.put("specMapSet",specMapSet);
        resultMap.put("pageNum",pageNum);
        resultMap.put("pageSize",pageSize);
        return resultMap;
    }

    private List<String> getBrandList(StringTerms stringTermsBrand) {
        List<String> brandList = new ArrayList<>();
        if (stringTermsBrand != null) {
            for (StringTerms.Bucket bucket : stringTermsBrand.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();
                brandList.add(keyAsString);
            }
        }
        return brandList;
    }


}
