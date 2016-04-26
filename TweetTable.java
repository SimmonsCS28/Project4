package Project4;

import java.util.*;

public class TweetTable {

	HashMap<String, ArrayList<TweetInfo>> tweetMap = new HashMap<String, ArrayList<TweetInfo>>();

	// Import csv file and build List of keywords with sub list of tweets that
	// contain that keyword
	public HashMap<String, ArrayList<TweetInfo>> createMap() {

		String tempKeyword = " ", prevKeyword = null;
		// Data members for tweet object
		String delimiter = ",";
		FileIn fileInput = new FileIn();
		fileInput
				.openFile("C:\\Users\\SimmonsCS28\\workspace\\CompSci223\\src\\Project4\\project4.csv");
		String line = fileInput.readLine();
		String[] tempInputArray = line.split(delimiter);

		tempKeyword = tempInputArray[2];

		while (line != null) {

			// Create list of subTweets that contain the current keyword
			// If there's no keyword or the previous keyword does not match the
			// current keyword create a new list
			if (prevKeyword == null || !prevKeyword.equals(tempKeyword)) {
				ArrayList<TweetInfo> list = new ArrayList<TweetInfo>();
				list = subListBuilder(tempKeyword);
				tweetMap.put(tempKeyword, list);
				prevKeyword = tempInputArray[2];

			}

			line = fileInput.readLine();
			if (line != null) {
				tempInputArray = line.split(delimiter);
				tempKeyword = tempInputArray[2];
			}
		}

		return tweetMap;
	}
	
	// Method to build sublist for each keyword's tweet
	public ArrayList<TweetInfo> subListBuilder(String keyword) {

		ArrayList<TweetInfo> subTweets = new ArrayList<TweetInfo>();
		String popularity = " ", id = " ", user = " ", text = " ";
		FileIn fileIn = new FileIn();
		fileIn.openFile("C:\\Users\\SimmonsCS28\\workspace\\CompSci223\\src\\Project4\\project4.csv");
		String line = fileIn.readLine();

		while (line != null) {
			String[] tempInputArray = line.split(",");
			String keyCheck = tempInputArray[2];
			if (keyword.equalsIgnoreCase(keyCheck)) {
				popularity = tempInputArray[0];
				id = tempInputArray[1];
				keyword = tempInputArray[2];
				user = tempInputArray[3];
				text = tempInputArray[4];
				TweetInfo tweet = new TweetInfo(popularity, id, keyword, user,
						text);
				subTweets.add(tweet);
			}
			line = fileIn.readLine();
		}
		return subTweets;
	}
	
	// Method to sort found tweets by popularity first then by user
	public void sort(){
		
	}

	// Method to search table for specific keyword
	public void searchMap() {
		Set<String> kSet = tweetMap.keySet();
		System.out.println("Please enter a keyword to search for.");
		Scanner input = new Scanner(System.in);

		String lInput = input.nextLine();
		Iterator<String> tempIter = kSet.iterator();
		boolean found = false;
		boolean keepSearching = true;
		int i = 0;
		
		// While the user wants to keep searching and the keyword has not been found, continue looping to search the map
		while (keepSearching == true && found == false) {
			while (tempIter.hasNext()) {
				String aKey = tempIter.next();
				if (aKey.equalsIgnoreCase(lInput)) {
					System.out.println("Keyword: " + aKey + " has values: ");
					ArrayList<TweetInfo> lValue = tweetMap.get(aKey);
					Iterator<TweetInfo> mIter = lValue.iterator();
					while (mIter.hasNext()) {
						mIter.next();
						System.out.println(lValue.get(i).getUser() + " "
								+ lValue.get(i).getTweet());
						i++;
					} // end of while loop
					found = true;
				} // end of if statement

			} // end of nested while loop
			
			// If the keyword entered does not exist, let the user know and ask
			// for another keyword.
			if (found == false) {
				System.out
						.println("Error. The keyword you've entered does not exist"
								+ "\nWould you like to search for another keyword? Yes or no?");
				if (input.nextLine().equalsIgnoreCase("no")) {
					keepSearching = false;
					input.close();
				} else {
					System.out.println("Please enter a keyword to search for.");
					lInput = input.nextLine();
				}
			} // end of if statement
			
			// If the keyword was found, ask the user if they would like to continue searching for keywords
			else {
				System.out
						.println("Would you like to search for another keyword? Yes or no?");
				if (input.nextLine().equalsIgnoreCase("no")) {
					keepSearching = false;
					input.close();
				} else {
					// Reset found variable too false and index starting point back to zero
					found = false;
					i=0;
					System.out.println("Please enter a keyword to search for.");
					lInput = input.nextLine();
				}
			}
			// Create a new iterator to reset the original iterator 
			Iterator<String> searchIter = kSet.iterator();
			tempIter = searchIter;
		}// end of main while loop
		
		input.close();
	} // end of searchMap method



}
