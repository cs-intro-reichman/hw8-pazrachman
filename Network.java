/**
 * Represents a social network. The network has users, who follow other uesrs.
 * Each user is an instance of the User class.
 */
public class Network {

    // Fields
    private User[] users; // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /**
     * Creates a network with some users. The only purpose of this constructor is
     * to allow testing the toString and getUser methods, before implementing other
     * methods.
     */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /**
     * Finds in this network, and returns, the user that has the given name.
     * If there is no such user, returns null.
     * Notice that the method receives a String, and returns a User object.
     * user[] -> [user(dani),user(omer),user(michal),.......]
     */
    public int getUserCount() {
        return userCount;
    }

    public User getUser(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getName().toLowerCase().equals(name.toLowerCase())) {
                return this.users[i]; // return the object not the name
            }
        }

        return null;
    }

    /**
     * Adds a new user with the given name to this network.
     * If ths network is full, does nothing and returns false;
     * If the given name is already a user in this network, does nothing and returns
     * false;
     * Otherwise, creates a new user with the given name, adds the user to this
     * network, and returns true.
     */
    public boolean addUser(String name) {
        //// Replace the following statement with your cod
        if (users.length == userCount) {
            return false;
        }
        User exsitUser = this.getUser(name);
        if (exsitUser != null) {
            return false;
        }
        exsitUser = new User(name);
        users[userCount] = exsitUser;
        userCount++;
        return true;
    }

    /**
     * Makes the user with name1 follow the user with name2. If successful, returns
     * true.
     * If any of the two names is not a user in this network,
     * or if the "follows" addition failed for some reason, returns false.
     */
    public boolean addFollowee(String name1, String name2) {
        //// Replace the following statement with your code
        User user1 = this.getUser(name1);
        User user2 = this.getUser(name2);
        if (user1 == null || user2 == null || name1.toLowerCase().equals(name2.toLowerCase())) {
            return false;
        }
        return user1.addFollowee(name2);
    }

    /**
     * For the user with the given name, recommends another user to follow. The
     * recommended user is
     * the user that has the maximal mutual number of followees as the user with the
     * given name.
     */
    public String recommendWhoToFollow(String name) {
        //// Replace the following statement with your code
        User currentUser = this.getUser(name);
        if (currentUser == null) {
            return null;
        }
        User recomndedUser = null;
        int maxMutal = 0;
        for (int i = 0; i < users.length; i++) {
            User other = users[i];
            if (other != null && !other.equals(currentUser)) {
                int mutual = currentUser.countMutual(other);
                if (mutual > maxMutal) {
                    maxMutal = mutual;
                    recomndedUser = other;
                }
            }

        }
        return recomndedUser != null ? recomndedUser.getName() : null;
    }

    /**
     * Computes and returns the name of the most popular user in this network:
     * The user who appears the most in the follow lists of all the users.
     */
    public String mostPopularUser() {
        //// Replace the following statement with your code
        if (userCount == 0) {
            return null;
        }
        int maxCount = 0;
        String popular = "";
        for (int i = 0; i < userCount; i++) {
            String name = users[i].getName();
            int count = 0;
            for (int j = 0; j < userCount; j++) {
                if (users[j] != null && users[j].follows(name.toLowerCase())) {
                    count++;
                }
            }
            if (count > maxCount) {
                maxCount = count;
                popular = name;
            }
        }

        return popular;
    }

    /**
     * Returns the number of times that the given name appears in the follows lists
     * of all
     * the users in this network. Note: A name can appear 0 or 1 times in each list.
     */
    private int followeeCount(String name) {
        //// Replace the following statement with your code
        int count = 0;
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null && users[i].follows(name.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they
    // follow.
    public String toString() {
        StringBuilder list = new StringBuilder();
        list.append("Network:\n");
        for (int i = 0; i < userCount; i++) {
            if (users[i] != null) {
                list.append(users[i].toString());
                list.append("\n");
            }
        }
        return list.toString();
    }
}
