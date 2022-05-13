package cubscout.backend.pojos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionMessageBuilder;

import cubscout.utilities.Util;

public class Team {

  private Instant mLastUpdated = Instant.now();
  private int mNumber = 0;
  private String mName = new String();
  private String mScouterId = new String();
  private ArrayList<String> mCommentTitles = new ArrayList<>();
  private ArrayList<String> mCommentContents = new ArrayList<>();
  private String mImageLink = new String();
  private ArrayList<BufferedImage> mImages = new ArrayList<>();

  public Team() {}

  public Instant getLastUpdated() {
    return mLastUpdated;
  }

  protected void setLastUpdated(Instant lastUpdated) {
    mLastUpdated = lastUpdated;
  }

  public int getNumber() {
    return mNumber;
  }

  protected void setNumber(int number) {
    mNumber = number;
  }

  public String getName() {
    return mName;
  }

  protected void setName(String name) {
    mName = name;
  }

  public String getScouterId() {
    return mScouterId;
  }

  protected void setScouterId(String scouterId) {
    mScouterId = scouterId;
  }

  public ArrayList<String> getCommentTitles() {
    return mCommentTitles;
  }

  protected void setCommentTitles(ArrayList<String> commentTitles) {
    mCommentTitles = commentTitles;
  }

  public ArrayList<String> getCommentContents() {
    return mCommentContents;
  }

  protected void setCommentContents(ArrayList<String> commentContents) {
    mCommentContents = commentContents;
  }

  public List<BufferedImage> getImages() {
    return mImages;
  }

  protected void setImages(ArrayList<BufferedImage> images) {
    mImages = images;
  }
}
