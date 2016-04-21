package CandyCrashGame;

import java.util.Comparator;

public class CompareUser implements Comparator<User> {

	@Override
	public int compare(User user1, User user2) {
		if(user1.getScore()>=user2.getScore())return -1;
		else return 1;
	}

}
