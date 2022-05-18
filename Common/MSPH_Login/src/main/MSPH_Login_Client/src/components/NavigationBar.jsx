/*
NavigationBar Module for SFR project.
@author Esteban Arce
*/
import React, { Component } from 'react';
import { Navbar, Image, Nav } from "react-bootstrap";
import logoMuni from "./images/MSPH_LOGO.png"
import '../css/NavigationBar.css';
import Cookies from 'universal-cookie';
const cookies = new Cookies();

export default class NavigationBar extends Component {

    render() {
        let perm = false;
        if (typeof cookies.get('roles', { path: process.env.REACT_APP_AUTH }) !== 'undefined') {
            cookies.get('roles', { path: process.env.REACT_APP_AUTH }).map((rol) => {
                if (rol.description === "SUPER_ADMIN") {
                    perm = true;
                    return true;
                }
                return false;
            })
        }

        return (
            <div className="Header container-fluid">
                <Navbar collapseOnSelect expand="lg" variant="dark">
                    <Navbar.Brand href="#/menu"><Image src={logoMuni} fluid height={25} width={50} /></Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        {(typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined'
                            || cookies.get('username', { path: process.env.REACT_APP_AUTH }) === null) ?
                            null :
                            <Nav className='me-auto'>
                                <Nav.Link href="#/menu">Menú Principal</Nav.Link>
                            </Nav>
                        }
                        {(typeof cookies.get('username', { path: process.env.REACT_APP_AUTH }) === 'undefined'
                            || cookies.get('username', { path: process.env.REACT_APP_AUTH }) === null) ?
                            null :
                            <Nav>
                                {
                                    (perm) ?
                                        <Nav.Link href="#/users">Agregar Usuario</Nav.Link> :
                                        <div></div>
                                }
                                <Nav.Link href={"#/profile?id=" + cookies.get('username', { path: process.env.REACT_APP_AUTH })} >Mi Perfil</Nav.Link>
                                <Nav.Link href="#/logout">Cerrar sesión</Nav.Link>
                            </Nav>
                        }

                    </Navbar.Collapse>
                </Navbar>
            </div>
        );
    }
};