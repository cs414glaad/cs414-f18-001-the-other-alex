package edu.colostate.cs.cs414.f18.the_other_alex.model.controllers;

import edu.colostate.cs.cs414.f18.the_other_alex.model.Invite;
import edu.colostate.cs.cs414.f18.the_other_alex.model.User;
import edu.colostate.cs.cs414.f18.the_other_alex.model.exceptions.InvalidInputException;
import edu.colostate.cs.cs414.f18.the_other_alex.model.exceptions.UserNotFoundException;
import edu.colostate.cs.cs414.f18.the_other_alex.model.Database;
import edu.colostate.cs.cs414.f18.the_other_alex.server.exceptions.FailedApiCallException;
import edu.colostate.cs.cs414.f18.the_other_alex.server.exceptions.InvalidApiCallException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

public class UserService extends Observable {
  // TODO: set changed followed by notify for any changes (save to database)
  private Database database;
  private ArrayList<User> cachedUsers;

  public UserService(Database database) {
    this.database = database;
    cachedUsers = new ArrayList<>();
  }

  /**
   * Throws exception for when user not found
   *
   * @param username
   * @return
   */
  public User getUser(String username) throws UserNotFoundException {
    for (User user : cachedUsers) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }
    if (database != null) {
      try {
        return database.getUser(username);
      } catch (Exception e) {
        throw new UserNotFoundException();
      }
    } else {
      throw new UserNotFoundException();
    }
  }

  /**
   * Throws exception if user not found
   *
   * @param email
   * @return
   */
  public User getUserByEmail(String email) throws UserNotFoundException {
    for (User user : cachedUsers) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    if (database != null) {
      try {
        return database.getUserByEmail(email);
      } catch (Exception e) {
        throw new UserNotFoundException();
      }
    } else {
      throw new UserNotFoundException();
    }
  }
  /**
    This method creates and cashes a user if that user has valid credentials and doesn't already exist
   */
  public User registerUser(String username, String email, String password) throws FailedApiCallException, InvalidInputException,
          SQLException, ClassNotFoundException, IllegalAccessException, IOException, InstantiationException {
    try {
      getUser(username); // throws exception
      throw new FailedApiCallException("user already exists");
    } catch (UserNotFoundException e) {
      // user doesn't exist
    }
    try {
      getUserByEmail(email); // throws exception
      throw new FailedApiCallException("user already exists");
    } catch (UserNotFoundException e) {
      // user doesn't exist
    }
      //validate email
      if(!isValidEmailAddress(email)) {
        throw new InvalidInputException("invalid email");
      }

    // Create user if user doesn't already exist under the given username/email and if email is valid
    User user = new User(username, email, password);
    cachedUsers.add(user);
    setChanged();
    notifyObservers();
    if (database != null) {
      database.addSerializedObject(user);
    }
    return user;
  }

  public User unregisterUser(User user) {
    return null; // TODO remove user from users and database
  }

  private User loadUser(User user) {
    // TODO load user from database
    if (cachedUsers.indexOf(user) == -1) {
      cachedUsers.add(user);
    }
    return user;
  }

  private User saveUser(User user) {
    return null; // TODO save user to database
  }

  /**
   * This method will get the invite with the specified inviteId from the fromUser. If the fromUser doesn't have
   * the invite, it will create it and assign the involved users.
   *
   * @param fromUser The user creating the invite
   * @param toUser The user receiving the invite
   * @param inviteId The invite id to send, null if new invite
   * @return Returns the created invite
   */
  public synchronized Invite sendInvite(String fromUser, String inviteId, String toUser) throws FailedApiCallException, InvalidInputException,
          SQLException, ClassNotFoundException, IllegalAccessException, IOException, InstantiationException, UserNotFoundException {
    User fromUserObj = getUser(fromUser);
    User toUserObj = getUser(toUser);
    Invite invite = fromUserObj.getSendInvite(inviteId);
    if (invite == null) {
      invite = new Invite(fromUserObj);
    }
    fromUserObj.sendInvite(invite);
    toUserObj.receiveInvite(invite);
    setChanged();
    notifyObservers();
    if (database != null) {
      database.updateUserObject(fromUserObj);
      database.updateUserObject(toUserObj);
    }
    return invite;
  }

  public void shutdown() {
    setChanged();
    notifyObservers();
  }

  public User authenticate(String username, String email, String password) throws InvalidApiCallException, FailedApiCallException {
    try {
      User user;
      if (username != null) {
        user = getUser(username);
      } else if (email != null) {
        user = getUserByEmail(email);
      } else {
        // shouldn't happen
        throw new InvalidApiCallException("username or email must be provided to authenticate");
      }
      if (!user.checkPassword(password)) {
        throw new FailedApiCallException();
      }
      return user;
    } catch (InvalidApiCallException e) {
      throw e;
    } catch (Exception e) {
      throw new FailedApiCallException("credentials invalid");
    }
  }
  /**
   * Checks if email passed in by user is of the correct form
   * @param email
   * returns true if email is valid
   */
    public boolean isValidEmailAddress(String email){
      String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
      java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
      java.util.regex.Matcher m = p.matcher(email);
      return m.matches();
    }

  /**
   * Returns
   * @param currentUser
   * @param inviteId
   * @return returns gameId (which is really just inviteId)
   * @throws FailedApiCallException
   */
  public synchronized String acceptInvite(String currentUser, String inviteId) throws FailedApiCallException, FailedApiCallException, InvalidInputException,
  SQLException, ClassNotFoundException, IllegalAccessException, IOException, InstantiationException, UserNotFoundException {
    Invite invite;
    try {
      invite = getUser(currentUser).getReceivedInvite(inviteId);
      if (!invite.acceptInvite(currentUser, this, database)) {
        throw new FailedApiCallException("unable to accept invitation");
      }
    } catch (UserNotFoundException e) {
      throw new FailedApiCallException(e.getMessage());
    }
    if (database != null) {
      User u = getUser(currentUser);
      User toUser = getUser(invite.getToUser());
      database.updateUserObject(u);
      database.updateUserObject(toUser);
    }
    return inviteId;
  }

  public synchronized String rejectInvite(String currentUser, String inviteId) throws FailedApiCallException, FailedApiCallException, InvalidInputException,
  SQLException, ClassNotFoundException, IllegalAccessException, IOException, InstantiationException, UserNotFoundException {
    try {
      Invite invite = getUser(currentUser).getReceivedInvite(inviteId);
      if (!invite.rejectInvite(currentUser, this, database)) {
        throw new FailedApiCallException("unable to reject invitation");
      }
    } catch (UserNotFoundException e) {
      throw new FailedApiCallException(e.getMessage());
    }
    if (database != null) {
        User u = getUser(currentUser);
        database.updateUserObject(u);
    }
    return inviteId;
  }

  public synchronized String cancelInvite(String currentUser, String inviteId) throws FailedApiCallException, FailedApiCallException, InvalidInputException,
          SQLException, ClassNotFoundException, IllegalAccessException, IOException, InstantiationException, UserNotFoundException {
    try {
      //get invite
      Invite invite = getUser(currentUser).getSendInvite(inviteId);

      //find everyone who has this invite and reject that invite
      for(String user : invite.getToUsers()) {
        User person = getUser(user);
        person.rejectInvite(invite);
        database.updateUserObject(person);
      }
      //finally remove that invite from the current users list of pending invites
      getUser(currentUser).removeInviteFromPendingInvites(invite);
    } catch (UserNotFoundException e) {
      throw new FailedApiCallException(e.getMessage());
    }
    if (database != null) {
      User u = getUser(currentUser);
      database.updateUserObject(u);
    }
    return inviteId;
  }

  public void deleteUserEntryUsingUsername(String username) throws FailedApiCallException, InvalidInputException,
          SQLException, ClassNotFoundException, IllegalAccessException, IOException, InstantiationException {
    database.deleteUserEntryUsingUsername(username);
  }
}
