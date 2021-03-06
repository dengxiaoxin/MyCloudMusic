package com.jacky.mycloudmusic.domain;

import com.jacky.mycloudmusic.util.Constant;

import java.util.List;

/**
 * 歌单
 */
public class Sheet extends BaseMultiItemEntity {
    /**
     * 歌单标题
     */
    private String title;

    /**
     * 歌单封面
     */
    private String banner;

    /**
     * 描述
     */
    private String description;

    /**
     * 点击数
     */
    private int clicks_count;

    /**
     * 收藏数
     */
    private int collections_count;

    /**
     * 评论数
     */
    private int comments_count;

    /**
     * 音乐数量
     */
    private int songs_count;

    /**
     * 歌单创建者
     */
    private User user;

    /**
     * 歌曲
     */
    private List<Song> songs;

    /**
     * 是否收藏了
     * 有值就表示收藏了
     */
    private Integer collection_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClicks_count() {
        return clicks_count;
    }

    public void setClicks_count(int clicks_count) {
        this.clicks_count = clicks_count;
    }

    public int getCollections_count() {
        return collections_count;
    }

    public void setCollections_count(int collections_count) {
        this.collections_count = collections_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getSongs_count() {
        return songs_count;
    }

    public void setSongs_count(int songs_count) {
        this.songs_count = songs_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Integer getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(Integer collection_id) {
        this.collection_id = collection_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sheet{");
        sb.append("title='").append(title).append('\'');
        sb.append(", banner='").append(banner).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", clicks_count=").append(clicks_count);
        sb.append(", collections_count=").append(collections_count);
        sb.append(", comments_count=").append(comments_count);
        //sb.append(", songs_count=").append(songs_count);
        sb.append(", user=").append(user);
        sb.append(", songs=").append(songs);
        sb.append(", collection_id=").append(collection_id);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 是否收藏
     *
     * @return true:收藏；false:没有收藏
     */
    public boolean isCollection() {
        return collection_id != null;
    }

    /**
     * 使用了BaseRecyclerViewAdapterHelper框架
     * 实现多类型列表
     * 需要实现该接口返回类型
     *
     * @return
     */
    @Override
    public int getItemType() {
        return Constant.TYPE_SHEET;
    }

    /**
     * 占多少列
     *
     * @return
     */
    @Override
    public int getSpanSize() {
        return 1;
    }

    /**
     * 获取音乐数量
     *
     * @return
     */
    public int getSongsCount() {
        if (getSongs() != null) {
            return getSongs().size();
        }
        return 0;
    }
}
