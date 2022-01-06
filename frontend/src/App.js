import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { applyMiddleware } from 'redux';

import reducer from './reducer';
import { createStore } from 'redux';


import AuthRoute from './components/AuthRoute';

import HomePage from './pages/HomePage';
//import LoginPage from './pages/Login';

import { appMiddleware } from "./middlewares/app";
import { apiMiddleware } from "./middlewares/core";
import MyAccount from './pages/MyAccount';
import { AccountBox } from './components';

const createStoreWithMiddleware = applyMiddleware(
    appMiddleware,
    apiMiddleware
)(createStore);

const store = createStoreWithMiddleware(reducer);

export default function App() {
    return (
        <Provider store={store}>
            <Router>
                <div className='container'>
                    <Switch>
                        <AuthRoute path='/login' render={AccountBox} type='guest' />
                        <AuthRoute path='/home' isAuthUser={true} type="private" >
                            <div>you're in home rn</div>
                        </AuthRoute>
                        <AuthRoute path='/my-account' type='private'>
                            <MyAccount />
                        </AuthRoute>
                        <AuthRoute path='/' type='index' />
                    </Switch>
                </div>
            </Router>
        </Provider>
    );
}