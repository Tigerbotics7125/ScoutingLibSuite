package cubscout.backend.pojos;

import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.ArrayList;

public class Team {

  private ObjectId id;
  private Instant submissionTime = Instant.now();
  private int number = 0;
  private String name = "";
  private long scouterId = 0L;
  private ArrayList<Comment> comments = new ArrayList<>();

  public Team() {}

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public Instant getSubmissionTime() {
    return submissionTime;
  }

  public void setSubmissionTime(Instant submissionTime) {
    this.submissionTime = submissionTime;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getScouterId() {
    return scouterId;
  }

  public void setScouterId(long scouterId) {
    this.scouterId = scouterId;
  }

  public ArrayList<Comment> getComments() {
    return comments;
  }

  public void setComments(ArrayList<Comment> comments) {
    this.comments = comments;
  }

  public void addComment(Comment comment) {
    this.comments.add(comment);
  }

  public static class Comment {
    private String title;
    private String content;

    public Comment() {}

    public Comment(String title, String content) {
      this.title = title;
      this.content = content;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }
  }
}
