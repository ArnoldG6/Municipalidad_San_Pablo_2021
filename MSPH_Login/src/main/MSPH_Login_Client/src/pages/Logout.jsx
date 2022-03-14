/*
Authentication Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from 'universal-cookie';
//import { Redirect } from 'react-router-dom/cjs/react-router-dom.min';
const cookies = new Cookies();

export default class Login extends React.Component {
    componentDidMount() {
        console.log("saliendo");
        cookies.remove("username", { path: process.env.REACT_APP_AUTH });
        cookies.remove("full_name", { path: process.env.REACT_APP_AUTH });
        cookies.remove("roles", { path: process.env.REACT_APP_AUTH });
        cookies.remove("token", { path: process.env.REACT_APP_AUTH });
        this.props.history.push("/auth");
    }
    render() {
        return (
            <h1>Redireccionando...</h1>
        );
    }
}