import React from 'react';
import { HashRouter, Switch, Route, Redirect } from 'react-router-dom';
import Login from '../pages/Login';
import Menu from '../pages/Menu';
import Footer from '../components/Footer';
import NavigationBar from '../components/NavigationBar';
import '../css/Routes.css';

function Routes() {
  document.title = 'MSPH Login'
  return (
    <div className="page-container">
      <HashRouter>
        <div className="content-wrap">
          <NavigationBar />
          <Switch>
            <Route exact path="/auth" component={Login} />
            <Route path="/menu" component={Menu} />
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

export default Routes;
