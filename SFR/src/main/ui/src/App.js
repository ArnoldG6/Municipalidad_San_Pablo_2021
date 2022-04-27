import React, { Component } from 'react';
//import Header from "./components/Header";
import {
  HashRouter as Router,
  Switch,
  Route,
  Redirect
} from "react-router-dom";
import './App.css';
import NavBar from './SharedComponents/NavBar/NavBar'
import Planes from './Pages/Planes/Planes';
import Plan from './Pages/Plan/Plan';
import Riesgos from './Pages/Riesgos/Riesgos';
import Riesgo from './Pages/Riesgo/Riesgo';
import Perfil from './Pages/Perfil/Perfil';
import Error from './Pages/Error/Error';
import Footer from "./SharedComponents/Footer/Footer"

class App extends Component {
  render() {
    document.title = 'SFR'
    return (
      <div className="page-container">
        <Router>
          <div className="content-wrap">
            <NavBar />
            <Switch classname="App">
              <Route exact path="/">
                <Redirect to="/planes"/>
              </Route>
              <Route path="/planes" component={Planes} />
              <Route path="/plan" component={Plan} />
              <Route path="/riesgos" component={Riesgos} />
              <Route path="/riesgo" component={Riesgo} />
              <Route path="/perfil" component={Perfil}>
              </Route>
              <Route render={() => <Error status={404} text={'Página no encontrada'} />} />
            </Switch>
          </div>
          <Footer />
        </Router>
      </div>
    );
  }
};
export default App;
