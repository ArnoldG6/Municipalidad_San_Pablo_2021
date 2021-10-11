import React, { Component } from 'react';
//import Header from "./components/Header";
import {
  HashRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import './App.css';
import Planes from './pages/planes/Planes';
import Plan from './pages/planes/Plan';
import NavBar from './Components/NavBar/NavBar'
import Error from './pages/Error/Error'

class App extends Component {
  render() {
    return (
      <Router>
        <NavBar/>
        <Switch>
          <Route exact path="/" component={Planes} />
          <Route path="/perfil" component={Plan} />
          <Route path="/mensajes">
            <h1>Mensajes</h1>
          </Route>
          <Route path="/reportes">
            <h1>Reportes</h1>
          </Route>
          <Route render={() => <Error status={404} text={'Página no encontrada'}/>}/>
        </Switch>
      </Router>
    );
  }
};
export default App;
