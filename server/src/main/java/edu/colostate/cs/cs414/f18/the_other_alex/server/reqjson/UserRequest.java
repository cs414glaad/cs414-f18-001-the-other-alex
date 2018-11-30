package edu.colostate.cs.cs414.f18.the_other_alex.server.reqjson;

import edu.colostate.cs.cs414.f18.the_other_alex.model.Invite;
import edu.colostate.cs.cs414.f18.the_other_alex.model.User;
import edu.colostate.cs.cs414.f18.the_other_alex.model.controllers.ModelFacade;
import edu.colostate.cs.cs414.f18.the_other_alex.model.exceptions.InvalidInputException;
import edu.colostate.cs.cs414.f18.the_other_alex.server.RestRequest;
import edu.colostate.cs.cs414.f18.the_other_alex.server.exceptions.FailedApiCallException;
import edu.colostate.cs.cs414.f18.the_other_alex.server.exceptions.InvalidApiCallException;
import edu.colostate.cs.cs414.f18.the_other_alex.server.resjson.InviteList;
import edu.colostate.cs.cs414.f18.the_other_alex.server.resjson.ResponseData;
import edu.colostate.cs.cs414.f18.the_other_alex.server.resjson.UserList;
import spark.Request;
import spark.Response;

/**
 * Available game types are:
 *
 * - 'replno':
 *     requires inviteId
 *     rejects invite
 *     returns ResponseData
 * - 'repl':
 *     requires inviteId
 *     accepts invite
 *     returns ResponseData
 * - 'inv':
 *     requires toUser, optional: inviteId
 *     sends invite to toUser
 *     returns InviteList
 * - 'user':
 *     requires username, email, password
 *     creates new user
 *     returns UserList
 *
 * Json format:
 * {
 *   type: {type},
 *   email: {email},
 *   username: {username},
 *   password: {password},
 *   toUser: {toUsername},
 *   inviteId: {inviteId}
 * }
 */
public class UserRequest extends RestRequest {
  public String username;
  public String email;
  public String password;
  public String toUser; // username of new user
  public String inviteId;

  private String handleUserReplno(Request request, Response response, String currentUser, ModelFacade modelFacade) throws FailedApiCallException {
    modelFacade.rejectInvite(currentUser, inviteId);
    ResponseData responseData = new ResponseData(ResponseData.SUCCESS, "rejected invite");
    return responseData.toString();
  }

  private String handleUserRepl(Request request, Response response, String currentUser, ModelFacade modelFacade) throws FailedApiCallException {
    modelFacade.acceptInvite(currentUser, inviteId);
    ResponseData responseData = new ResponseData(ResponseData.SUCCESS, "accepted invite");
    return responseData.toString();
  }

  private String handleUserInv(Request request, Response response, String currentUser, ModelFacade modelFacade) throws FailedApiCallException {
    Invite invite = modelFacade.sendInvite(currentUser, toUser, inviteId);
    InviteList inviteList = new InviteList(invite);
    return inviteList.toString();
  }

  private String handleUserUser(Request request, Response response, String currentUser, ModelFacade modelFacade) throws FailedApiCallException, InvalidApiCallException {
    try {
      User user = modelFacade.createUser(
          username,
          email,
          password);
      UserList userList = new UserList(user);
      return userList.toString();
    } catch (InvalidInputException e) {
      throw new InvalidApiCallException(e.getMessage());
    }
  }

  @Override
  protected String handleRequest(Request request, Response response, String currentUser, ModelFacade modelFacade) throws FailedApiCallException, InvalidApiCallException {
    switch(type) {
      case "replno": // reject invite
        return handleUserReplno(request, response, currentUser, modelFacade);
      case "repl": // accept invite
        return handleUserRepl(request, response, currentUser, modelFacade);
      case "inv": // send invite
        return handleUserInv(request, response, currentUser, modelFacade);
      case "user": // create user
        return handleUserUser(request, response, currentUser, modelFacade);
    }
    return null;
  }

  @Override
  public void validate(String currentUser) throws InvalidApiCallException, FailedApiCallException {
    super.validate(currentUser);
    switch (type) {
      case "replno": // reject invite
      case "repl": // accept invite
        assertNotNull(inviteId, "inviteId");
        requireLoggedIn(currentUser);
        break;
      case "inv": // create invite
        assertNotNullOrEmpty(toUser, "toUser");
        requireLoggedIn(currentUser);
        break;
      case "user": // create user
        assertNotNullOrEmpty(username, "username");
        assertNotNullOrEmpty(email, "email");
        assertNotNullOrEmpty(password, "password");
        break;
      default:
        throw new InvalidApiCallException("invalid type for user");
    }
  }
}
