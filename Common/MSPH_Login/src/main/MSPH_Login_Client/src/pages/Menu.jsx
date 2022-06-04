/*
Menu Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { Container, Button, Row, Card } from 'react-bootstrap';
import axios from 'axios';
import '../css/Login.css';
import NavigationBar from '../components/NavigationBar';
const cookies = new Cookies();

export default class Menu extends Component {
    /*
    Menu class corresponds to the component that is shown to the user
    if the auth-request was accepted by the server.
    */
    constructor(props) {
        super(props);
        this.redirectToSIGCD = this.redirectToSIGCD.bind(this);
        this.redirectToSIVAC = this.redirectToSIVAC.bind(this);
        this.redirectToSIGEP = this.redirectToSIGEP.bind(this);
    }
    redirectToSIGCD() {
        var options = {
            url: process.env.REACT_APP_SIGCD_REDIRECTION_PATH,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
            data: {
                'username': parseInt(cookies.get('username', { path: process.env.REACT_APP_AUTH }))
            }
        }
        axios(options).then(response => {
            document.location = process.env.REACT_APP_SIGCD_PATH;
        }).catch(function (error) {
            if (error.response) {
                if (error.response.status === 403)
                    alert("Acceso denegado");
                else
                    alert("Error al intentar redirigir al SIGCD");
            } else
                alert("Error al intentar redirigir al SIGCD");
        });
    }
    redirectToSIGEP() {
        console.log("Redirigiendo a SIGEP");
        /*
        var options = {
            url: ,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
            data: {
                'username': parseInt(cookies.get('username', { path: process.env.REACT_APP_AUTH }))
            }
        }
        axios(options).then(response => {
            document.location = ;
        }).catch(function (error) {
            if (error.response) {
                if (error.response.status === 403)
                    alert("Acceso denegado");
                else
                    alert("Error al intentar redirigir al SIGCD");
            } else
                alert("Error al intentar redirigir al SIGCD");
        });
        */
    }
    redirectToSIVAC() {
        var options = {
            url: process.env.REACT_APP_SIVAC_REDIRECTION_PATH,
            method: 'POST',
            header: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': '*',
            },
            data: {
                'username': parseInt(cookies.get('username', { path: process.env.REACT_APP_AUTH }))
            }
        }
        axios(options).then(response => {
            //document.location = process.env.REACT_APP_SIGCD_PATH;
            document.location = "http://localhost:8081/home/moduloSivac/MainScreen.jsp";
        }).catch(function (error) {
            alert("Error al intentar redirigir al SIVAC");
        });

        // document.location = "http://localhost:8081/home/moduloSivac/MainScreen.jsp"+
        //"?username="+cookies.get('username', { path: process.env.REACT_APP_AUTH });
    }

    componentDidMount() {
        if (!(cookies.get('username', { path: process.env.REACT_APP_AUTH })
            && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
            && cookies.get('full_name', { path: process.env.REACT_APP_AUTH })))
            this.props.history.push('/auth');
    }
    render() {
        return (
            <div>
                <NavigationBar />
                <div className="p-3 ">
                    <Row>
                        <Container className='vertical-center'> <h1> Sistema de Identificación de la Municipalidad de San Pablo </h1></Container>
                    </Row>
                    <Row className='vertical-center'>

                        <Card className='menuCard' onClick={() => { document.location = process.env.REACT_APP_SFR_PATH; }}>
                            <Card.Title>Sistema de Factibilidad de Riesgos</Card.Title>
                            <Card.Header variant="top" className='vertical-center'><i className="bi bi-table menuIcon"></i></Card.Header>
                            <Button className="btnSFR"  >
                                SFR
                            </Button>
                        </Card>

                        <Card className='menuCard' onClick={() => { this.redirectToSIGCD(); }}>
                            <Card.Title>Sistema de Gestión y Control de Donaciones</Card.Title>
                            <Card.Header variant="top" className='vertical-center'><i className="bi bi-pencil menuIcon"></i> </Card.Header>
                            <Button className="btnSFR" >
                                SIGCD
                            </Button>
                        </Card>

                        <Card className='menuCard' onClick={() => { this.redirectToSIVAC(); }}>
                            <Card.Title>Sistemas de Vacaciones Permisos e Incapacidades</Card.Title>
                            <Card.Header variant="top" className='vertical-center'><i className="bi bi-pencil menuIcon"></i> </Card.Header>
                            <Button className="btnSFR" >
                                SIVAC
                            </Button>
                        </Card>


                        <Card className='menuCard' onClick={() => { this.redirectToSIGEP(); }}>
                            <Card.Title>Sistemas de Vacaciones Permisos e Incapacidades</Card.Title>
                            <Card.Header variant="top" className='vertical-center'><i className="bi bi-pencil menuIcon"></i> </Card.Header>
                            <Button className="btnSFR" >
                                SIGEP
                            </Button>
                        </Card>

                    </Row>

                </div>
            </div>

        );
    }
}
