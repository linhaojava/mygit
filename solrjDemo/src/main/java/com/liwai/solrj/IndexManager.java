package com.liwai.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * solrJ索引管理
 *
 * @author lh
 * @version 1.0
 */
public class IndexManager {
    SolrServer solrServer;

    /**
     * 初始化solrServer
     *
     */
    @Before
    public void init() {
        solrServer = SolrJUtils.getInstance();
    }

    /**
     * 测试添加solr索引
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void testIndexAdd() throws IOException, SolrServerException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "123456");
        doc.addField("name", "林浩");
        solrServer.add(doc);
        solrServer.commit();
    }


    /**
     * 测试删除solr索引
     *
     * @throws IOException
     * @throws SolrServerException
     */
    @Test
    public void testIndexDelete() throws IOException, SolrServerException {
        solrServer.deleteById("123456", 1000);
        solrServer.commit();
    }

    /**
     * 测试查询
     */
    @Test
    public void testQueryindex() throws SolrServerException {
        SolrQuery query = new SolrQuery();
        query.setQuery("product_name:门");
        query.set("fq", "product_catalog_name:幽默杂货");
        query.set("fq", "product_price:[10 TO *]");
        query.set("fl", "id,product_name");
        query.addSort("product_price", SolrQuery.ORDER.desc);
        query.setHighlight(true);
        query.addHighlightField("product_name");
        query.setHighlightSimplePre("<span style='color:red;'>");
        query.setHighlightSimplePost("</span>");
        QueryResponse response = solrServer.query(query);
        SolrDocumentList documents = response.getResults();


        //获取查询条数
        long numFound = documents.getNumFound();
        System.out.println("查询总结果:" + numFound);

        //获取高亮结果
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument document : documents) {
            System.out.println(document.get("product_name"));
            System.out.println(document.get("id"));
            System.out.println("=========================================");
            Map<String, List<String>> map = highlighting.get(document.get("id"));
            List<String> list = map.get("product_name");
            System.out.println(list.get(0));
            System.out.println("==========================================");
        }


    }


}
