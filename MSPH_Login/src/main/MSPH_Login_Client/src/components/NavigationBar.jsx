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
        return (
            <div className="Header  container-fluid">
                <Navbar collapseOnSelect expand="lg" variant="dark">
                    <Navbar.Brand href="/"><Image src={logoMuni} fluid height={25} width={50} /></Navbar.Brand>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav">
                        <Nav className="me-auto">
                            <Nav.Link href={"#/profile?id=" + cookies.get('username', { path: process.env.REACT_APP_AUTH })} >Perfil</Nav.Link>
                        </Nav>
                    </Navbar.Collapse>
                </Navbar>
            </div>
        );
    }
};
