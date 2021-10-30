import React, { Component } from 'react';
import { Navbar, Nav, Image } from "react-bootstrap";
import logo from "../images/logoHeader.jpg"

class NavBar extends Component {
    render() {
        return (
            <div className="Header">
                <Navbar collapseOnSelect expand="lg" variant="dark">
                    <Navbar.Brand href="#home"><Image src={logo} fluid height={50} width={100} /></Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="#/">Home</Nav.Link>
                            <Nav.Link href="#/planes">Planes</Nav.Link>
                            <Nav.Link href="#/riesgos">Riesgos</Nav.Link>
                            <Nav.Link href="#/perfil">Perfil</Nav.Link>
                            <Nav.Link href="#/mensajes">Mensajes</Nav.Link>
                            <Nav.Link href="#/reportes">Reportes</Nav.Link>
                            <Nav.Link href="#/tempLogin">tempLogin</Nav.Link>
                        </Nav>
                        <Nav>
                            <Nav.Link href="#deets">Login</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </div>
        );
    }
};
export default NavBar;