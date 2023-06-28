import { TOKEN_VALIDATED, USER_FETCHED } from "./constrains";

const userKey = "_sppg_user";
const INITIAL_USER = {
  user: JSON.parse(localStorage.getItem(userKey)),
  validToken: false,
};

export const authReducer = (state = INITIAL_USER, action) => {
  switch (action.type) {
    case TOKEN_VALIDATED:
      if (action.payload) {
        return { ...state, validToken: true };
      } else {
        localStorage.removeItem(userKey);
        return { ...state, validToken: false, user: null };
      }

    case USER_FETCHED:
      localStorage.setItem(userKey, JSON.stringify(action.payload));
      return { ...state, user: action.payload, validToken: true };

    default:
      return state;
  }
};
