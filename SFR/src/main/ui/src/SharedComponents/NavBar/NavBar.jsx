import React, { Component } from 'react';
import { Navbar, Nav, Image } from "react-bootstrap";
import logoMuni from "../images/logoHeader.png"
import Cookies from 'universal-cookie';
const cookies = new Cookies();

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
                        </Nav>
                        <Nav>
                            <Nav.Link href={process.env.REACT_APP_SIMSP_PROFILE + "?id=" + cookies.get('username', { path: process.env.REACT_APP_AUTH })}>Perfil</Nav.Link>
                            <Nav.Link href={process.env.REACT_APP_SIMSP_MENU}>Menú Principal</Nav.Link>
                            <Nav.Link href={process.env.REACT_APP_SIMSP_LOGOUT}>Cerrar sesión</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </div>
        );
    }
};
export default NavBar;