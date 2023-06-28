import { USER_FETCHED, TOKEN_VALIDATED } from "./constrains";
import { axios } from "axios";

export const login = (values) => {
  return submit(values, "qualquer");
};
export const cadastro = (values) => {
  return submit(values, "qualquer/cadastro");
};

const submit = (values, url) => {
  return (dispatch) => {
    axios
      .post(url, values)
      .then((res) => {
        dispatch([{ type: USER_FETCHED, payload: res.data }]);
      })
      .catch((err) => {
        console.error(err);
      });
  };
};

export const logout = () => {
  return { type: TOKEN_VALIDATED, payload: false };
};

export const validateToken = (token) => {
  return (dispatch) => {
    if (token) {
      axios
        .post("qualquer", { token })
        .then((resp) => {
          dispatch({ type: TOKEN_VALIDATED, payload: false });
        })
        .catch((err) => {
          console.error(err);
        });
    } else {
      return { type: TOKEN_VALIDATED, payload: false };
    }
  };
};
