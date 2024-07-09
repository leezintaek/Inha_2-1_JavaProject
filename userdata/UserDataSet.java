package userdata;

import java.io.*;
import java.util.HashMap;

public class UserDataSet {
    private HashMap<String, User> userMap;
    private static final String DATA_FILE = "users.dat";

    public UserDataSet() {
        userMap = new HashMap<>();
        loadUserData();
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
    }

    public User getUser(String id) {
        return userMap.get(id);
    }

    public boolean containsUser(String id) {
        return userMap.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    private void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            userMap = (HashMap<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("User data file not found. A new file will be created.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUserData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
