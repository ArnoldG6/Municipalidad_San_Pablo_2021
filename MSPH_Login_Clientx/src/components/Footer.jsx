import React from 'react';
import {Navbar} from "react-bootstrap";
import '../css/Footer.css';
export default function Footer() {
    return (
        <div>
            <Navbar className="Footer">       
            <p className="align-middle text_center">
                ©{new Date().getFullYear()}. Municipalidad de San Pablo de Heredia.
            </p>
            </Navbar>
        </div>
    );
}