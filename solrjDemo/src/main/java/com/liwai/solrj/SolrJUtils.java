package com.liwai.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

/**
 * solrj服务连接
 */
public class SolrJUtils {
    private static final String baseURL = "http://localhost:8081/solr/collection1/";
    private static SolrServer instance = null;

    static {
        instance = new HttpSolrServer(baseURL);
    }

    public SolrJUtils() {
    }
    public static SolrServer getInstance(){
        return instance;
    }
}
