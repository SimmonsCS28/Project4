package Project4;

import java.util.*;

public class TweetTable {

	HashMap<String, ArrayList<TweetInfo>> tweetMap = new HashMap<String, ArrayList<TweetInfo>>();
	public static final String FILE_PATH = "src\\Project4\\project4.csv";

	// Import csv file and build List of keywords with sub list of tweets that
	// contain that keyword

	// **********************************************************************************************************
	public HashMap<String, ArrayList<TweetInfo>> createMap() {

		String tempKeyword = " ", prevKeyword = null;

		String delimiter = ",";
		FileIn fileInput = new FileIn();
		fileInput.openFile(FILE_PATH);
		String line = fileInput.readLine();
		String[] tempInputArray = line.split(delimiter);

		tempKeyword = tempInputArray[2];

		while (line != null) {

			// Create list of subTweets that contain the current keyword
			// If there's no keyword or the previous keyword does not match the
			// current keyword create a new list
			if (prevKeyword == null || !prevKeyword.equals(tempKeyword)) {
				ArrayList<TweetInfo> list = new ArrayList<TweetInfo>();
				list = subListBuilder(tempKeyword); // call subListBuilder
													// method to create list of
													// tweets containing the
													// keyword
				tweetMap.put(tempKeyword, list);
				prevKeyword = tempInputArray[2];

			}

			line = fileInput.readLine();
			if (line != null) {
				tempInputArray = line.split(delimiter);
				tempKeyword = tempInputArray[2];
			}
		}// End of while loop

		return tweetMap;
	}

	// **********************************************************************************************************

	// Method to build sublist for each keyword's tweet
	// **********************************************************************************************************
	public ArrayList<TweetInfo> subListBuilder(String key) {

		ArrayList<TweetInfo> subTweets = new ArrayList<TweetInfo>();

		FileIn fileIn = new FileIn();
		fileIn.openFile(FILE_PATH);
		String line = fileIn.readLine();

		while (line != null) {
			String[] tempInputArray = line.split(",");
			String keyCheck = tempInputArray[2];
			if (key.equalsIgnoreCase(keyCheck)) {
				@SuppressWarnings("resource")
				Scanner sr = new Scanner(line).useDelimiter(",");
				String popularity = sr.next();
				String id = sr.next();
				String keyword = sr.next();
				String user = sr.next();
				String text = sr.next();

				while (sr.hasNext()) {
					String next = sr.next();
					text = text + "," + next;
				}
				TweetInfo tweet = new TweetInfo(popularity, id, keyword, user,
						text);
				subTweets.add(tweet);
			}

			line = fileIn.readLine();
		}
		return subTweets;
	}

	// **********************************************************************************************************

	// Method to sort found tweets by popularity first then by user
	// **********************************************************************************************************
	public ArrayList<TweetInfo> sortTweets(ArrayList<TweetInfo> list) {
		for (int k = 0; k < list.size() - 1; k++) {
			int minIndex = k;

			for (int j = k + 1; j < list.size(); j++) {
				if (list.get(j)
						.getPopularity()
						.compareToIgnoreCase(
								(list.get(minIndex)).getPopularity()) < 0) {
					minIndex = j;
				} else if (list
						.get(j)
						.getPopularity()
						.compareToIgnoreCase(
								(list.get(minIndex)).getPopularity()) == 0) {

					if (list.get(minIndex)
							.getUser()
							.compareToIgnoreCase((list.get(j)).getUser()) < 0)
						minIndex = j;
				}
			}
			if (minIndex != k) {
				TweetInfo tempTweet = list.get(k);
				list.add(k, list.get(minIndex));
				list.remove(k + 1);
				list.add(minIndex, tempTweet);
				list.remove(minIndex + 1);
			}
		}

		return list;
	}

	// **********************************************************************************************************

	// Method to search table for specific keyword
	// **********************************************************************************************************
	public void searchMap() {
		Set<String> kSet = tweetMap.keySet();
		System.out.println("Please enter a keyword to search for.");
		Scanner input = new Scanner(System.in);

		String lInput = input.nextLine();
		Iterator<String> tempIter = kSet.iterator();
		boolean found = false;
		boolean keepSearching = true;
		int i = 0;

		// While the user wants to keep searching and the keyword has not been
		// found, continue looping to search the map
		while (keepSearching == true && found == false) {
			while (tempIter.hasNext()) {
				String aKey = tempIter.next();
				if (aKey.equalsIgnoreCase(lInput)) {
					ArrayList<TweetInfo> lValue = tweetMap.get(aKey);

					lValue = sortTweets(lValue); // call method to sort the
													// tweets

					Iterator<TweetInfo> mIter = lValue.iterator();
					i = lValue.size() - 1;
					System.out.println("\nThe keyword: " + aKey + " contains "
							+ lValue.size() + " tweets.\n");
					while (mIter.hasNext()) {
						mIter.next();

						System.out.println("Popularity: "
								+ lValue.get(i).getPopularity() + " ID: "
								+ lValue.get(i).getId() + " User: "
								+ lValue.get(i).getUser() + " Tweet: "
								+ lValue.get(i).getTweet());
						i--;
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
				lInput = input.nextLine();
				// if response is yes, ask for keyword and reset index starting
				// point to zero
				if (lInput.equalsIgnoreCase("yes")) {
					System.out
							.println("\nPlease enter a keyword to search for.");
					lInput = input.nextLine();
					i = 0;

				}
				// if the user accidentally inputs something other than yes or
				// no, notify them and ask again
				else if (!lInput.equalsIgnoreCase("yes")
						&& !lInput.equalsIgnoreCase("no")) {
					while (!lInput.equalsIgnoreCase("yes")
							&& !lInput.equalsIgnoreCase("no")
							|| !keepSearching == false) {
						System.out
								.println("Oops. You did not enter a valid response. Please try again");
						System.out
								.println("Would you like to search for another keyword? Yes or no?");
						lInput = input.nextLine();
						// if response is yes, reset keepSearching variable to
						// true and reset index starting point to zero
						if (lInput.equalsIgnoreCase("yes")) {
							keepSearching = true;
							i = 0;
							System.out
									.println("\nPlease enter a keyword to search for.");
							lInput = input.nextLine();
							break;
						}
						// else if the response is no, change keepSearching
						// value to false to end search loop
						else if (lInput.equalsIgnoreCase("no")) {
							keepSearching = false;
						}
					}
				}
				// else if the response is no, change keepSearching value to
				// false to end search loop
				else {
					keepSearching = false;
					input.close();
				}
			} // end of if statement

			// If the keyword was found, ask the user if they would like to
			// continue searching for keywords
			else {
				System.out
						.println("\nWould you like to search for another keyword? Yes or no?");
				lInput = input.nextLine();
				// if response is yes, reset found variable to false and reset
				// index starting point to zero
				if (lInput.equalsIgnoreCase("yes")) {
					found = false;
					i = 0;
					System.out
							.println("\nPlease enter a keyword to search for.");
					lInput = input.nextLine();
				}
				// if the user accidentally inputs something other than yes or
				// no, notify them and ask again
				else if (!lInput.equalsIgnoreCase("yes")
						&& !lInput.equalsIgnoreCase("no")) {
					while (!lInput.equalsIgnoreCase("yes")
							&& !lInput.equalsIgnoreCase("no")
							|| !keepSearching == false) {
						System.out
								.println("Oops. You did not enter a valid response. Please try again");
						System.out
								.println("Would you like to search for another keyword? Yes or no?");
						lInput = input.nextLine();
						// if response is yes, reset found variable to false
						// reset index starting point to zero
						if (lInput.equalsIgnoreCase("yes")) {
							keepSearching = true;
							found = false;
							i = 0;
							System.out
									.println("\nPlease enter a keyword to search for.");
							lInput = input.nextLine();
							break;
						}
						// else if the response is no, change keepSearching
						// value to false to end search loop
						else if (lInput.equalsIgnoreCase("no")) {
							keepSearching = false;
						}
					}
				}
				// else if the response is no, change keepSearching value to
				// false to end search loop
				else {
					keepSearching = false;
					input.close();
				}
			}
			// Create a new iterator to reset the original iterator
			Iterator<String> searchIter = kSet.iterator();
			tempIter = searchIter;
		}// end of main search while loop

		input.close();
	} // end of searchMap method
		// **********************************************************************************************************

}// end of TweetTable class
