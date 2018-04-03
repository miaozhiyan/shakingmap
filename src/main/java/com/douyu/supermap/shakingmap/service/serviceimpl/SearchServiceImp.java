package com.douyu.supermap.shakingmap.service.serviceimpl;

import com.douyu.supermap.shakingmap.common.entity.Content;
import com.douyu.supermap.shakingmap.common.entity.User;
import com.douyu.supermap.shakingmap.common.vo.inner.ContentTemplate;
import com.douyu.supermap.shakingmap.dao.ContentRepository;
import com.douyu.supermap.shakingmap.dao.UserRepository;
import com.douyu.supermap.shakingmap.service.interfaces.ISearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImp implements ISearchService{
    private static final Logger logger = LoggerFactory.getLogger(ISearchService.class);

    private static final String INDEX_NAME="find";

    private static final String INDEX_TYPE="content";

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransportClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean index(Long contentId) {
        Content content = contentRepository.findOne(contentId);
        if (content == null){
            logger.error("Index content {} does not exist!",contentId);
            return false;
        }
        ContentTemplate template = new ContentTemplate();
        BeanUtils.copyProperties(content,template);
        String [] locations = content.getLocation().split("--");
        if (locations.length!=3){
            logger.error("localtions length error! location:{}",content.getLocation());
            return false;
        }
        template.setLocationCountry(locations[0]);
        template.setLocationRegion(locations[1]);
        template.setLocationCity(locations[2]);

        User user = userRepository.findOne(content.getUid());
        if (user == null){
            logger.error("user with id {} does not exist!",content.getUid());
            return false;
        }
        template.setNickname(user.getNickname());
        template.setAvatar(user.getAvatar());

        SearchRequestBuilder requestBuilder = this.esClient
                .prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery("KEY_CONTENT_ID",contentId));

        logger.debug(requestBuilder.toString());

        SearchResponse searchResponse= requestBuilder.get();

        Long totalHits = searchResponse.getHits().getTotalHits();
        boolean flag = false;
        switch (totalHits.intValue()){
            case 0: {
                flag = create(template);
                break;
            }
            case 1: {
                String esId = searchResponse.getHits().getAt(0).getId();
                flag = update(esId,template);
                break;
            }
            default:{
                flag = deleteAndCreate(totalHits,template);
            }
        }

        if (flag){
            logger.debug("index success with contentId:{}",contentId);
        }
        return flag;
    }

    @Override
    public void remove(Long contentId) {
        DeleteByQueryRequestBuilder builder =
                DeleteByQueryAction.INSTANCE
                        .newRequestBuilder(esClient)
                        .filter(QueryBuilders.termQuery("KEY_CONTENT_ID",contentId))
                        .source(INDEX_NAME);

        logger.debug("delete by query for house"+builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();

        logger.debug("delete total:"+deleted);
    }


    private boolean create(ContentTemplate template){
        try {
            IndexResponse response =
                    this.esClient.prepareIndex(INDEX_NAME,INDEX_TYPE)
                            .setSource(objectMapper.writeValueAsBytes(template), XContentType.JSON)
                            .get();
            logger.debug("Create index with house:"+template);
            if (response.status() == RestStatus.CREATED) return true;
        } catch (JsonProcessingException e) {
            logger.error("SearchService|create异常,template={}",template,e);
        }
        return false;
    }

    private boolean update(String esId,ContentTemplate template){
        try {
            UpdateResponse response =
                    this.esClient.prepareUpdate(INDEX_NAME,INDEX_TYPE,esId)
                            .setDoc(objectMapper.writeValueAsBytes(template), XContentType.JSON)
                            .get();
            logger.debug("Create index with house:"+template);
            if (response.status() == RestStatus.OK) return true;
        } catch (JsonProcessingException e) {
            logger.error("SearchService|create异常,template={}",template,e);
        }
        return false;
    }

    private boolean deleteAndCreate(long totalHit,ContentTemplate template){
        DeleteByQueryRequestBuilder builder =
                DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery("KEY_CONTENT_ID",template.getId()))
                .source(INDEX_NAME);

        logger.debug("delete by query for house"+builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if (deleted!=totalHit){
            logger.warn("need delete {}，but {} was deleted!",totalHit,deleted);
            return false;
        }
        return create(template);
    }
}
