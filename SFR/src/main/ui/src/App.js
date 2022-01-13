import React, { Component } from 'react';
//import Header from "./components/Header";
import {
  HashRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import './App.css';
import NavBar from './SharedComponents/NavBar/NavBar'
import Planes from './Pages/Planes/Planes';
import Plan from './Pages/Plan/Plan';
import Riesgos from './Pages/Riesgos/Riesgos';
import Error from './Pages/Error/Error';
import TempLogIn from './SharedComponents/TempLogIn';
import Footer from "./SharedComponents/Footer/Footer"
class App extends Component {
  render() {
    document.title = 'SFR'
    return (
      
      <Router>
        <NavBar/>
        <Switch>
          <Route exact path="/">
            <h1>Home</h1>
          </Route>
          <Route path="/planes" component={Planes} />
          <Route path="/plan" component={Plan} />
          <Route path="/riesgos" component={Riesgos} />
          <Route path="/perfil">
            <h1>Perfil</h1>
          </Route>
          <Route path="/mensajes">
            <h1>Mensajes</h1>
          </Route>
          <Route path="/reportes">
            <h1>Reportes</h1>
          </Route>
          <Route path="/tempLogin" component={TempLogIn} />
          <Route render={() => <Error status={404} text={'Página no encontrada'} />} />
        </Switch>
        <Footer/>
      </Router>
    );
  }
};
export default App;
