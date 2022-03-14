/*
Routes Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React from 'react';
import { HashRouter, Switch, Route, Redirect } from 'react-router-dom';
import Login from '../pages/Login';
import Logout from '../pages/Logout';
import Menu from '../pages/Menu';
import Footer from '../components/Footer';
import NavigationBar from '../components/NavigationBar';
import '../css/Routes.css';

function Routes() {
    /*
  Routes() function defines the behaviour of the website rendering-response based on 
  client requests' path and other important information such as cookies present in the request.
  */
  document.title = 'MSPH Login'
  return (
    <div className="page-container">
      <HashRouter>
        <div className="content-wrap">
          <NavigationBar />
          <Switch>
            <Route exact path="/auth" component={Login} />
            <Route exact path="/logout" component={Logout} />
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
