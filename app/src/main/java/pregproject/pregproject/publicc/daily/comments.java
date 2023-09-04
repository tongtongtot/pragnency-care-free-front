package pregproject.pregproject.publicc.daily;

public class comments {
    private Integer userid;
    private String data,username;
    private Integer postid;
    private Integer comment_id;
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getUsername(){return username;}

    public Integer getComment_id() {
        return comment_id;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
