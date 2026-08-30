package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.*;
import com.example.entity.es.TopicDocument;
import com.example.entity.vo.request.AddCommentVO;
import com.example.entity.vo.request.TopicCreateVO;
import com.example.entity.vo.request.TopicTypeCreateVO;
import com.example.entity.vo.request.TopicUpdateVO;
import com.example.entity.vo.response.*;
import com.example.mapper.*;
import com.example.repository.TopicRepository;
import com.example.service.NotificationService;
import com.example.service.TopicService;
import com.example.utils.CacheUtils;
import com.example.utils.Const;
import com.example.utils.FlowUtils;
import com.example.utils.ProhibitedUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

    @Resource
    TopicTypeMapper mapper;

    @Resource
    FlowUtils flowUtils;

    @Resource
    CacheUtils cacheUtils;

    @Resource
    ProhibitedUtils prohibitedUtils;

    @Resource
    AccountMapper accountMapper;

    @Resource
    AccountPrivacyMapper accountPrivacyMapper;

    @Resource
    AccountDetailsMapper accountDetailsMapper;

    @Resource
    TopicCommentMapper topicCommentMapper;

    @Resource
    StringRedisTemplate template;

    @Resource
    NotificationService notificationService;

    @Resource
    TopicRepository topicRepository;

    private Set<Integer> types=null;

    @PostConstruct
    private void initTypes(){
        types=this.listTypes()
                .stream().map(TopicType::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public List<TopicType> listTypes() {
        return mapper.selectList(null);
    }

    @Override
    public void updateTopicType(TopicTypeVO vo) {
        TopicType topicType = mapper.selectById(vo.getId());
        BeanUtils.copyProperties(vo,topicType);
        mapper.updateById(topicType);
    }

    @Override
    public void deleteTopicType(int id) {
        TopicType type = mapper.selectById(id);
        if (mapper.deleteById(id)>0) {
            List<Topic> list = baseMapper.selectList(Wrappers.<Topic>query().eq("type", type.getId()));
            list.forEach(topic-> deleteTopic(topic.getId()));
        }
    }

    @Override
    public void createTopicType(TopicTypeCreateVO vo) {
        TopicType type=new TopicType();
        BeanUtils.copyProperties(vo,type);
        mapper.insert(type);
    }

    @Override
    public void changeTopicType(int tid, int type) {
        if(baseMapper.update(null, Wrappers.<Topic>update()//改了这个要更新缓存（缓存可以封装成方法了）
                .eq("id", tid)
                .set("type", type)
        )>1){
            cacheUtils.deleteCachePattern(Const.FORUM_TOPIC_PREVIEW_CACHE + "*");
        }
    }

    @Override
    public String createTopic(int uid, TopicCreateVO vo) {
        if (!textLimitCheck(vo.getContent(),20000))
            return "文章内容太多，发文失败";
        if (!types.contains(vo.getType()))
            return "文章类型非法！";
        String key= Const.FORUM_TOPIC_CREATE_COUNTER+uid;
        if (!flowUtils.limitPeriodCounterCheck(key,3,3600))
            return "发文频繁，请稍后再试！";
        if (prohibitedUtils.containsProhibitedWord(vo.getContent()))
            return "包含违禁词，发文失败!";
        Topic topic = new Topic();
        BeanUtils.copyProperties(vo,topic);
        topic.setContent(vo.getContent().toJSONString());
        topic.setUid(uid);
        topic.setTime(new Date());
        topic.createIntro();
        if (this.save(topic)){
            cacheUtils.deleteCachePattern(Const.FORUM_TOPIC_PREVIEW_CACHE + "*");
            return null;
        }else {
            return "内部错误，请联系管理员!";
        }
    }

    @Override
    public String updateTopic(int uid, TopicUpdateVO vo) {
        if (!textLimitCheck(vo.getContent(),20000))
            return "文章内容太多，发文失败";
        if (!types.contains(vo.getType()))
            return "文章类型非法！";
        if (prohibitedUtils.containsProhibitedWord(vo.getContent()))
            return "包含违禁词，发文失败!";
        int result= baseMapper.update(null,Wrappers.<Topic>update()
                .eq("uid",uid)
                .eq("id",vo.getId())
                        .eq("locked",0)
                .set("title",vo.getTitle())
                .set("content",vo.getContent().toString())
                .set("type",vo.getType())
                .set("intro",Topic.recreateIntro(vo.getContent()))
        );
        return result >0?null:"文章被锁定，无法进行修改！";
    }

    @Override
    public String createComment(int uid, AddCommentVO vo) {
        if (!textLimitCheck(JSONObject.parseObject(vo.getContent()),2000))
            return "评论内容太多，发表失败！";
        String key= Const.FORUM_TOPIC_COMMENT_COUNTER+uid;
        if (!flowUtils.limitPeriodCounterCheck(key,2,60))
            return "发表评论频繁，请稍后再试！";
        if (prohibitedUtils.containsProhibitedWord(vo.getContent()))
            return "包含违禁词，发文失败!";
        TopicComment comment=new TopicComment();
        comment.setUid(uid);
        BeanUtils.copyProperties(vo,comment);
        comment.setTime(new Date());
        topicCommentMapper.insert(comment);
        Topic topic=baseMapper.selectById(vo.getTid());
        Account account =accountMapper.selectById(uid);
        if (vo.getQuote()>0){
            TopicComment com =topicCommentMapper.selectById(vo.getQuote());
            if (!Objects.equals(account.getId(),com.getUid())){
                notificationService.addNotification(
                        com.getUid(),
                        "您有新的帖子评论被回复",
                        account.getUsername()+"回复了你发表的评论，快去看看吧！",
                        "success","/index/topic-detail/"+com.getTid()
                );
            }
        }else if (!Objects.equals(account.getId(),topic.getUid())){
            notificationService.addNotification(
                    topic.getUid(),
                    "您有新的帖子回复",
                    account.getUsername()+"回复了你发表的主题"+topic.getTitle()+"，快去看看吧！",
                    "success","/index/topic-detail/"+topic.getId()
            );
        }
        return null;
    }

    @Override
    public List<CommentVO> comments(int tid, int pageNumber) {
        Page<TopicComment> page=Page.of(pageNumber,10);
        topicCommentMapper.selectPage(page,Wrappers.<TopicComment>query().eq("tid",tid));
        return page.getRecords().stream().map(dto->{
            CommentVO vo=new CommentVO();
            BeanUtils.copyProperties(dto,vo);
            if (dto.getQuote()>0){
                TopicComment comment = topicCommentMapper.selectOne(Wrappers.<TopicComment>query()
                        .eq("id", dto.getQuote()).orderByAsc("time"));
                if (comment!=null){
                    JSONObject object=JSONObject.parseObject(comment.getContent());
                    StringBuilder builder = new StringBuilder();
                    this.shortContent(object.getJSONArray("ops"),builder,ignore->{});
                    vo.setQuote(builder.toString());
                }else {
                    vo.setQuote("此评论已被删除！");
                }
            }
            CommentVO.User user=new CommentVO.User();
            this.fillUserDetailByPrivacy(user,dto.getUid());
            vo.setUser(user);
            return vo;
        }).toList();
    }

    @Override
    public void deleteComment(int id, int uid) {
        topicCommentMapper.delete(Wrappers.<TopicComment>query().eq("id",id).eq("uid",uid));
    }

    @Override
    public void deleteTopic(int id) {
        baseMapper.deleteById(id);
        cacheUtils.deleteCachePattern(Const.FORUM_TOPIC_PREVIEW_CACHE + "*");
        baseMapper.deleteTopicCollect(id);
    }

    @Override
    public void deleteTopic(int tid, int uid) {
        int result= baseMapper.delete(Wrappers.<Topic>query()
                .eq("id",tid)
                .eq("uid",uid)
        );
        if (result>0){
            cacheUtils.deleteCachePattern(Const.FORUM_TOPIC_PREVIEW_CACHE + "*");
            baseMapper.deleteTopicCollect(tid);
        }
    }

    @Override
    public void setTopicTop(int tid, boolean top) {
        baseMapper.update(null,Wrappers.<Topic>update()
        .eq("id",tid)
        .set("top",top));
    }

    @Override
    public void setTopicLocked(int tid, boolean locked) {
        baseMapper.update(null,Wrappers.<Topic>update()
        .eq("id",tid)
                .set("locked",locked));
    }

    @Override
    public void setTopicInvisible(int tid, boolean invisible) {
        baseMapper.update(null,Wrappers.<Topic>update()
        .eq("id",tid)
                .set("invisible",invisible));
        cacheUtils.deleteCachePattern(Const.FORUM_TOPIC_PREVIEW_CACHE + "*");
    }

    @Override
    public List<TopicPreviewVO> listTopicCollects(int uid) {
        return baseMapper.collectTopics(uid)
                .stream()
                .map(topic -> {
                    TopicPreviewVO vo=new TopicPreviewVO();
                    BeanUtils.copyProperties(topic,vo);
                    return vo;
                })
                .toList();
    }

    @Override
    public JSONObject listAllTopicByPage(int page, int size, String keyword) {
        Page<Topic> topicPage=baseMapper.selectPage(Page.of(page,size),
                Wrappers.<Topic>query()
                        .select("id","title","uid","type","time","top","locked","invisible")
                        .like(keyword!=null,"title","%"+ keyword +"%")
                        .orderByDesc("time"));
        List<TopicPreviewVO> list = topicPage.getRecords().stream().map(this::resolveToPreview).toList();
        JSONObject object=new JSONObject();
        object.put("list",list);
        object.put("total", topicPage.getTotal());
        return object;
    }

    @Override
    public List<TopicPreviewVO> listTopicByPage(int pageNumber, int type) {
        String key=Const.FORUM_TOPIC_PREVIEW_CACHE+pageNumber+":"+type;
        List<TopicPreviewVO> list=cacheUtils.takeListFromCache(key,TopicPreviewVO.class);
        if (list!=null) return list;//有缓存就返回缓存，没有就执行下面的代码（重新构造一次）
        Page<Topic> page= Page.of(pageNumber,10 );
        if (type==0){
            baseMapper.selectPage(page,Wrappers.<Topic>query()
                    .eq("invisible",0)
                    .orderByDesc("time"));
        }else {
            baseMapper.selectPage(page,Wrappers.<Topic>query()
                    .eq("type",type)
                    .eq("invisible",0)
                    .orderByDesc("time"));
        }
        List<Topic> topics=page.getRecords();
        if (topics.isEmpty()) return List.of();
        list=topics.stream().map(this::resolveToPreview).toList();
        cacheUtils.saveListToCache(key,list,60);
        return list;
    }

    @Override
    public List<TopicTopVO> listTopTopics() {
        List<Topic> topics = baseMapper.selectList(Wrappers.<Topic>query()
                .select("id","title","time")
                .eq("top",1));
        return topics.stream().map(topic -> {
            TopicTopVO vo=new TopicTopVO();
            BeanUtils.copyProperties(topic,vo);
            return vo;
        }).toList();
    }

    @Override
    public TopicDetailVO getTopic(int tid,int uid) {
        TopicDetailVO vo=new TopicDetailVO();
        Topic topic = baseMapper.selectById(tid);
        if (topic.getInvisible()==1 && topic.getUid()!=uid){
            return null;
        }
        BeanUtils.copyProperties(topic,vo);
        TopicDetailVO.Interact interact=new TopicDetailVO.Interact(
                hasInteract(tid, uid, "like"),
                hasInteract(tid, uid, "collect")
        );
        vo.setInteract(interact);
        TopicDetailVO.User user = new TopicDetailVO.User();
        vo.setUser(this.fillUserDetailByPrivacy(user,topic.getUid()));
        vo.setComments(topicCommentMapper.selectCount(Wrappers.<TopicComment>query().eq("tid",tid)));
        return vo;
    }

    @Override
    public void interact(Interact interact, boolean state) {
        String type=interact.getType();
        synchronized (type.intern()){
            template.opsForHash().put(type,interact.toKey(),Boolean.toString(state));
            this.saveInteractSchedule(type);
        }
    }

    @Override
    public List<Topic> listTopicByUser(int uid) {
        return baseMapper.selectList(Wrappers.<Topic>query().eq("uid",uid));
    }

    @Override
    public List<TopicSearchVO> searchTopic(String keyword) {
        List<SearchHit<TopicDocument>> list = topicRepository.findByTitleOrIntro(keyword);
        return list.stream().map(item ->{
            TopicSearchVO vo=new TopicSearchVO();
            BeanUtils.copyProperties(item.getContent(),vo);
            vo.setHighlight(item.getHighlightFields());
            return vo;
        }).toList();
    }

    private boolean hasInteract(int tid, int uid, String type){
        String key=tid+":"+uid;
        if (template.opsForHash().hasKey(type,key)){
            return Boolean.parseBoolean(template.opsForHash().entries(type).get(key).toString());
        }
        return baseMapper.userInteractCount(tid, uid, type) >0;
    }

    private final Map<String,Boolean> state=new HashMap<>();
    ScheduledExecutorService service= Executors.newScheduledThreadPool(2);
    private void saveInteractSchedule(String type){
        if (!state.getOrDefault(type,false)){
            state.put(type,true);
            service.schedule(()->{
                this.saveInteract(type);
                state.put(type,false);
            },3, TimeUnit.SECONDS);
        }
    }

    private void saveInteract(String type){
        synchronized (type.intern()){
            LinkedList<Interact> check=new LinkedList<>();
            LinkedList<Interact> uncheck=new LinkedList<>();
            template.opsForHash().entries(type).forEach((k,v)->{
                if (Boolean.parseBoolean(v.toString()))
                    check.add(Interact.parseInteract(k.toString(),type));
                else
                    uncheck.add(Interact.parseInteract(k.toString(),type));
            });
            if (!check.isEmpty())
                baseMapper.addInteract(check,type);
            if (!uncheck.isEmpty())
                baseMapper.deleteInteract(uncheck,type);
            template.delete(type);//把hash删掉，继续迎接下一波来找我们玩的信息
        }
    }

    private <T> T fillUserDetailByPrivacy(T target, int uid){
        AccountDetails details = accountDetailsMapper.selectById(uid);
        Account account = accountMapper.selectById(uid);
        AccountPrivacy privacy = accountPrivacyMapper.selectById(uid);
        String[] ignores = privacy.hiddenFields();
        BeanUtils.copyProperties(account,target,ignores);
        BeanUtils.copyProperties(details,target,ignores);
        return target;
    }

    private TopicPreviewVO resolveToPreview(Topic topic){
        TopicPreviewVO vo = new TopicPreviewVO();
        BeanUtils.copyProperties(accountMapper.selectById(topic.getUid()),vo);
        BeanUtils.copyProperties(topic,vo);
        vo.setLike(baseMapper.interactCount(topic.getId(),"like"));
        vo.setCollect(baseMapper.interactCount(topic.getId(),"collect"));
        List<String> images = new ArrayList<>();
        StringBuilder previewText = new StringBuilder();
        if (topic.getContent()!=null){
            JSONArray ops =JSONObject.parseObject(topic.getContent()).getJSONArray("ops");
            this.shortContent(ops,previewText,obj->images.add(obj.toString()));
        }
        vo.setText(previewText.length()>300?previewText.substring(0,300):previewText.toString());
        vo.setImages(images);
        return vo;
    }

    private void shortContent(JSONArray ops, StringBuilder previewText, Consumer<Object> imageHandler){
        for (Object op : ops) {
            Object insert =JSONObject.from(op).get("insert");
            if (insert instanceof String text){
                if (previewText.length()>=300)  continue;
                previewText.append(text);
            } else if (insert instanceof Map<?, ?> map) {
                Optional.ofNullable(map.get("image")).ifPresent(imageHandler);
            }
        }
    }

    private boolean textLimitCheck(JSONObject object,int max){
        if (object==null) return false;
        long length=0;
        for (Object op : object.getJSONArray("ops")) {
            length+=JSONObject.from(op).getString("insert").length();
            if (length>max) return false;
        }
        return true;
    }
}
