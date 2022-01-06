import { apiRequest } from "../actions/api";
import { LOGIN } from "../actions/auth"

const SERVER_URL = `localhost:3000`;

export const appMiddleware = () => next => action => {
    next(action);
    switch (action.type) {
        case LOGIN: {
            next(
                apiRequest({
                    url: `${SERVER_URL}/login`,
                    method: "POST",
                    data: action.payload
                })
            )
            break
        }
        default:
            break;
    }
}

