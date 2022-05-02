/*
Menu Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { Container, Button, Row, Card } from 'react-bootstrap';

import '../css/Login.css';
const cookies = new Cookies();

export default class Menu extends Component {
    /*
    Menu class corresponds to the component that is shown to the user
    if the auth-request was accepted by the server.
    */
    componentDidMount() {
        if (!(cookies.get('username', { path: process.env.REACT_APP_AUTH })
            && cookies.get('roles', { path: process.env.REACT_APP_AUTH })
            && cookies.get('full_name', { path: process.env.REACT_APP_AUTH })))
            this.props.history.push('/auth');
    }

    render() {
        return (

            <div className="p-3 ">
                <Row>
                    <Container className='vertical-center'> <h1> Sistema de Identificación de la Municipalidad de San Pablo </h1></Container>
                </Row>
                <Row className='vertical-center'>

                    <Card className='menuCard' onClick={() => { document.location = process.env.REACT_APP_SFR_CLIENT_PATH; }}>
                        <Card.Header variant="top" className='vertical-center'><i class="bi bi-table menuIcon"></i></Card.Header>
                        <Button className="btnSFR"  >
                            SFR
                        </Button>
                    </Card>

                    <Card className='menuCard' onClick={() => { document.location = process.env.REACT_APP_SIGCD_PATH; }}>
                        <Card.Header variant="top" className='vertical-center'><i class="bi bi-pencil menuIcon"></i> </Card.Header>
                        <Button className="btnSFR" >
                            SIGCD
                        </Button>
                    </Card>

                </Row>

            </div>


        );
    }
}
