import { apiRequest } from "../actions/api";
import { LOGIN } from "../actions/auth";

const SERVER_URL = `http://192.168.1.105:8080`;

export const appMiddleware = () => next => action => {
    next(action);
    switch (action.type) {
        case LOGIN: {
            next(
                apiRequest({
                    url: `${SERVER_URL}/login`,
                    method: "POST",
                    data: {
                        email: action.payload.email,
                        password: action.payload.password
                    }
                })
            )
            break;
        }
        default:
            break;
    }
}

