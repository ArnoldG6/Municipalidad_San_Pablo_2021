import React from 'react';
import {BrowserRouter, Switch, Route} from 'react-router-dom';
import Login from '../pages/Login';
import Menu from '../pages/Menu';
import Footer from '../components/Footer';
import NavigationBar from '../components/NavigationBar';
import '../css/Routes.css';
function Routes() {
  return (
    <div className="page-container">
    <BrowserRouter>
    <div className="content-wrap">
      <NavigationBar/>
      <Switch>
        <Route exact path="/auth" component={Login}/>
        <Route exact path="/menu" component={Menu}/>
      </Switch>
      </div>
      <Footer/>
    </BrowserRouter>
    </div>
  );
}

export default Routes;
