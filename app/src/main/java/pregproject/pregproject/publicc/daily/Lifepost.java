package pregproject.pregproject.publicc.daily;

import android.graphics.Bitmap;

public class Lifepost {

    private String title;
    private  String data,pic_url,create,commentlist,username;
    private Integer pic_cnt,like,userid,type,happiness,commentnum,id;
    public int getid(){return id;}
    public void setid(int id){this.id=id;}
    public String getTitle() {
        return title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(String commentlist) {
        this.commentlist = commentlist;
    }

    public Integer getPic_cnt() {
        return pic_cnt;
    }

    public void setPic_cnt(Integer pic_cnt) {
        this.pic_cnt = pic_cnt;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getHappiness() {
        return happiness;
    }

    public void setHappiness(Integer happiness) {
        this.happiness = happiness;
    }

    public Integer getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Integer commentnum) {
        this.commentnum = commentnum;
    }
}
