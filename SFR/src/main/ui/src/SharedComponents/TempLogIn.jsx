import React, { Component } from 'react';
import { Button } from "react-bootstrap";
import { ToastContainer, toast } from 'react-toastify';
import axios from 'axios';


class TempLogIn extends Component {
    constructor(props) {
        super(props);
        this.state = {

        };
        this.buttonLogin = this.buttonLogin.bind(this);
    }

    buttonLogin(type) {

        let options = {
            url: "http://localhost:8080/SFR/API/RetrievePlan",
            method: "POST",
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: {
                'type': type
            }
        }
        axios(options)
            .then(response => {
                toast.success("Usuario cambiado!", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 10000
                });
            }).catch(error => {
                toast.error("Error al cambiar el tipo de usuario", {
                    position: toast.POSITION.TOP_RIGHT,
                    pauseOnHover: true,
                    theme: 'colored',
                    autoClose: 10000
                });
            });
    }

    render() {
        return (
            <div>
                <Button variant="primary" onClick={() => this.buttonLogin(1)}>SuperAdmin</Button><br /><br />
                <Button variant="primary" onClick={() => this.buttonLogin(2)}>Admin</Button><br /><br />
                <Button variant="primary" onClick={() => this.buttonLogin(3)}>Usuario #1</Button><br /><br />
                <Button variant="primary" onClick={() => this.buttonLogin(4)}>Usuario #2</Button><br /><br />
                <ToastContainer /><br /><br />
            </div>
        );
    }
}
export default TempLogIn;