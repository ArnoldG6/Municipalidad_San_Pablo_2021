/*
NavigationBar Module for SFR project.
@author Esteban Arce
*/
import React, { Component } from 'react';
import { Navbar, Image } from "react-bootstrap";
import logoMuni from "./images/MSPH_LOGO.png"
import '../css/NavigationBar.css';
export default class NavigationBar extends Component {
    render() {
        return (
            <div className="Header  container-fluid">
                <Navbar collapseOnSelect expand="lg" variant="dark">
                    <Navbar.Brand href="/"><Image src={logoMuni} fluid height={25} width={50} /></Navbar.Brand>
                </Navbar>
            </div>
        );
    }
};
