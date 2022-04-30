/*
Menu Module for SFR project.
@author Arnoldo J. González Quesada.
Github user: "ArnoldG6".
Contact me via: "arnoldgq612@gmail.com".
*/
import React, { Component } from 'react';
import Cookies from 'universal-cookie';
import { Container, Button, Table, Col, Row } from 'react-bootstrap';
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
            && cookies.get('token', { path: process.env.REACT_APP_AUTH })
            && cookies.get('full_name', { path: process.env.REACT_APP_AUTH })))
            this.props.history.push('/login');
    }

    render() {
        return (
            <div className = "vertical-center">
                <Container className="w-auto text-center mx-auto p-3 mt-2 container">
                    <Table>
                        <Row>
                            <Col>
                                <Button className="btnSFR" onClick={() => { document.location = process.env.REACT_APP_SFR_CLIENT_PATH; }}>
                                    {/*<Image src={SFRLogo} height={100} width={250} className=' hover-shadow' />*/}
                                    SFR
                                </Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Button className="btnSFR" onClick={() => { document.location = process.env.REACT_APP_SIGCD_PATH; }}>
                                    {/*<Image src={SFRLogo} height={100} width={250} className=' hover-shadow' />*/}
                                    SIGCD
                                </Button>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Button className="btnSFR">
                                {/*<Image src={exitDoor} height={100} width={250} className=' hover-shadow' onClick = {() => {this.props.history.push("/logout");}}/>
                                */}
                                Salir
                                </Button>
                            </Col>
                        </Row>
                    </Table>
                </Container>

            </div>
        );
    }
}
