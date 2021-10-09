import React, {Component} from 'react';
//import Header from "./components/Header";
import {
    HashRouter as Router,
    Switch,
    Route
  } from "react-router-dom";
import logo from "./images/logoHeader.jpg"
import {Navbar, Nav, Image} from "react-bootstrap";
import './App.css';
import Planes from './pages/planes/Planes';

class App extends Component {
    render() {
        return (
          <Router>
            <Navbar collapseOnSelect expand="lg" className="Header" variant="dark">
              <Navbar.Brand href="#home"><Image src={logo} fluid height={50} width={100} /></Navbar.Brand>
              <Navbar.Toggle aria-controls="responsive-navbar-nav" />
              <Navbar.Collapse id="responsive-navbar-nav">
                <Nav className="me-auto">
                
                  <Nav.Link href="#/">Home</Nav.Link>
                  <Nav.Link href="#/perfil">Perfil</Nav.Link>
                  <Nav.Link href="#/mensajes">Mensajes</Nav.Link>
                  <Nav.Link href="#/reportes">Reportes</Nav.Link>
                </Nav>
                <Nav>
                  <Nav.Link href="#deets">Login</Nav.Link>
                </Nav>
              </Navbar.Collapse>
            </Navbar>
            <Switch>

              <Route path="/perfil">
                <h1>Perfil</h1> 
              </Route>
              <Route path="/mensajes">
                <h1>Mensajes</h1> 
              </Route>
              <Route path="/reportes">
                <h1>Reportes</h1>
              </Route>
              <Route path="/">
                <Planes/>
              </Route>
            </Switch>
          </Router>

                );
    }
};
export default App;
