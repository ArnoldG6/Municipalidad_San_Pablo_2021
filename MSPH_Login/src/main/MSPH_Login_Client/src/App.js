import React, { Component } from 'react';
import { HashRouter, Switch, Route, Redirect } from 'react-router-dom';
import Login from './pages/Login';
import Logout from './pages/Logout';
import Menu from './pages/Menu';
import Perfil from './pages/Perfil/Perfil';
import Footer from './components/Footer';
import './App.css';

class App extends Component {
    render() {
        document.title = 'MSPH Login'
        return (
            <div className="page-container" >
                <HashRouter>
                    <div className="content-wrap">
                        <Switch>
                            <Route exact path="/auth" component={Login} />
                            <Route exact path="/logout" component={Logout} />
                            <Route path="/menu" component={Menu} />
                            <Route path="/profile" component={Perfil} />
                            <Route path="/">
                                <Redirect to="/auth" />
                            </Route>
                        </Switch>
                    </div>
                    <Footer />
                </HashRouter>
            </div>
        );
    }
}

export default App;
