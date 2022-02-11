import React, { Component } from 'react';
import { Navbar, Nav, Image } from "react-bootstrap";
import logoMuni from "../images/logoHeader.png"

class NavBar extends Component {
    render() {
        return (
            <div className="Header container-fluid">
                <Navbar collapseOnSelect expand="lg" variant="dark">
                    <Navbar.Brand href="#/planes"><Image src={logoMuni} fluid height={25} width={50} /></Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href="#/planes">Planes</Nav.Link>
                            <Nav.Link href="#/riesgos">Riesgos</Nav.Link>
                            <Nav.Link href="#/tempLogin">tempLogin</Nav.Link>
                        </Nav>
                        <Nav>
                            <Nav.Link href="#/perfil">Perfil</Nav.Link>
                            <Nav.Link href="#/">Salir del SFR</Nav.Link>
                            <Nav.Link href="#/">Logout</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </div>
        );
    }
};
export default NavBar;