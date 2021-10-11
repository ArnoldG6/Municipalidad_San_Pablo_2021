import React, { Component } from 'react';
import { Row, Col, Card, ListGroup, Button, ButtonGroup } from "react-bootstrap";
import './Planes.css'

class Plan extends Component {
    render() {
        return (
            <main>
                <div className="container-fluid">
                    <Row className="Content">
                        <Col md={3} sm={3}>
                            <Card>
                                <Card.Body>
                                    <center className="Comentarios">Comentarios</center>
                                    <ListGroup variant="flush">
                                        <ListGroup.Item>Cras justo odio</ListGroup.Item>
                                        <ListGroup.Item>Dapibus ac facilisis in</ListGroup.Item>
                                        <ListGroup.Item>Vestibulum at eros</ListGroup.Item>
                                    </ListGroup>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col className="col">
                            <Row>
                                <Col>
                                    <Button className="boton" variant="primary">Agregar un riesgo</Button>{' '}
                                </Col>
                                <Col>
                                    <Button className="boton" variant="primary">Agregar involucradas</Button>{' '}
                                </Col>
                                <Col>
                                    <Button className="boton" variant="warning">Matriz de riesgos</Button>{' '}
                                </Col>
                                <Col>
                                    <Button className="boton" variant="success">Matriz de riesgos</Button>{' '}
                                </Col>
                                <Col>
                                    <Button className="boton" variant="light">Generar reporte</Button>{' '}
                                </Col>
                            </Row>
                        </Col>
                    </Row>
                </div>
            </main>
        );
    }
}
export default Plan;