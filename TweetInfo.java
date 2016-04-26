package Project4;

import java.util.*;

public class TweetInfo {

	private String popularity;
	private String id;
	private String keyword;
	private String user;
	private String tweet;
	private LinkedList<String> subTweets;

	public TweetInfo(String popularity, String id, String keyword, String user,
			String tweet) {
		this.popularity = popularity;
		this.id = id;
		this.keyword = keyword;
		this.user = user;
		this.tweet = tweet;
	}

	public TweetInfo(String popularity, String id, String keyword, String user,
			String tweet, LinkedList<String> subTweets) {
		super();
		this.popularity = popularity;
		this.id = id;
		this.keyword = keyword;
		this.user = user;
		this.tweet = tweet;
		this.subTweets = subTweets;
	}

	public String getPopularity() {
		return popularity;
	}

	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LinkedList<String> getSubTweets() {
		return subTweets;
	}

	public void setSubTweets(LinkedList<String> subTweets) {
		this.subTweets = subTweets;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

}
